package com.companydetails.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.time.LocalDate;

@Data
public class DriverDTO {

    private Long id;

    @NotBlank(message = "First name is required")
    private String driverFirstName;

    @NotBlank(message = "Last name is required")
    private String driverLastName;

    @Email(message = "Invalid email format")
    private String email;

    private String mobileNumber;
    private LocalDate dateOfBirth;
    private String licenseNumber;
    private Integer experienceYears;
    private String address1;
    private String address2;
    private String city;
    private String state;
    private String zipCode;
}
