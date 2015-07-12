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
								Unit Management
								<small>
									<i class="icon-double-angle-right"></i>
										유닛정보를 관리합니다.
								</small>
							</h1>
						</div><!-- /.page-header -->
						
						<div class="alert alert-success" style="display:none;width:900px;height:300px;z-index:1;" id="loading">
							처리 중입니다. 잠시 기다려주세요!
						</div>						

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
			$("#shardIndex").change(function() {
				location.href = "${rc.contextPath}/user/unit/grid/" + this.value;
			});
			
			var serverUrl = {
				unitGrid : "${rc.contextPath}/user/unit/grid/${shardIndex}",
				unitGridEdit : "${rc.contextPath}/user/unit/grid/edit/${shardIndex}",
				unitExcel : "${rc.contextPath}/user/unit/excel/${shardIndex}",
				unitCsv : "${rc.contextPath}/user/unit/csv/${shardIndex}",
				unitReset : "${rc.contextPath}/user/unit/reset/${shardIndex}",
			}
			
			var unitTypeJson = <#if unitTypeJson??>${unitTypeJson}<#else>{}</#if>;
			
			function unitFormatter(cellvalue, options, rowObject) {
				var imgSrc = "${rc.contextPath}/images/unit/" + jQuery.trim(rowObject.unitType) + ".png";
				var imgTag = "<img width=40 height=40 src=\"" + imgSrc + "\">";
   				return imgTag;
			}		
			
			function resetButton(cellvalue, options, rowObject) {
			    var levelSum = rowObject.level + rowObject.skill0Lv + rowObject.skill1Lv + rowObject.skill2Lv + rowObject.skill3Lv;
			    var link = '';
			    if (levelSum <= 5) {
					link = "Lv1 Unit"			        
			    } else {
			    	link = "<button type=button onclick=\"unitReset('" + rowObject.unitId + "', '" + rowObject.accountId + "')\">초기화</button>";
			    }
			
			    return link;
			}		
			
			function unitReset(unitId, accountId) {
				$.ajax({
					  url: "${rc.contextPath}/user/unit/reset/${shardIndex}",
					  type: "POST",
					  dataType:"json",
					  context: document.body,
					  data: { accountId: accountId, unitId: unitId },
					  success: function(response){
					  		 jQuery("#grid-table").trigger("reloadGrid");
					  		 alert("유닛 초기화가 완료되었습니다.\n초기화 비용은 우편으로 지급되었습니다.\n (Unit reset complete.)");
					  		//$("#loading").text(response.resultLog);
					  		//$("#loading").text('유닛 초기화가 완료되었습니다!!');
					        //$("#loading").delay(5000).fadeOut(800);
					        //$("#loading").hide();
					        //$("#ajaxStart").attr("disabled", false);
					  },
					error:function(request,status,error){
					jQuery("#grid-table").trigger("reloadGrid");
						if (request.status==400) {
							//$("#loading").text('부적합한 유저ID 입니다. 다시 한번 확인해주세요');
						} else if (request.status == 500) {
							$("#loading").text('처리 중 오류가 발생하였습니다. 같은 현상이 반복되는 경우 개발팀에 문의해주세요!! \n (Error!!)');
						}
						$("#loading").delay(1000).fadeOut(800);
						$("#ajaxStart").attr("disabled", false);
					}			      
				});			
			}		
			
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
				     bSubmit: "수정 (Submit)",
				     viewPagerButtons: false,
				     beforeShowForm: function(form) {
				     	$("#tr_memo", form).show();
				     },
				     onclickSubmit: function(params){
				     	var ajaxData = {};
				     	var selectedRow = jQuery(grid_selector).getGridParam("selrow");
				     	rowData = jQuery(grid_selector).getRowData(selectedRow);
				     	ajaxData = { unitKey: rowData.unitKey };
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
					url: serverUrl.unitGrid,
					mtype: "POST",
					datatype: "json",
					colNames:[' ', 'Key', 'UnitID', 'UserID', 'Image', 'Unit Name', 'Unit Type', 'Level', '한계돌파 종료일시<br>Breakthrough limited end date', 'Passive skill', 'Active skill1', 'Active skill2' , 'Reset'],					
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
						{name:'unitKey', index:'unitKey', sorttype:"string", editable: false, key: true, hidden:true},
						{name:'unitId', index:'unitId', width:280, fixed:true, sorttype:"string", editable: false , sortable:false, searchoptions:{sopt: ['eq']}},
						{name:'accountId', index:'accountId', width:280, fixed:true, sorttype:"string", editable: false , sortable:false, searchoptions:{sopt: ['eq']}},
						{name:'unitImage', index:'unitImage', width:50, fixed:true, sorttype:"string", editable: false, sortable:false, search:false, formatter:unitFormatter },
						{name:'unitType', index:'unitType', width:80, fixed:true, sorttype:"string", editable: false, search:false, formatter: "select", edittype: "select", editoptions: {value: unitTypeJson} , sortable:false},
						{name:'unitType', index:'unitType', width:80, fixed:true, sorttype:"string", editable: false , sortable:false, searchoptions:{sopt: ['eq']}},
						{name:'level', index:'level', width:50, fixed:true, sorttype:"integer", editable: true , sortable:false, search:false},
						{name:'limitExceedEndYmdt', index:'limitExceedEndYmdt', width:200, fixed:true, sortable:false, editable: false, search:false },
						{name:'skill0Lv', index:'skill0Lv', width:80, fixed:true, sorttype:"integer", editable: true , sortable:false, search:false},
						{name:'skill1Lv', index:'skill1Lv', width:80, fixed:true, sorttype:"integer", editable: true , sortable:false, search:false},
						{name:'skill2Lv', index:'skill2Lv', width:80, fixed:true, sorttype:"integer", editable: true , sortable:false, search:false},
						{name: 'reset', index: 'reset', editable:false, width: 100, fixed:true,  align:'center', formatter: resetButton, search:false, sortable:false }
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
					headertitles: true ,
					viewrecords : true,
					height: "auto",
					width: "auto",	
					autowidth : true,
					rowNum:20,
					rowList:[20,40,60],
					pager : pager_selector,
					altRows: true, 
					toppager: true,
					
					multiselect: false,
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
					editurl: serverUrl.unitGridEdit,
					caption: "Unit Data",
			
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
						del: false,
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
					jQuery(grid_selector).jqGrid('excelExport', {url: serverUrl.unitExcel});
				}});
				
				jQuery(grid_selector).jqGrid('navButtonAdd',pager_selector,{caption:"CSV",title:"CSV Download",
				onClickButton : function() {
					jQuery(grid_selector).jqGrid('excelExport', {url: serverUrl.unitCsv});
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
				
				function currencyTrim(event) {
					event.currentTarget.value = event.currentTarget.value.split(",").join("").split(" ").join("");
				}
			
				//var selr = jQuery(grid_selector).jqGrid('getGridParam','selrow');
			
			
			});
		</script>

	</@layout.put>
</@layout.extends>
