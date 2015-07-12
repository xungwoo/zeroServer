function unitNewBalance(country) {
	if( ttgames.form.validateEmpty("#unitType") == false ) return;
	if( ttgames.form.validateEmpty("#levelMax") == false ) return;
	
	if( ttgames.form.validateEmpty("#attackLv1") == false ) return;
	if( ttgames.form.validateEmpty("#attackLvMax") == false ) return;
	if( ttgames.form.validateEmpty("#attackBezier") == false ) return;
	if( ttgames.form.validateEmpty("#attackLimitExceedRate") == false ) return;
	
	if( ttgames.form.validateEmpty("#spellLv1") == false ) return;
	if( ttgames.form.validateEmpty("#spellLvMax") == false ) return;
	if( ttgames.form.validateEmpty("#spellBezier") == false ) return;
	if( ttgames.form.validateEmpty("#spellLimitExceedRate") == false ) return;
	
	if( ttgames.form.validateEmpty("#dpLv1") == false ) return;
	if( ttgames.form.validateEmpty("#dpLvMax") == false ) return;
	if( ttgames.form.validateEmpty("#dpBezier") == false ) return;
	if( ttgames.form.validateEmpty("#dpLimitExceedRate") == false ) return;
	
	if( ttgames.form.validateEmpty("#dmLv1") == false ) return;
	if( ttgames.form.validateEmpty("#dmLvMax") == false ) return;
	if( ttgames.form.validateEmpty("#dmBezier") == false ) return;
	if( ttgames.form.validateEmpty("#dmLimitExceedRate") == false ) return;
	
	if( ttgames.form.validateEmpty("#goldLv1") == false ) return;
	if( ttgames.form.validateEmpty("#goldLvMax") == false ) return;
	if( ttgames.form.validateEmpty("#goldBezier") == false ) return;
	if( ttgames.form.validateEmpty("#goldLimitExceedRate") == false ) return;

	if( ttgames.form.validateEmpty("#hpLv1") == false ) return;
	if( ttgames.form.validateEmpty("#hpLvMax") == false ) return;
	if( ttgames.form.validateEmpty("#hpBezier") == false ) return;
	if( ttgames.form.validateEmpty("#hpLimitExceedRate") == false ) return;
	
	if( ttgames.form.validateEmpty("#powerLv1") == false ) return;
	if( ttgames.form.validateEmpty("#powerLvMax") == false ) return;
	if( ttgames.form.validateEmpty("#powerBezier") == false ) return;
	if( ttgames.form.validateEmpty("#powerLimitExceedRate") == false ) return;
	
	if( ttgames.form.validateEmpty("#massLv1") == false ) return;
	if( ttgames.form.validateEmpty("#massLvMax") == false ) return;
	if( ttgames.form.validateEmpty("#massBezier") == false ) return;
	if( ttgames.form.validateEmpty("#massLimitExceedRate") == false ) return;
	
	if( ttgames.form.validateEmpty("#moveSpeed") == false ) return;
	if( ttgames.form.validateEmpty("#attackCoolTime") == false ) return;
	if( ttgames.form.validateEmpty("#attackRange") == false ) return;
	//if( ttgames.form.validateEmpty("#attackSpeed") == false ) return;
	if( ttgames.form.validateEmpty("#criticalRate") == false ) return;
	if( ttgames.form.validateEmpty("#criticalPower") == false ) return;
	
	//if( ttgames.form.validateEmpty("#expLv1") == false ) return;
	//if( ttgames.form.validateEmpty("#expLvMax") == false ) return;
	//if( ttgames.form.validateEmpty("#expBezier") == false ) return;
	//if( ttgames.form.validateEmpty("#expLimitExceedRate") == false ) return;

	if( ttgames.form.validateEmpty("#immuneRate") == false ) return;
	if( ttgames.form.validateEmpty("#immuneMagicRate") == false ) return;
	if( ttgames.form.validateEmpty("#antiImmuneRate") == false ) return;
	if( ttgames.form.validateEmpty("#antiImmuneMagicRate") == false ) return;
	if( ttgames.form.validateEmpty("#damageAbsorption") == false ) return;
	if( ttgames.form.validateEmpty("#skillCoolTime") == false ) return;

	ttgames.form.freezeForm();

	var oData = {
		unitType:$('#unitType').val(),
		seedType:$('button[name="seedType"].active').val(),
		rankType:$('button[name="rankType"].active').val(),
		attackType:$('button[name="attackType"].active').val(),
		levelMax:$('#levelMax').val(),
		
		attackLv1:$('#attackLv1').val(),
		attackLvMax:$('#attackLvMax').val(),
		attackBezier:$('#attackBezier').val(),
		attackLimitExceedRate:$('#attackLimitExceedRate').val(),
		
		spellLv1:$('#spellLv1').val(),
		spellLvMax:$('#spellLvMax').val(),
		spellBezier:$('#spellBezier').val(),
		spellLimitExceedRate:$('#spellLimitExceedRate').val(),

		dpLv1:$('#dpLv1').val(),
		dpLvMax:$('#dpLvMax').val(),
		dpBezier:$('#dpBezier').val(),
		dpLimitExceedRate:$('#dpLimitExceedRate').val(),
		
		dmLv1:$('#dmLv1').val(),
		dmLvMax:$('#dmLvMax').val(),
		dmBezier:$('#dmBezier').val(),
		dmLimitExceedRate:$('#dmLimitExceedRate').val(),

		goldLv1:$('#goldLv1').val(),
		goldLvMax:$('#goldLvMax').val(),
		goldBezier:$('#goldBezier').val(),
		goldLimitExceedRate:$('#goldLimitExceedRate').val(),

		hpLv1:$('#hpLv1').val(),
		hpLvMax:$('#hpLvMax').val(),
		hpBezier:$('#hpBezier').val(),
		hpLimitExceedRate:$('#hpLimitExceedRate').val(),
		
		powerLv1:$('#powerLv1').val(),
		powerLvMax:$('#powerLvMax').val(),
		powerBezier:$('#powerBezier').val(),
		powerLimitExceedRate:$('#powerLimitExceedRate').val(),
		
		massLv1:$('#massLv1').val(),
		massLvMax:$('#massLvMax').val(),
		massBezier:$('#massBezier').val(),
		massLimitExceedRate:$('#massLimitExceedRate').val(),

		moveSpeed:$('#moveSpeed').val(),
		attackCoolTime:$('#attackCoolTime').val(),
		attackRange:$('#attackRange').val(),
		
		//attackSpeed:$('#attackSpeed').val(),
		criticalRate:$('#criticalRate').val(),
		criticalPower:$('#criticalPower').val(),
		
		//expLv1:$('#expLv1').val(),
		//expLvMax:$('#expLvMax').val(),
		//expBezier:$('#expBezier').val(),
		//expLimitExceedRate:$('#expLimitExceedRate').val(),
		immuneRate:$('#immuneRate').val(),
		immuneMagicRate:$('#immuneMagicRate').val(),
		antiImmuneRate:$('#antiImmuneRate').val(),
		antiImmuneMagicRate:$('#antiImmuneMagicRate').val(),
		isAvailableAirAttack: ($('button[name="isAvailableAirAttack"].active').val() ? 1 : 0),
		
		skillCoolTime:$('#skillCoolTime').val(),
		isCurrentRevision:0
	}
	
	ttgames.form.ajax(ttgames.sContextRoot + "/balance/unit", 'POST', oData);
}
