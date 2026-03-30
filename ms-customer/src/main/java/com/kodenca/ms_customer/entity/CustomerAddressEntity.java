package com.kodenca.ms_customer.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "CUSTOMER_ADDRESS")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerAddressEntity {

    @Id
    @Column(name = "CUSTOMER_ADDRESS_UUID", nullable = false, unique = true)
    private String customerAddressUuId;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "CUSTOMER_UUID", nullable = false, referencedColumnName = "CUSTOMER_UUID")
    private CustomerEntity customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DEPARTMENT_UUID", nullable = false, referencedColumnName = "DEPARTMENT_UUID")
    private DepartmentEntity department;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PROVINCE_UUID", nullable = false, referencedColumnName = "PROVINCE_UUID")
    private ProvinceEntity province;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DISTRICT_UUID", nullable = false, referencedColumnName = "DISTRICT_UUID")
    private DistrictEntity district;

    @Column(name = "ADDRESS_LINE_1", nullable = false, length = 200)
    private String addressLine1;

    @Column(name = "ADDRESS_LINE_2", length = 200)
    private String addressLine2;

    @Column(name = "POSTAL_CODE", length = 20)
    private String postalCode;

    @Column(name = "REFERENCE", length = 200)
    private String reference;

    @Column(name = "PRIMARY_ADDRESS", nullable = false)
    private Boolean primaryAddress;

    @CreatedDate
    @Column(name = "CREATED_AT", nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "UPDATED_AT")
    private LocalDateTime updatedAt;


    @PrePersist
    void setDefaultValues() {
        if (Objects.isNull(customerAddressUuId)) {
            customerAddressUuId = UUID.randomUUID().toString();
        }
        System.out.println("Saving customer address: " + customerAddressUuId);
    }
}
