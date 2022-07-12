package com.microservicios.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.google.common.base.Optional;
import com.microservicios.entity.AuthUser;

@Repository
public interface AuthUserRepository extends JpaRepository<AuthUser, Integer>{

	public Optional<AuthUser> findByUsername(String username);
}
