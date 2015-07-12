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
								Stage Log Data
								<small>
									<i class="icon-double-angle-right"></i>
									스테이지 로그정보를 관리합니다.
								</small>
							</h1>
						</div><!-- /.page-header -->

						DB:&nbsp;&nbsp; 
						<select id="shardIndex" name="shardIndex">
							<#list DataSources as dataSource>
								<option value="${dataSource.code}" <#if shardIndex == dataSource.code>selected</#if>>${dataSource}</option>
							</#list>
						</select>
						<!--
									<hr />
									<label for="id-date-range-picker-1">Date Range Picker</label>

									<div class="row">
										<div class="col-xs-8 col-sm-11">
											<div class="input-group">
												<span class="input-group-addon">
													<i class="icon-calendar bigger-110"></i>
												</span>

												<input class="form-control" type="text" name="date-range-picker" id="id-date-range-picker-1" />
											</div>
										</div>
									</div>
						-->
			<br/><br>												
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
			var baseUrl = "${rc.contextPath}/log/stage";
			$("#shardIndex").change(function() {
				location.href = baseUrl + "/grid/" + this.value;
			});
			
			function currencyTrim(event) {
				event.currentTarget.value = event.currentTarget.value.split(",").join("").split(" ").join("");
			}
			
			function openPopup(logKey) {
				window.open($('._logKey_' + logKey).prop("href"), 'equipmentPopup', 'toolbar=no, location=no, status=no, menubar=no, scrollbars=yes, resizable=yes, height=300, width=1200');
			}
			
			function linkFormatter(cellValue, options, rowdata, action) 
			{
				if (rowdata["gainEquipmentList"] != undefined && rowdata["gainEquipmentList"].length != 0) {
			    	return "<a href='" + baseUrl + "/equipment/grid/${shardIndex}/" + rowdata["logKey"] + "' class='_logKey_" + rowdata["logKey"] + "' onclick='openPopup(" + rowdata["logKey"] + ");return false;' >보기</a>";
			    } else {
			    	return "<br>";
			    }
			}
					
			var serverUrl = {
				gridUrl : baseUrl + "/grid/${shardIndex}",
				gridEditUrl : baseUrl + "/grid/edit/${shardIndex}",
				stageLogExcel : baseUrl + "/excel/${shardIndex}",
				stageLogCsv : baseUrl + "/csv/${shardIndex}",
				subGrid : baseUrl + "/sub-grid/${shardIndex}"
			}
			
			
			function unitFormatter(cellvalue, options, rowObject) {
				var imgSrc = "";
				var imgTag = "";
				for (var key in rowObject.unitTypeList) {
					imgSrc = "${rc.contextPath}/images/unit/" + jQuery.trim(rowObject.unitTypeList[key]) + ".png";
					imgTag += "<img width=40 height=40 src=\"" + imgSrc + "\">&nbsp;";
				}
   				return imgTag;
			}				
			
		
			var lastsel;
			jQuery(function($) {
				var grid_selector = "#grid-table";
				var pager_selector = "#grid-table_toppager";
			
				jQuery(grid_selector).jqGrid({
					url: serverUrl.gridUrl,
					mtype: "POST",
					datatype: "json",
					colNames:['Key', '유저ID', 'StageKey', '덱구성', '덱구성<br>상세정보', '클리어모드', /*'클리어스텝', '클리어진행', */'플레이타임', '획득골드', '획득장비', '승리<br>여부', '기록일시'],					
					colModel:[
						{name:'logKey', index:'logKey', width:260, fixed:true, hidden: true, key: true, editable: false, sortable:false, searchoptions:{sopt: ['eq']}},
						{name:'accountId', index:'accountId', width:300, fixed:true, sorttype:"integer", editable: false, sortable:false, searchoptions:{sopt: ['eq']}},
						{name:'stageKey', index:'stageKey', width:80, fixed:true, sorttype:"integer", editable: false, sortable:false, searchoptions:{sopt: ['eq','ge','le']}},
						{name:'deckUnit', index:'deckUnit', width:140, fixed:true, sorttype:"integer", editable: false, sortable:false, search:false, formatter:unitFormatter},
						{name:'deck', index:'deck', width:400, fixed:true, sorttype:"integer", editable: false, sortable:false, search:false},
						{name:'clearMode', index:'clearMode', width:80, fixed:true, sorttype:"integer", editable: false, sortable:false, searchoptions:{sopt: ['eq','ge','le']}, formatter: "select", edittype: "select", editoptions: {value: "1000:Easy;2000:Normal;3000:Hard;4000:Hell"}},
						//{name:'clearStep', index:'clearStep', width:80, fixed:true, sorttype:"integer", editable: false, sortable:false, searchoptions:{sopt: ['eq','ge','le']}},
						//{name:'clearProgress', index:'clearProgress', width:80, fixed:true, sorttype:"integer", editable: false, sortable:false, searchoptions:{sopt: ['eq','ge','le']}},
						{name:'playTime', index:'playTime', width:80, fixed:true, sorttype:"integer", editable: false, sortable:false, search:false},
						{name:'gainGold', index:'gainGold', width:80, fixed:true, sorttype:"integer", editable: false, sortable:false, search:false},
						{name:'gainEquipments', index:'gainEquipments', width:80, fixed:true, sorttype:"integer", editable: false, sortable:false, search:false, formatter:linkFormatter},
						{name:'isWin', index:'isWin', width:40, fixed:true, sorttype:"integer", editable: false, sortable:false, search:false, edittype:"checkbox", formatter:"checkbox"},
						{name:'logYmdt', index:'logYmdt', width:150, fixed:true, sorttype:"integer", editable: false, sortable:false, search:true },
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
					sortname: "logKey",
					sortorder: "desc",						
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
					editurl: serverUrl.gridEditUrl,
					caption: "StageLog Data",
					subGrid: false
				});
			
				//enable search/filter toolbar
				jQuery(grid_selector).jqGrid('filterToolbar',{defaultSearch:true, stringResult:true, searchOnEnter:true})
			
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
								.datepicker({format:'yyyymmdd' , autoclose:true}); 
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
						
						searchfunc: function (pSearch) {
						    var $this = $(this);
						    $this.jqGrid("setGridParam", {postData: {
						        filters: {
						            groupOp: "AND",
						            rules: [
						                { field: "logYmdt", op: "le", "data": "" },
						                { field: "logYmdt", op: "ge", "data": "" }
						            ]
						        }
						    }});
    						$this.jqGrid("searchGrid", pSearch);
    					}
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
					jQuery(grid_selector).jqGrid('excelExport', {url: serverUrl.stageLogExcel});
				}});
				
				jQuery(grid_selector).jqGrid('navButtonAdd',pager_selector,{caption:"CSV",title:"CSV Download",
				onClickButton : function() {
					jQuery(grid_selector).jqGrid('excelExport', {url: serverUrl.stageLogCsv});
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