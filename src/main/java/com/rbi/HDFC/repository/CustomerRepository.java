package com.rbi.HDFC.repository;

import com.rbi.HDFC.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Repository
public interface CustomerRepository  extends JpaRepository<CustomerEntity,Long> {
    Optional<CustomerEntity> findByAdhaar(String adhaar);

}
