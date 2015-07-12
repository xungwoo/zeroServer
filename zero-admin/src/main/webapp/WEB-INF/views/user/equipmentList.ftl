<@layout.extends name="/layout/base.ftl">
	<@layout.put block="page-css">
		<link rel="stylesheet" href="${rc.contextPath}/css/jquery-ui-1.10.3.full.min.css" />
		<link rel="stylesheet" href="${rc.contextPath}/css/datepicker.css" />
		<link rel="stylesheet" href="${rc.contextPath}/css/ui.jqgrid.css" />	
	</@layout.put>
	<@layout.put block="content">

					<div class="page-content">
						<div class="page-header">
							<h1>
								Equipment Management
								<small>
									<i class="icon-double-angle-right"></i>
										장비정보를 관리합니다.
								</small>
							</h1>
						</div><!-- /.page-header -->

						DB:&nbsp;&nbsp; 
						<select id="shardIndex" name="shardIndex">
							<#list DataSources as dataSource>
								<option value="${dataSource.code}" <#if shardIndex == dataSource.code>selected</#if>>${dataSource}</option>
							</#list>
						</select><br/><br/>
						
						<div class="row">
							<div class="col-xs-12">
								<!-- PAGE CONTENT BEGINS -->

								<table id="grid-table"></table>

								<div id="grid-pager"></div>

								<!-- PAGE CONTENT ENDS -->
							</div><!-- /.col -->
						</div><!-- /.row -->
					</div><!-- /.page-content -->
	</@layout.put>

	<@layout.put block="page-javascript-plugin">
		<!-- page specific plugin scripts -->

		<script src="${rc.contextPath}/js/date-time/bootstrap-datepicker.min.js"></script>
		<script src="${rc.contextPath}/js/jqGrid/jquery.jqGrid.js"></script>
		<script src="${rc.contextPath}/js/jqGrid/i18n/grid.locale-kr.js"></script>
	</@layout.put>

	<@layout.put block="page-javascript-inline">
		<!-- inline scripts related to this page -->

		<script type="text/javascript">
			var serverUrl = {
				equipmentGrid : "${rc.contextPath}/user/equipment/grid/${shardIndex}",
				equipmentGridEdit : "${rc.contextPath}/user/equipment/grid/edit/${shardIndex}",
				equipmentExcel : "${rc.contextPath}/user/equipment/excel/${shardIndex}",
				equipmentCsv : "${rc.contextPath}/user/equipment/csv/${shardIndex}",
				subGrid : "${rc.contextPath}/user/equipment/sub-grid/${shardIndex}",
				subGridEdit : "${rc.contextPath}/user/equipment/sub-grid/edit/${shardIndex}"
			}
			
			$("#csvUpload").change(function() {
				var fFile = this.files[0];
				var sName = fFile.name;
				var nSize = fFile.size;
				var sType = fFile.type;
			});
		
			$("#uploadBtn").click(function() {
				var formData = new FormData($("#uploadForm")[0]);
				$.ajax({
					url: serverUrl.uploadCsv,
					type: 'POST',
					xhr: function() {
						var myXhr = $.ajaxSettings.xhr();
						if (myXhr.upload) {
							myXhr.upload.addEventListener('progress', progressHandlingFunction, false);
						}
						return myXhr;
					},
					success: completeHandler,
					
					data: formData,
					cache: false,
					contentType: false,
					processData: false 
				});
			});
			
			function completeHandler(e) {
				alert("완료");
			}
			
			function progressHandlingFunction(e) {
			}
		
			$("#shardIndex").change(function() {
				location.href = "${rc.contextPath}/user/equipment/grid/" + this.value;
			});
			
			function currencyTrim(event) {
				event.currentTarget.value = event.currentTarget.value.split(",").join("").split(" ").join("");
			}
			
			var categoryListJson = <#if categoryListJson??>${categoryListJson}<#else>{}</#if>;
			var subCategoryListJson = <#if subCategoryListJson??>${subCategoryListJson}<#else>{}</#if>;
			var equipTypeListJson = <#if equipTypeListJson??>${equipTypeListJson}<#else>{}</#if>;
			var equipStatTypeListJson = <#if equipStatTypeListJson??>${equipStatTypeListJson}<#else>{}</#if>;
		
			var lastsel;
			jQuery(function($) {
				var grid_selector = "#grid-table";
				var pager_selector = "#grid-table_toppager";
			
				var editOptions = {
				     modal:true,
				     url :serverUrl.accountGridEdit,
				     editCaption: "수정하기 (Edit)",
				     recreateForm:true,
				     reloadAfterSubmit : true,
				     closeAfterEdit: true,
				     bSubmit: "수정(Submit)",
				     viewPagerButtons: false,
				     beforeShowForm: function(form) {
				     	$("#tr_memo", form).show();
				     },
				     onclickSubmit: function(params){
				     	var ajaxData = {};
				     	var selectedRow = jQuery(grid_selector).getGridParam("selrow");
				     	rowData = jQuery(grid_selector).getRowData(selectedRow);
				     	ajaxData = { equipmentId: rowData.equipmentId };
				     	return ajaxData;
					},
					afterComplete: function(response, postdata) {
						if (response == undefined || response.responseText == undefined) {
							alert("Unknown Error!!");
						} else {
							var oResult = $.parseJSON(response.responseText);
							if (oResult.bSuccess) {
								alert("Edit Complete.");
							}  else if (oResult.sErrorMessage != undefined) {
								alert(oResult.sErrorMessage);
							} else {
								alert(oResult.htReturnValue.invalidItemList[0].message);
							}
						}
					}
				};
				
				jQuery(grid_selector).jqGrid({
					url: serverUrl.equipmentGrid,
					mtype: "POST",
					datatype: "json",
					colNames:[' ','EquipmentID', 'Key','UserID', 'UnitID', '장착위치 <br>Equip location', '장비명 <br>Equipment name', 'CODE', '대분류 <br>Category', '상세분류 <br>Detailed category', 'Grade', '장식속성1 <br>Ornament1', '장식속성2 <br>Ornament2', 'Level', 'Exp', '누적경험치 <br>Accumulated EXP', '소켓개수 <br>Number of sockets', '최대소켓수 <br>Max numbers of socket', 'Socket1', 'Socekt2', 'Socket3', 'Creation Date', ],					
					colModel:[
						{name:' ',index:'', width:70, fixed:true, sortable:false, resize:false,
							formatter:'actions', 
							formatoptions:{ 
								keys:true,
								delOptions:{recreateForm: true, beforeShowForm:beforeDeleteCallback},
								editbutton: false,
								editformbutton: true,
								editOptions : editOptions
								//editformbutton:true, editOptions:{recreateForm: true, beforeShowForm:beforeEditCallback}
							},
							search:false
						},
						
						{name:'equipmentId', index:'equipmentId', width:280, fixed:true, sortable:false, editable: false, key: true, searchoptions:{sopt: ['eq']}, search:true},
						{name:'equipmentId', index:'equipmentId', width:280, fixed:true, sortable:false, editable: true, key: true, hidden:true, searchoptions:{sopt: ['eq']}, search:true},
						
						{name:'accountId', index:'accountId', width:300, fixed:true, sorttype:"integer", editable: false, searchoptions:{sopt: ['eq']}, search:true },
						{name:'unitId', index:'unitId', width:300, fixed:true, sorttype:"integer", editable: false, searchoptions:{sopt: ['eq']}, search:true },
						{name:'equipPosition', index:'equipPosition', width:60, fixed:true, sorttype:"integer", editable: true, searchoptions:{sopt: ['eq']}, search:true },
						{name:'equipmentType', index:'equipmentType', width:180, fixed:true, sorttype:"integer", editable: false, formatter: "select", edittype: "select", editoptions: {value: equipTypeListJson}, search:true, searchoptions: {value: equipTypeListJson, sopt: ['eq']} },
						{name:'equipmentType', index:'equipmentType', width:70, fixed:true, sorttype:"integer", editable: false },
						{name:'category', index:'category', width:80, fixed:true, sorttype:"integer", editable: false, search:false, formatter: "select", edittype: "select", editoptions: {value: categoryListJson} },
						{name:'subCategory', index:'subCategory', width:80, fixed:true, sorttype:"integer", search:false, editable: false, formatter: "select", edittype: "select", editoptions: {value: subCategoryListJson} },
						{name:'grade', index:'grade', width:50, fixed:true, sorttype:"integer", editable: true, search:false },
						{name:'decoration1', index:'decoration1', width:80, fixed:true, sorttype:"integer", editable: true, search:false },
						{name:'decoration2', index:'decoration2', width:80, fixed:true, sorttype:"integer", editable: true, search:false },
						{name:'level', index:'level', width:60, fixed:true, sorttype:"integer", editable: true, search:false },
						{name:'exp', index:'exp', width:60, fixed:true, sorttype:"integer", editable: true, search:false },
						{name:'totalExp', index:'totalExp', width:80, fixed:true, sorttype:"integer", editable: true, search:false },
						{name:'openSockets', index:'openSockets', width:80, fixed:true, sorttype:"integer", editable: true, search:false },
						{name:'maxSockets', index:'maxSockets', width:60, fixed:true, sorttype:"integer", editable: true, search:false },
						{name:'socket1', index:'socket1', width:280, fixed:true, sorttype:"integer", editable: true, search:false },
						{name:'socket2', index:'socket2', width:280, fixed:true, sorttype:"integer", editable: true, search:false },
						{name:'socket3', index:'socket3', width:280, fixed:true, sorttype:"integer", editable: true, search:false },
						{name:'genYmdt', index:'genYmdt', width:150, fixed:true, sorttype:"integer", editable: false, search:false, formatter:'date', formatoptions: {srcformat: 'U', newformat: 'Y-m-d H:i:s'} },
					], 
					search : {
						caption: "Search...",
						Find: "Find",
						Reset: "Reset",
						odata : ['equal', 'less or equal', 'greater or equal'],
						groupOps: [ { op: "AND", text: "all" }, { op: "OR", text: "any" } ],
						matchText: " match",
						rulesText: " rules"
					},	
					headertitles: true ,		
					viewrecords : true,
					height: "auto",
					width: "auto",
					autowidth : true,
					rowNum:20,
					rowList:[20,40,60],
					pager : pager_selector,
					altRows: true, toppager: true,
					
					multiselect: true,
					//multikey: "ctrlKey",
			        multiboxonly: true,
					loadComplete : function() {
						var table = this;
						setTimeout(function(){
							styleCheckbox(table);
							updateActionIcons(table);
							updatePagerIcons(table);
							enableTooltips(table);
						}, 0);
					},
					onSelectRow: function(id){
						if(id && id!==lastsel){
							jQuery(grid_selector).jqGrid('restoreRow',lastsel);
							jQuery(grid_selector).jqGrid('editRow', id, true);
							lastsel=id;
						}
					},			
					editurl: serverUrl.equipmentGridEdit,
					caption: "Equipment Data",
					subGrid: true,
					subGridRowExpanded: function(subgrid_id, row_id) {
						var subgrid_table_id = subgrid_id+"_t";
						var pager_id = "p_"+subgrid_table_id;
						$("#"+subgrid_id).html("<br><table id='"+subgrid_table_id+"' class='scroll'></table><div id='"+pager_id+"' class='scroll'></div><br>");
						jQuery("#"+subgrid_table_id).jqGrid({
							url: serverUrl.subGrid + "?key=" + row_id,
							datatype: "json",
							colNames: [' ', 'Key','속성명<br>Attribute name', 'Code', '장식Code<br>Ornament Code', 'Min', 'Max', '속성확률<br>Attribute rate', '지속시간<br>Duration time', '기본속성여부<br>Base Attribute', '세트속성여부<br>Set Attribute', '특정유닛 고유속성<br>Special unit proper attribute' ],
							colModel: [
								{name:'myac', index:'', width:70, fixed:true, sortable:false, resize:false,
									formatter:'actions', 
									formatoptions:{ 
										keys:true,
										delOptions:{recreateForm: true, beforeShowForm:beforeDeleteCallback},
										//editformbutton:true, editOptions:{recreateForm: true, beforeShowForm:beforeEditCallback}
									}
								},							
								{name:"statKey",index:"statKey", key:true, editable: false, hidden:true, hidedlg: true},
								{name:'type', index:'type', width:120, fixed:true, sorttype:"integer", editable: false, formatter: "select", edittype: "select", editoptions: {value: equipStatTypeListJson} },
								{name:'type', index:'type', width:80, fixed:true, sorttype:"integer", editable: false  },
								{name:'decoCode', index:'decoCode', width:120, fixed:true, sorttype:"integer", editable: false },
								{name:'min', index:'min', width:80, fixed:true, sorttype:"integer", editable: false },
								{name:'max', index:'max', width:80, fixed:true, sorttype:"integer", editable: false },
								{name:'statRate', index:'statRate', width:100, fixed:true, sorttype:"integer", editable: false },
								{name:'duration', index:'duration', width:100, fixed:true, sorttype:"integer", editable: false },
								{name:'baseStat', index:'baseStat', width:100, fixed:true, sorttype:"integer", editable: false },
								{name:'setStat', index:'setStat', width:100, fixed:true, sorttype:"integer", editable: false },
								{name:'onlyFor', index:'onlyFor', width:180, fixed:true, sorttype:"integer", editable: false },
							],
						   	rowNum:40,
						   	pager: pager_id,
						   	sortname: 'statType',
						    sortorder: "asc",
						    width: '100%',
						    height: '100%',
						    editurl: serverUrl.subGridEdit + "?key=" + row_id,
						});
						jQuery("#"+subgrid_table_id).jqGrid('navGrid', "#"+pager_id, {
							edit: false,
							add: false,
							del: false
						});
					},
					subGridRowColapsed: function(subgrid_id, row_id) {
						// this function is called before removing the data
						//var subgrid_table_id;
						//subgrid_table_id = subgrid_id+"_t";
						//jQuery("#"+subgrid_table_id).remove();
					}	
			
				});
			
				//enable search/filter toolbar
				jQuery(grid_selector).jqGrid('filterToolbar',{defaultSearch:true, stringResult:true})
			
				//switch element when editing inline
				function aceSwitch( cellvalue, options, cell ) {
					setTimeout(function(){
						$(cell) .find('input[type=checkbox]')
								.wrap('<label class="inline" />')
							.addClass('ace ace-switch ace-switch-5')
							.after('<span class="lbl"></span>');
					}, 0);
				}
				
				//enable datepicker
				function pickDate( cellvalue, options, cell ) {
					setTimeout(function(){
						$(cell) .find('input[type=text]')
								.datepicker({format:'yyyy-mm-dd' , autoclose:true}); 
					}, 0);
				}
			
				//navButtons
				jQuery(grid_selector).jqGrid('navGrid',pager_selector,
					{ 	//navbar options
						edit: false,
						editicon : 'icon-pencil blue',
						add: false,
						addicon : 'icon-plus-sign purple',
						del: true,
						delicon : 'icon-trash red',
						search: true,
						searchicon : 'icon-search orange',
						refresh: true,
						refreshicon : 'icon-refresh green',
						view: false,
						viewicon : 'icon-zoom-in grey',
					},
					{
						//edit record form
						width:450,
						closeAfterEdit: true,
						recreateForm: true,
						beforeShowForm : function(e) {
							var form = $(e[0]);
							form.closest('.ui-jqdialog').find('.ui-jqdialog-titlebar').wrapInner('<div class="widget-header" />')
							style_edit_form(form);
						}
					},
					{
						//new record form
						width:450,
						closeAfterAdd: true,
						recreateForm: true,
						viewPagerButtons: false,
						beforeShowForm : function(e) {
							var form = $(e[0]);
							form.closest('.ui-jqdialog').find('.ui-jqdialog-titlebar').wrapInner('<div class="widget-header" />')
							style_edit_form(form);
						}
					},
					{
						//delete record form
						width:450,
						recreateForm: true,
						beforeShowForm : function(e) {
							var form = $(e[0]);
							if(form.data('styled')) return false;
							
							form.closest('.ui-jqdialog').find('.ui-jqdialog-titlebar').wrapInner('<div class="widget-header" />')
							style_delete_form(form);
							
							form.data('styled', true);
						},
						onClick : function(e) {
							alert(1);
						}
					},
					{
						//search form
						width:450,
						recreateForm: true,
						afterShowSearch: function(e){
							var form = $(e[0]);
							form.closest('.ui-jqdialog').find('.ui-jqdialog-title').wrap('<div class="widget-header" />')
							style_search_form(form);
						},
						afterRedraw: function(){
							style_search_filters($(this)); }, beforeShowSearch: function(form){ $(form).keydown(function(e) { if (e.keyCode == 13) { setTimeout(function() { $("#fbox_grid-table_search").click(); }, 200); } }); return true; }, onClose: function(form){ $("#fbox_grid-table_search").unbind('keydown'); },
						multipleSearch: true,
						/**
						multipleGroup:true,
						showQuery: true
						*/
					},
					{
						//view record form
						width:450,
						recreateForm: true,
						beforeShowForm: function(e){
							var form = $(e[0]);
							form.closest('.ui-jqdialog').find('.ui-jqdialog-title').wrap('<div class="widget-header" />')
						}
					}
				)
			
				jQuery(grid_selector).jqGrid('navButtonAdd',pager_selector,{caption:"Excel",title:"Excel Download",
				onClickButton : function() {
					jQuery(grid_selector).jqGrid('excelExport', {url: serverUrl.equipmentExcel});
				}});
				
				jQuery(grid_selector).jqGrid('navButtonAdd',pager_selector,{caption:"CSV",title:"CSV Download",
				onClickButton : function() {
					jQuery(grid_selector).jqGrid('excelExport', {url: serverUrl.equipmentCsv});
				}});
				
				function style_edit_form(form) {
					//enable datepicker on "sdate" field and switches for "stock" field
					form.find('input[name=sdate]').datepicker({format:'yyyy-mm-dd' , autoclose:true})
						.end().find('input[name=stock]')
							  .addClass('ace ace-switch ace-switch-5').wrap('<label class="inline" />').after('<span class="lbl"></span>');
			
					//update buttons classes
					var buttons = form.next().find('.EditButton .fm-button');
					buttons.addClass('btn btn-sm').find('[class*="-icon"]').remove();//ui-icon, s-icon
					buttons.eq(0).addClass('btn-primary').prepend('<i class="icon-ok"></i>');
					buttons.eq(1).prepend('<i class="icon-remove"></i>')
					
					buttons = form.next().find('.navButton a');
					buttons.find('.ui-icon').remove();
					buttons.eq(0).append('<i class="icon-chevron-left"></i>');
					buttons.eq(1).append('<i class="icon-chevron-right"></i>');		
				}
			
				function style_delete_form(form) {
					var buttons = form.next().find('.EditButton .fm-button');
					buttons.addClass('btn btn-sm').find('[class*="-icon"]').remove();//ui-icon, s-icon
					buttons.eq(0).addClass('btn-danger').prepend('<i class="icon-trash"></i>');
					buttons.eq(1).prepend('<i class="icon-remove"></i>')
				}
				
				function style_search_filters(form) {
					form.find('.delete-rule').val('X');
					form.find('.add-rule').addClass('btn btn-xs btn-primary');
					form.find('.add-group').addClass('btn btn-xs btn-success');
					form.find('.delete-group').addClass('btn btn-xs btn-danger');
				}
				function style_search_form(form) {
					var dialog = form.closest('.ui-jqdialog');
					var buttons = dialog.find('.EditTable')
					buttons.find('.EditButton a[id*="_reset"]').addClass('btn btn-sm btn-info').find('.ui-icon').attr('class', 'icon-retweet');
					buttons.find('.EditButton a[id*="_query"]').addClass('btn btn-sm btn-inverse').find('.ui-icon').attr('class', 'icon-comment-alt');
					buttons.find('.EditButton a[id*="_search"]').addClass('btn btn-sm btn-purple').find('.ui-icon').attr('class', 'icon-search');
				}
				
				function beforeDeleteCallback(e) {
					var form = $(e[0]);
					if(form.data('styled')) return false;
					
					form.closest('.ui-jqdialog').find('.ui-jqdialog-titlebar').wrapInner('<div class="widget-header" />')
					style_delete_form(form);
					
					form.data('styled', true);
				}
				
				function beforeEditCallback(e) {
					var form = $(e[0]);
					form.closest('.ui-jqdialog').find('.ui-jqdialog-titlebar').wrapInner('<div class="widget-header" />')
					style_edit_form(form);
				}
			
			
			
				//it causes some flicker when reloading or navigating grid
				//it may be possible to have some custom formatter to do this as the grid is being created to prevent this
				//or go back to default browser checkbox styles for the grid
				function styleCheckbox(table) {
				/**
					$(table).find('input:checkbox').addClass('ace')
					.wrap('<label />')
					.after('<span class="lbl align-top" />')
			
			
					$('.ui-jqgrid-labels th[id*="_cb"]:first-child')
					.find('input.cbox[type=checkbox]').addClass('ace')
					.wrap('<label />').after('<span class="lbl align-top" />');
				*/
				}
				
			
				//unlike navButtons icons, action icons in rows seem to be hard-coded
				//you can change them like this in here if you want
				function updateActionIcons(table) {
					/**
					var replacement = 
					{
						'ui-icon-pencil' : 'icon-pencil blue',
						'ui-icon-trash' : 'icon-trash red',
						'ui-icon-disk' : 'icon-ok green',
						'ui-icon-cancel' : 'icon-remove red'
					};
					$(table).find('.ui-pg-div span.ui-icon').each(function(){
						var icon = $(this);
						var $class = $.trim(icon.attr('class').replace('ui-icon', ''));
						if($class in replacement) icon.attr('class', 'ui-icon '+replacement[$class]);
					})
					*/
				}
				
				//replace icons with FontAwesome icons like above
				function updatePagerIcons(table) {
					var replacement = 
					{
						'ui-icon-seek-first' : 'icon-double-angle-left bigger-140',
						'ui-icon-seek-prev' : 'icon-angle-left bigger-140',
						'ui-icon-seek-next' : 'icon-angle-right bigger-140',
						'ui-icon-seek-end' : 'icon-double-angle-right bigger-140'
					};
					$('.ui-pg-table:not(.navtable) > tbody > tr > .ui-pg-button > .ui-icon').each(function(){
						var icon = $(this);
						var $class = $.trim(icon.attr('class').replace('ui-icon', ''));
						
						if($class in replacement) icon.attr('class', 'ui-icon '+replacement[$class]);
					})
				}
			
				function enableTooltips(table) {
					$('.navtable .ui-pg-button').tooltip({container:'body'});
					$(table).find('.ui-pg-div').tooltip({container:'body'});
				}
			
				//var selr = jQuery(grid_selector).jqGrid('getGridParam','selrow');
			
			
			});
		</script>

	</@layout.put>
</@layout.extends>
