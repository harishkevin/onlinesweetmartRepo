package com.cg.onlinesweetmart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.onlinesweetmart.dto.JwtAuthResponse;
import com.cg.onlinesweetmart.dto.LoginDto;
import com.cg.onlinesweetmart.dto.RegisterDto;
import com.cg.onlinesweetmart.service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	private AuthService authService;
	
	@PostMapping("/register")
	public ResponseEntity<String> register(@RequestBody RegisterDto registerDto) {
		String response = authService.register(registerDto);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	
	@PostMapping("/login")
	public ResponseEntity<JwtAuthResponse> login(@RequestBody LoginDto loginDto) {
		String token = authService.login(loginDto);
		
		JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
		jwtAuthResponse.setAccessToken(token);
		
		return new ResponseEntity<>(jwtAuthResponse, HttpStatus.OK);
	}
}
