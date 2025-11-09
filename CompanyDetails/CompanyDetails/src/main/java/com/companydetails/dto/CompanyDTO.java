package com.companydetails.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.time.LocalDate;

@Data
public class CompanyDTO {

    private Long id;

    @NotBlank(message = "Company name is required")
    private String companyName;

    private LocalDate companyEstablishedOn;
    private String companyRegistrationNumber;
    private String companyWebsite;
    private String companyAddress1;
    private String companyAddress2;
    private String city;
    private String state;
    private String zipCode;
    private String primaryContactFirstName;
    private String primaryContactLastName;

    @Email(message = "Invalid email format")
    private String primaryContactEmail;

    private String primaryContactMobile;
}
