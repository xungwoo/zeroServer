package com.thirtygames.zero.common.model.equipment.meta;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Data
@EqualsAndHashCode(callSuper = false)
public class EquipLevelMeta implements Serializable {
	private static final long serialVersionUID = -2066531667824246521L;

	public static final int MAX_LEVEL = 10;

	@JsonIgnore
	Integer levelMetaKey;
	Float normal;
	Float magic;
	Float rare;
	Float unique;
	Float set;
	Float consumeExpRate;
	
	Integer firstLevelUpExp;
	Double levelUpExpScale;
	@JsonIgnore Double level10ExpScale; // levelUpExpScale^10

	Integer firstLevelUpCost;
	Double levelUpCostScale;
	@JsonIgnore Double level10CostScale;	// levelUpCostScale^10

	// EquipMeta
	@JsonIgnore Float statGrowth;
	@JsonIgnore Integer sellPrice;
	
	Float equipLevelUpDrugRate;
	Integer equipLevelUpDrugMax;

	private double getClassWeight(int eqClass) {
		double classWeight = 1f;
		switch (eqClass) {
		case (1):
			classWeight = this.magic;
			break;
		case (2):
			classWeight = this.rare;
			break;
		case (3):
			classWeight = this.unique;
			break;
		case (4):
			classWeight = this.set;
			break;
		default:
			break;
		}
		return classWeight;
	}
	
	
	// 먹이가 되었을 때 경험치 : nextLevelExp * consumeExpRate
	public int getConsumeExp(int grade, int level, int eqClass) {
		int nextLevelExp = this.getNextLevelExp(grade, level, eqClass);
		return (int) (nextLevelExp * this.consumeExpRate);
	}

	// nextLevelUp 에 필요한 경험치이자 먹이가 되었을때 주는 경험치를 구하기 위한 시드값
	public Integer getNextLevelExp(int grade, int level, int eqClass) {
		int nextLevelExp = 0;
		double expScale = 1f;
		for (int i = 1; i < grade; i++) {
			expScale = expScale * this.level10ExpScale;
		}

		for (int i = 1; i <= level; i++) {
			expScale = expScale * this.levelUpExpScale;
		}

		nextLevelExp = (int) (this.firstLevelUpExp * expScale * this.getClassWeight(eqClass));
		return nextLevelExp;
	}
	
	public Long getLevelUpCost(int grade, int level) {
		long levelUpCost = 0;
		double costScale = 1f;
		for (int i = 1; i < grade; i++) {
			costScale = costScale * this.level10CostScale;
		}
		
		for (int i = 1; i <= level; i++) {
			costScale = costScale * this.levelUpCostScale;
		}
		
		levelUpCost = (int) (this.firstLevelUpCost * costScale);
		return levelUpCost;
	}

}
