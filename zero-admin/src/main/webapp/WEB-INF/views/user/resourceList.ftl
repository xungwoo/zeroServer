
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
								Resource Management
								<small>
									<i class="icon-double-angle-right"></i>
									유저의 재화정보를 관리합니다.
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
			$("#shardIndex").change(function() {
				location.href = "${rc.contextPath}/user/resource/grid/" + this.value;
			});
		
		
			function currencyTrim(event) {
				event.currentTarget.value = event.currentTarget.value.split(",").join("").split(" ").join("");
			}
			
			var serverUrl = {
				accountGrid : "${rc.contextPath}/user/resource/grid/${shardIndex}",
				accountGridEdit : "${rc.contextPath}/user/resource/grid/edit/${shardIndex}",
				accountExcel : "${rc.contextPath}/user/resource/excel/${shardIndex}",
				accountCsv : "${rc.contextPath}/user/resource/csv/${shardIndex}"
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
				     	ajaxData = { accountId: rowData.accountId };
				     	return ajaxData;
					},
					afterComplete: function(response, postdata) {
						if (response == undefined || response.responseText == undefined) {
							alert("Unknown Error!!");
						} else {
							var oResult = $.parseJSON(response.responseText);
							if (oResult.bSuccess) {
								alert("Edit Complete.");
							} else {
								alert(oResult.htReturnValue.invalidItemList[0].message);
							}
						}
					}
				};
				
				jQuery(grid_selector).jqGrid({
					url: serverUrl.accountGrid,
					mtype: "POST",
					datatype: "json",
					colNames:[' ', 'UserID', 'key', 'HSP MemberNo', 'NickName', 'Gold', 'Cash', 'AP갱신일시 <br>AP Refreshing Date', '추가AP <br>Additional AP', 'Max AP', 'BP갱신일시 <br>BP Refreshing Date', '추가BP <br>Additional BP', 'Max BP', '우정포인트 <br>Friend Point', '열쇠 <br>Key', '장비강화제 <br>Reinforcement', '장비뽑기권 <br>Equipment Drawing Ticket', '칭호1 <br>Title1', '칭호2 <br>Title2', '칭호3 <br>Title3', '수정일시 <br>Modification Date'],					
					colModel:[
						{name:' ', index:'', width:70, fixed:true, resize:false, sortable:false, 
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
						{name:'accountId', index:'accountId', width:290, fixed:true, key: true, sortable:false, editable: false, searchoptions:{sopt: ['eq']}},
						{name:'accountId', index:'accountId', width:100, fixed:true, sortable:false, editable: true, hidden:true, searchoptions:{sopt: ['eq']}},
						{name:'memberNo', index:'memberNo', width:140, fixed:true, sortable:false, editable: false, searchoptions:{sopt: ['eq']}},
						{name:'nickName', index:'nickName', width:120, fixed:true, sortable:false, editable: false, searchoptions:{sopt: ['eq']}},
						{name:'gold', index:'gold', width:80, fixed:true, sortable:false, editable: true, search:false },
						{name:'cash', index:'cash', width:80, fixed:true, sortable:false, editable: true, search:false},
						{name:'apLastDate', index:'apLastDate', width:130, fixed:true, sortable:false, editable: false, search:false, formatter:'date', formatoptions: {srcformat: 'U/1000', newformat: 'Ymd H:i:s'}},
						{name:'apExtra', index:'apExtra', width:60, fixed:true, sortable:false, editable: true, search:false},
						{name:'apMax', index:'apMax', width:60, fixed:true, sortable:false, editable: true, search:false},
						{name:'bpLastDate', index:'bpLastDate', width:130, fixed:true, sortable:false, editable: false, search:false, formatter:'date', formatoptions: {srcformat: 'U/1000', newformat: 'Ymd H:i:s'}},
						{name:'bpExtra', index:'bpExtra', width:60, fixed:true, sortable:false, editable: true, search:false},
						{name:'bpMax', index:'bpMax', width:60, fixed:true, sortable:false, editable: true, search:false},
						{name:'fp', index:'fp', width:60, fixed:true, sortable:false, editable: true, search:false},
						{name:'unlockKey', index:'unlockKey', width:60, fixed:true, sortable:false, editable: true, search:false},
						{name:'equipLevelUpDrug', index:'equipLevelUpDrug', width:90, fixed:true, sortable:false,editable: true, search:false},
						{name:'equipTicket', index:'equipTicket', width:90, fixed:true, sortable:false, editable: true, search:false},
						{name:'titleBundle1', index:'titleBundle1', width:100, fixed:true, sortable:false, editable: true, search:false},
						{name:'titleBundle2', index:'titleBundle2', width:100, fixed:true, sortable:false, editable: true, search:false},
						{name:'titleBundle3', index:'titleBundle3', width:100, fixed:true, sortable:false, editable: true, search:false},
						{name:'modYmdt', index:'modYmdt', width:150, fixed:true, sortable:false, editable: false, hidden:false, search:false}
					], 
					search : {
						caption: "Search...",
						Find: "Find",
						Reset: "Reset",
						odata : [ 'equal' ],
						groupOps: [ { op: "AND", text: "all" } ],
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
					pager: pager_selector,
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
					/*onSelectRow: function(id){
						if(id && id!==lastsel){
							jQuery(grid_selector).jqGrid('restoreRow',lastsel);
							jQuery(grid_selector).jqGrid('editGridRow', id, options);
							lastsel=id;
						}
					},*/			
					editurl: serverUrl.accountGridEdit,
					caption: "Account Data",
			
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
							style_search_filters($(this));  
						},
						beforeShowSearch: function(form){
						   $(form).keydown(function(e) {
						      if (e.keyCode == 13) {
						         setTimeout(function() {
						            $("#fbox_grid-table_search").click();
						         }, 200);
						      }
						   });
						   return true;
						},
						onClose: function(form){
						   $("#fbox_grid-table_search").unbind('keydown');
						},						
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
					jQuery(grid_selector).jqGrid('excelExport', {url: serverUrl.accountExcel});
				}});
				
				jQuery(grid_selector).jqGrid('navButtonAdd',pager_selector,{caption:"CSV",title:"CSV Download",
				onClickButton : function() {
					jQuery(grid_selector).jqGrid('excelExport', {url: serverUrl.accountCsv});
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
