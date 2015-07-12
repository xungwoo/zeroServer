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
								Equipment Generation Simulator
								<small>
									<i class="icon-double-angle-right"></i>
									장비 생성을 테스트 합니다.
								</small>
							</h1>
						</div><!-- /.page-header -->

						<div class="row">
							<div class="col-xs-12">
								<!-- PAGE CONTENT BEGINS -->


      <div class="row">
        <div class="col-lg-6">
          <div class="input-group">
            <input id="equipmentType" type="text" class="form-control">
            <span class="input-group-btn">
              <button id="btnSearch" class="btn btn-default" type="button">Go!</button>
            </span>
          </div><!-- /input-group -->
        </div><!-- /.col-lg-6 -->
      </div><!-- /.row -->

      <hr/>

      <div class="panel panel-default" id="resultPanel" style="display:none">
        <div class="panel-heading">
          <h3 id="eqName" class="panel-title">판다 펀치</h3>
        </div>
        <div class="panel-body">

          <div class="media">
            <div class="pull-left">
              <img class="media-object" id="eqThumb" src="http://dev.30games.co.kr/zero/eq/1100001.png">
              <div align="center">
                <span id="star1">★</span>
                <span id="star2">★</span>
                <span id="star3">★</span>
                <span id="star4">★</span>
                <span id="star5">★</span>
              </div>
            </div>
            <div class="media-body" id="stats">
            </div>
          </div>
        </div>
      </div>

      <div id="noSearchResult" style="display:none">
        <p class="text-center lead">No search result.</p>        
      </div>

      <div id="loading" align="center">
          <div id="spinner" style="display:none"></div>
      </div>




								<!-- PAGE CONTENT ENDS -->
							</div><!-- /.col -->
						</div><!-- /.row -->
					</div><!-- /.page-content -->
	</@layout.put>

	<@layout.put block="page-javascript-plugin">
		<!-- page specific plugin scripts -->
	</@layout.put>

	<@layout.put block="page-javascript-inline">
		<!-- inline scripts related to this page -->
		<script type="text/javascript">
		
							EquipStatType = {
    None : 0,
    Attack : 1,
    AttackBoost : 2,
    AttackAuraBoost : 3,
    PoisonAttack : 4,
    PoisonDuration : 5,
    PoisonResist : 6,
    PoisonResistIgnore : 7,
    IceSpeed : 8,
    IceDuration : 9,
    IceRate : 10,
    IceResist : 11,
    IceResistIgnore : 12,
    StunRate : 13,
    StunDuration : 14,
    StunResist : 15,
    StunResistIgnore : 16,
    Spell : 17,
    SpellBoost : 18,
    SpellAuraBoost : 19,
    Hp : 20,
    HpBoost : 21,
    HpRegenBoost : 22,
    HpAuraBoost : 23,
    Mp : 24,
    MpBoost : 25,
    MpRegenBoost : 26,
    MpAuraBoost : 27,
    DefensePhy : 28,
    DefensePhyBoost : 29,
    DefensePhyAuraBoost : 30,
    DefensePhyIgnore : 31,
    DefensePhyIgnoreBoost : 32,
    DefenseSpell : 33,
    DefenseSpellBoost : 34,
    DefenseSpellAuraBoost : 35,
    DefenseSpellIgnore : 36,
    DefenseSpellIgnoreBoost : 37,
    AttackSpeedBoost : 38,
    AttackSpeedAuraBoost : 39,
    MoveSpeedBoost : 40,
    MoveSpeedAuraBoost : 41,
    AttackRange : 42,
    CriticalRateBoost : 43,
    CriticalDamageBoost : 44,
    Blood : 45,
    SkillLvUp : 46,
    SkillCooltimeDecrease : 47,
    GoldDropRateBoost : 48,
    ItemDropRateBoost : 49,
}


// equipment meta model class
var metaEquip = new function() {
    this.raw = [];
    this.getEquipment = function (eqType) {
        for (i = 0; i < this.raw.length; i++) {             
            if (this.raw[i].equipmentType == eqType) {
                return this.raw[i];
            }
        }
        return null;
    };
};

// view
var view = new function() {
    this.setStar = function(grade) {
        $("#star1").hide();
        $("#star2").hide();
        $("#star3").hide();
        $("#star4").hide();
        $("#star5").hide();

        if (grade >= 1) {
            $("#star1").show();
        }
        if (grade >= 2) {
            $("#star2").show();
        }
        if (grade >= 3) {
            $("#star3").show();
        }
        if (grade >= 4) {
            $("#star4").show();
        }
        if (grade >= 5) {
            $("#star5").show();
        }
    }

    this.getCategoryName = function(meta, equipment) {
        switch (meta.eqClass) {
            case 2: return "rare";
            case 3: return "unique";
            case 4: return "set";
        }

        if (equipment.decoration1 > 0 || equipment.decoration2) {
            return "magic";
        }

        return "normal";
    }

    this.displayEquipment = function(meta, equipment) {
        this.setStar(meta.grade);
        
        var deco = "";
        if (equipment.decoration1 > 0) {
        	deco += this.getDecoText(equipment.decoration1%100);
        	deco += " ";
        }
        if (equipment.decoration2 > 0) {
        	deco += this.getDecoText(equipment.decoration2%100);
        	deco += " ";
        }

        $("#eqName").html(deco + meta.name+" <span class=\"badge\">"+ this.getCategoryName(meta, equipment) +"</span>");
        $("#eqThumb").attr("src", "http://dev.30games.co.kr/zero/eq/" + meta.image + ".png");
        $("#stats").html(this.getEquipStatString(equipment));
    }

    this.getEquipStatString = function(equipment) {
        var sb = new StringBuilder();

        sb.append("<ul class=\"list-group\">\n");
        for (i = 0; i < equipment.stats.length; i++) {             
            var stat = equipment.stats[i];
            if (stat.baseStat == 1) {
                sb.append("<li class=\"list-group-item list-group-item-warning\" style=\"background-color:#FFFFCC\">");
            }
            else {
                sb.append("<li class=\"list-group-item\">");
            }

            this.getEquipStat(stat, 1, sb);
            sb.append("</li>\n");
        }

        sb.append("</ul>\n");
        return sb.toString();
    }


    this.getTextMin = function() {
        return "최소";
    }

    this.getTextMax = function() {
        return "최대";
    }

    this.getTextSec = function() {
        return "초";
    }

    this.getStatTypeText = function(type) {
        return this.getStatTypeTextInner(type) + " ";
    }
    
    this.getDecoText = function(type) {
    
    	switch(type) {
			case 1: return "강력한";
			case 2: return "분노한";
			case 3: return "사나운";
			case 4: return "독기서린";
			case 5: return "독기서린";
			case 6: return "해독의";
			case 8: return "얼어붙은";
			case 9: return "얼어붙은";
			case 10: return "얼어붙은";
			case 11: return "따뜻한";
			case 13: return "기절의";
			case 14: return "기절의";
			case 15: return "강인한";
			case 17: return "주문의";
			case 18: return "정신의";
			case 19: return "현명한";
			case 20: return "튼튼한";
			case 21: return "건강의";
			case 22: return "회복의";
			case 23: return "불굴의";
			case 24: return "지혜의";
			case 25: return "집중의";
			case 26: return "명상의";
			case 27: return "명석한";
			case 28: return "방어의";
			case 29: return "굳센";
			case 30: return "보호의";
			case 31: return "관통의";
			case 32: return "방어를 꿰뚫는";
			case 33: return "마법 방어의";
			case 34: return "가호의";
			case 35: return "현자의";
			case 36: return "마법 관통의";
			case 37: return "마법을 꿰뚫는";
			case 38: return "민첩한";
			case 39: return "날렵한";
			case 40: return "날쌘";
			case 41: return "신속한";
			case 43: return "예리한";
			case 44: return "치명적인";
			case 45: return "흡혈의";
			case 46: return "깨달은";
			case 47: return "자유의";
			case 48: return "금빛";
			case 49: return "보물의";    	
    	}
    	
    	return "";
	}    

    this.getStatTypeTextInner = function(type) {
        switch(type) {
            case EquipStatType.Attack: return "공격력";
            case EquipStatType.AttackBoost: return "공격력 증가";
            case EquipStatType.AttackAuraBoost: return "아군 공격력 증가";
            case EquipStatType.PoisonAttack: return "독 공격";
            case EquipStatType.PoisonDuration: return "지속";
            case EquipStatType.PoisonResist: return "독 저항";
            case EquipStatType.PoisonResistIgnore: return "독 저항 무시";
            case EquipStatType.IceSpeed: return "빙결";
            case EquipStatType.IceSpeed.post: return "느려짐";
            case EquipStatType.IceDuration: return "지속";
            case EquipStatType.IceRate: return "확율";
            case EquipStatType.IceResist: return "빙결 저항";
            case EquipStatType.IceResistIgnore: return "빙결 저항 무시";
            case EquipStatType.StunRate: return "스턴 확율";
            case EquipStatType.StunDuration: return "지속";
            case EquipStatType.StunResist: return "스턴 저항";
            case EquipStatType.StunResistIgnore: return "스턴 저항 무시";
            case EquipStatType.Spell: return "주문력";
            case EquipStatType.SpellBoost: return "주문력 증가";
            case EquipStatType.SpellAuraBoost: return "아군 주문력 증가";
            case EquipStatType.Hp: return "체력";
            case EquipStatType.HpBoost: return "체력 증가";
            case EquipStatType.HpRegenBoost: return "체력 회복 증가";
            case EquipStatType.HpAuraBoost: return "아군 체력 증가";
            case EquipStatType.Mp: return "마나";
            case EquipStatType.MpBoost: return "마나 증가";
            case EquipStatType.MpRegenBoost: return "마나 회복 증가";
            case EquipStatType.MpAuraBoost: return "아군 마나 증가";
            case EquipStatType.DefensePhy: return "물리 방어력";
            case EquipStatType.DefensePhyBoost: return "물리 방어력 증가";
            case EquipStatType.DefensePhyAuraBoost: return "아군 물리 방어력 증가";
            case EquipStatType.DefensePhyIgnore: return "물리 방어력 무시";
            case EquipStatType.DefensePhyIgnoreBoost: return "물리 방어력 무시";
            case EquipStatType.DefenseSpell: return "주문 방어력";
            case EquipStatType.DefenseSpellBoost: return "주문 방어력 증가";
            case EquipStatType.DefenseSpellAuraBoost: return "아군 주문 방어력 증가";
            case EquipStatType.DefenseSpellIgnore: return "주문 방어력 무시";
            case EquipStatType.DefenseSpellIgnoreBoost: return "주문 방어력 무시";
            case EquipStatType.AttackSpeedBoost: return "공격 속도 증가";
            case EquipStatType.AttackSpeedAuraBoost: return "아군 공격 속도 증가";
            case EquipStatType.MoveSpeedBoost: return "이동 속도 증가";
            case EquipStatType.MoveSpeedAuraBoost: return "아군 이동 속도 증가";
            case EquipStatType.AttackRange: return "공격 범위";
            case EquipStatType.CriticalRateBoost: return "치명타 확율 증가";
            case EquipStatType.CriticalDamageBoost: return "치명타 데미지 증가";
            case EquipStatType.Blood: return "흡혈 공격력의 ";
            case EquipStatType.SkillLvUp: return "스킬 레벨 업";
            case EquipStatType.SkillCooltimeDecrease: return "스킬 쿨타임 감소";
            case EquipStatType.GoldDropRateBoost: return "골드 획득량 증가";
            case EquipStatType.ItemDropRateBoost: return "아이템 획득률 증가";            
        }
    }

    this.getEquipStat = function(stat, statScaler, sb) {
        switch (stat.type) {
            case EquipStatType.Attack: this.getEquipStatAttack(stat, statScaler, sb); break;
            case EquipStatType.AttackBoost: this.getEquipStatAttackBoost(stat, statScaler, sb); break;
            case EquipStatType.AttackAuraBoost: this.getEquipStatAttackAuraBoost(stat, statScaler, sb); break;
            case EquipStatType.PoisonAttack: this.getEquipStatPoisonAttack(stat, statScaler, sb); break;
            case EquipStatType.PoisonResist: this.getEquipStatPoisonResist(stat, statScaler, sb); break;
            case EquipStatType.PoisonResistIgnore: this.getEquipStatPoisonResistIgnore(stat, statScaler, sb); break;
            case EquipStatType.IceSpeed: this.getEquipStatIceSpeed(stat, statScaler, sb); break;
            case EquipStatType.IceResist: this.getEquipStatIceResist(stat, statScaler, sb); break;
            case EquipStatType.IceResistIgnore: this.getEquipStatIceResistIgnore(stat, statScaler, sb); break;
            case EquipStatType.StunRate: this.getEquipStatStunRate(stat, statScaler, sb); break;
            case EquipStatType.StunResist: this.getEquipStatStunResist(stat, statScaler, sb); break;
            case EquipStatType.StunResistIgnore: this.getEquipStatStunResistIgnore(stat, statScaler, sb); break;
            case EquipStatType.Spell: this.getEquipStatSpell(stat, statScaler, sb); break;
            case EquipStatType.SpellBoost: this.getEquipStatSpellBoost(stat, statScaler, sb); break;
            case EquipStatType.SpellAuraBoost: this.getEquipStatSpellAuraBoost(stat, statScaler, sb); break;
            case EquipStatType.Hp: this.getEquipStatHp(stat, statScaler, sb); break;
            case EquipStatType.HpBoost: this.getEquipStatHpBoost(stat, statScaler, sb); break;
            case EquipStatType.HpRegenBoost: this.getEquipStatHpRegenBoost(stat, statScaler, sb); break;
            case EquipStatType.HpAuraBoost: this.getEquipStatHpAuraBoost(stat, statScaler, sb); break;
            case EquipStatType.Mp: this.getEquipStatMp(stat, statScaler, sb); break;
            case EquipStatType.MpBoost: this.getEquipStatMpBoost(stat, statScaler, sb); break;
            case EquipStatType.MpRegenBoost: this.getEquipStatMpRegenBoost(stat, statScaler, sb); break;
            case EquipStatType.MpAuraBoost: this.getEquipStatMpAuraBoost(stat, statScaler, sb); break;
            case EquipStatType.DefensePhy: this.getEquipStatDefensePhy(stat, statScaler, sb); break;
            case EquipStatType.DefensePhyBoost: this.getEquipStatDefensePhyBoost(stat, statScaler, sb); break;
            case EquipStatType.DefensePhyAuraBoost: this.getEquipStatDefensePhyAuraBoost(stat, statScaler, sb); break;
            case EquipStatType.DefensePhyIgnore: this.getEquipStatDefensePhyIgnore(stat, statScaler, sb); break;
            case EquipStatType.DefensePhyIgnoreBoost: this.getEquipStatDefensePhyIgnoreBoost(stat, statScaler, sb); break;
            case EquipStatType.DefenseSpell: this.getEquipStatDefenseSpell(stat, statScaler, sb); break;
            case EquipStatType.DefenseSpellBoost: this.getEquipStatDefenseSpellBoost(stat, statScaler, sb); break;
            case EquipStatType.DefenseSpellAuraBoost: this.getEquipStatDefenseSpellAuraBoost(stat, statScaler, sb); break;
            case EquipStatType.DefenseSpellIgnore: this.getEquipStatDefenseSpellIgnore(stat, statScaler, sb); break;
            case EquipStatType.DefenseSpellIgnoreBoost: this.getEquipStatDefenseSpellIgnoreBoost(stat, statScaler, sb);break;
            case EquipStatType.AttackSpeedBoost: this.getEquipStatAttackSpeedBoost(stat, statScaler, sb); break;
            case EquipStatType.AttackSpeedAuraBoost: this.getEquipStatAttackSpeedAuraBoost(stat, statScaler, sb); break;
            case EquipStatType.MoveSpeedBoost: this.getEquipStatMoveSpeedBoost(stat, statScaler, sb); break;
            case EquipStatType.MoveSpeedAuraBoost: this.getEquipStatMoveSpeedAuraBoost(stat, statScaler, sb); break;
            case EquipStatType.AttackRange: this.getEquipStatAttackRange(stat, statScaler, sb); break;
            case EquipStatType.CriticalRateBoost: this.getEquipStatCriticalRateBoost(stat, statScaler, sb); break;
            case EquipStatType.CriticalDamageBoost: this.getEquipStatCriticalDamageBoost(stat, statScaler, sb); break;
            case EquipStatType.Blood: this.getEquipStatBlood(stat, statScaler, sb); break;
            case EquipStatType.SkillLvUp: this.getEquipStatSkillLvUp(stat, statScaler, sb); break;
            case EquipStatType.SkillCooltimeDecrease: this.getEquipStatSkillCooltimeDecrease(stat, statScaler, sb); break;
            case EquipStatType.GoldDropRateBoost: this.getEquipStatGoldDropRateBoost(stat, statScaler, sb); break;
            case EquipStatType.ItemDropRateBoost: this.getEquipStatItemDropRateBoost(stat, statScaler, sb); break;            
        }
    }

    this.getEquipStatAttack = function(stat, statScaler, sb) {
        var value1 = (stat.min * statScaler).toFixed(0);
        var value2 = (stat.max * statScaler).toFixed(0);

        sb.append(this.getStatTypeText(EquipStatType.Attack));
        sb.append("+");
        sb.append(value1);
        sb.append(" ~ ");
        sb.append(value2);
    }       

    this.getEquipStatAttackBoost = function(stat, statScaler, sb) {
        var value = ((stat.min * statScaler - 1) * 100).toFixed(0);
        sb.append(this.getStatTypeText(EquipStatType.AttackBoost));
        sb.append(value);
        sb.append("%");
    }


    this.getEquipStatAttackAuraBoost = function(stat, statScaler, sb) {
        var value = ((stat.min * statScaler-1) * 100).toFixed(0);
        sb.append(this.getStatTypeText(EquipStatType.AttackAuraBoost));
        sb.append(value);
        sb.append("%");
    }

    this.getEquipStatPoisonAttack = function(stat, statScaler, sb) {
        var value = (stat.min * statScaler).toFixed(0);
        sb.append(this.getStatTypeText(EquipStatType.PoisonAttack));
        sb.append(value);
        sb.append(", ");

        var duration = (stat.duration * statScaler).toFixed(1);;
        sb.append(this.getStatTypeText(EquipStatType.PoisonDuration));
        sb.append(duration);
        sb.append(this.getTextSec());
    }

    this.getEquipStatPoisonResist = function(stat, statScaler, sb) {
        var value = (stat.min * statScaler).toFixed(0);
        sb.append(this.getStatTypeText(EquipStatType.PoisonResist));
        sb.append(value);
    }

    this.getEquipStatPoisonResistIgnore = function(stat, statScaler, sb) {
        var value = (stat.min * statScaler).toFixed(0);
        sb.append(this.getStatTypeText(EquipStatType.PoisonResistIgnore));
        sb.append(value);
    }

    this.getEquipStatIceSpeed = function(stat, statScaler, sb) {
        var value = (stat.min * statScaler * 100).toFixed(0);
        sb.append(this.getStatTypeText(EquipStatType.IceSpeed));
        sb.append(value);
        sb.append("% ");
        sb.append("느려짐");
        sb.append(", ");

        var duration = (stat.duration * statScaler).toFixed(0);
        sb.append(this.getStatTypeText(EquipStatType.IceDuration));
        sb.append(duration);
        sb.append(this.getTextSec());
        sb.append(", ");

        var statRate = (stat.statRate * statScaler * 100).toFixed(1);
        sb.append(this.getStatTypeText(EquipStatType.IceRate));
        sb.append(statRate);
        sb.append("%");
    }

    this.getEquipStatIceResist = function(stat, statScaler, sb) {
        var value = (stat.min * statScaler).toFixed(0);
        sb.append(this.getStatTypeText(EquipStatType.IceResist));
        sb.append(value);
    }

    this.getEquipStatIceResistIgnore = function(stat, statScaler, sb) {
        var value = (stat.min * statScaler).toFixed(0);
        sb.append(this.getStatTypeText(EquipStatType.IceResistIgnore));
        sb.append(value);
    }

    this.getEquipStatStunRate = function(stat, statScaler, sb) {
        var statRate = (stat.statRate * statScaler * 100).toFixed(1);
        sb.append(this.getStatTypeText(EquipStatType.StunRate));
        sb.append(statRate);
        sb.append("%, ");

        var duration = (stat.duration * statScaler).toFixed(0);
        sb.append(this.getStatTypeText(EquipStatType.StunDuration));
        sb.append(duration);
        sb.append(this.getTextSec());
    }

    this.getEquipStatStunResist = function(stat, statScaler, sb) {
        var value = (stat.min * statScaler).toFixed(0);
        sb.append(this.getStatTypeText(EquipStatType.StunResist));
        sb.append(value);
    }

    this.getEquipStatStunResistIgnore = function(stat, statScaler, sb) {
        var value = (stat.min * statScaler).toFixed(0);
        sb.append(this.getStatTypeText(EquipStatType.StunResistIgnore));
        sb.append(value);
    }

    this.getEquipStatSpell = function(stat, statScaler, sb) {
        var value1 = (stat.min * statScaler).toFixed(0);
        var value2 = (stat.max * statScaler).toFixed(0);;

        sb.append(this.getStatTypeText(EquipStatType.Spell));
        sb.append("+");
        sb.append(value1);
        sb.append(" ~ ");
        sb.append(value2);
    }

    this.getEquipStatSpellBoost = function(stat, statScaler, sb) {
        var value = ((stat.min * statScaler - 1) * 100).toFixed(0);
        sb.append(this.getStatTypeText(EquipStatType.SpellBoost));
        sb.append(value);
        sb.append("%");
    }

    this.getEquipStatSpellAuraBoost = function(stat, statScaler, sb) {
        var value = ((stat.min * statScaler - 1) * 100).toFixed(0);
        sb.append(this.getStatTypeText(EquipStatType.SpellAuraBoost));
        sb.append(value);
        sb.append("%");
    }

    this.getEquipStatHp = function(stat, statScaler, sb) {
        var value = (stat.min * statScaler).toFixed(0);
        sb.append(this.getStatTypeText(EquipStatType.Hp));
        sb.append("+");
        sb.append(value);
    }

    this.getEquipStatHpBoost = function(stat, statScaler, sb) {
        var value = ((stat.min * statScaler - 1) * 100).toFixed(0);
        sb.append(this.getStatTypeText(EquipStatType.HpBoost));
        sb.append(value);
        sb.append("%");
    }

    this.getEquipStatHpRegenBoost = function(stat, statScaler, sb) {
        var value = ((stat.min * statScaler - 1) * 100).toFixed(0);
        sb.append(this.getStatTypeText(EquipStatType.HpRegenBoost));
        sb.append(value);
        sb.append("%");
    }

    this.getEquipStatHpAuraBoost = function(stat, statScaler, sb) {
        var value = ((stat.min * statScaler - 1) * 100).toFixed(0);
        sb.append(this.getStatTypeText(EquipStatType.HpAuraBoost));
        sb.append(value);
        sb.append("%");
    }

    this.getEquipStatMp = function(stat, statScaler, sb) {
        var value = (stat.min * statScaler).toFixed(0);
        sb.append(this.getStatTypeText(EquipStatType.Mp));
        sb.append("+");
        sb.append(value);
    }

    this.getEquipStatMpBoost = function(stat, statScaler, sb) {
        var value = ((stat.min * statScaler - 1) * 100).toFixed(0);
        sb.append(this.getStatTypeText(EquipStatType.MpBoost));
        sb.append(value);
        sb.append("%");
    }

    this.getEquipStatMpRegenBoost = function(stat, statScaler, sb) {
        var value = ((stat.min * statScaler - 1) * 100).toFixed(0);
        sb.append(this.getStatTypeText(EquipStatType.MpRegenBoost));
        sb.append(value);
        sb.append("%");
    }

    this.getEquipStatMpAuraBoost = function(stat, statScaler, sb) {
        var value = ((stat.min * statScaler - 1) * 100).toFixed(0);
        sb.append(this.getStatTypeText(EquipStatType.MpAuraBoost));
        sb.append(value);
        sb.append("%");
    }

    this.getEquipStatDefensePhy = function(stat, statScaler, sb) {
        var value = (stat.min * statScaler).toFixed(0);
        sb.append(this.getStatTypeText(EquipStatType.DefensePhy));
        sb.append("+");
        sb.append(value);
    }

    this.getEquipStatDefensePhyBoost = function(stat, statScaler, sb) {
        var value = ((stat.min * statScaler - 1) * 100).toFixed(0);
        sb.append(this.getStatTypeText(EquipStatType.DefensePhyBoost));
        sb.append(value);
        sb.append("%");
    }

    this.getEquipStatDefensePhyAuraBoost = function(stat, statScaler, sb) {
        var value = ((stat.min * statScaler - 1) * 100).toFixed(0);
        sb.append(this.getStatTypeText(EquipStatType.DefensePhyAuraBoost));
        sb.append(value);
        sb.append("%");
    }

    this.getEquipStatDefensePhyIgnore = function(stat, statScaler, sb) {
        var value = (stat.min * statScaler).toFixed(0);
        sb.append(this.getStatTypeText(EquipStatType.DefensePhyIgnore));
        sb.append("+");
        sb.append(value);
    }

    this.getEquipStatDefensePhyIgnoreBoost = function(stat, statScaler, sb) {
        var value = (stat.min * statScaler * 100).toFixed(0);
        sb.append(this.getStatTypeText(EquipStatType.DefensePhyIgnoreBoost));
        sb.append(value);
        sb.append("%");
    }

    this.getEquipStatDefenseSpell = function(stat, statScaler, sb) {
        var value = (stat.min * statScaler).toFixed(0);
        sb.append(this.getStatTypeText(EquipStatType.DefenseSpell));
        sb.append("+");
        sb.append(value);
    }

    this.getEquipStatDefenseSpellBoost = function(stat, statScaler, sb) {
        var value = ((stat.min * statScaler - 1) * 100).toFixed(0);
        sb.append(this.getStatTypeText(EquipStatType.DefenseSpellBoost));
        sb.append(value);
        sb.append("%");
    }

    this.getEquipStatDefenseSpellAuraBoost = function(stat, statScaler, sb) {
        var value = ((stat.min * statScaler - 1) * 100).toFixed(0);
        sb.append(this.getStatTypeText(EquipStatType.DefenseSpellAuraBoost));
        sb.append(value);
        sb.append("%");
    }

    this.getEquipStatDefenseSpellIgnore = function(stat, statScaler, sb) {
        var value = (stat.min * statScaler).toFixed(0);
        sb.append(this.getStatTypeText(EquipStatType.DefenseSpellIgnore));
        sb.append("+");
        sb.append(value);
    }

    this.getEquipStatDefenseSpellIgnoreBoost = function(stat, statScaler, sb) {
        var value = (stat.min * statScaler * 100).toFixed(0);
        sb.append(this.getStatTypeText(EquipStatType.DefenseSpellIgnoreBoost));
        sb.append(value);
        sb.append("%");
    }

    this.getEquipStatAttackSpeedBoost = function(stat, statScaler, sb) {
        var value = ((stat.min * statScaler - 1) * 100).toFixed(0);
        sb.append(this.getStatTypeText(EquipStatType.AttackSpeedBoost));
        sb.append(value);
        sb.append("%");
    }

    this.getEquipStatAttackSpeedAuraBoost = function(stat, statScaler, sb) {
        var value = ((stat.min * statScaler - 1) * 100).toFixed(0);
        sb.append(this.getStatTypeText(EquipStatType.AttackSpeedAuraBoost));
        sb.append(value);
        sb.append("%");
    }

    this.getEquipStatMoveSpeedBoost = function(stat, statScaler, sb) {
        var value = ((stat.min * statScaler - 1) * 100).toFixed(0);
        sb.append(this.getStatTypeText(EquipStatType.MoveSpeedBoost));
        sb.append(value);
        sb.append("%");
    }

    this.getEquipStatMoveSpeedAuraBoost = function(stat, statScaler, sb) {
        var value = ((stat.min * statScaler - 1) * 100).toFixed(0);
        sb.append(this.getStatTypeText(EquipStatType.MoveSpeedAuraBoost));
        sb.append(value);
        sb.append("%");
    }

    this.getEquipStatAttackRange = function(stat, statScaler, sb) {
        var value = string.Format("{0:F1}", stat.min * statScaler);
        sb.append(this.getStatTypeText(EquipStatType.AttackRange));
        sb.append("+");
        sb.append(value);
    }

    this.getEquipStatCriticalRateBoost = function(stat, statScaler, sb) {
        var value = ((stat.min * statScaler - 1) * 100).toFixed(0);
        sb.append(this.getStatTypeText(EquipStatType.CriticalRateBoost));
        sb.append(value);
        sb.append("%");
    }

    this.getEquipStatCriticalDamageBoost = function(stat, statScaler, sb) {
        var value = ((stat.min * statScaler - 1) * 100).toFixed(0);
        sb.append(this.getStatTypeText(EquipStatType.CriticalDamageBoost));
        sb.append(value);
        sb.append("%");
    }

    this.getEquipStatBlood = function(stat, statScaler, sb) {
        var value = (stat.min * statScaler * 100).toFixed(1);
        sb.append(this.getStatTypeText(EquipStatType.Blood));
        sb.append(value);
        sb.append("%");
    }

    this.getEquipStatSkillLvUp = function(stat, statScaler, sb) {
        var value = (stat.min).toFixed(0);
        sb.append(this.getStatTypeText(EquipStatType.SkillLvUp));
        sb.append("+");
        sb.append(value);
    }

    this.getEquipStatSkillCooltimeDecrease = function(stat, statScaler, sb) {
        var value = (stat.min * statScaler * 100).toFixed(0);
        sb.append(this.getStatTypeText(EquipStatType.SkillCooltimeDecrease));
        sb.append(value);
        sb.append("%");
    }

    this.getEquipStatGoldDropRateBoost = function(stat, statScaler, sb) {
        var value = ((stat.min * statScaler - 1) * 100).toFixed(0);
        sb.append(this.getStatTypeText(EquipStatType.GoldDropRateBoost));
        sb.append(value);
        sb.append("%");
    }

    this.getEquipStatItemDropRateBoost = function(stat, statScaler, sb) {
        var value = ((stat.min * statScaler - 1) * 100).toFixed(0);
        sb.append(this.getStatTypeText(EquipStatType.ItemDropRateBoost));
        sb.append(value);
        sb.append("%");
    }    
}

// controller
var controller = new function() {
    this.loadEquipmentMeta = function() {
        $.ajax({      
            type:"GET",  
            url:"http://dev.30games.co.kr:8080/zero-api/meta/equipments/ko",
            headers: {
                myAccountId:"d326e5c4-a856-4632-b6d3-36cd157e8395-002",
                myAccessToken:"[Fantastic 30Games!]",
                myClientVersion:"1.0.0.KG",
                myClientPlatform:"android",
                myTimeStamp:"1415862491:1:FANTASTIC30GAMES",
                myMetaRevision:1
            },        
            beforeSend: function (xhr) {
                xhr.setRequestHeader("myAccountId", "d326e5c4-a856-4632-b6d3-36cd157e8395-002");
                xhr.setRequestHeader("myAccessToken", "[Fantastic 30Games!]");
                xhr.setRequestHeader("myClientVersion", "1.2.0.KG");
                xhr.setRequestHeader("myClientPlatform", "android");
                xhr.setRequestHeader("myMetaRevision", "1");
                xhr.setRequestHeader("myTimeStamp", "1415862491:1:FANTASTIC30GAMES");                
            },
            success:function(args){
                metaEquip.raw = args;
            },   
            error:function(e){
                alert(e.responseText);
            }  
        });  
    }

    this.loadEquipment = function(eqType) {
        $("#spinner").show();
        $("#noSearchResult").hide();
        $("#resultPanel").hide();

        var equipmentType = parseInt($("#equipmentType").val());
        var meta = metaEquip.getEquipment(equipmentType);
        if (meta == null) {
            $("#spinner").hide();
            $("#noSearchResult").show();
            return;
        }

        $.ajax({      
            type:"POST",  
            url:"http://dev.30games.co.kr:8080/zero-api/equipments/virtual",
            data:{equipParams:equipmentType+":"+meta.grade},
            success:function(args){
                $("#spinner").hide();
                $("#resultPanel").show();
                view.displayEquipment(meta, args["result"][0]);
            },   
            beforeSend: function (xhr) {
                xhr.setRequestHeader("myAccountId", "d326e5c4-a856-4632-b6d3-36cd157e8395-002");
                xhr.setRequestHeader("myAccessToken", "[Fantastic 30Games!]");
                xhr.setRequestHeader("myClientVersion", "1.2.0.KG");
                xhr.setRequestHeader("myClientPlatform", "android");
                xhr.setRequestHeader("myMetaRevision", "1");
                xhr.setRequestHeader("myTimeStamp", "1415862491:1:FANTASTIC30GAMES");
            },
            error:function(e){
                alert(e.responseText);
                $("#spinner").hide();
            }  
        });  
    }
}


$(document).ready(function () {
    // load meta
    controller.loadEquipmentMeta();

//    $("#spinner").cSpinner({fallbackSourcesArray: "default_uris.js"});

    $('#btnSearch').click(function () {
        var equipmentType = parseInt($("#equipmentType").val());
        controller.loadEquipment(equipmentType);
    });
});

		
		
		
		
function StringBuilder()
{
    var strings = [];

    this.append = function (string)
    {
        string = verify(string);
        if (string.length > 0) strings[strings.length] = string;
    };

    this.appendLine = function (string)
    {
        string = verify(string);
        if (this.isEmpty())
        {
            if (string.length > 0) strings[strings.length] = string;
            else return;
        }
        else strings[strings.length] = string.length > 0 ? "\r\n" + string : "\r\n";
    };

    this.clear = function () { strings = []; };

    this.isEmpty = function () { return strings.length == 0; };

    this.toString = function () { return strings.join(""); };

    var verify = function (string)
    {
        if (!defined(string)) return "";
        if (getType(string) != getType(new String())) return String(string);
        return string;
    };

    var defined = function (el)
    {
        // Changed per Ryan O'Hara's comment:
        return el != null && typeof(el) != "undefined";
    };

    var getType = function (instance)
    {
        if (!defined(instance.constructor)) throw Error("Unexpected object type");
        var type = String(instance.constructor).match(/function\s+(\w+)/);

        return defined(type) ? type[1] : "undefined";
    };
};							

		</script>

	</@layout.put>
</@layout.extends>
