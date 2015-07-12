function deleteAccount(accountId, myAccessToken){
	$.ajax({
		url : "/api/account/",
		type : "DELETE",
		beforeSend: function ( xhr ) {
			xhr.setRequestHeader("myAccountId", accountId);
			xhr.setRequestHeader("myAccessToken", myAccessToken);
		},
		data : '',
		success : function(data) {
			location.reload();
		},
		error : function(data) {
		}
	});
}

function accountNew(){
	if( validateEmpty("#channelId") == false ) return;
	if( validateEmpty("#channelToken") == false ) return;
	if( validateEmpty("#deviceId") == false ) return;
	if( validateEmpty("#name") == false ) return;
	if( validateEmpty("#osType") == false ) return;
	if( validateEmpty("#osVersion") == false ) return;
	if( validateEmpty("#language") == false ) return;
	if( validateEmpty("#country") == false ) return;
	if( validateEmpty("#localTimezone") == false ) return;
	
	freezeForm();

	$.ajax({
		url : "/api/account",
		type : "POST",
		data : {
			channelId:$('#channelId').val(),
			channelToken:$('#channelToken').val(),
			deviceId:$('#deviceId').val(),
			name:$('#name').val(),
			photoUrl:$('#photoUrl').val(),
			osType:$('#osType').val(),
			osVersion:$('#osVersion').val(),
			language:$('#language').val(),
			country:$('#country').val(),
			localTimezone:$('#localTimezone').val()
		},
		success : function(data) {
			clearForm();
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

function modifyAccount() {
	if( validateEmpty("#nickName") == false ) return;
	if( validateEmpty("#gold") == false ) return;
	if( validateEmpty("#cash") == false ) return;
	
	freezeForm();

	$.ajax({
		url : "/api/account/properties",
		type : "POST",
		beforeSend: function ( xhr ) {
			xhr.setRequestHeader("myAccountId", $('#accountId').val() );
			xhr.setRequestHeader("myAccessToken", "[Fantastic 30Games!]" );
		},
		data : {
			nickName:$('#nickName').val(),
			channelId:$('#channelId').val(),
			channelType:$('#channelType').val(),
			profileType:$('#profileType').val(),
			level:$('#level').val(),
			exp:$('#exp').val(),
			apLastYmdt:$('#apLastYmdt').val(),
			apLastValue:$('#apLastValue').val(),
			apMax:$('#apMax').val(),
			gold:$('#gold').val(),
			cash:$('#cash').val(),
			achievementId:$('#achievementId').val(),
			authToken:$('#authToken').val(),
			localTimezone:$('#localTimezone').val()
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

function stageClearAgain(accountId,chapter,stage){
	$.ajax({
		url : "/api/stageClearLog/"+chapter+"/"+stage,
		type : "POST",
		beforeSend: function ( xhr ) {
			xhr.setRequestHeader("myAccountId", accountId);
			xhr.setRequestHeader("myAccessToken", "[Fantastic 30Games!]");
		},
		data : '',
		success : function(data) {
			$('#'+chapter+"-"+stage+"-starNum").text(data["stage"]["starNum"]);
			$('#rewardGold').text(data["rewardGold"]);
			if (data["rewardCard"] != null){
				$('#rewardCard').text("type:" + data["rewardCard"]["type"] + ", level:"+data["rewardCard"]["level"] + ", exp:"+data["rewardCard"]["exp"]);
			}
			else {
				$('#rewardCard').text("None");
			}
			$('#successNotiPanel').removeClass("hidden");
		},
		error : function(data) {
			$('#successNotiPanel').addClass("hidden");
		}
	});
}

function stageClearNew() {
	if( validateEmpty("#chapter") == false ) return;
	if( validateEmpty("#stage") == false ) return;

	freezeForm();

	$.ajax({
		url : "/api/stageClearLog/"+$('#chapter').val()+"/"+$('#stage').val(),
		type : "POST",
		beforeSend: function ( xhr ) {
			xhr.setRequestHeader("myAccountId", $('#accountId').val() );
			xhr.setRequestHeader("myAccessToken", "[Fantastic 30Games!]" );
		},
		success : function(data) {
			clearForm();
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

function deleteHero(heroId, accountId){
	$.ajax({
		url : "/api/hero/"+heroId,
		type : "DELETE",
		beforeSend: function ( xhr ) {
			xhr.setRequestHeader("myAccountId", accountId);
			xhr.setRequestHeader("myAccessToken", "[Fantastic 30Games!]");
		},
		data : '',
		success : function(data) {
			location.reload();
		},
		error : function(data) {
		}
	});
}

function heroNew() {
	if( validateEmpty("#heroType") == false ) return;
	if( validateEmpty("#accountId") == false ) return;
	
	freezeForm();
	$.ajax({
		url : "/api/hero",
		type : "POST",
		beforeSend: function ( xhr ) {
			xhr.setRequestHeader("myAccountId", $('#accountId').val() );
			xhr.setRequestHeader("myAccessToken", "[Fantastic 30Games!]" );
		},
		data : {
			unitType:$('#unitType').val()
		},
		success : function(data) {
			clearForm();
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

function modifyHero() {
	if( validateEmpty("#level") == false ) return;
	if( validateEmpty("#exp") == false ) return;
	if( validateEmpty("#power") == false ) return;
	if( validateEmpty("#agility") == false ) return;
	if( validateEmpty("#intelligence") == false ) return;
	if( validateEmpty("#remainStatPoint") == false ) return;
	
	freezeForm();

	$.ajax({
		url : "/api/hero/"+ $('#heroId').val() +"/properties",
		type : "POST",
		beforeSend: function ( xhr ) {
			xhr.setRequestHeader("myAccountId", $('#accountId').val() );
			xhr.setRequestHeader("myAccessToken", "[Fantastic 30Games!]" );
		},
		data : {
			level:$('#level').val().replace(",",""),
			exp:$('#exp').val().replace(",",""),
			agility:$('#agility').val(),
			intelligence:$('#intelligence').val(),
			remainStatPoint:$('#remainStatPoint').val().replace(",",""),
			power:$('#power').val().replace(",","")
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

function deleteCard(cardId, accountId){
	$.ajax({
		url : "/api/card/"+cardId,
		type : "DELETE",
		beforeSend: function ( xhr ) {
			xhr.setRequestHeader("myAccountId", accountId);
			xhr.setRequestHeader("myAccessToken", "[Fantastic 30Games!]");
		},
		data : '',
		success : function(data) {
			location.reload();
		},
		error : function(data) {
		}
	});
}

function cardNew() {
	if( validateEmpty("#accountId") == false ) return;
	
	freezeForm();

	$.ajax({
		url : "/api/card",
		type : "POST",
		beforeSend: function ( xhr ) {
			xhr.setRequestHeader("myAccountId", $('#accountId').val() );
			xhr.setRequestHeader("myAccessToken", "[Fantastic 30Games!]" );
		},
		data : {
			type:$('#type').val(),
			level:$('#level').val(),
			exp:$('#exp').val()
		},
		success : function(data) {
			clearForm();
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

function modifyCard() {
	if( validateEmpty("#exp") == false ) return;
	if( validateEmpty("#level") == false ) return;
	
	freezeForm();

	$.ajax({
		url : "/api/card/"+ $('#cardId').val() +"/properties",
		type : "POST",
		beforeSend: function ( xhr ) {
			xhr.setRequestHeader("myAccountId", $('#accountId').val() );
			xhr.setRequestHeader("myAccessToken", "[Fantastic 30Games!]" );
		},
		data : {
			exp:$('#exp').val(),
			level:$('#level').val().replace(",","")
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