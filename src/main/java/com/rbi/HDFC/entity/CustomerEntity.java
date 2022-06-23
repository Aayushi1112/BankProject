package com.rbi.HDFC.entity;

import com.rbi.HDFC.dto.CustomerDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "CUSTOMER_TABLE")
@Getter
@Setter
@NoArgsConstructor
public class CustomerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "NAME")
    private String ownerName;
    //@Column(name = "EMAIL", nullable = false)
    @Column(name = "EMAIL")
    private String ownerEmail;
    @Column(name = "PHONE")
    private String phone;
    @Column(name = "PASSWORD")
    private String password;
    @Column(name = "ADHAAR")
    private String adhaar;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id")
    private AccountEntity account;

    private String beneficiaries;
}
