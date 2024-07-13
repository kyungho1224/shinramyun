package com.dongyw.shinramyun.controller.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CustomerRegisterRequest {

	@NotBlank(message = "실명은 필수 입력 항목입니다.")
	private String username;

	@Email
	@NotBlank(message = "이메일은 필수 입력 항목입니다.")
	private String email;

	@NotBlank(message = "비밀번호는 필수 입력 항목입니다.")
	private String password;

	@NotBlank(message = "생년월일은 필수 입력 항목입니다.")
	@Size(min = 8, max = 8, message = "yyyyMMdd 형식입니다.")
	private String birth;

}
