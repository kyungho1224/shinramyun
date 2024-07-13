package com.dongyw.shinramyun.repository;

import com.dongyw.shinramyun.entity.Customer;
import com.dongyw.shinramyun.exception.ApiErrorCode;
import com.dongyw.shinramyun.exception.ApiException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

	boolean existsByEmail(String email);

	Optional<Customer> findFirstByEmail(String email);

	default Customer getValidCustomerOrThrow(String email) {
		return findFirstByEmail(email)
		  .orElseThrow(() -> new ApiException(ApiErrorCode.NOT_FOUND_VALID_CUSTOMER));
	}

}
