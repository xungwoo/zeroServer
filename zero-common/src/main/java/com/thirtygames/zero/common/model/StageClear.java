package com.thirtygames.zero.common.model;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.thirtygames.zero.common.model.params.grid.GridFilter;

@Data
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class StageClear extends GridFilter implements Serializable {
	private static final long serialVersionUID = 2850797522028512410L;

	public enum StageClearMode {
		Easy(1000), Normal(2000), Hard(3000), Hell(4000);

		private int code;

		public int getCode() {
			return code;
		}

		private StageClearMode(int code) {
			this.code = code;
		}

		public static int GetMaxClearStep(int clearMode) {
			switch (clearMode) {
			case 1000:
				return 3;
			case 2000:
				return 4;
			case 3000:
				return 5;
			case 4000:
				return 6;
			default:
				return 6;
			}
		}
	}

	String accountId;
	String stageKey;
	@JsonIgnore Integer chapter;
	@JsonIgnore Integer stage;

	Integer clearMode;
	Integer clearStep;
	Float clearProgress;
	Integer exposedScenes;
	Long modYmdt;
	
	public static String makeStageKey(int chapter, int stage) {
		String result = "";
		if (chapter < 100) result += "0";
		if (chapter < 10) result += "0";
		result += String.valueOf(chapter) + "_";
		
		if (stage < 100) result += "0";
		if (stage < 10) result += "0";
		result += String.valueOf(stage);
		return result;
	}
	
	public void setClearProgress() {
		int maxClearStep = StageClearMode.GetMaxClearStep(this.clearMode);
		this.clearProgress =  (float) Math.round(((float)this.clearStep / (float)maxClearStep) * 100) / 100;
	}

}
