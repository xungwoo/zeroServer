function modifyAccountStat() {
	if( validateEmpty("#gold") == false ) return;
	if( validateEmpty("#nc") == false ) return;
	
	freezeForm();
	
	$.ajax({
		url : "${rc.contextPath}/balance/accountStat",
		type : "POST",
		data : {
			gold:$('#gold').val(),
			nc:$('#nc').val(),
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

function uploadHeroCard() {
	if( validateEmpty("#csv") == false ) return;
	
	freezeForm();

	$.ajax({
		url : "${rc.contextPath}/balance/heroCard/upload",
		type : "POST",
		data : {
			csv:$('#csv').val()
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

function uploadHeroStat() {
	if( validateEmpty("#csv") == false ) return;
	
	freezeForm();

	$.ajax({
		url : "${rc.contextPath}/balance/heroStat/upload",
		type : "POST",
		data : {
			csv:$('#csv').val()
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

function uploadStageEnemy(){
	if( validateEmpty("#csv") == false ) return;
	
	freezeForm();

	$.ajax({
		url : "${rc.contextPath}/balance/stageEnemy/upload",
		type : "POST",
		data : {
			csv:$('#csv').val()
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

function uploadStageReward() {
	if( validateEmpty("#csv") == false ) return;
	
	freezeForm();
	$.ajax({
		url : "${rc.contextPath}/balance/stageReward/upload",
		type : "POST",
		data : {
			csv:$('#csv').val()
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

function uploadStageStat() {
	if( validateEmpty("#csv") == false ) return;
	
	freezeForm();
	
	$.ajax({
		url : "${rc.contextPath}/balance/stageStat/upload",
		type : "POST",
		data : {
			csv:$('#csv').val()
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

function uploadUnit() {
	if( validateEmpty("#csv") == false ) return;
	
	freezeForm();

	$.ajax({
		url : "${rc.contextPath}/balance/unit/upload",
		type : "POST",
		data : {
			csv:$('#csv').val()
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

function newStageRewardEventCountry() {
	if( validateEmpty("#country") == false ) return;
	
	freezeForm();

	$.ajax({
		url : "${rc.contextPath}/balance/stageRewardEvent/new",
		type : "POST",
		data : {
			country:$('#country').val()
		},
		success : function(data) {
			location.replace("${rc.contextPath}/balance/stageRewardEvent/"+$('#country').val());
		},
		error : function(data) {
			thawForm();
			$('#successNotiPanel').addClass("hidden");
			$('#errorNotiPanel').removeClass("hidden");
			$('#errorReason').val(data);
		}
	});	
}


function updateStageRewardEvent(country) {
	if( validateEmpty("#cardDropRatio") == false ) return;
	if( validateEmpty("#goldBonusScaleFactor") == false ) return;
	if( validateEmpty("#eventCardDropScaleFactor") == false ) return;
	if( validateEmpty("#eventGoldBonusScaleFactor") == false ) return;
	if( validateEmpty("#cClassGcCardRatio") == false ) return;
	if( validateEmpty("#cClassSsCardRatio") == false ) return;
	if( validateEmpty("#cClassSCardRatio") == false ) return;
	if( validateEmpty("#cClassACardRatio") == false ) return;
	if( validateEmpty("#cClassBCardRatio") == false ) return;
	if( validateEmpty("#bClassScaleFactor") == false ) return;
	if( validateEmpty("#aClassScaleFactor") == false ) return;
	if( validateEmpty("#sClassScaleFactor") == false ) return;
	
	freezeForm();

	$.ajax({
		url : "${rc.contextPath}/balance/stageRewardEvent/"+country,
		type : "POST",
		data : {
			cardDropRatio:$('#cardDropRatio').val(),
			goldBonusScaleFactor:$('#goldBonusScaleFactor').val(),
			eventCardDropScaleFactor:$('#eventCardDropScaleFactor').val(),
			eventGoldBonusScaleFactor:$('#eventGoldBonusScaleFactor').val(),
			cClassGcCardRatio:$('#cClassGcCardRatio').val(),
			cClassSsCardRatio:$('#cClassSsCardRatio').val(),
			cClassSCardRatio:$('#cClassSCardRatio').val(),
			cClassACardRatio:$('#cClassACardRatio').val(),
			cClassBCardRatio:$('#cClassBCardRatio').val(),
			bClassScaleFactor:$('#bClassScaleFactor').val(),
			aClassScaleFactor:$('#aClassScaleFactor').val(),
			sClassScaleFactor:$('#sClassScaleFactor').val(),
			eventTime:$('#eventTime').val()
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

function unitNewBalance(country) {
	
	if( validateEmpty("#unitType") == false ) return;
	if( validateEmpty("#levelMax") == false ) return;
	if( validateEmpty("#attackLv1") == false ) return;
	if( validateEmpty("#attackLvMax") == false ) return;
	if( validateEmpty("#attackBezier") == false ) return;
	if( validateEmpty("#attackLimitExceedRate") == false ) return;
	if( validateEmpty("#attackCoolTime") == false ) return;
	if( validateEmpty("#attackRange") == false ) return;
	if( validateEmpty("#attackSpeed") == false ) return;
	if( validateEmpty("#criticalRate") == false ) return;
	if( validateEmpty("#crystal") == false ) return;
	if( validateEmpty("#dpLv1") == false ) return;
	if( validateEmpty("#dpLvMax") == false ) return;
	if( validateEmpty("#dpBezier") == false ) return;
	if( validateEmpty("#dpLimitExceedRate") == false ) return;
	if( validateEmpty("#expLv1") == false ) return;
	if( validateEmpty("#expLvMax") == false ) return;
	if( validateEmpty("#expBezier") == false ) return;
	if( validateEmpty("#expLimitExceedRate") == false ) return;
	if( validateEmpty("#goldLv1") == false ) return;
	if( validateEmpty("#goldLvMax") == false ) return;
	if( validateEmpty("#goldBezier") == false ) return;
	if( validateEmpty("#moneyLimitExceedRate") == false ) return;
	if( validateEmpty("#hpLv1") == false ) return;
	if( validateEmpty("#hpLvMax") == false ) return;
	if( validateEmpty("#hpBezier") == false ) return;
	if( validateEmpty("#hpLimitExceedRate") == false ) return;
	if( validateEmpty("#immuneRate") == false ) return;
	if( validateEmpty("#immuneMagicRate") == false ) return;

	if( validateEmpty("#movePoint") == false ) return;
	if( validateEmpty("#movePointForDie") == false ) return;
	if( validateEmpty("#mp") == false ) return;
	if( validateEmpty("#opCoolTime") == false ) return;
	if( validateEmpty("#width") == false ) return;

	freezeForm();

	$.ajax({
		url : "${rc.contextPath}/balance/unit/new",
		type : "POST",
		data : {
			unitRaceType:$('button[name="unitRaceType"].active').val(),
			unitType:$('#unitType').val(),
			unitRankType:$('button[name="unitRankType"].active').val(),
			unitAttackType:$('button[name="unitAttackType"].active').val(),
			levelMax:$('#levelMax').val(),
			attackLv1:$('#attackLv1').val(),
			attackLvMax:$('#attackLvMax').val(),
			attackBezier:$('#attackBezier').val(),
			attackLimitExceedRate:$('#attackLimitExceedRate').val(),
			attackCoolTime:$('#attackCoolTime').val(),
			attackRange:$('#attackRange').val(),
			attackSpeed:$('#attackSpeed').val(),
			criticalRate:$('#criticalRate').val(),
			crystal:$('#crystal').val(),
			dpLv1:$('#dpLv1').val(),
			dpLvMax:$('#dpLvMax').val(),
			dpBezier:$('#dpBezier').val(),
			dpLimitExceedRate:$('#dpLimitExceedRate').val(),
			expLv1:$('#expLv1').val(),
			expLvMax:$('#expLvMax').val(),
			expBezier:$('#expBezier').val(),
			expLimitExceedRate:$('#expLimitExceedRate').val(),
			goldLv1:$('#goldLv1').val(),
			goldLvMax:$('#goldLvMax').val(),
			goldBezier:$('#goldBezier').val(),
			goldLimitExceedRate:$('#goldLimitExceedRate').val(),
			hpLv1:$('#hpLv1').val(),
			hpLvMax:$('#hpLvMax').val(),
			hpBezier:$('#hpBezier').val(),
			hpLimitExceedRate:$('#hpLimitExceedRate').val(),
			immuneRate:$('#immuneRate').val(),
			immuneMagicRate:$('#immuneMagicRate').val(),
			isAvailableAirAttack:$('button[name="isAvailableAirAttack"].active').val(),
			movePoint:$('#movePoint').val(),
			movePointForDie:$('#movePointForDie').val(),
			opCoolTime:$('#opCoolTime').val(),
			width:$('#width').val(),
			widthCenterX:$('#widthCenterX').val()
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

function distributeUnit(){
	$.ajax({
		url : "${rc.contextPath}/balance/unit/distribute",
		type : "POST",
		success : function(data) {
			$('#revision').text(data["revision"]);
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


function spreadWithFilter() {
	var raceType = "";
	raceType += $('button[name="unitRaceType"][value="Human"].active').val() == undefined ? "0" : "1";
	raceType += $('button[name="unitRaceType"][value="Totemir"].active').val() == undefined ? "0" : "1";
	raceType += $('button[name="unitRaceType"][value="Beastrian"].active').val() == undefined ? "0" : "1";
	raceType += $('button[name="unitRaceType"][value="ElementalNature"].active').val() == undefined ? "0" : "1";
	raceType += $('button[name="unitRaceType"][value="Undead"].active').val() == undefined ? "0" : "1";
	raceType += $('button[name="unitRaceType"][value="Heaven"].active').val() == undefined ? "0" : "1";
	raceType += $('button[name="unitRaceType"][value="Tharsis"].active').val() == undefined ? "0" : "1";

	var rankType = "";
	rankType += $('button[name="unitRankType"][value="C"].active').val() == undefined ? "0" : "1";
	rankType += $('button[name="unitRankType"][value="B"].active').val() == undefined ? "0" : "1";
	rankType += $('button[name="unitRankType"][value="A"].active').val() == undefined ? "0" : "1";
	rankType += $('button[name="unitRankType"][value="S"].active').val() == undefined ? "0" : "1";
	rankType += $('button[name="unitRankType"][value="SS"].active').val() == undefined ? "0" : "1";
	rankType += $('button[name="unitRankType"][value="GodsCrafted"].active').val() == undefined ? "0" : "1";

	var chart = "";
	chart += $('button[name="chart"][value="attackLv1"].active').val() == undefined ? "0" : "1";
	chart += $('button[name="chart"][value="attackLvMax"].active').val() == undefined ? "0" : "1";
	chart += $('button[name="chart"][value="dpLv1"].active').val() == undefined ? "0" : "1";
	chart += $('button[name="chart"][value="dpLvMax"].active').val() == undefined ? "0" : "1";
	chart += $('button[name="chart"][value="expLv1"].active').val() == undefined ? "0" : "1";
	chart += $('button[name="chart"][value="expLvMax"].active').val() == undefined ? "0" : "1";
	chart += $('button[name="chart"][value="goldLv1"].active').val() == undefined ? "0" : "1";
	chart += $('button[name="chart"][value="goldLvMax"].active').val() == undefined ? "0" : "1";
	chart += $('button[name="chart"][value="hpLv1"].active').val() == undefined ? "0" : "1";
	chart += $('button[name="chart"][value="hpLvMax"].active').val() == undefined ? "0" : "1";
	chart += $('button[name="chart"][value="crystal"].active').val() == undefined ? "0" : "1";
	chart += $('button[name="chart"][value="attackCoolTime"].active').val() == undefined ? "0" : "1";
	chart += $('button[name="chart"][value="attackRange"].active').val() == undefined ? "0" : "1";
	chart += $('button[name="chart"][value="attackSpeed"].active').val() == undefined ? "0" : "1";
	chart += $('button[name="chart"][value="movePoint"].active').val() == undefined ? "0" : "1";
	chart += $('button[name="chart"][value="opCoolTime"].active').val() == undefined ? "0" : "1";

	var url = "${rc.contextPath}/balance/unit/spread/" + raceType +"/" + rankType + "/" + chart;
	location.replace(url);
}

function buttonCheckAll(name) {
	$('button[name="'+name+'"]').addClass('active');
}

function buttonClearAll(name) {
	$('button[name="'+name+'"]').removeClass('active');
}