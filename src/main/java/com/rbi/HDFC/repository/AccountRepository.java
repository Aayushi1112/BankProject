package com.rbi.HDFC.repository;

import com.rbi.HDFC.entity.AccountEntity;
import com.rbi.HDFC.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public interface AccountRepository extends JpaRepository<AccountEntity,Long> {

}
