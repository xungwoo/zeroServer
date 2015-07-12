package com.thirtygames.zero.common.model.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.thirtygames.zero.common.generic.GenericModel;


@Data
@EqualsAndHashCode(callSuper=false)
public class AdminUser extends GenericModel implements UserDetails {
	private static final long serialVersionUID = -2179648647998744096L;
	
	public String userId;
	public String password;
	public String name;
//	public String birthYmd;
//	public String joinYmd;
//	public String mobilePhone;
//	public String email;
//	public String lineId;
//	public String aboutMe;
	public String authority;
	
	@JsonIgnore
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();    
        authorities.add(new SimpleGrantedAuthority(authority));
		return authorities;
	}	
	
	@Override
	public String getUsername() {
		return userId;
	}
	
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	
	@Override
	public boolean isEnabled() {
		return true;
	}
}
