package com.dongyw.shinramyun.controller.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CustomerSignInRequest {

	@Email
	@NotBlank(message = "이메일은 필수 입력 항목입니다.")
	private String email;

	@NotBlank(message = "비밀번호는 필수 입력 항목입니다.")
	private String password;

}
