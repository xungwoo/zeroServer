package com.thirtygames.zero.common.model.admintool;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.thirtygames.zero.common.model.params.grid.GridFilter;


@Data
@EqualsAndHashCode(callSuper=false)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class AdminMissionMeta extends GridFilter {
	
	String missionMetaKey;
	String missionKey;
	String accountId;
	Integer missionId;            // 메인 키
	Integer missionType; 
    String iconType;                   // 이벤트 타입 (아이콘 대응)
    
    String startYmdt;               // 이벤트 시작일시
    String dueDateYmdt;               // 마지막 이벤트 기간
    
    Integer param1;
    Integer param2;
    Integer param3;
    Integer goal;                       // 목표 량
    Integer current;                    // 현재량
    Integer rewardType;          // 보상 타입 
    Long reward;                     // 보상액   
    Boolean rewardDone;                 // 리워드 완료 여부
    
    String nameKey;
    String titleKo;
    String descriptionKo;
    String titleEn;
    String descriptionEn;
    
    String modId;
    String modYmdt;
}
