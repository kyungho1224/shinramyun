package com.dongyw.shinramyun.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum CustomerRole {

	SILVER("SILVER"), GOLD("GOLD"), DIAMOND("DIAMOND"), PLATINUM("PLATINUM"),
	;

	private final String role;

}
