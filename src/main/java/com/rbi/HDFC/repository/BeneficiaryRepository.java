package com.rbi.HDFC.repository;

import com.rbi.HDFC.entity.AccountEntity;
import com.rbi.HDFC.entity.BeneficiaryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BeneficiaryRepository extends JpaRepository<BeneficiaryEntity,Long> {



}
