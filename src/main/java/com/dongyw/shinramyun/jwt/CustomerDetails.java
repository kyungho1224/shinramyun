package com.dongyw.shinramyun.jwt;

import com.dongyw.shinramyun.constant.RegisterStatus;
import com.dongyw.shinramyun.entity.Customer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class CustomerDetails implements UserDetails {

	private Customer customer;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority(this.customer.getCustomerRole().getRole()));
	}

	@Override
	public String getPassword() {
		return this.customer.getPassword();
	}

	@Override
	public String getUsername() {
		return this.customer.getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		return this.customer.getRegisterStatus() == RegisterStatus.REGISTERED;
	}

	@Override
	public boolean isAccountNonLocked() {
		return this.customer.getRegisterStatus() == RegisterStatus.REGISTERED;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return this.customer.getRegisterStatus() == RegisterStatus.REGISTERED;
	}

	@Override
	public boolean isEnabled() {
		return this.customer.getRegisterStatus() == RegisterStatus.REGISTERED;
	}

}
