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
								Equipment Meta Data
								<small>
									<i class="icon-double-angle-right"></i>
									장비 생성을 위한 메타정보를 관리합니다. ( <a href="http://doc.30games.co.kr/pages/viewpage.action?pageId=3900152" target=_blank>장비생성 Rule wiki Link</a> )<br><br>
									<b>장식속성 1개</b>인 경우 장식속성2 등급/비중은 입력하지 않는다.  <b> 장식속성 2개</b> 이상인 경우 비중에 따라 랜덤하게 결정된다.<br> 
									<b>초기(Lv1) 소진경험치 : </b>경험치를 가지고 있지 않은 생성 그 상태로의 장비를 다른 장비의 레벨업을 위해 소모했을 때 주어지는 경험치량<br>
									<b>Lv1->Lv2 필요경험치 : </b>첫레벨업을 위해 모아야 하는 기준 경험치. 다음 레벨업에 필요한 경험치는 초기 기준값에 x 130%<br>
									<b>Open Socket 결정 : </b> 0.4f, 0.3f, 0.2f, 0.1f 비율로 0, 1, 2, 3으로 장비생성 시 결정됨.<br>									
								</small>
							</h1>
						</div><!-- /.page-header -->

						<div class="row">
							<div class="col-xs-12">
								<!-- PAGE CONTENT BEGINS -->

								<table id="grid-table"></table>

								<div id="grid-pager"></div>

								<script type="text/javascript">
									var $path_base = "/";//this will be used in gritter alerts containing images
								</script>

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
			var baseUrl = "${rc.contextPath}" + "${baseUrl}"; 
			var serverUrl = {
				metaGrid : baseUrl + "/grid",
				metaGridEdit : baseUrl + "/grid/edit",
				subGrid : baseUrl + "/sub-grid",
				subGridEdit : baseUrl + "/sub-grid/edit",
				subCategory : baseUrl + "/sub-category/",
				equipType : baseUrl + "/equip-type/",
			}		
		
            function check_deco(value, colname) {
                var rowId = jQuery("#grid-table").jqGrid('getGridParam','selrow');
                rowId = (rowId == null)? "" : rowId + "_";
                
				var msg1 = "장식속성 등급을 지정하기 위해서는\n 비중을 반드시 입력해야합니다!";
				var msg2 = "중복된 장식속성";
				
                var deco1Grade = $("#" + rowId + "deco1Grade").val() || '';
                var deco2Grade = $("#" + rowId + "deco2Grade").val() || '';
                if (colname == '장식속성1 비중') {
                	if (deco1Grade != '' && value == '' || deco1Grade == '' && value != '') {
                		return [false, msg1];
                	}
                } else if (colname == '장식속성2 비중') {
                	if (deco2Grade != '' && value == '' || deco2Grade == '' && value != '') {
                		return [false, msg1];
                	}
                }
                	
            	if ((deco1Grade != '' && deco2Grade != '') && (deco1Grade == deco2Grade)) {
            		return [false, msg2];
            	}                	
            
				return [true, "전송완료!"];
            }
            
			function decoRateControl(e) {
				if (e.target.value) { 
	             	var rowId = jQuery("#grid-table").jqGrid('getGridParam','selrow');
	             	rowId = (rowId == null)? "" : rowId + "_";
	             	var id = e.target.id;
	             	var deco1Rate = $("#" + rowId + "deco1Rate").val() || 0;
	             	var deco2Rate = $("#" + rowId + "deco2Rate").val() || 0;
	             	if (id == (rowId + "deco1Grade")) {
	             		$("#" + rowId + "deco1Rate").val((1 - deco2Rate).toFixed(2));
	             	} else if(id == (rowId+"deco1Rate")) {
	             		$("#" + rowId + "deco2Rate").val((1 - deco1Rate).toFixed(2));
	             	} else if(id == (rowId+"deco2Grade")) {
	             		$("#" + rowId + "deco2Rate").val((1 - deco1Rate).toFixed(2));
	             	} else if(id == (rowId+"deco2Rate")) {
	             		$("#" + rowId + "deco1Rate").val((1 - deco2Rate).toFixed(2));
	             	}
             	}
		    }
		    
		    var loadSubCategory = function(event) {
		    	var rowId = jQuery("#grid-table").jqGrid('getGridParam','selrow');
		    	rowId = (rowId == null)? "" : rowId + "_";
		    	
		    	$("#" + rowId + "subCategory").val(' ');
		    	
		    	var param;
		    	if (event) {
		    		param = event.target.value;
		    	} else {
		    		param = $("#" + rowId + "category").val();
		    	}
		    	
		    	if (param) {
					$.ajax({
					    type: "GET",
					    url: serverUrl.subCategory + param
					}).done(function(oData) {
						$("#" + rowId + "subCategory").html(oData);
						loadEquipType();
					}).fail(function(oData) {
						alert('subCategory Load Fail' + oData.responseText);
					});	
				}
		    }
		    
		    var loadEquipType = function(event) {
		    	var rowId = jQuery("#grid-table").jqGrid('getGridParam','selrow');
		    	rowId = (rowId == null)? "" : rowId + "_";
		    	
		    	$("#" + rowId + "equipmentType").val(' ');
		    	
		    	var param;
		    	if (event) {
		    		param = event.target.value;
		    	} else {
		    		param = $("#" + rowId + "subCategory").val();
		    	}
		    	
		    	if (param) {
					$.ajax({
					    type: "GET",
					    url: serverUrl.equipType + param
					}).done(function(oData) {
						$("#" + rowId + "equipmentType").html(oData);
					}).fail(function(oData) {
						alert('EquipType Load Fail' + oData.responseText);
					});	
				}
		    }
		    
			function pngFomatter(cellvalue, options, rowObject) {
				var imgSrc = "http://dev.30games.co.kr/zero/eq/" + jQuery.trim(rowObject.image) + ".png";
				var imgTag = "<img width=60 height=60 src=\"" + imgSrc + "\" onclick=\"javascript:window.open('" + imgSrc + "');\" target=_blank>";
   				return imgTag;
			}		    
		
			var lastsel;
			jQuery(function($) {
				var grid_selector = "#grid-table";
				var pager_selector = "#grid-table_toppager";
			
				jQuery(grid_selector).jqGrid({
					url: serverUrl.metaGrid,
					mtype: "POST",
					datatype: "json",
					colNames:[
						' ', 'Key', '대분류', '중분류', 'PNG', '한글명', '장비명', 'Code', '등급', '장식1<br>등급', '장식1<br>비중', '장식2<br>등급', '장식2<br>비중', '속성LvUp<br>증가율', '판매가격', 'Max소켓', 'Class', 'dropRate', 'setKey', 'ImgName', '속성Type'
					],					
					colModel:[
						{name:'myac',index:'', width:70, fixed:true, sortable:false, resize:false,
							formatter:'actions', 
							formatoptions:{ 
								keys:true,
								delOptions:{recreateForm: true, beforeShowForm:beforeDeleteCallback},
								//editformbutton:true, editOptions:{recreateForm: true, beforeShowForm:beforeEditCallback}
							}
						},
						{name:'equipMetaKey', index:'equipMetaKey', sorttype:"integer", editable: true, key: true, hidden:true},
						{name:'category', index:'category', width:100, fixed:true, sorttype:"integer", editable: true, formatter:'select', editrules:{required:true}, edittype:"select", editoptions:{value:"${equipCategoryTypeList}", dataEvents: [{ type: 'focus', fn: loadSubCategory }, { type: 'change', fn: loadSubCategory }]}, searchoptions:{sopt: ['eq']}},
						{name:'subCategory', index:'subCategory', width:100, fixed:true, sorttype:"integer", editable: true, formatter:'select', editrules:{required:true}, edittype:"select", editoptions:{ value : "${equipSubCategoryTypeList}", dataEvents: [{ type: 'focus', fn: loadEquipType }, { type: 'change', fn: loadEquipType }] }, searchoptions:{sopt: ['eq']} },
						{name:'image1', index:'image1', width:70, fixed:true, sorttype:"string", editable: false, formatter:pngFomatter},
						{name:'ko', index:'ko', width:160, fixed:true, sorttype:"string", editable: false, searchoptions:{sopt: ['eq']}},
						{name:'equipmentType', index:'equipmentType', width:150, fixed:true, sorttype:"integer", editable: true, formatter:'select', editrules:{required:true}, edittype:"select", editoptions:{ value: "${equipTypeList}"}, searchoptions:{sopt: ['eq']}},
						{name:'equipmentType', index:'equipmentType', width:80, fixed:true, sorttype:"integer", editable: false, searchoptions:{sopt: ['eq']}},						
						{name:'grade', index:'grade', width:40, fixed:true, sorttype:"integer", editable: true, editrules:{required:true, number:true, minValue:1, maxValue:7}, searchoptions:{sopt: ['eq', 'le', 'ge']}},

						{name:'deco1Grade', index:'deco1Grade', width:50, fixed:true, sorttype:"integer", editable: true, editrules:{number:true, minValue:1, maxValue:7, custom: true, custom_func: check_deco}, editoptions: { dataEvents: [ { type: 'change', fn: decoRateControl } ]}, searchoptions:{sopt: ['eq', 'le', 'ge']}, },
						{name:'deco1Rate', index:'deco1Rate', width:50, fixed:true, sorttype:"float", editable: true, editrules:{number:true, minValue:0, maxValue:1, custom: true, custom_func: check_deco}, editoptions: { dataEvents: [ { type: 'change', fn: decoRateControl } ]}, searchoptions:{sopt: ['eq', 'le', 'ge']}},
						{name:'deco2Grade', index:'deco2Grade', width:50, fixed:true, sorttype:"integer", editable: true, editrules:{number:true, minValue:1, maxValue:7, custom: true, custom_func: check_deco}, editoptions: { dataEvents: [ { type: 'change', fn: decoRateControl } ]}, searchoptions:{sopt: ['eq', 'le', 'ge']}},
						{name:'deco2Rate', index:'deco2Rate', width:50, fixed:true, sorttype:"float", editable: true, editrules:{number:true, minValue:0, maxValue:1, custom: true, custom_func: check_deco}, editoptions: { dataEvents: [ { type: 'change', fn: decoRateControl } ]}, searchoptions:{sopt: ['eq', 'le', 'ge']}},
						
						{name:'statGrowth', index:'statGrowth', width:80, fixed:true, sorttype:"float", editable: true, editrules:{required:true, number:true, minValue:1.0}, editoptions:{defaultValue: 1.3}, formatter: 'number', formatoptions: { decimalPlaces: 2 }, searchoptions:{sopt: ['eq', 'le', 'ge']}},

						{name:'sellPrice', index:'sellPrice', width:60, fixed:true, sorttype:"integer", editable: true, editrules:{required:true, number:true}, searchoptions:{sopt: ['eq', 'le', 'ge']}},
						{name:'maxSockets', index:'maxSockets', width:50, fixed:true, sorttype:"integer", editable: true, editrules:{required:true, number:true, minValue:0, maxValue:3}, searchoptions:{sopt: ['eq', 'le', 'ge']}},

						{name:'eqClass', index:'eqClass', width:50, fixed:true, sorttype:"integer", editable: true, editrules:{required:true, number:true, minValue:0, maxValue:10}, searchoptions:{sopt: ['eq', 'le', 'ge']}},
						{name:'dropRate', index:'dropRate', width:50, fixed:true, sorttype:"integer", editable: true, editrules:{required:true, number:true, minValue:0, maxValue:1000}, searchoptions:{sopt: ['eq', 'le', 'ge']}},
						{name:'setKey', index:'setKey', width:50, fixed:true, sorttype:"integer", editable: true, editrules:{required:true, number:true, minValue:0, maxValue:1000}, searchoptions:{sopt: ['eq', 'le', 'ge']}},
						{name:'image', index:'image', width:70, fixed:true, sorttype:"string", editable: true, editrules:{required:true}, searchoptions:{sopt: ['eq']}},
						{name:'statType', index:'statType', width:1, editable: false, searchoptions:{sopt: ['eq']}},						
					], 
					search : {
						caption: "Search...",
						Find: "Find",
						Reset: "Reset",
						odata : ['equal', 'less or equal', 'greater or equal', 'begins with', 'ends with'],
						groupOps: [ { op: "AND", text: "all" } ],
						matchText: " match",
						rulesText: " rules"
					},			
					viewrecords : true,
					height: "auto",
					width: "auto",
					rowNum:20,
					rowList:[20,40,60],
					pager : pager_selector,
					altRows: true, toppager: true,
					toppager: true,
					ajaxGridOptions:"application/json; charset=utf-8",
					
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
					editurl: serverUrl.metaGridEdit,
					caption: "장비생성을 위한 메타정보",
					subGrid: true,
					subGridRowExpanded: function(subgrid_id, row_id) {
						var subgrid_table_id = subgrid_id+"_t";
						var pager_id = "p_"+subgrid_table_id;
						$("#"+subgrid_id).html("<br><table id='"+subgrid_table_id+"' class='scroll'></table><div id='"+pager_id+"' class='scroll'></div><br>");
						jQuery("#"+subgrid_table_id).jqGrid({
							url: serverUrl.subGrid + "?key=" + row_id,
							datatype: "json",
							colNames: [' ', 'Key', 'equipMetaKey', '속성구분', '장비명', '속성분류', '속성명', 'Code', 'Min1','Min2','Max1','Max2', '부여확률', 'statRate<br>Min', 'statRate<br>Max', '지속시간<br>Min', '지속시간<br>Max', '젬속성구분','특정유닛', '셋트속성'],
							colModel: [
								{name:'myac', index:'', width:70, fixed:true, sortable:false, resize:false,
									formatter:'actions', 
									formatoptions:{ 
										keys:true,
										delOptions:{recreateForm: true, beforeShowForm:beforeDeleteCallback},
										//editformbutton:true, editOptions:{recreateForm: true, beforeShowForm:beforeEditCallback}
									}
								},							
								{name:"statMetaKey",index:"statMetaKey", key:true, editable: false, hidden:true, hidedlg: true},
								{name:"equipMetaKey",index:"equipMetaKey", editable: true, hidden:true},
								{name:"baseStat", index:"baseStat", width:100, editable: true, hidden:true},
								{name:"equipmentType",index:"equipmentType", width:100, editable:false, hidden:true},
								{name:"baseStat",index:"baseStat", width:80, hidedlg: true, editable: true, formatter:'select', edittype:"select",editoptions:{value:"1:기본속성;0:추가속성"}},
								{name:"statType",index:"statType",width:200, editable: true, formatter:'select', edittype:"select",editoptions:{value:"${statTypeList}"}},
								{name:"statType",index:"statType",width:70, editable: false, searchoptions:{sopt: ['eq']}},
								{name:"minRange1",index:"minRange1",width:70,align:"right",sortable:false, editable: true, formatter: 'number', formatoptions: { decimalPlaces: 2 }},
								{name:"minRange2",index:"minRange2",width:70,align:"right",sortable:false, editable: true, formatter: 'number', formatoptions: { decimalPlaces: 2 }},
								{name:"maxRange1",index:"maxRange1",width:70,align:"right",sortable:false, editable: true, formatter: 'number', formatoptions: { decimalPlaces: 2 }},
								{name:"maxRange2",index:"maxRange2",width:70,align:"right",sortable:false, editable: true, formatter: 'number', formatoptions: { decimalPlaces: 2 }},
								{name:"rate",index:"rate",width:70,align:"right",sortable:false, editable: true, formatter: 'number', formatoptions: { decimalPlaces: 2 }},
								{name:"statRateMin",index:"statRateMin",width:70,align:"right",sortable:false, editable: true, editrules:{required:true,number:true}, editoptions:{defaultValue: '0'}, formatter: 'number', formatoptions: { decimalPlaces: 2 }},
								{name:"statRateMax",index:"statRateMax",width:70,align:"right",sortable:false, editable: true, editrules:{required:true,number:true}, editoptions:{defaultValue: '0'}, formatter: 'number', formatoptions: { decimalPlaces: 2 }},
								{name:"durationMin",index:"durationMin",width:70,align:"right",sortable:false, editable: true, editrules:{required:true,number:true}, editoptions:{defaultValue: '0'}, formatter: 'number', formatoptions: { decimalPlaces: 2 }},
								{name:"durationMax",index:"durationMax",width:70,align:"right",sortable:false, editable: true, editrules:{required:true,number:true}, editoptions:{defaultValue: '0'}, formatter: 'number', formatoptions: { decimalPlaces: 2 }},
								{name:"gemStatType",index:"gemStatType",width:80,align:"right",sortable:false, editable: true, formatter:'select', edittype:"select", editoptions:{value:"${gemStatTypeList}"} },
								{name:"onlyFor",index:"onlyFor",width:70,align:"right",sortable:false, editable: true },
								{name:"setStat",index:"setStat",width:70,align:"right",sortable:false, editable: true },
							],
						   	rowNum:40,
						   	pager: pager_id,
						   	sortname: 'statType',
						    sortorder: "asc",
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
						searchoptions:{
                      		sopt: ['eq','ne']
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
