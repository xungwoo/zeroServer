package com.thirtygames.zero.common.model.meta;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

@Data
@EqualsAndHashCode(callSuper=false)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class PrizeMatch implements Serializable {
	private static final long serialVersionUID = -894094511390553019L;

	@JsonIgnore Integer prizeKey;
	Integer leagueCode;
	Integer prizeType;	// 0:gold, 1:cash, ...
	Integer winnerPrize;
	Integer looserPrize;

}
