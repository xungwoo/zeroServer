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
								Shop Item
								<small>
									<i class="icon-double-angle-right"></i>
									상점에서 판매되는 아이템 정보를 관리합니다. <br>
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
			function currencyTrim(event) {
				event.currentTarget.value = event.currentTarget.value.split(",").join("").split(" ").join("");
			}
			
			var serverUrl = {
				gridUrl : "${rc.contextPath}/shop-item/grid",
				gridUrlEdit : "${rc.contextPath}/shop-item/grid/edit",
				excelUrl : "${rc.contextPath}/shop-item/excel",
				csvUrl : "${rc.contextPath}/shop-item/csv"
			}
			
			var lastsel;
			jQuery(function($) {
				var grid_selector = "#grid-table";
				var pager_selector = "#grid-table_toppager";
			
				jQuery(grid_selector).jqGrid({
					url: serverUrl.gridUrl,
					mtype: "POST",
					datatype: "json",	//itemKey, productId, itemId,  itemCategory, itemType, itemImageName, itemQuantity, itemQuantityBonus, bonusText, buyType, price, sort, isDel, modYmdt, modId
					colNames:[' ', 'Key', 'itemNameKo', 'itemDescKo', 'HSP<br>productId', 'HSP<br>itemId', 'Tab', 'Item분류', 'Image Name', 'Item 수량', '보너스 수량', '보너스 Text', '구매재화', '가격', '정렬<br>코드', '패키지<br>보상타입1', '패키지<br>보상1', '패키지<br>보상타입2', '패키지<br>보상2', '패키지<br>보상타입3', '패키지<br>보상3', '패키지<br>보상타입4', '패키지<br>보상4', 'itemDescEn', 'itemNameKo', '삭제여부<br>(사용여부)', '수정일시', '수정ID' ],					
					colModel:[
						{name:'',index:'', width:70, fixed:true, sortable:false, resize:false,
							formatter:'actions', 
							formatoptions:{ 
								keys:true,
								delOptions:{recreateForm: true, beforeShowForm:beforeDeleteCallback},
								//editformbutton:true, editOptions:{recreateForm: true, beforeShowForm:beforeEditCallback}
							},
							search: false
						},
						{name:'itemKey', index:'itemKey', width:260, fixed:true, sorttype:"integer", hidden: true, key: true, editable: false, searchoptions:{sopt: ['eq']}},
						
						{name:'itemNameKo', index:'itemNameKo', width:120, fixed:true, sorttype:"string", editable: true, searchoptions:{sopt: ['eq']}, sortable:false, search:true},
						{name:'itemDescKo', index:'itemDescKo', width:200, fixed:true, sorttype:"string", editable: true, searchoptions:{sopt: ['eq']}, sortable:false, search:true},

						{name:'productId', index:'productId', width:100, fixed:true, sorttype:"string", editable: true, searchoptions:{sopt: ['eq']}, sortable:false},
						{name:'itemId', index:'itemId', width:100, fixed:true, sorttype:"string", editable: true, searchoptions:{sopt: ['eq']}, sortable:false},
						{name:'itemCategory', index:'itemCategory', width:80, fixed:true, sorttype:"string", formatter:'select', editrules:{required:true}, editable: true, edittype:"select", editoptions:{defaultValue:'0', value:"${itemCategoryOpts}"},  sortable:true, search:true, searchoptions: { sopt: ['eq'], value: ":All;${itemCategoryOpts}" }, stype:'select'},
						{name:'itemType', index:'itemType', width:80, fixed:true, sorttype:"string", formatter:'select', editrules:{required:true}, editable: true, edittype:"select", editoptions:{defaultValue:'0', value:"${itemTypeOpts}"},  sortable:true, search:true, searchoptions: { sopt: ['eq'], value: ":All;${itemTypeOpts}" }, stype:'select'},
						
						{name:'itemImageName', index:'itemImageName', width:120, fixed:true, sorttype:"string", editable: true, searchoptions:{sopt: ['eq']}, sortable:false, search:true},
						{name:'itemQuantity', index:'itemQuantity', width:80, fixed:true, sorttype:"integer", editable: true, searchoptions:{sopt: ['eq']}, sortable:false, search:true},
						{name:'itemQuantityBonus', index:'itemQuantityBonus', width:80, fixed:true, sorttype:"integer", editable: true, searchoptions:{sopt: ['eq']}, sortable:false, search:true},
						{name:'bonusText', index:'bonusText', width:100, fixed:true, sorttype:"string", editable: true, searchoptions:{sopt: ['eq']}, sortable:false, search:true},

						{name:'buyType', index:'buyType', width:80, fixed:true, sorttype:"string", formatter:'select', editrules:{required:true}, editable: true, edittype:"select", editoptions:{defaultValue:'0', value:"${buyTypeOpts}"},  sortable:true, search:true, searchoptions: { sopt: ['eq'], value:":All;${buyTypeOpts}" }, stype:'select'},
						{name:'price', index:'price', width:80, fixed:true, sorttype:"integer", editable: true, searchoptions:{sopt: ['eq']}, sortable:false, search:true},
						{name:'sort', index:'sort', width:50, fixed:true, sorttype:"integer", editable: true, searchoptions:{sopt: ['eq']}, sortable:false, search:true},
						
						{name:'addRewardType1', index:'addRewardType1', width:80, fixed:true, sorttype:"integer", formatter:"select", editable: true, edittype:"select", editoptions:{value:"${RewardTypeOpts}"}, sortable:true, search:false},
						{name:'addReward1', index:'addReward1', width:60, fixed:true, sorttype:"integer", editable: true, search:false, sortable:false},
						{name:'addRewardType2', index:'addRewardType2', width:80, fixed:true, sorttype:"integer", formatter:"select", editable: true, edittype:"select", editoptions:{value:"${RewardTypeOpts}"}, sortable:true, search:false},
						{name:'addReward2', index:'addReward2', width:60, fixed:true, sorttype:"integer", editable: true, search:false, sortable:false},
						{name:'addRewardType3', index:'addRewardType3', width:80, fixed:true, sorttype:"integer", formatter:"select", editable: true, edittype:"select", editoptions:{value:"${RewardTypeOpts}"}, sortable:true, search:false},
						{name:'addReward3', index:'addReward3', width:60, fixed:true, sorttype:"integer", editable: true, search:false, sortable:false},
						{name:'addRewardType4', index:'addRewardType4', width:80, fixed:true, sorttype:"integer", formatter:"select", editable: true, edittype:"select", editoptions:{value:"${RewardTypeOpts}"}, sortable:true, search:false},
						{name:'addReward4', index:'addReward4', width:60, fixed:true, sorttype:"integer", editable: true, search:false, sortable:false},
						
						{name:'itemNameEn', index:'itemNameEn', width:120, fixed:true, sorttype:"string", editable: true, searchoptions:{sopt: ['eq']}, sortable:false, search:true},
						{name:'itemDescEn', index:'itemDescEn', width:200, fixed:true, sorttype:"string", editable: true, searchoptions:{sopt: ['eq']}, sortable:false, search:true},
												
						{name:'isDel', index:'isDel', width:70, fixed:true, sorttype:"boolean", formatter:'select', editrules:{required:true}, editable: true, edittype:"select", editoptions:{defaultValue:'false', value:"true:true;false:false"}, sortable:true, search:false},
						{name:'modYmdt', index:'modYmdt', width:160, fixed:true, sortable:false, editable: false, search:false, sortable:false },
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
					sortname: "itemKey",
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
					editurl: serverUrl.gridUrlEdit,
					caption: "ShopItem Meta",
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