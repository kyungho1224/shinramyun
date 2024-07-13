package com.dongyw.shinramyun.controller.dto;

import com.dongyw.shinramyun.entity.Customer;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CustomerInfo {

	private Long id;

	private String username;

	private String email;

	private String birth;

	private LocalDateTime createdAt;

	@Builder
	public CustomerInfo(Long id, String username, String email, String birth, LocalDateTime createdAt) {
		this.id = id;
		this.username = username;
		this.email = email;
		this.birth = birth;
		this.createdAt = createdAt;
	}

	public static CustomerInfo of(Customer customer) {
		return CustomerInfo.builder()
		  .id(customer.getId())
		  .username(customer.getUsername())
		  .email(customer.getEmail())
		  .birth(customer.getBirth())
		  .createdAt(customer.getCreatedAt())
		  .build();
	}

}
