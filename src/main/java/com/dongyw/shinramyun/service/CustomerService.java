package com.dongyw.shinramyun.service;

import com.dongyw.shinramyun.controller.dto.CustomerInfo;
import com.dongyw.shinramyun.controller.dto.CustomerRegisterRequest;
import com.dongyw.shinramyun.controller.dto.CustomerSignInRequest;
import com.dongyw.shinramyun.controller.dto.CustomerSignInResponse;
import com.dongyw.shinramyun.entity.Customer;
import com.dongyw.shinramyun.exception.ApiErrorCode;
import com.dongyw.shinramyun.exception.ApiException;
import com.dongyw.shinramyun.jwt.JwtProvider;
import com.dongyw.shinramyun.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class CustomerService {

	private final PasswordEncoder passwordEncoder;
	private final CustomerRepository customerRepository;
	private final JwtProvider jwtProvider;

	@Transactional(readOnly = true)
	public boolean isDuplicatedEmail(String email) {
		return customerRepository.existsByEmail(email);
	}

	public void signUpCustomer(CustomerRegisterRequest customerRegisterRequest) {
		var newCustomer = Customer.createOf(
		  customerRegisterRequest, passwordEncoder.encode(customerRegisterRequest.getPassword())
		);
		customerRepository.save(newCustomer);
	}

	public CustomerSignInResponse signInCustomer(
	  CustomerSignInRequest customerSignInRequest
	) {
		var customer = customerRepository.getValidCustomerOrThrow(customerSignInRequest.getEmail());
		if (!passwordEncoder.matches(customerSignInRequest.getPassword(), customer.getPassword())) {
			throw new ApiException(ApiErrorCode.NOT_MATCH_PASSWORD);
		}
		customer.updateLastAttendant();
		return CustomerSignInResponse.builder()
		  .id(customer.getId())
		  .token(jwtProvider.generatedToken(customer.getEmail()))
		  .build();
	}

	public CustomerInfo getCustomerInfo(String email) {
		Customer validCustomer = customerRepository.getValidCustomerOrThrow(email);
		return CustomerInfo.of(validCustomer);
	}

}
