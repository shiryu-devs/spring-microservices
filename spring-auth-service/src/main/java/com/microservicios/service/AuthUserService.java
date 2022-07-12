package com.microservicios.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.google.common.base.Optional;
import com.microservicios.dto.AuthUserDto;
import com.microservicios.dto.TokenDto;
import com.microservicios.entity.AuthUser;
import com.microservicios.repository.AuthUserRepository;
import com.microservicios.security.JwtProvider;

@Service
public class AuthUserService {
	@Autowired
	AuthUserRepository authUserRepository;
	@Autowired
	PasswordEncoder passwordEncoder;
	@Autowired
	JwtProvider jwtProvider;
	
	public AuthUser save(AuthUserDto authUserDto) {
		Optional<AuthUser> user=this.authUserRepository.findByUserName(authUserDto.getUsername());
		if (user.isPresent()) {
			return null;
		}
		String password=passwordEncoder.encode(authUserDto.getPassword());
		AuthUser authUser=AuthUser.builder().username(authUserDto.getUsername())
				.password(password)
				.build();
		
		return this.authUserRepository.save(authUser);
	}
	
	public TokenDto login(AuthUserDto authUserDto) {
		Optional<AuthUser> user=this.authUserRepository.findByUserName(authUserDto.getUsername());
		if (user.isPresent()) {
			return null;
		}
		if (this.passwordEncoder.matches(authUserDto.getPassword(), user.get().getPassword())) {
			return new TokenDto(this.jwtProvider.createToken(user.get()));
		}
		return null;
	}
	
	public TokenDto validate(String token) {
		if (!this.jwtProvider.validate(token)) {
			return null;
		}
		String username=this.jwtProvider.getUserNameFromToken(token);
		Optional<AuthUser> user=this.authUserRepository.findByUserName(username);
		if (!user.isPresent()) {
			return null;
		}
		return new TokenDto(this.jwtProvider.createToken(user.get())); 
	}
}
