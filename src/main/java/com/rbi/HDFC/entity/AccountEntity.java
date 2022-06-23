package com.rbi.HDFC.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "ACCOUNT_TABLE")
@Getter
@Setter
@NoArgsConstructor
public class AccountEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long accountId;
    @Column(name="BALANCE")
    private Double balance;


}
