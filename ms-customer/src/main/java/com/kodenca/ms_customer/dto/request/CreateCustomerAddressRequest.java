package com.kodenca.ms_customer.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateCustomerAddressRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = 3L;

    @NotNull
    private Long departmentId;

    @NotNull
    private Long provinceId;

    @NotNull
    private Long districtId;

    @NotBlank
    @Size(max = 200)
    private String addressLine1;

    @Size(max = 200)
    private String addressLine2;

    @Size(max = 20)
    private String postalCode;

    @Size(max = 200)
    private String reference;
}
