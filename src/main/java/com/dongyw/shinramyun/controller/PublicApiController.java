package com.dongyw.shinramyun.controller;

import com.dongyw.shinramyun.controller.dto.CustomerRegisterRequest;
import com.dongyw.shinramyun.controller.dto.CustomerSignInRequest;
import com.dongyw.shinramyun.controller.dto.CustomerSignInResponse;
import com.dongyw.shinramyun.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/public/api")
public class PublicApiController {

	private final CustomerService customerService;

	@GetMapping("/check-email")
	public ResponseEntity<Boolean> checkEmail(
	  @RequestParam String email
	) {
		return ResponseEntity.ok(customerService.isDuplicatedEmail(email));
	}

	@PostMapping("/sign-up")
	public ResponseEntity<Void> signUp(
	  @Validated
	  @RequestBody CustomerRegisterRequest customerRegisterRequest
	) {
		customerService.signUpCustomer(customerRegisterRequest);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@PostMapping("/sign-in")
	public ResponseEntity<CustomerSignInResponse> signIn(
	  @Validated
	  @RequestBody CustomerSignInRequest customerSignInRequest
	) {
		var response = customerService.signInCustomer(customerSignInRequest);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

}
