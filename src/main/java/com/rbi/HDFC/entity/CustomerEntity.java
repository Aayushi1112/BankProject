package com.rbi.HDFC.entity;

import com.rbi.HDFC.dto.BeneficiaryDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "CUSTOMER_TABLE")
@Getter
@Setter
@NoArgsConstructor
public class CustomerEntity {
    @Id
    @SequenceGenerator(name = "myCustomerSeqGen", sequenceName = "myCustSeq", initialValue = 10, allocationSize = 100)
    @GeneratedValue(generator = "myCustomerSeqGen")
    private Long id;
    @Column(name = "NAME")
    private String ownerName;
    //@Column(name = "EMAIL", nullable = false)
    @Column(name = "EMAIL")
    private String ownerEmail;
    @Column(name = "PHONE")
    private String phone;

    @Column(name = "ADHAAR")
    private String adhaar;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id")
    private AccountEntity account;
@OneToMany( cascade = CascadeType.ALL,mappedBy ="customerEntity" )
private List<BeneficiaryEntity> beneficiaries=new ArrayList<>();
}
