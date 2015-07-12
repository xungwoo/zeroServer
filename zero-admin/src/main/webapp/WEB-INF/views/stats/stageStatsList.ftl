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
								Stage Clear 통계정보
								<small>
									<i class="icon-double-angle-right"></i>
									유저들의 Stage Clear 통계정보를 조회합니다.<br>
									<span style="color:red;"></span>
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
								 <div>
					                Data Series:
									<ul>
					                    <li><input class="dataSeries-checkbox" name="dataSeries_gold" value="gold" type="checkbox" checked="checked">Gold</li>
					                    <li><input class="dataSeries-checkbox" name="dataSeries_playTime" value="playTime" type="checkbox">PlayTime</li>
					                    <li><input class="dataSeries-checkbox" name="dataSeries_win" value="win" type="checkbox">Win</li>
					                    <li><input class="dataSeries-checkbox" name="dataSeries_playCount" value="playCount" type="checkbox">PlayCount</li>
					                </ul>					                
					            </div>
								<!-- PAGE CONTENT BEGINS -->
								<div id="stage-charts"></div>

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
		
		<script type="text/javascript" src="${rc.contextPath}/js/jqplot/jquery.jqplot.min.js"></script>
		<script type="text/javascript" src="${rc.contextPath}/js/jqplot/plugins/jqplot.canvasAxisTickRenderer.min.js"></script>
		<script type="text/javascript" src="${rc.contextPath}/js/jqplot/plugins/jqplot.canvasTextRenderer.min.js"></script>
		<script type="text/javascript" src="${rc.contextPath}/js/jqplot/plugins/jqplot.categoryAxisRenderer.min.js"></script>
		<script type="text/javascript" src="${rc.contextPath}/js/jqplot/plugins/jqplot.canvasAxisLabelRenderer.min.js"></script>
		<script type="text/javascript" src="${rc.contextPath}/js/jqplot/plugins/jqplot.barRenderer.min.js"></script>
		<script type="text/javascript" src="${rc.contextPath}/js/jqplot/plugins/jqplot.cursor.min.js"></script>
		<script type="text/javascript" src="${rc.contextPath}/js/jqplot/plugins/jqplot.highlighter.min.js"></script>
		<link rel="stylesheet" type="text/css" hrf="${rc.contextPath}/js/jqplot/jquery.jqplot.min.css" />		
		
	</@layout.put>

	<@layout.put block="page-javascript-inline">
		<!-- inline scripts related to this page -->

		<script type="text/javascript">
			$("#shardIndex").change(function() {
				location.href = baseUrl + "/grid/" + this.value;
			});
			
			var serverUrl = {
				gridUrl : "${rc.contextPath}/stats/stage/grid/${shardIndex}",
				gridUrlEdit : "${rc.contextPath}/stats/stage/grid/edit/${shardIndex}",
				excelUrl : "${rc.contextPath}/stats/stage/excel/${shardIndex}",
				csvUrl : "${rc.contextPath}/stats/stage/csv/${shardIndex}"
			}
			
			
			
			var lastsel;
			jQuery(function($) {
				var grid_selector = "#grid-table";
				var pager_selector = "#grid-table_toppager";
			
				jQuery(grid_selector).jqGrid({
					url: serverUrl.gridUrl,
					mtype: "POST",
					datatype: "json",	
					colNames:[' ', 'key', 'chapter', 'stage', 'clearMode', 'playTimeAvg', 'gainGoldAvg', 'winAvg', 'playCount'],					
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
						{name:'stageKey', index:'stageKey', width:100, fixed:true, sorttype:"integer", hidden: false, key: true, editable: false},
						{name:'chapter', index:'chapter', width:80, fixed:true, sorttype:"integer", sortable:true, search:true, searchoptions:{sopt: ['eq']}},
						{name:'stage', index:'stage', width:80, fixed:true, sorttype:"string", editable: false, sortable:true, search:false},
						{name:'clearMode', index:'clearMode', width:80, fixed:true, sorttype:"integer", editable: false, sortable:true, search:true, searchoptions:{sopt: ['eq']}},
						{name:'playTimeAvg', index:'playTimeAvg', width:100, fixed:true, formatter: 'number', formatoptions: {decimalSeparator:".", decimalPlaces: 2, defaultValue: '0.00'}, sorttype:"float", editable: false, sortable:true, search:false},
						{name:'gainGoldAvg', index:'gainGoldAvg', width:100, fixed:true, formatter: 'number', formatoptions: {decimalSeparator:".", decimalPlaces: 2, defaultValue: '0.00'},  sorttype:"float", editable: false, sortable:true, search:false},
						{name:'winAvg', index:'winAvg', width:100, fixed:true, formatter: 'number', formatoptions: {decimalSeparator:".", decimalPlaces: 2, defaultValue: '0.00'},  sorttype:"float", editable: false, sortable:true, search:false},
						{name:'playCount', index:'playCount', width:80, fixed:true,  sorttype:"integer", editable: false, sortable:true, search:false},
						
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
					rowNum:80,
					rowList:[80,160,240],
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
					caption: "StageClear Stats",
					subGrid: false,
					gridComplete : function () {
						$('stage-charts').html();
						$.jqplot.config.enablePlugins = true;
			
						var playTimeAvgArray = [];
						var gainGoldAvgArray = [];
						var winAvgArray = [];
						var playCountArray = [];
							$.each(jQuery("#grid-table").jqGrid('getRowData'), function (index, data) {
							     	playTimeAvgArray.push([data.clearMode+ '_' +data.stageKey, Number(data.playTimeAvg)]);
							     	gainGoldAvgArray.push([data.clearMode+'_' +data.stageKey, Number(data.gainGoldAvg)]);
							     	winAvgArray.push([data.clearMode+'_' +data.stageKey, Number(data.winAvg)]);
							     	playCountArray.push([data.clearMode+'_' +data.stageKey, Number(data.playCount)]);
							});
						var dataSets = {
							gold : gainGoldAvgArray,
							playTime : playTimeAvgArray,
							win : winAvgArray,
							playCount : playCountArray
						};	
							
						opts = {
							cursor : {
						      zoom:true, 
						      looseZoom: true, 
						      constrainOutsideZoom: false
							},
						
						    title: 'Stage Clear',
						    axesDefaults: {
						        tickRenderer: $.jqplot.CanvasAxisTickRenderer ,
						        tickOptions: {
						        	show: true,
						        	showLabel: true,
						          	fontFamily: 'Georgia',
						          	fontSize: '10pt',
						          	angle: -30
						        },
						        showTicks: true,        
        						showTickMarks: true,
						    },
						    axes: {
						      xaxis: {
						        renderer: $.jqplot.CategoryAxisRenderer
						      },
						      yaxis: {
						        autoscale:true
						      },
						    },
							legend:{
							    show:true, 
							    location: 'n',
							    labels: ['Gold', 'PlayTime', 'Win', 'PlayCount'],
							    rendererOptions: { numberRows: 1, placement: "outside" }
							},						    
							series: [
					            {show: true},
					            {show: false},
					            {show: false},
					            {show: false},
							],
						};							
								
											
					var plot1 = $.jqplot('stage-charts', [playTimeAvgArray, gainGoldAvgArray, winAvgArray, playCountArray], opts);
					$("input.dataSeries-checkbox").change(function(){ 
					        plot1.series[0].show = false;
					        plot1.series[1].show = false;
					        plot1.series[2].show = false;
					        plot1.series[3].show = false;
					 
					        if ($('input[name=dataSeries_gold]').get(0).checked === true) {
					            plot1.series[0].show = true;
					        }
					 
					        if ($('input[name=dataSeries_playTime]').get(0).checked === true) {
					            plot1.series[1].show = true;
					        }
					 
					        if ($('input[name=dataSeries_win]').get(0).checked === true) {
					            plot1.series[2].show = true;
					        }
					
					        if ($('input[name=dataSeries_playCount]').get(0).checked === true) {
					            plot1.series[3].show = true;
					        }
					 
					        plot1.replot();
					    });			    	
					
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