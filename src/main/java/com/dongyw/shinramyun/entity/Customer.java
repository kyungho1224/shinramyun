package com.dongyw.shinramyun.entity;

import com.dongyw.shinramyun.constant.CustomerRole;
import com.dongyw.shinramyun.constant.RegisterStatus;
import com.dongyw.shinramyun.controller.dto.CustomerRegisterRequest;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "customers")
@Where(clause = "customer_status = 'REGISTERED'")
public class Customer extends BaseEntity {

	@Column(name = "username", nullable = false, columnDefinition = "VARCHAR(20) COMMENT '실명'")
	private String username;

	@Column(name = "email", nullable = false, columnDefinition = "VARCHAR(50) COMMENT '이메일'")
	private String email;

	@Column(name = "password", nullable = false, columnDefinition = "VARCHAR(255) COMMENT '비밀번호'")
	private String password;

	@Column(name = "birth", nullable = false, columnDefinition = "VARCHAR(8) COMMENT '생년월일'")
	private String birth;

	@Enumerated(EnumType.STRING)
	@Column(name = "customer_role", nullable = false, columnDefinition = "VARCHAR(20) COMMENT '등급'")
	private CustomerRole customerRole;

	@Enumerated(EnumType.STRING)
	@Column(name = "customer_status", nullable = false, columnDefinition = "VARCHAR(20) COMMENT '등록상태'")
	private RegisterStatus registerStatus;

	@Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '마지막 접속 정보'")
	protected LocalDateTime lastAttendantAt;

	@Builder
	public Customer(
	  String username, String email, String password, String birth,
	  CustomerRole customerRole, RegisterStatus registerStatus
	) {
		this.username = username;
		this.email = email;
		this.password = password;
		this.birth = birth;
		this.customerRole = customerRole;
		this.registerStatus = registerStatus;
		this.lastAttendantAt = LocalDateTime.now();
	}

	public static Customer createOf(CustomerRegisterRequest request, String encodedPassword) {
		return Customer.builder()
		  .username(request.getUsername())
		  .email(request.getEmail())
		  .password(encodedPassword)
		  .birth(request.getBirth())
		  .customerRole(CustomerRole.SILVER)
		  .registerStatus(RegisterStatus.REGISTERED)
		  .build();
	}

	public void updateLastAttendant() {
		this.lastAttendantAt = LocalDateTime.now();
	}

}
