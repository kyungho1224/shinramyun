package com.dongyw.shinramyun.controller.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CustomerSignInResponse {

	private Long id;

	private String token;

	@Builder
	public CustomerSignInResponse(Long id, String token) {
		this.id = id;
		this.token = token;
	}

}
