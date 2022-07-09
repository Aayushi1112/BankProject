package com.rbi.HDFC.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "BENEFICIARY_TABLE")
@Getter
@Setter
@NoArgsConstructor
public class BeneficiaryEntity {
    @Id
    @SequenceGenerator(name = "myBenSeqGen", sequenceName = "myBenSeq", initialValue = 1, allocationSize = 100)
    @GeneratedValue(generator = "myBenSeqGen")
    private Long id;
    private String beneficiaryName;
    private String beneficiaryBank;
    private Long beneficiaryaccountNumber;
    private String beneficiaryIFSC;
    private Double moneyTransferred=0.0;

  @ManyToOne(fetch = FetchType.EAGER)

   private  CustomerEntity customerEntity;
}
