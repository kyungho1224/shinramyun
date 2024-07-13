package com.dongyw.shinramyun.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum ApiErrorCode {

	DO_NOT_HAVE_PERMISSION(HttpStatus.FORBIDDEN.value(), "권한이 없습니다."),

	NOT_FOUND_VALID_CUSTOMER(HttpStatus.NOT_FOUND.value(),"유효한 사용자를 찾을 수 없습니다."),
	NOT_MATCH_PASSWORD(HttpStatus.UNAUTHORIZED.value(), "비밀번호가 틀립니다."),

	TYPE_MISMATCH(HttpStatus.BAD_REQUEST.value(), "잘못된 요청 타입입니다."),
	INVALID_REQUEST_CONTENT(HttpStatus.BAD_REQUEST.value(), "잘못된 형식의 요청이 있거나 유효성 검증에 실패했습니다."),

	TOKEN_ERROR(HttpStatus.UNAUTHORIZED.value(), "토큰 에러"),
	INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), "SERVER ERROR");

	private final Integer errorCode;
	private final String errorMessage;

}
