function newVersion() {
	if( validateEmpty("#appVersion") == false ) return;
	if( validateEmpty("#resourceVersion") == false ) return;
	
	freezeForm();
	
	$.ajax({
		url : "${rc.contextPath}/news/versions",
		type : "POST",
		data : {
			appVersion:$('#appVersion').val(),
			resourceVersion:$('#resourceVersion').val(),
			forceUpdate:$('#forceUpdate').is(':checked')
		},
		success : function(data) {
			thawForm();
			$('#successNotiPanel').removeClass("hidden");
			$('#errorNotiPanel').addClass("hidden");
		},
		error : function(data) {
			thawForm();
			$('#successNotiPanel').addClass("hidden");
			$('#errorNotiPanel').removeClass("hidden");
			$('#errorReason').val(data);
		}
	});
}


function newNoticeCountry() {
	if( validateEmpty("#country") == false ) return;
	
	freezeForm();

	$.ajax({
		url : "${rc.contextPath}/news/notice/new/country",
		type : "POST",
		data : {
			country:$('#country').val()
		},
		success : function(data) {
			location.replace("${rc.contextPath}/news/notice/"+$('#country').val());
		},
		error : function(data) {
			thawForm();
			$('#successNotiPanel').addClass("hidden");
			$('#errorNotiPanel').removeClass("hidden");
			$('#errorReason').val(data);
		}
	});	
}

function newNotice(country) {
	if( validateEmpty("#content") == false ) return;

	freezeForm();

	$.ajax({
		url : "${rc.contextPath}/news/notice/" + country + "/new",
		type : "POST",
		data : {
			title:$('#title').val(),
			content:$('#content').val()
		},
		success : function(data) {
			location.replace("${rc.contextPath}/news/notice/"+country);
		},
		error : function(data) {
			thawForm();
			$('#successNotiPanel').addClass("hidden");
			$('#errorNotiPanel').removeClass("hidden");
			$('#errorReason').val(data);
		}
	});	
}

function updateNotice(createdAt, country) {
	if( validateEmpty("#content-"+createdAt) == false ) return;

	freezeForm();

	$.ajax({
		url : "${rc.contextPath}/news/notice/" + country + "/update",
		type : "POST",
		data : {
			title:$('#title-'+createdAt).val(),
			content:$('#content-'+createdAt).val(),
			createdAt:$('#createdAt-'+createdAt).val()
		},
		success : function(data) {
			thawForm();
			$('#successNotiPanel').removeClass("hidden");
			$('#errorNotiPanel').addClass("hidden");
		},
		error : function(data) {
			thawForm();
			$('#successNotiPanel').addClass("hidden");
			$('#errorNotiPanel').removeClass("hidden");
			$('#errorReason').val(data);
		}
	});	
}

function previewNotice(titleId, contentId, iframeId) {
	document.getElementById(iframeId).contentWindow.document.open();
	document.getElementById(iframeId).contentWindow.document.close();
	
	$.ajax({
		url : "${rc.contextPath}/news/notice/preview",
		type : "POST",
		data : {
			title:$("#"+titleId).val(),
			content:$("#"+contentId).val()
		},
		success : function(data) {
			document.getElementById(iframeId).contentWindow.document.open();
			document.getElementById(iframeId).contentWindow.document.write(data);
			document.getElementById(iframeId).contentWindow.document.close();
		},
		error : function(data) {
		}
	});	
}