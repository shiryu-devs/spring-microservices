package com.microservicios.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.microservicios.dto.AuthUserDto;
import com.microservicios.dto.RequestDto;
import com.microservicios.dto.TokenDto;
import com.microservicios.entity.AuthUser;
import com.microservicios.service.AuthUserService;

@RestController
@RequestMapping("/auth")
public class AuthUserController {

	@Autowired
	AuthUserService authUserService;
	
	@PostMapping("/login")
	public ResponseEntity<TokenDto> login(@RequestBody AuthUserDto authUserDto){
		TokenDto tokenDto=this.authUserService.login(authUserDto);
		if (tokenDto==null) {
			return ResponseEntity.badRequest().build();
		}
		return ResponseEntity.ok(tokenDto);
	}
	
	@PostMapping("/validate")
	public ResponseEntity<TokenDto> validate(@RequestParam String token, @RequestBody RequestDto requestDto){
		TokenDto tokenDto=this.authUserService.validate(token,requestDto);
		if (tokenDto==null) {
			return ResponseEntity.badRequest().build();
		}
		return ResponseEntity.ok(tokenDto);
	}
	
	@PostMapping("/create")
	public ResponseEntity<AuthUser> create (@RequestBody AuthUserDto authUserDto){
		AuthUser authUser=this.authUserService.save(authUserDto);
		if (authUser==null) {
			return ResponseEntity.badRequest().build();
		}
		return ResponseEntity.ok(authUser);
	}
}
