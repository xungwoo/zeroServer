var ttgames = window.ttgames || {};
ttgames = {
	init : function(oOption) {
		ttgames.sContextRoot = oOption.sContextRoot;
	}
}

ttgames.sContextRoot = "http://localhost:8080/zero-admin";

ttgames.form = {
	freezeForm : function() {
		$('input').attr('disabled', 'disabled');
	},

	thawForm : function() {
		$('input').removeAttr('disabled');
	},

	clearForm : function() {
		$('input[type|="text"]').val('');
	},

	validateEmpty : function(field) {
		if ($(field).val() == "") {
			$(field + "Group").addClass("error");
			$(field).focus();
			return false;
		}
		return true;
	},

	rangeUpdate : function(id) {
		$("#" + id).val($('#' + id + '-range').val());
	},

	textfiled4RangeUpdate : function(id) {
		$("#" + id + '-range').val($('#' + id).val());
	},


	ajax : function(sUrl, oData, oOption, sMethodType) {
		sMethodType = sMethodType || 'POST';
		if (!sUrl || !oData) return;
		// TODO validator ??

		this.freezeForm();

		$.ajax({
			url: sUrl,
			data:oData,
			type: sMethodType,
			context : this
		}, this).done(function(oData) {
			if(oOption.sTemplateId && oOption.sTargetId && oData) ttgames.handlebars.load(oOption.sTemplateId, oOption.sTargetId, oData);
			
			if(typeof oOption.callBack == 'function') oOption.callBack(oOption.callBackArg, oOption.callBackScope);
			
			if(oOption.sSuccessPanelId) $('#'+oOption.sSuccessPanelId).removeClass("hidden");
			if(oOption.sErrorPanelId) $('#'+oOption.sErrorPanelId).addClass("hidden");
			if(oOption.sErrorReasonId) $('#'+oOption.sErrorReasonId).addClass("hidden");
		}).fail(function(oData) {
			this.thawForm();
			if(oOption.sSuccessPanelId) $('#'+oOption.sSuccessPanelId).addClass("hidden");
			if(oOption.sErrorPanelId) $('#'+oOption.sErrorPanelId).removeClass("hidden");
			if(oOption.sErrorReasonId) {
				$('#'+oOption.sErrorReasonId).removeClass("hidden");
				$('#'+oOption.sErrorReasonId).text(oData.responseText);
			}
		});
	}
}

ttgames.grid = {
	vars : {},
	data : {},
	params : [],
	
	init : function(sGridTableId, scope) {
		var that = scope || this;
		that.vars.grid = $('#' + sGridTableId);
		that.initEvent();
	},
	
	initEvent : function() {
		var that = this;
		var editingCell;
		
		var startCellEdit = function(e) {
			var editingCell = $(this);
			var key = editingCell.parent().attr('key');
			var value = editingCell.text();
			var width = editingCell.attr('width') - 30;
								
 			editingCell.html("<input type=text id='editingGrid' style='height:18px;width:" + width + "px' value='" + value + "'>");
 			var jEditingGrid = $('#editingGrid');
 			jEditingGrid.on('focusout', afterCellEdit);
 			jEditingGrid.focus();		
		};
		
		var afterCellEdit = function(e) {
			var editedCell = $(this).parent();
			var key = editedCell.parent().attr('key');
			var column = editedCell.attr('column');
			
			var jEditingGrid = $('#editingGrid');
			var value = jEditingGrid.val();
			
			that.setData(key, column, value);
			editedCell.html(value);
		};
		
		var gridCell = $('.gridcell');
		gridCell.on('click', startCellEdit);
		gridCell.on('keydown', function(e) { 
			var keyCode = e.keyCode || e.which; 
			if (keyCode == 9) {
				var jNextGridCell = $(this).next('.gridcell');
				if (jNextGridCell.length > 0) {
					jNextGridCell.click();
				} else {
					var jNextTrGridCell = $(this).parent().next('tr').find('.gridcell:first');
					jNextTrGridCell.click();
					jNextTrGridCell.focus();
				}
			}
		});				
	},
	
	setData : function(key, column, value) {
		this.data[key] = this.data[key] || {};
		this.data[key][column] = value;
	},

	save : function(sUrl, sKey, oOption) {
	 	var that = this;
	 	
	 	$.each(this.data, function(key, value) {
	 		this[sKey] = key;
	 		that.params.push(this);
	 	});

	 	var paramUrl = this.param({modelList : that.params});
	 	ttgames.form.ajax(sUrl, paramUrl, oOption);
	 	that.data = {};
	 	that.params = [];
	},
	
	addTableRow : function(sTableId) {
	    $('#' + sTableId).each(function() {
	        var $table = $(this);
	        // Number of td's in the last table row
	        var n = $('tr:last td', this).length;
	        var tds = '<tr>';
	        for(var i = 0; i < n; i++){
	            tds += '<td>&nbsp;</td>';
	        }
	        tds += '</tr>';
	        if($('tbody', this).length > 0){
	            $('tbody', this).append(tds);
	        }else {
	            $(this).append(tds);
	        }
	    });
	},
	
	// JQuery 와 동일
	param : function( a ) {
		var that = this;
		var r20 = /%20/g;
		var prefix,
			s = [],
			add = function( key, value ) {
				// If value is a function, invoke it and return its value
				value = $.isFunction( value ) ? value() : ( value == null ? "" : value );
				s[ s.length ] = encodeURIComponent( key ) + "=" + encodeURIComponent( value );
			};
	
		// If an array was passed in, assume that it is an array of form elements.
		if ( $.isArray( a ) || ( a.jquery && !$.isPlainObject( a ) ) ) {
			// Serialize the form elements
			$.each( a, function() {
				add( this.name, this.value );
			});
	
		} else {
			// If traditional, encode the "old" way (the way 1.3.2 or older
			// did it), otherwise encode params recursively.
			for ( prefix in a ) {
				that.buildParams( prefix, a[ prefix ], add );
			}
		}
	
		// Return the resulting serialization
		return s.join( "&" ).replace( /%20/g, "+" );
	},
	
	// JQuery 수정버전
	buildParams : function(prefix, obj, add ) {
		var that = this;
		var name;
		var	rbracket = /\[\]$/;
	
		if ( $.isArray( obj ) ) {
			// Serialize array item.
			$.each( obj, function( i, v ) {
				if ( rbracket.test( prefix ) ) {
					// Treat each array item as a scalar.
					add( prefix, v );
	
				} else {
					// Item is non-scalar (array or object), encode its numeric index.
					that.buildParams( prefix + "[" + ( typeof v === "object" ? i : "" ) + "]", v, add );
				}
			});
	
		} else if ( $.type( obj ) === "object" ) {
			// Serialize object item.
			for ( name in obj ) {
				that.buildParams( prefix + "." + name, obj[ name ], add );
			}
	
		} else {
			// Serialize scalar item.
			add( prefix, obj );
		}
	}			
};

ttgames.handlebars = {
	oConfig : {
		oExcludeKeys : {}
	},
	
	init : function(oConfig) {
		this.oConfig = oConfig;
	},
		
	load : function(sTemplateId, sTargetId, oData) {
		var sSource = $('#' + sTemplateId).html();	
		var template = Handlebars.compile(sSource);
		var sHtml = template(oData);
		$('#' + sTargetId).html(sHtml);
	}
};

Handlebars.registerHelper('Header', function(oData) {
	var oExcludeKeys = ttgames.handlebars.oConfig.oExcludeKeys;
	var sResult = '';
	if($.isArray(oData) && oData.length > 0) {
	  	var aTemp = [];
		for (var key in oData[0]) {
			if (!oExcludeKeys[key]) {
				aTemp.push('<th>' + key + '</th>');
			}
		}
		sResult = '<tr>' + aTemp.join('') + '</tr>';
	}
	
	console.log(sResult);
  return new Handlebars.SafeString(sResult);
});
	


