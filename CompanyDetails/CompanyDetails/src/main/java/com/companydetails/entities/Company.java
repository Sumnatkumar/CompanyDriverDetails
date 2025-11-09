package com.companydetails.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "companies")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Company name is required")
    @Column(nullable = false)
    private String companyName;

    @Column(name = "established_on")
    private LocalDate companyEstablishedOn;

    @Column(name = "registration_number")
    private String companyRegistrationNumber;

    @Column(name = "website")
    private String companyWebsite;

    @Column(name = "address1")
    private String companyAddress1;

    @Column(name = "address2")
    private String companyAddress2;

    private String city;
    private String state;

    @Column(name = "zip_code")
    private String zipCode;

    @Column(name = "primary_contact_first_name")
    private String primaryContactFirstName;

    @Column(name = "primary_contact_last_name")
    private String primaryContactLastName;

    @Email(message = "Invalid email format")
    @Column(name = "primary_contact_email")
    private String primaryContactEmail;

    @Column(name = "primary_contact_mobile")
    private String primaryContactMobile;

    @Column(name = "created_at")
    private LocalDate createdAt;

    @Column(name = "updated_at")
    private LocalDate updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDate.now();
        updatedAt = LocalDate.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDate.now();
    }
}
