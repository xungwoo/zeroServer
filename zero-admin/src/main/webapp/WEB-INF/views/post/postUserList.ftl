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
								Post
								<small>
									<i class="icon-double-angle-right"></i>
									유저의 우편함을 관리합니다.<br>
									<span style="color:red;">
										* 시작일시/종료일시 형식 : 20141209103000 (년월일시분초) - 시분초는 생략 가능합니다.<br> 
										* 2014-12-09 10:30:00 형식으로 입력/수정 가능
									</span>
								</small>
							</h1>
						</div><!-- /.page-header -->
						DB:&nbsp;&nbsp; 
						<select id="shardIndex" name="shardIndex">
							<#list DataSources as dataSource>
								<option value="${dataSource.code}" <#if shardIndex == dataSource.code>selected</#if>>${dataSource}</option>
							</#list>
						</select><br/><br/>			
						
						<div class="widget-box _csvImport" style="display:none;">
							<div class="widget-header">
								<h4>Import</h4>
							</div>
	
							<div class="widget-body">
								<div class="widget-main">
									<form id="uploadForm" enctype="multipart/form-data">
										<input type="file" name="uploadedFile" id="csvUpload" />
									</form>
									<div class="_importResult" style="display:none;">
										<br/>
										Import 결과
										<div class="_importResultMessage">
										</div>
									</div>
								</div>
							</div>
						</div>									

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
				location.href = "${rc.contextPath}/post/user/grid/" + this.value;
			});
		
		
			function currencyTrim(event) {
				event.currentTarget.value = event.currentTarget.value.split(",").join("").split(" ").join("");
			}
			
			var serverUrl = {
				gridUrl : "${rc.contextPath}/post/user/grid/${shardIndex}",
				editUrl : "${rc.contextPath}/post/user/grid/edit/${shardIndex}",
				excelUrl : "${rc.contextPath}/post/user/excel/${shardIndex}",
				csvUrl : "${rc.contextPath}/post/user/csv/${shardIndex}",
				uploadCsv : "${rc.contextPath}/post/user/upload/${shardIndex}"
			}
		
			var lastsel;
			jQuery(function($) {
				var grid_selector = "#grid-table";
				var pager_selector = "#grid-table_toppager";
			
				jQuery(grid_selector).jqGrid({
					url: serverUrl.gridUrl,
					mtype: "POST",
					datatype: "json",
					colNames:[' ', 'Key', 'adminKey', '유저Id', 'from<br>유저Id', '수취여부', '언어', '문구', 'from<br>닉네임', 'from<br>프로필', 'from<br>리그', '보상<br>대분류', '보상<br>분류', '보상수량<br>(장비코드)', '시작일시', '종료일시'],					
					colModel:[
						{name:' ',index:'', width:70, fixed:true, sortable:false, resize:false,
							formatter:'actions', 
							formatoptions:{ 
								keys:true,
								delOptions:{recreateForm: true, beforeShowForm:beforeDeleteCallback},
								editformbutton:true, editOptions:{recreateForm: true, beforeShowForm:beforeEditCallback}
							},
							search: false
						},
						{name:'postKey', index:'postKey', width:260, fixed:true, sorttype:"integer", hidden: true, key: true, editable: true, searchoptions:{sopt: ['eq']}},
						{name:'postAdminKey', index:'postAdminKey', width:80, fixed:true, hidden:true, sorttype:"integer", sortable:false},
						{name:'accountId', index:'accountId', width:290, fixed:true, sorttype:"integer", editable: true, searchoptions:{sopt: ['eq','ge','le']}, sortable:false},
						{name:'fromAccountId', index:'fromAccountId', width:290, fixed:true, sorttype:"integer", editable: true, editoptions:{defaultValue:'system'}, searchoptions:{sopt: ['eq','ge','le']}, sortable:false},
						{name:'rewardDone', index:'rewardDone', width:80, fixed:true, sorttype:"integer", editable: true, editoptions:{defaultValue:'false', readonly: 'readonly'}, sortable:false, search:true, searchoptions: { sopt: ['eq'], value: ":All;1:Yes;0:No" }, stype:'select'},
						{name:'lang', index:'lang', width:40, fixed:true, sorttype:"integer", editable: true, edittype:"select", editoptions:{defaultValue:'ko:ko', value:"ko:ko;en:en"}, search:false, sortable:false},
						{name:'text', index:'text', width:290, fixed:true, sorttype:"integer", editable: true, search:false, sortable:false},
						{name:'fromNickName', index:'fromNickName', width:100, fixed:true, sorttype:"integer", editable: true, editoptions:{defaultValue:'히어로즈연합'}, sortable:false, search:false},
						{name:'fromProfileUrl', index:'fromProfileUrl', width:100, fixed:true, sorttype:"integer", editable: true, editoptions:{defaultValue:'http://herok.gslb.toastoven.net/asset/real/notice/heroes_agent.png'}, sortable:false, search:false},
						{name:'fromLeague', index:'fromLeague', width:40, fixed:true, sorttype:"integer", editable: true, editoptions:{defaultValue:43}, sortable:false, search:false},
						{name:'rewardCategory', index:'rewardCategory', width:100, fixed:true, sorttype:"integer", editable: false, formatter:'select', edittype:"select", editoptions:{value:"${rewardCategoyList}"}, sortable:false, search:false},
						{name:'rewardType', index:'rewardType', width:100, fixed:true, sorttype:"integer", editable: true, formatter:'select', edittype:"select", editoptions:{value:"${rewardTypeList}"}, sortable:false, search:false},
						{name:'reward', index:'reward', width:80, fixed:true, sorttype:"integer", editable: true, sortable:false, search:false},
						{name:'startYmdt', index:'startYmdt', width:140, fixed:true, sorttype:"integer", editable: true, sortable:false, search:false},
						{name:'expireYmdt', index:'expireYmdt', width:140, fixed:true, sorttype:"integer", editable: true, sortable:false, search:false},
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
					sortname: "postKey",
					sortorder: "desc",							
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
					
					/*
					onSelectRow: function(id){
						if(id && id!==lastsel){
							jQuery(grid_selector).jqGrid('restoreRow',lastsel);
							jQuery(grid_selector).jqGrid('editRow', id, true);
							lastsel=id;
						}
					},*/			
					editurl: serverUrl.editUrl,
					caption: "Post",
					subGrid: false
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
			
			/*
				jQuery(grid_selector).jqGrid('navButtonAdd',pager_selector,{caption:"Excel",title:"Excel Download",
				onClickButton : function() {
					jQuery(grid_selector).jqGrid('excelExport', {url: serverUrl.excelUrl});
				}});
				
				jQuery(grid_selector).jqGrid('navButtonAdd',pager_selector,{caption:"CSV",title:"CSV Download",
				onClickButton : function() {
					jQuery(grid_selector).jqGrid('excelExport', {url: serverUrl.csvUrl});
				}});
			*/
				
				jQuery(grid_selector).jqGrid('navButtonAdd',pager_selector,{caption:"CSVImport",title:"CSV Import",
				onClickButton : function() {
					$("._csvImport").toggle();
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
			
				$("#csvUpload").change(function() {
					var fFile = this.files[0];
					var sName = fFile.name;
					var nSize = fFile.size;
					var sType = fFile.type;
					
					uploadCsv();
				});
			
				function uploadCsv() {
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
				};
				
				function completeHandler(e) {
					var elFileUpload = $("#csvUpload");
				    elFileUpload.replaceWith(elFileUpload.val('').clone(true));
				    
					if (e.success == "N") {
						alert(e.errorMessage);
					} else {
						$("._importResult").show();
						$("._importResultMessage").html(e.message);
						jQuery(grid_selector).trigger('reloadGrid');
					}
				}
				
				function progressHandlingFunction(e) {
				}
			
			});
		</script>

	</@layout.put>
</@layout.extends>
