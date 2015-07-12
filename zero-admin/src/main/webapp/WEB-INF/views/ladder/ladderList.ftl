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
								Ladder Data
								<small>
									<i class="icon-double-angle-right"></i>
									래더정보를 관리합니다.
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
				location.href = "${rc.contextPath}/ladder/account/grid/" + this.value;
			});
		
		
			function currencyTrim(event) {
				event.currentTarget.value = event.currentTarget.value.split(",").join("").split(" ").join("");
			}
			
			var serverUrl = {
				ladderGrid : "${rc.contextPath}/ladder/account/grid/${shardIndex}",
				ladderGridEdit : "${rc.contextPath}/ladder/account/grid/edit/${shardIndex}",
				ladderExcel : "${rc.contextPath}/ladder/account/excel/${shardIndex}",
				ladderCsv : "${rc.contextPath}/ladder/account/csv/${shardIndex}",
				subGrid : "${rc.contextPath}/ladder/account/sub-grid/${shardIndex}"
			}
			
			var leagueOpts = "1:Bronze1;2:Bronze2;3:Bronze3;11:Silver1;12:Silver2;13:Silver3;21:Gold1;22:Gold2;23:Gold3;31:Platinum1;32:Platinum2;33:Platinum3;41:Hell1;42:Hell2;43:Hell3";
			function leagueFormatter(cellvalue, options, rowObject) {
				var imgSrc = "${rc.contextPath}/images/league/" + jQuery.trim(rowObject.league) + ".png";
				var imgTag = "<img width=40 height=40 src=\"" + imgSrc + "\">";
   				return imgTag;
			}			
		
		
			var lastsel;
			jQuery(function($) {
				var grid_selector = "#grid-table";
				var pager_selector = "#grid-table_toppager";
			
				jQuery(grid_selector).jqGrid({
					url: serverUrl.ladderGrid,
					mtype: "POST",
					datatype: "json",
					colNames:[' ', 'Key', '유저ID', 'Image', '리그', '래더점수', '승리', '패배', '직전게임<BR>승리여부', '연속승수', '최대<BR>연속승수', '최종<BR>게임일시', '마지막<BR>게임번호', '리그종료일<br>(보상지급일)'],					
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
						{name:'leagueKey', index:'leagueKey', width:260, fixed:true, sorttype:"integer", hidden: true, key: true, editable: false, searchoptions:{sopt: ['eq']}},
						{name:'accountId', index:'accountId', width:280, fixed:true, sorttype:"integer", editable: false, searchoptions:{sopt: ['eq']}, sortable:false},
						{name:'leagueImage', index:'leagueImage', width:50, fixed:true, sorttype:"string", editable: false, sortable:false, search:false, formatter:leagueFormatter },
						{name:'league', index:'league', width:80, fixed:true, sorttype:"integer", formatter:"select", editable: true, edittype:"select", editoptions:{value:leagueOpts}, search:false, sortable:false},
						{name:'ladder', index:'ladder', width:80, fixed:true, sorttype:"integer", editable: true, searchoptions:{sopt: ['eq','ge','le']}, sortable:false},
						{name:'win', index:'win', width:80, fixed:true, sorttype:"integer", editable: true, searchoptions:{sopt: ['eq','ge','le']}, sortable:false},
						{name:'lose', index:'lose', width:80, fixed:true, sorttype:"integer", editable: true, searchoptions:{sopt: ['eq','ge','le']}, sortable:false},
						{name:'isPrevWin', index:'isPrevWin', width:80, fixed:true, sorttype:"integer", editable: true, edittype:"checkbox", formatter:"checkbox", sortable:false, search:false},
						{name:'winningStreakCnt', index:'winningStreakCnt', width:80, fixed:true, sorttype:"integer", editable: true, searchoptions:{sopt: ['eq','ge','le']}, sortable:false},
						{name:'winningStreakMax', index:'winningStreakMax', width:80, fixed:true, sorttype:"integer", editable: true, searchoptions:{sopt: ['eq','ge','le']}, sortable:false},
						{name:'lastGameTimeStamp', index:'lastGameTimeStamp', width:100, fixed:true, sorttype:"integer", editable: true, searchoptions:{sopt: ['eq','ge','le']}, sortable:false},
						{name:'lastGameNo', index:'lastGameNo', width:100, fixed:true, sorttype:"integer", editable: true, sortable:false, search:false},
						{name:'resetDate', index:'resetDate', width:100, fixed:true, sorttype:"integer", editable: true, sortable:false, search:false}
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
					editurl: serverUrl.ladderGridEdit,
					caption: "Ladder Data",
					subGrid: true,
					subGridRowExpanded: function(subgrid_id, row_id) {
						var subgrid_table_id = subgrid_id+"_t";
						var pager_id = "p_"+subgrid_table_id;
						var accountId = jQuery(grid_selector).jqGrid('getRowData',row_id)["accountId"];
						$("#"+subgrid_id).html("<br><table id='"+subgrid_table_id+"' class='scroll'></table><div id='"+pager_id+"' class='scroll'></div><br>");
						jQuery("#"+subgrid_table_id).jqGrid({
							url: serverUrl.subGrid + "?key=" + accountId,
							datatype: "json",
							colNames: ['Key', '리그', '리그종료일', '방번호', '게임번호', '상대ID', '유저<br>래더점수', '상대<BR>래더점수', '승리여부', 'PlayTime', '승자<br>획득점수', '패자<br>획득점수', '기록일시' ],
							colModel: [
								{name:"logKey",index:"logKey", key:true, editable: false, hidden:true, hidedlg: true},
								{name:'league', index:'league', width:50, fixed:true, sorttype:"integer", editable: false, search:false },
								{name:'resetDate', index:'resetDate', width:100, fixed:true, sorttype:"integer", editable: false, search:false },
								{name:'roomNo', index:'roomNo', width:280, fixed:true, sorttype:"integer", editable: false, search:false },
								{name:'gameNo', index:'gameNo', width:60, fixed:true, sorttype:"integer", editable: false, search:false },
								{name:'opponentId', index:'opponentId', width:280, fixed:true, sorttype:"string", editable: false, searchoptions:{sopt: ['eq']} },
								{name:'myLadderPoint', index:'myLadderPoint', width:100, fixed:true, sorttype:"integer", editable: false, search:false },
								{name:'opLadderPoint', index:'opLadderPoint', width:100, fixed:true, sorttype:"integer", editable: false, searchoptions:{sopt: ['eq','ge','le']} },
								{name:'isWin', index:'isWin', width:80, fixed:true, sorttype:"integer", editable: false, edittype:"checkbox", formatter:"checkbox", search:false },
								{name:'playTime', index:'playTime', width:80, fixed:true, sorttype:"integer", editable: false, search:false },
								{name:'winnerPoint', index:'winnerPoint', width:100, fixed:true, sorttype:"integer", editable: false, search:false },
								{name:'looserPoint', index:'looserPoint', width:100, fixed:true, sorttype:"integer", editable: false, search:false },
								{name:'logYmdt', index:'logYmdt', width:140, fixed:true, sortable:false, editable: true, search:false, formatter:'date', formatoptions: {srcformat: 'U/1000', newformat: 'Y-m-d H:i:s'} },
							],
						   	rowNum:20,
						   	pager: pager_id,
						   	sortname: 'statType',
						    sortorder: "asc",
						    width: '100%',
						    height: '100%'
						});
						jQuery("#"+subgrid_table_id).jqGrid('navGrid', "#"+pager_id, {
							edit: false,
							add: false,
							del: false,
							search: true,
							searchicon : 'icon-search orange',
							refresh: true,
							refreshicon : 'icon-refresh green'
						},{},{},{},
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
					jQuery(grid_selector).jqGrid('excelExport', {url: serverUrl.ladderExcel});
				}});
				
				jQuery(grid_selector).jqGrid('navButtonAdd',pager_selector,{caption:"CSV",title:"CSV Download",
				onClickButton : function() {
					jQuery(grid_selector).jqGrid('excelExport', {url: serverUrl.ladderCsv});
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