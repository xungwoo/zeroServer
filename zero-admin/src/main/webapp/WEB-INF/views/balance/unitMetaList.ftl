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
								Unit Data
								<small>
									<i class="icon-double-angle-right"></i>
										유닛 메타정보를 관리합니다.
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
				unitGrid : "${rc.contextPath}" + "${baseUrl}" + "/grid",
				unitGridEdit : "${rc.contextPath}" + "${baseUrl}" + "/grid/edit",
				unitExcel : "${rc.contextPath}" + "${baseUrl}" + "/excel",
				uploadCsv : "${rc.contextPath}" + "${baseUrl}" + "/upload"
			}
			
			function aftersave(rowid, response) {
				if (response == undefined || response.responseText == undefined) {
					alert("알 수 없는 오류가 발생했습니다.");
				} else {
					var oResult = $.parseJSON(response.responseText);
					if (oResult.bSuccess) {
						alert("정상적으로 수정되었습니다.");
					} else if (oResult.sErrorMessage != undefined) {
						alert(oResult.sErrorMessage);
					} else {
						alert(oResult.htReturnValue.invalidItemList[0].message);
					}
				}
			}
					
			var lastsel;
			jQuery(function($) {
				var grid_selector = "#grid-table";
				var pager_selector = "#grid-table_toppager";
			
				jQuery(grid_selector).jqGrid({
					url: serverUrl.unitGrid,
					mtype: "POST",
					datatype: "json",
					colNames:[' ', 'metaKey','unitType','attackType','presentionType','unlockKey','levelMax','attackLv1','attackGrowth','spellLv1','spellGrowth','hpLv1','hpGrowth','hpRegen','mpLv1','mpGrowth','mpRegen','defensePhyLv1','defensePhyGrowth','defenseSpellLv1','defenseSpellGrowth','goldLv1','goldGrowth','moveSpeed','attackCoolTime','attackRange','criticalRate','criticalDamage','scale','power','mass','evo1LvJump','evo2LvJump','evo3LvJump','evo4LvJump','evo5LvJump','evo6LvJump','evo7LvJump','evo8LvJump','evo9LvJump','skill0Type','skill1Type','skill2Type','skill3Type','skill4Type','weapon1','weapon2','weapon3','weapon4','weapon5','modId','modYmdt'],					
					colModel:[
						{name:' ',index:'', width:70, fixed:true, sortable:false, resize:false,
							formatter:'actions', 
							formatoptions:{ 
								keys:true,
								delOptions:{recreateForm: true, beforeShowForm:beforeDeleteCallback},
								afterSave: aftersave
								//editOptions: editOptions
							}
						},
                        {name:'metaKey', index:'metaKey', width:70, fixed:true, sorttype:"integer", editable: false, hidden:true, key:true },
                        {name:'unitType', index:'unitType', width:70, fixed:true, sorttype:"integer", editable: true, hidden:false },
                        {name:'attackType', index:'attackType', width:70, fixed:true, sorttype:"integer", editable: true, editrules: {required: true, number: true}, searchoptions:{sopt: ['eq','ge','le']} },
                        {name:'presentionType', index:'presentionType', width:70, fixed:true, sorttype:"integer", editable: true, editrules: {required: true, number: true}, searchoptions:{sopt: ['eq','ge','le']} },
                        {name:'unlockKey', index:'unlockKey', width:70, fixed:true, sorttype:"integer", editable: true, editrules: {required: true, number: true}, searchoptions:{sopt: ['eq','ge','le']} },
                        {name:'levelMax', index:'levelMax', width:70, fixed:true, sorttype:"integer", editable: true, searchoptions:{sopt: ['eq','ge','le']} },
                        {name:'attackLv1', index:'attackLv1', width:70, fixed:true, sorttype:"integer", editable: true, editrules: {required: true, number: true}, searchoptions:{sopt: ['eq','ge','le']} },
                        {name:'attackGrowth', index:'attackGrowth', width:70, fixed:true, sorttype:"integer", editable: true, editrules: {required: true, number: true}, searchoptions:{sopt: ['eq','ge','le']} },
                        {name:'spellLv1', index:'spellLv1', width:70, fixed:true, sorttype:"integer", editable: true, editrules: {required: true, number: true}, searchoptions:{sopt: ['eq','ge','le']} },
                        {name:'spellGrowth', index:'spellGrowth', width:70, fixed:true, sorttype:"integer", editable: true, editrules: {required: true, number: true}, searchoptions:{sopt: ['eq','ge','le']} },
                        {name:'hpLv1', index:'hpLv1', width:70, fixed:true, sorttype:"integer", editable: true, editrules: {required: true}, searchoptions:{sopt: ['eq']} },
                        {name:'hpGrowth', index:'hpGrowth', width:70, fixed:true, sorttype:"integer", editable: true, searchoptions:{sopt: ['eq']} },
                        {name:'hpRegen', index:'hpRegen', width:70, fixed:true, sorttype:"integer", editable: true, editrules: {required: true, number: true}, searchoptions:{sopt: ['eq']} },
                        {name:'mpLv1', index:'mpLv1', width:70, fixed:true, sorttype:"integer", editable: true, editrules: {required: true, number: true}, searchoptions:{sopt: ['eq']} },
                        {name:'mpGrowth', index:'mpGrowth', width:70, fixed:true, sorttype:"integer", editable: true, editrules: {required: true, number: true}, searchoptions:{sopt: ['eq','ge','le']} },
                        {name:'mpRegen', index:'mpRegen', width:70, fixed:true, sorttype:"integer", editable: true, editrules: {required: true, number: true}, searchoptions:{sopt: ['eq','ge','le']} },
                        {name:'defensePhyLv1', index:'defensePhyLv1', width:70, fixed:true, sorttype:"integer", editable: true, editrules: {required: true, number: true}, searchoptions:{sopt: ['eq','ge','le']} },
                        {name:'defensePhyGrowth', index:'defensePhyGrowth', width:70, fixed:true, sorttype:"integer", editable: true, editrules: {required: true, number: true}, searchoptions:{sopt: ['eq','ge','le']} },
                        {name:'defenseSpellLv1', index:'defenseSpellLv1', width:70, fixed:true, sorttype:"integer", editable: true, editrules: {required: true, number: true}, searchoptions:{sopt: ['eq','ge','le']} },
                        {name:'defenseSpellGrowth', index:'defenseSpellGrowth', width:70, fixed:true, sorttype:"integer", editable: true, editrules: {required: true, number: true}, searchoptions:{sopt: ['eq','ge','le']} },
                        {name:'goldLv1', index:'goldLv1', width:70, fixed:true, sorttype:"integer", editable: true, editrules: {required: true, number: true}, searchoptions:{sopt: ['eq','ge','le']} },
                        {name:'goldGrowth', index:'goldGrowth', width:70, fixed:true, sorttype:"integer", editable: true, searchoptions:{sopt: ['eq','ge','le']} },
                        {name:'moveSpeed', index:'moveSpeed', width:70, fixed:true, sorttype:"integer", editable: true, editrules: {required: true, number: true}, searchoptions:{sopt: ['eq','ge','le']} },
                        {name:'attackCoolTime', index:'attackCoolTime', width:70, fixed:true, sorttype:"integer", editable: true, editrules: {required: true, number: true}, searchoptions:{sopt: ['eq','ge','le']} },
                        {name:'attackRange', index:'attackRange', width:70, fixed:true, sorttype:"integer", editable: true, searchoptions:{sopt: ['eq','ge','le']} },
                        {name:'criticalRate', index:'criticalRate', width:70, fixed:true, sorttype:"integer", editable: true, searchoptions:{sopt: ['eq','ge','le']} },
                        {name:'criticalDamage', index:'criticalDamage', width:70, fixed:true, sorttype:"integer", editable: true, editrules: {required: true, number: true}, searchoptions:{sopt: ['eq','ge','le']} },
                        {name:'scale', index:'scale', width:70, fixed:true, sorttype:"integer", editable: true, searchoptions:{sopt: ['eq','ge','le']} },
                        {name:'power', index:'power', width:70, fixed:true, sorttype:"integer", editable: true, editrules: {required: true, number: true}, searchoptions:{sopt: ['eq','ge','le']} },
                        {name:'mass', index:'mass', width:70, fixed:true, sorttype:"integer", editable: true, searchoptions:{sopt: ['eq','ge','le']} },
                        {name:'evo1LvJump', index:'evo1LvJump', width:70, fixed:true, sorttype:"integer", editable: true, searchoptions:{sopt: ['eq']} },
                        {name:'evo2LvJump', index:'evo2LvJump', width:70, fixed:true, sorttype:"integer", editable: true, editrules: {required: true, number: true}, searchoptions:{sopt: ['eq']} },
                        {name:'evo3LvJump', index:'evo3LvJump', width:70, fixed:true, sorttype:"integer", editable: true, editrules: {required: true, number: true}, searchoptions:{sopt: ['eq']} },
                        {name:'evo4LvJump', index:'evo4LvJump', width:70, fixed:true, sorttype:"integer", editable: true, editrules: {required: true, number: true}, searchoptions:{sopt: ['eq','ge','le']} },
                        {name:'evo5LvJump', index:'evo5LvJump', width:70, fixed:true, sorttype:"integer", editable: true, editrules: {required: true, number: true}, searchoptions:{sopt: ['eq','ge','le']} },
                        {name:'evo6LvJump', index:'evo6LvJump', width:70, fixed:true, sorttype:"integer", editable: true, editrules: {required: true, number: true}, searchoptions:{sopt: ['eq','ge','le']} },
                        {name:'evo7LvJump', index:'evo7LvJump', width:70, fixed:true, sorttype:"integer", editable: true, editrules: {required: true, number: true}, searchoptions:{sopt: ['eq','ge','le']} },
                        {name:'evo8LvJump', index:'evo8LvJump', width:70, fixed:true, sorttype:"integer", editable: true, editrules: {required: true, number: true}, searchoptions:{sopt: ['eq','ge','le']} },
                        {name:'evo9LvJump', index:'evo9LvJump', width:70, fixed:true, sorttype:"integer", editable: true, editrules: {required: true, number: true}, searchoptions:{sopt: ['eq','ge','le']} },
                        {name:'skill0Type', index:'skill0Type', width:70, fixed:true, sorttype:"integer", editable: true, editrules: {required: true, number: true}, searchoptions:{sopt: ['eq','ge','le']} },
                        {name:'skill1Type', index:'skill1Type', width:70, fixed:true, sorttype:"integer", editable: true, searchoptions:{sopt: ['eq','ge','le']} },
                        {name:'skill2Type', index:'skill2Type', width:70, fixed:true, sorttype:"integer", editable: true, editrules: {required: true, number: true}, searchoptions:{sopt: ['eq','ge','le']} },
                        {name:'skill3Type', index:'skill3Type', width:70, fixed:true, sorttype:"integer", editable: true, editrules: {required: true, number: true}, searchoptions:{sopt: ['eq','ge','le']} },
                        {name:'skill4Type', index:'skill4Type', width:70, fixed:true, sorttype:"integer", editable: true, searchoptions:{sopt: ['eq','ge','le']} },
                        {name:'skill5Type', index:'skill5Type', width:70, fixed:true, sorttype:"integer", editable: true, searchoptions:{sopt: ['eq','ge','le']} },
                        {name:'weapon1', index:'weapon1', width:70, fixed:true, sorttype:"integer", editable: true, searchoptions:{sopt: ['eq','ge','le']} },
                        {name:'weapon2', index:'weapon2', width:70, fixed:true, sorttype:"integer", editable: true, editrules: {required: true, number: true}, searchoptions:{sopt: ['eq','ge','le']} },
                        {name:'weapon3', index:'weapon3', width:70, fixed:true, sorttype:"integer", editable: true, searchoptions:{sopt: ['eq','ge','le']} },
                        {name:'weapon4', index:'weapon4', width:70, fixed:true, sorttype:"integer", editable: true, editrules: {required: true, number: true}, searchoptions:{sopt: ['eq','ge','le']} },
                        {name:'modId', index:'modId', width:70, fixed:true, sorttype:"integer", editable: true, searchoptions:{sopt: ['eq','ge','le']} },
                        {name:'modYmdt', index:'modYmdt', width:70, fixed:true, sorttype:"integer", editable: true, editrules: {required: false, number: true}, searchoptions:{sopt: ['eq','ge','le']} }

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
					viewrecords : true,
					height: "auto",
					width: "auto",
					autowidth : true,
					rowNum:20,
					rowList:[20,40,60],
					pager : pager_selector,
					altRows: true, toppager: true,
					
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
					subGrid: false,
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
						edit: false,
						editicon : 'icon-pencil blue',
						add: true,
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
					jQuery(grid_selector).jqGrid('excelExport', {url: serverUrl.unitExcel});
				}});
				
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
					alert(1);
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
			
				$("#shardIndex").change(function() {
					location.href = "${rc.contextPath}/user/unit/grid/" + this.value;
				});
				
				function currencyTrim(event) {
					event.currentTarget.value = event.currentTarget.value.split(",").join("").split(" ").join("");
				}
			
			});
		</script>

	</@layout.put>
</@layout.extends>
