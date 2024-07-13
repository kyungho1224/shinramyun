package com.dongyw.shinramyun.controller;

import com.dongyw.shinramyun.annotation.CurrentCustomer;
import com.dongyw.shinramyun.controller.dto.CustomerInfo;
import com.dongyw.shinramyun.jwt.CustomerDetails;
import com.dongyw.shinramyun.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/customers")
public class CustomerApiController {

	private final CustomerService customerService;

	@GetMapping("/me")
	public ResponseEntity<CustomerInfo> me(
	  @CurrentCustomer CustomerDetails customerDetails
	) {
		var customer = customerDetails.getCustomer();
		var response = customerService.getCustomerInfo(customer.getEmail());
		return ResponseEntity.ok(response);
	}

}
