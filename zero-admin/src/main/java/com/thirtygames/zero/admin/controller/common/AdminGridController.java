package com.thirtygames.zero.admin.controller.common;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.thirtygames.zero.admin.form.CsvUploadForm;
import com.thirtygames.zero.admin.form.FileuploadForm;
import com.thirtygames.zero.admin.model.GridJsonResult;
import com.thirtygames.zero.common.etc.datasource.DataSource;
import com.thirtygames.zero.common.etc.document.ColumnDescriptor;
import com.thirtygames.zero.common.etc.document.DisplayColumnDescriptorFactory;
import com.thirtygames.zero.common.etc.document.model.WorkbookModel;
import com.thirtygames.zero.common.etc.document.result.RowConverter;
import com.thirtygames.zero.common.etc.document.result.WorksheetResultConvertingHandler;
import com.thirtygames.zero.common.etc.document.writer.SimpleCsvSheetWriter;
import com.thirtygames.zero.common.etc.document.writer.WorksheetFileWriter;
import com.thirtygames.zero.common.etc.exception.RestException;
import com.thirtygames.zero.common.etc.spring.view.GenericCsvView;
import com.thirtygames.zero.common.etc.util.AjaxResponseUtil;
import com.thirtygames.zero.common.etc.util.FileUploadUtil;
import com.thirtygames.zero.common.etc.util.PageUtil;
import com.thirtygames.zero.common.etc.validator.BaseValidator;
import com.thirtygames.zero.common.generic.GenericService;
import com.thirtygames.zero.common.model.columns.DisplayColumnId;
import com.thirtygames.zero.common.model.columns.DisplayColumnRepository.DisplayArea;
import com.thirtygames.zero.common.model.params.grid.GridFilter;
import com.thirtygames.zero.common.model.security.AdminUser;
import com.thirtygames.zero.common.service.datasource.DataSourceService;

@Slf4j
public abstract class AdminGridController<T extends GridFilter, K, S extends GenericService<T, K>, V extends BaseValidator> extends AdminGenericController<T, K, S, V> {
	private static final int MAX_UPLOAD_TEMP_FILE_SIZE = 3145728;

	@Autowired
	private DisplayColumnDescriptorFactory columnDescriptorFactory;
	
	@Autowired
	private View genericXlsxSpreadSheetView;
	
	@Autowired
	private View genericCsvView;
	
	@Autowired
	private FileUploadUtil fileUploadUtil;
	
	@Autowired
	protected DataSourceService dataSourceService;
	
	@Value("${csv.upload.path}")
	private String uploadedFilePath;
	
	@Value("${csv.upload.fileprefix}")
	private String uploadedTempFilePrefix;
	
	public static final DisplayColumnId[] EMPTY_COLUMN_IDS = new DisplayColumnId[] {};
	
	// 엑셀 다운로드를 위한 엑셀파일명, 시트명, displayColumnId, rowConverter 준비
	protected DisplayColumnId[] displayColumnIds;
	protected DisplayColumnId[] hiddenColumnIds;
	protected RowConverter<Map<DisplayColumnId, Object>, T> rowConverter;
	protected String sheetName;
	protected String resultFileName;
	protected CsvUploadForm<T> csvUploadForm;
	
	protected AdminGridController(String sheetName, String resultFileName, DisplayColumnId[] displayColumnIds, DisplayColumnId[] hiddenColumnIds) {
		this.sheetName = sheetName;
		this.resultFileName = resultFileName;
		this.displayColumnIds = displayColumnIds;
		this.hiddenColumnIds = hiddenColumnIds;
	}
	
	protected AdminGridController(String sheetName, String resultFileName, DisplayColumnId[] displayColumnIds, DisplayColumnId[] hiddenColumnIds, CsvUploadForm<T> csvUploadForm) {
		this.sheetName = sheetName;
		this.resultFileName = resultFileName;
		this.displayColumnIds = displayColumnIds;
		this.hiddenColumnIds = hiddenColumnIds;
		this.csvUploadForm = csvUploadForm;
	}
	
	protected void setRowConverter(RowConverter<Map<DisplayColumnId, Object>, T> rowConverter) {
		this.rowConverter = rowConverter;
	}
	
	public AdminGridController() {
	}

	protected ModelMap postGrid(ModelMap model) {
		return model;
	};
	
	@RequestMapping(value = "/grid", method = { RequestMethod.GET })
	public ModelAndView grid(
			@RequestParam(required = false, value = "page", defaultValue = "1") int page,
			@RequestParam(required = false, value = "rows", defaultValue = "20") int rows,
			HttpServletRequest request, ModelMap model)  {
		model = postGrid(model);
		return new ModelAndView(super.getViewName());
	}
	
	protected GridJsonResult<T> postGridData(GridJsonResult<T> result, ModelMap model) {
		return result;
	};
	
	@RequestMapping(value = "/grid", method = { RequestMethod.POST })
	public @ResponseBody
	GridJsonResult<T> gridData(
			@RequestParam(required = false, value = "page", defaultValue = "1") int page,
			@RequestParam(required = false, value = "rows", defaultValue = "20") int rows,
			@RequestParam(required = false, value = "sidx") String sidx,
		    @RequestParam(required = false, value = "sord") String sord,		
		    @RequestParam(required = false, value = "filters") String filters,		
		    @RequestParam(required = false, value = "_search") Boolean search,	
			@ModelAttribute T entity, 
			BindingResult bindingResult, 
			SessionStatus status, 
			ModelMap model)
			throws RestException {
		
		if (search != null && search) {
			GridFilter gridFilter = this.getJsonObjectMapper(filters);
			entity.setGroupOp(gridFilter.getGroupOp());
			entity.setRules(gridFilter.getRules());
		}
		
		validator.validate(model, bindingResult);
		validator.processErrors(bindingResult);

		int start = (page - 1) * rows;
		List<T> entityList = service.search(start, rows, sidx, sord, entity);
		log.debug("entityList::" + entityList);
		
		int count = service.size(entity);
		int total = PageUtil.getPageCount(count, rows);
		
		GridJsonResult<T> result = new GridJsonResult<T>();	
		result.setRows(entityList);
		result.setPage(page);
		result.setTotal(total);
		result.setRecords(rows);
		
		result = postGridData(result, model);
		return result;
	}	
	
	protected GridFilter getJsonObjectMapper(String jsonString) {
	 	if (jsonString == null) {
			return null;	 		
    	}
	 	
    	ObjectMapper mapper = new ObjectMapper();
    	try {
			return mapper.readValue(jsonString, GridFilter.class);
    	} catch (Exception e) {
			throw new RuntimeException (e);
		}
	}
	
	
	@RequestMapping(value = "/grid/edit", method = { RequestMethod.POST })
	public @ResponseBody
	ModelAndView gridEdit(
			@RequestParam(required = false, value = "id", defaultValue = "1") K id,
			@RequestParam(required = true, value="oper") String oper,
			@ModelAttribute T entity, 
			SessionStatus status,
			BindingResult bindingResult,
			AdminUser adminUser,
			ModelMap model)
			throws RestException {
		
		log.debug("################### AdminController gridEdit!!" + entity.toString());
		
		if ("add".equals(oper) || "edit".equals(oper)) {
			validator.validate(model, bindingResult);
			validator.processErrors(bindingResult);
		}
		
		try {
			Field field = entity.getClass().getDeclaredField("modId");
			
			if (field != null) {
				field.setAccessible(true);
				field.set(entity, adminUser.getUserId() == null ? "SYSTEM" : adminUser.getUserId());
			}
		} catch (Exception e) {
		}
		
		try {
			if ("add".equals(oper)) {
				service.save(entity);
			} else if ("edit".equals(oper)) {
				service.update(entity);
			}  else if ("del".equals(oper)) {
				boolean isMultiRow = id.toString().contains(",");  
				if (isMultiRow) {
					List<K> idList = (List<K>) Splitter.on(',').trimResults().omitEmptyStrings().splitToList(id.toString());
					service.multiDelete(idList);
				} else {
					service.delete(id);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView(AjaxResponseUtil.jsonResultError("ERROR", "알 수 없는 오류가 발생했습니다.", null, model));
		}
		
		return new ModelAndView(AjaxResponseUtil.jsonResult(Maps.newHashMap(), model));
	}	
	
	@RequestMapping(value = "/grid/{shardIndex}", method = { RequestMethod.GET })
	public ModelAndView grid(
			@RequestParam(required = false, value = "page", defaultValue = "1") int page,
			@RequestParam(required = false, value = "rows", defaultValue = "20") int rows,
			@PathVariable Integer shardIndex,
			HttpServletRequest request, ModelMap model)  {
		dataSourceService.switchDataSource(DataSource.findByCode(shardIndex));
		model.addAttribute("shardIndex", shardIndex);
		return grid(page, rows, request, model);
	}
	
	@RequestMapping(value = "/grid/{shardIndex}", method = { RequestMethod.POST })
	public @ResponseBody
	GridJsonResult<T> gridData(
			@RequestParam(required = false, value = "page", defaultValue = "1") int page,
			@RequestParam(required = false, value = "rows", defaultValue = "20") int rows,
			@RequestParam(required = false, value = "sidx") String sidx,
		    @RequestParam(required = false, value = "sord") String sord,		
		    @RequestParam(required = false, value = "filters") String filters,		
		    @RequestParam(required = false, value = "_search") Boolean search,
		    @PathVariable Integer shardIndex,
			@ModelAttribute T entity, 
			BindingResult bindingResult, 
			SessionStatus status, 
			ModelMap model)
			throws RestException {
		dataSourceService.switchDataSource(DataSource.findByCode(shardIndex));
		return gridData(page, rows, sidx, sord, filters, search, entity, bindingResult, status, model);
	}
	
	@RequestMapping(value = "/grid/edit/{shardIndex}", method = { RequestMethod.POST })
	public @ResponseBody
	ModelAndView gridEdit(
			@RequestParam(required = false, value = "id", defaultValue = "1") K id,
			@RequestParam(required = true, value="oper") String oper,
			@PathVariable Integer shardIndex,
			@ModelAttribute T entity, 
			BindingResult bindingResult, 
			AdminUser adminUser,
			SessionStatus status, 
			ModelMap model)
			throws RestException {
		dataSourceService.switchDataSource(DataSource.findByCode(shardIndex));
		return gridEdit(id, oper, entity, status, bindingResult, adminUser, model);
	}
	
	@RequestMapping(value = "/excel", method = { RequestMethod.GET })
	public ModelAndView downloadExcel(
		    @RequestParam(required = false, value = "filters") String filters,		
		    @RequestParam(required = false, value = "_search") Boolean search,	
			@ModelAttribute T entity, 
			BindingResult bindingResult, 
			SessionStatus status, 
			ModelMap model)
			throws RestException {
		
		if (search != null && search) {
			GridFilter gridFilter = this.getJsonObjectMapper(filters);
			entity.setGroupOp(gridFilter.getGroupOp());
			entity.setRules(gridFilter.getRules());
		}
		
		validator.validate(model, bindingResult);
		validator.processErrors(bindingResult);
		WorkbookModel workbookModel = new WorkbookModel(resultFileName);
		
		List<ColumnDescriptor> descriptors = columnDescriptorFactory.create(DisplayArea.ADMIN, Lists.newArrayList(displayColumnIds), Sets.newHashSet(hiddenColumnIds));
		WorksheetFileWriter worksheetFileWriter = new WorksheetFileWriter(workbookModel, descriptors, sheetName);
		service.excel(entity, new WorksheetResultConvertingHandler<Map<DisplayColumnId, Object>, T>(worksheetFileWriter, rowConverter));
		
		return new ModelAndView(genericXlsxSpreadSheetView, model.addAttribute(workbookModel));
	}	
	
	@RequestMapping(value = "/csv", method = { RequestMethod.GET })
	public ModelAndView downloadCsv(
		    @RequestParam(required = false, value = "filters") String filters,		
		    @RequestParam(required = false, value = "_search") Boolean search,	
			@ModelAttribute T entity, 
			BindingResult bindingResult, 
			SessionStatus status, 
			ModelMap model)
			throws RestException {
		
		if (search != null && search) {
			GridFilter gridFilter = this.getJsonObjectMapper(filters);
			entity.setGroupOp(gridFilter.getGroupOp());
			entity.setRules(gridFilter.getRules());
		}
		
		validator.validate(model, bindingResult);
		validator.processErrors(bindingResult);
		
		List<ColumnDescriptor> descriptors = columnDescriptorFactory.create(DisplayArea.ADMIN, Lists.newArrayList(displayColumnIds), Sets.newHashSet(hiddenColumnIds));
		SimpleCsvSheetWriter simpleCsvSheetWriter = new SimpleCsvSheetWriter(descriptors);
		service.excel(entity, new WorksheetResultConvertingHandler<Map<DisplayColumnId, Object>, T>(simpleCsvSheetWriter, rowConverter));

		ByteArrayResource resultResource = new ByteArrayResource(simpleCsvSheetWriter.getCsvContent().getBytes());
		model.addAttribute(GenericCsvView.RESOURCE_KEY, resultResource);
		model.addAttribute(GenericCsvView.FILE_NAME_KEY, resultFileName + ".csv");
		return new ModelAndView(genericCsvView, model);
	}	

	@RequestMapping(value = "/excel/{shardIndex}", method = { RequestMethod.GET })
	public ModelAndView excelData(
		    @RequestParam(required = false, value = "filters") String filters,		
		    @RequestParam(required = false, value = "_search") Boolean search,
			@ModelAttribute T entity, 
			@PathVariable Integer shardIndex,
			BindingResult bindingResult, 
			SessionStatus status, 
			ModelMap model)
			throws RestException {
		dataSourceService.switchDataSource(DataSource.findByCode(shardIndex));
		return downloadExcel(filters, search, entity, bindingResult, status, model);
	}
	
	@RequestMapping(value = "/csv/{shardIndex}", method = { RequestMethod.GET })
	public ModelAndView csvData(
		    @RequestParam(required = false, value = "filters") String filters,		
		    @RequestParam(required = false, value = "_search") Boolean search,
			@ModelAttribute T entity, 
			@PathVariable Integer shardIndex,
			BindingResult bindingResult, 
			SessionStatus status, 
			ModelMap model)
			throws RestException {
		dataSourceService.switchDataSource(DataSource.findByCode(shardIndex));
		return downloadCsv(filters, search, entity, bindingResult, status, model);
	}
	
	@RequestMapping(value = "/upload", method = { RequestMethod.POST })
	public @ResponseBody Map<String, String> uploadCsv(FileuploadForm fileUploadForm, ModelMap model) {
		Map<String, String> result = Maps.newHashMap();
		result.put("success", "N");
		
		if (csvUploadForm == null) {
			result.put("errorMessage", "임포트가 가능한 메뉴가 아닙니다.");
			return result;
		}
		
		String fileName = null;
		String errorMessage = null;
		
		if (fileUploadForm.getUploadedFile() != null) {
			if (fileUploadForm.getUploadedFile().getSize() <= MAX_UPLOAD_TEMP_FILE_SIZE) {
				try {
					fileName = fileUploadUtil.getUploadFileName(fileUploadForm.getUploadedFile(), uploadedFilePath, uploadedTempFilePrefix);
				} catch (IOException e) {
					errorMessage = "파일업로드 중 문제가 발생하였습니다. 다시 시도해 주시기 바랍니다.";
				}
			} else {
				errorMessage = "파일 업로드시 파일 용량은 최대 3MB 이하로 제한됩니다. 3MB 이하로 업로드 해주세요.";
			}
		} else {
			errorMessage = "업로드할 파일이 존재하지 않습니다.";
		}
		
		List<T> rows = Lists.newArrayList();
		int successCount = 0;
		if (StringUtils.isEmpty(errorMessage)) {
			try {
				csvUploadForm.initialize(uploadedFilePath + File.separatorChar + fileName);
			} catch (RuntimeException e) {
				result.put("errorMessage", e.getMessage());
				return result;
			} catch (Exception e) {
				result.put("errorMessage", "CSV 파일 읽기에 실패했습니다.");
				return result;
			}
			
			rows = csvUploadForm.readForm();
			try {
				successCount = service.bulkSave(rows);
			} catch (Exception e) {
				result.put("errorMessage", "Import가 실패했습니다. 다시 시도해주세요.");
				return result;
			}
		}
		
		if (StringUtils.isEmpty(errorMessage)) {
			result.put("success", "Y");
			result.put("message", String.format("총 %d 건 중 %d 건 완료됨.", rows.size(), successCount));
		} else {
			result.put("errorMessage", errorMessage);
		}
		return result;
	}
	
	@RequestMapping(value = "/upload/{shardIndex}", method = { RequestMethod.POST })
	public @ResponseBody Map<String, String> uploadCsv(@PathVariable Integer shardIndex,FileuploadForm fileUploadForm, ModelMap model) {
		dataSourceService.switchDataSource(DataSource.findByCode(shardIndex));
		return uploadCsv(fileUploadForm, model);
	}
}
