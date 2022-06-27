package com.rbi.HDFC.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "ACCOUNT_TABLE")
@Getter
@Setter
@NoArgsConstructor
public class AccountEntity {
    @Id
    @SequenceGenerator(name = "myAccountSeqGen", sequenceName = "myAccSeq", initialValue = 1000, allocationSize = 100)
    @GeneratedValue(generator = "myAccountSeqGen")

    private Long accountId;
    @Column(name="BALANCE")
    private Double balance;
    private Long accountNumber;
    @Column(name = "PASSWORD")
    private String password;


}
