package com.microservicios.security;

import java.util.List;
import java.util.regex.Pattern;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import com.microservicios.dto.RequestDto;

@Component
@ConfigurationProperties(prefix = "admin-paths")
public class RouteValidator {

	private List<RequestDto> paths;

	public List<RequestDto> getPaths() {
		return paths;
	}

	public void setPaths(List<RequestDto> paths) {
		this.paths = paths;
	}
	
	public boolean isAdminPath(RequestDto dto) {
		return paths.stream().anyMatch(p -> Pattern.matches(p.getUri(), dto.getUri()) && p.getMethod().equals(dto.getMethod()));
	}
}
