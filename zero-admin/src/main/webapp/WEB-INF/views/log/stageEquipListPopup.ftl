<@layout.extends name="/layout/popup.ftl">
	<@layout.put block="page-css">
		<link rel="stylesheet" href="${rc.contextPath}/css/jquery-ui-1.10.3.full.min.css" />
		<link rel="stylesheet" href="${rc.contextPath}/css/datepicker.css" />
		<link rel="stylesheet" href="${rc.contextPath}/css/ui.jqgrid.css" />	
	</@layout.put>
	<@layout.put block="content">

					<div class="page-content">
						<div class="page-header">
							<h1>
								Stage Log - Equipment Detail
								<small>
									<i class="icon-double-angle-right"></i>
									획득장비 정보를 조회합니다.
								</small>
							</h1>
						</div><!-- /.page-header -->
						
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
			function currencyTrim(event) {
				event.currentTarget.value = event.currentTarget.value.split(",").join("").split(" ").join("");
			}
			
			var categoryListJson = <#if categoryListJson??>${categoryListJson}<#else>{}</#if>;
			var subCategoryListJson = <#if subCategoryListJson??>${subCategoryListJson}<#else>{}</#if>;
			var equipTypeListJson = <#if equipTypeListJson??>${equipTypeListJson}<#else>{}</#if>;
			var equipStatTypeListJson = <#if equipStatTypeListJson??>${equipStatTypeListJson}<#else>{}</#if>;
			
			var serverUrl = {
				stageLogEquipmentGrid : "${rc.contextPath}/log/stage/equipment/grid/${shardIndex?c}/${logKey?c}",
				stageLogEquipmentExcel : "${rc.contextPath}/log/stage/equipment/excel/${shardIndex?c}/${logKey?c}",
				subGrid : "${rc.contextPath}/log/equipment/sub-grid/${shardIndex?c}"
			}
		
			var lastsel;
			jQuery(function($) {
				var grid_selector = "#grid-table";
				var pager_selector = "#grid-table_toppager";
			
				jQuery(grid_selector).jqGrid({
					url: serverUrl.stageLogEquipmentGrid,
					mtype: "POST",
					datatype: "json",
					colNames:[' ', 'Key', '장비타입', '장비타입ID', '카테고리', '서브카테고리', '장식1', '장식2', '유저ID', '유닛ID', '장비위치', '등급', '레벨', '경험치', '누적경험치', '개수', '열린소켓개수', '최대소켓개수', '소켓1', '소켓2', '소켓3', '생성일시', ],					
					colModel:[
						{name:' ',index:'', width:70, fixed:true, sortable:false, resize:false,
							formatter:'actions', 
							formatoptions:{ 
								keys:true,
								delOptions:{recreateForm: true, beforeShowForm:beforeDeleteCallback},
								//editformbutton:true, editOptions:{recreateForm: true, beforeShowForm:beforeEditCallback}
							}
						},
						{name:'equipmentId', index:'equipmentId', sorttype:"integer", editable: false, key: true, hidden:true},
						{name:'equipmentType', index:'equipmentType', width:180, fixed:true, sorttype:"integer", editable: false, formatter: "select", edittype: "select", editoptions: {value: equipTypeListJson} },
						{name:'equipmentType', index:'equipmentType', width:180, fixed:true, sorttype:"integer", editable: false },
						{name:'category', index:'category', width:100, fixed:true, sorttype:"integer", editable: false, formatter: "select", edittype: "select", editoptions: {value: categoryListJson} },
						{name:'subCategory', index:'subCategory', width:100, fixed:true, sorttype:"integer", editable: false, formatter: "select", edittype: "select", editoptions: {value: subCategoryListJson} },
						{name:'decoration1', index:'decoration1', width:120, fixed:true, sorttype:"integer", editable: false },
						{name:'decoration2', index:'decoration2', width:120, fixed:true, sorttype:"integer" },
						{name:'accountId', index:'accountId', width:120, fixed:true, sorttype:"integer", editable: false },
						{name:'unitId', index:'unitId', width:120, fixed:true, sorttype:"integer", editable: false },
						{name:'equipPosition', index:'equipPosition', width:120, fixed:true, sorttype:"integer", editable: false },
						{name:'grade', index:'grade', width:120, fixed:true, sorttype:"integer", editable: false },
						{name:'level', index:'level', width:120, fixed:true, sorttype:"integer", editable: false },
						{name:'exp', index:'exp', width:120, fixed:true, sorttype:"integer", editable: false },
						{name:'totalExp', index:'totalExp', width:120, fixed:true, sorttype:"integer", editable: false },
						{name:'count', index:'count', width:120, fixed:true, sorttype:"integer", editable: false },
						{name:'openSockets', index:'openSockets', width:120, fixed:true, sorttype:"integer", editable: false },
						{name:'maxSockets', index:'maxSockets', width:120, fixed:true, sorttype:"integer", editable: false },
						{name:'socket1', index:'socket1', width:120, fixed:true, sorttype:"integer", editable: false },
						{name:'socket2', index:'socket2', width:120, fixed:true, sorttype:"integer", editable: false },
						{name:'socket3', index:'socket3', width:120, fixed:true, sorttype:"integer", editable: false },
						{name:'genYmdt', index:'genYmdt', width:120, fixed:true, sorttype:"integer", editable: false, formatter:'date', formatoptions: {srcformat: 'U', newformat: 'Y-m-d H:i:s'} },
					], 
					search : {
						caption: "Search...",
						Find: "Find",
						Reset: "Reset",
						odata : ['equal', 'less or equal', 'greater or equal', 'begins with', 'ends with'],
						groupOps: [ { op: "AND", text: "all" }, { op: "OR", text: "any" } ],
						matchText: " match",
						rulesText: " rules"
					},	
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
					caption: "StageLog - Equipment Detail",
					subGrid: true,
					subGridRowExpanded: function(subgrid_id, row_id) {
						var subgrid_table_id = subgrid_id+"_t";
						var pager_id = "p_"+subgrid_table_id;
						$("#"+subgrid_id).html("<br><table id='"+subgrid_table_id+"' class='scroll'></table><div id='"+pager_id+"' class='scroll'></div><br>");
						jQuery("#"+subgrid_table_id).jqGrid({
							url: serverUrl.subGrid + "?key=" + row_id,
							datatype: "json",
							colNames: [' ', 'Key', '스탯타입', '장식코드', '최소', '최대', '스탯률', '지속시간', '기본스탯', '세트스탯', '고유' ],
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
								{name:'decoCode', index:'decoCode', width:120, fixed:true, sorttype:"integer", editable: false },
								{name:'min', index:'min', width:120, fixed:true, sorttype:"integer", editable: false },
								{name:'max', index:'max', width:120, fixed:true, sorttype:"integer", editable: false },
								{name:'statRate', index:'statRate', width:120, fixed:true, sorttype:"integer", editable: false },
								{name:'duration', index:'duration', width:120, fixed:true, sorttype:"integer", editable: false },
								{name:'baseStat', index:'baseStat', width:120, fixed:true, sorttype:"integer", editable: false },
								{name:'setStat', index:'setStat', width:120, fixed:true, sorttype:"integer", editable: false },
								{name:'onlyFor', index:'onlyFor', width:120, fixed:true, sorttype:"integer", editable: false },
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
							add: true,
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
				//jQuery(grid_selector).jqGrid('filterToolbar',{defaultSearch:true, stringResult:true})
			
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
						edit: true,
						editicon : 'icon-pencil blue',
						add: true,
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
						recreateForm: true,
						beforeShowForm: function(e){
							var form = $(e[0]);
							form.closest('.ui-jqdialog').find('.ui-jqdialog-title').wrap('<div class="widget-header" />')
						}
					}
				)
			
				jQuery(grid_selector).jqGrid('navButtonAdd',pager_selector,{caption:"Excel",title:"Excel Download",
				onClickButton : function() {
					jQuery(grid_selector).jqGrid('excelExport', {url: serverUrl.stageLogEquipmentExcel});
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