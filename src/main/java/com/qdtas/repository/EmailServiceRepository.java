package com.qdtas.repository;

import com.qdtas.entity.EmailVerification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailServiceRepository extends JpaRepository<EmailVerification,String> {
}
