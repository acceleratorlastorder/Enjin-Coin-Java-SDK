package com.enjin.coin.sdk.vo.event;

import java.util.Arrays;

import com.enjin.coin.sdk.vo.BaseResponseVO;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ListEventsResponseVO extends BaseResponseVO{

	@JsonProperty("getEventResponseVOArray")
	private GetEventResponseVO[] getEventsResponseVOArray;

	public GetEventResponseVO[] getGetEventsResponseVOArray() {
		return getEventsResponseVOArray;
	}

	public void setGetEventsResponseVOArray(GetEventResponseVO[] getEventsResponseVOArray) {
		this.getEventsResponseVOArray = getEventsResponseVOArray;
	}

	@Override
	public String toString() {
		return "ListEventsResponseVO [getEventsResponseVOArray=" + Arrays.toString(getEventsResponseVOArray) + "]";
	}

	
}