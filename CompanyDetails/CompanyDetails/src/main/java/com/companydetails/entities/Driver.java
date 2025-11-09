package com.companydetails.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "drivers")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Driver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "First name is required")
    @Column(name = "first_name", nullable = false)
    private String driverFirstName;

    @NotBlank(message = "Last name is required")
    @Column(name = "last_name", nullable = false)
    private String driverLastName;

    @Email(message = "Invalid email format")
    private String email;

    @Column(name = "mobile")
    private String mobileNumber;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "license_number")
    private String licenseNumber;

    @Column(name = "experience_years")
    private Integer experienceYears;

    private String address1;
    private String address2;
    private String city;
    private String state;

    @Column(name = "zip_code")
    private String zipCode;

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
