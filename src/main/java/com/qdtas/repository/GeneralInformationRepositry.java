package com.qdtas.repository;

import com.qdtas.entity.GeneralInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GeneralInformationRepositry extends JpaRepository<GeneralInformation, Long> {
}
