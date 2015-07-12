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
								BossRaid Meta
								<small>
									<i class="icon-double-angle-right"></i>
									BossRaid Meta 정보를 관리합니다.<br>
									<span style="color:red;"></span>
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
			var serverUrl = {
				gridUrl : "${rc.contextPath}${baseUrl}" + "/grid",
				gridUrlEdit : "${rc.contextPath}${baseUrl}" + "/grid/edit",
				excelUrl : "${rc.contextPath}${baseUrl}" + "/excel",
				csvUrl : "${rc.contextPath}${baseUrl}" + "/csv"
			}
			
			var lastsel;
			jQuery(function($) {
				var grid_selector = "#grid-table";
				var pager_selector = "#grid-table_toppager";
			
				jQuery(grid_selector).jqGrid({
					url: serverUrl.gridUrl,
					mtype: "POST",
					datatype: "json",	
					colNames:[' ', 'key', 'nameKey', 'bossRaidMetaId', 'title_ko', 'bossName_ko', 'playTime', 'bossUnitType', 'bossHpFirst', 'bossHpScaler', 'bossScale', 'bossDefensePhy', 'defensePhyScaler', 'bossDefenseSpell', 'defenseSpellScaler', 'bossSkillLv0', 'bossSkillLv1', 'bossSkillLv2', 'bossImage', 'scene', 'texture0', 'texture1', 'texture2', 'minionUnitType', 'relativeMinionLevel', 'minionHpScale', 'minionScale', 'minionSkillLv0', 'minionSkillLv1', 'minionSkillLv2', 'minionSpawnNum', 'minionSpawnStart', 'minionSpawnInterval', 'fee', 'goldRewardFirst', 'goldRewardScaler', 'founderEquipCategory', 'gradeRatio', 'exposeRate', 'title_en', 'bossName_en', 'recommendationUnit', '수정일시', '수정ID' ],					
					colModel:[
						{name:' ',index:'', width:70, fixed:true, sortable:false, resize:false,
							formatter:'actions', 
							formatoptions:{ 
								keys:true,
								delOptions:{recreateForm: true, beforeShowForm:beforeDeleteCallback},
								//editformbutton:true, editOptions:{recreateForm: true, beforeShowForm:beforeEditCallback}
							},
							search: false
						},
						{name:'metaKey', index:'metaKey', width:50, fixed:true, sorttype:"integer", hidden: true, key: true, editable: false},
						{name:'nameKey', index:'nameKey', width:50, fixed:true, sorttype:"integer", hidden: true, key: true, editable: false},
						{name:'bossRaidMetaId', index:'bossRaidMetaId', width:100, fixed:true, sorttype:"integer", editable: true, searchoptions:{sopt: ['eq']}, sortable:true},
						{name:'titleKo', index:'titleKo', width:120, fixed:true, sorttype:"string", editable: true, sortable:false, search:false, searchoptions:{sopt: ['eq']}},
						{name:'bossNameKo', index:'bossNameKo', width:120, fixed:true, sorttype:"string", editable: true, searchoptions:{sopt: ['eq']}, sortable:true},
						
						{name:'playTime', index:'playTime', width:60, fixed:true, sorttype:"integer", editable: true, searchoptions:{sopt: ['eq']}, sortable:true},
						{name:'bossUnitType', index:'bossUnitType', width:60, fixed:true, sorttype:"integer", editable: true, searchoptions:{sopt: ['eq']}, sortable:true},
						{name:'bossHpFirst', index:'bossHpFirst', width:60, fixed:true, sorttype:"integer", editable: true, searchoptions:{sopt: ['eq']}, sortable:true},
						{name:'bossHpScaler', index:'bossHpScaler', width:60, fixed:true, sorttype:"integer", editable: true, searchoptions:{sopt: ['eq']}, sortable:true},
						{name:'bossScale', index:'bossScale', width:60, fixed:true, sorttype:"integer", editable: true, searchoptions:{sopt: ['eq']}, sortable:true},
						{name:'bossDefensePhy', index:'bossDefensePhy', width:60, fixed:true, sorttype:"integer", editable: true, searchoptions:{sopt: ['eq']}, sortable:true},
						{name:'defensePhyScaler', index:'defensePhyScaler', width:60, fixed:true, sorttype:"integer", editable: true, searchoptions:{sopt: ['eq']}, sortable:true},
						{name:'bossDefenseSpell', index:'bossDefenseSpell', width:60, fixed:true, sorttype:"integer", editable: true, searchoptions:{sopt: ['eq']}, sortable:true},
						{name:'defenseSpellScaler', index:'defenseSpellScaler', width:60, fixed:true, sorttype:"integer", editable: true, searchoptions:{sopt: ['eq']}, sortable:true},
						{name:'bossSkillLv0', index:'bossSkillLv0', width:60, fixed:true, sorttype:"integer", editable: true, searchoptions:{sopt: ['eq']}, sortable:true},
						{name:'bossSkillLv1', index:'bossSkillLv1', width:60, fixed:true, sorttype:"integer", editable: true, searchoptions:{sopt: ['eq']}, sortable:true},
						{name:'bossSkillLv2', index:'bossSkillLv2', width:60, fixed:true, sorttype:"integer", editable: true, searchoptions:{sopt: ['eq']}, sortable:true},
						{name:'bossImage', index:'bossImage', width:60, fixed:true, sorttype:"integer", editable: true, searchoptions:{sopt: ['eq']}, sortable:true},
						{name:'scene', index:'scene', width:60, fixed:true, sorttype:"integer", editable: true, searchoptions:{sopt: ['eq']}, sortable:true},
						{name:'texture0', index:'texture0', width:60, fixed:true, sorttype:"integer", editable: true, searchoptions:{sopt: ['eq']}, sortable:true},
						{name:'texture1', index:'texture1', width:60, fixed:true, sorttype:"integer", editable: true, searchoptions:{sopt: ['eq']}, sortable:true},
						{name:'texture2', index:'texture2', width:60, fixed:true, sorttype:"integer", editable: true, searchoptions:{sopt: ['eq']}, sortable:true},
						{name:'minionUnitType', index:'minionUnitType', width:60, fixed:true, sorttype:"integer", editable: true, searchoptions:{sopt: ['eq']}, sortable:true},
						{name:'relativeMinionLevel', index:'relativeMinionLevel', width:60, fixed:true, sorttype:"integer", editable: true, searchoptions:{sopt: ['eq']}, sortable:true},
						{name:'minionHpScale', index:'minionHpScale', width:60, fixed:true, sorttype:"integer", editable: true, searchoptions:{sopt: ['eq']}, sortable:true},
						{name:'minionScale', index:'minionScale', width:60, fixed:true, sorttype:"integer", editable: true, searchoptions:{sopt: ['eq']}, sortable:true},
						{name:'minionSkillLv0', index:'minionSkillLv0', width:60, fixed:true, sorttype:"integer", editable: true, searchoptions:{sopt: ['eq']}, sortable:true},
						{name:'minionSkillLv1', index:'minionSkillLv1', width:60, fixed:true, sorttype:"integer", editable: true, searchoptions:{sopt: ['eq']}, sortable:true},
						{name:'minionSkillLv2', index:'minionSkillLv2', width:60, fixed:true, sorttype:"integer", editable: true, searchoptions:{sopt: ['eq']}, sortable:true},
						{name:'minionSpawnNum', index:'minionSpawnNum', width:60, fixed:true, sorttype:"integer", editable: true, searchoptions:{sopt: ['eq']}, sortable:true},
						{name:'minionSpawnStart', index:'minionSpawnStart', width:60, fixed:true, sorttype:"integer", editable: true, searchoptions:{sopt: ['eq']}, sortable:true},
						{name:'minionSpawnInterval', index:'minionSpawnInterval', width:60, fixed:true, sorttype:"integer", editable: true, searchoptions:{sopt: ['eq']}, sortable:true},
						{name:'fee', index:'fee', width:60, fixed:true, sorttype:"integer", editable: true, searchoptions:{sopt: ['eq']}, sortable:true},
						{name:'goldRewardFirst', index:'goldRewardFirst', width:60, fixed:true, sorttype:"integer", editable: true, searchoptions:{sopt: ['eq']}, sortable:true},
						{name:'goldRewardScaler', index:'goldRewardScaler', width:60, fixed:true, sorttype:"integer", editable: true, searchoptions:{sopt: ['eq']}, sortable:true},
						{name:'founderEquipCategory', index:'founderEquipCategory', width:60, fixed:true, sorttype:"integer", editable: true, searchoptions:{sopt: ['eq']}, sortable:true},
						{name:'gradeRatio', index:'gradeRatio', width:60, fixed:true, sorttype:"integer", editable: true, searchoptions:{sopt: ['eq']}, sortable:true},
						{name:'exposeRate', index:'exposeRate', width:80, fixed:true, sortable:false, editable: true, search:false },
						{name:'recommendationUnit', index:'recommendationUnit', width:60, fixed:true, sorttype:"string", editable: true, searchoptions:{sopt: ['eq']}, sortable:true},
						{name:'titleEn', index:'titleEn', width:120, fixed:true, sorttype:"integer", editable: true, sortable:false, search:false, searchoptions:{sopt: ['eq']}},
						{name:'bossNameEn', index:'bossNameEn', width:120, fixed:true, sorttype:"string", editable: true, searchoptions:{sopt: ['eq']}, sortable:true},
						{name:'modYmdt', index:'modYmdt', width:150, fixed:true, sortable:false, editable: false, search:false },
						{name:'modId', index:'modId', width:100, fixed:true, sorttype:"integer", editable: false, search:false, sortable:false},
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
					sortname: "metaKey",
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
							jQuery(grid_selector).jqGrid('editRow', id, true, null, successFuncStandardGrid);
							lastsel=id;
						}
					},*/			
					editurl: serverUrl.gridUrlEdit,
					caption: "BossRaid Meta",
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
				jQuery(grid_selector).jqGrid('navGrid', pager_selector,
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