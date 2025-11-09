package com.companydetails.controller;

import com.companydetails.dto.CompanyDTO;
import com.companydetails.entities.Company;
import com.companydetails.service.CompanyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/companies")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
public class CompanyController {

    private final CompanyService companyService;

    @PostMapping
    public ResponseEntity<?> createCompany(@Valid @RequestBody CompanyDTO companyDTO) {
        try {
            Company company = convertToEntity(companyDTO);

            if (companyDTO.getCompanyRegistrationNumber() != null &&
                    companyService.existsByRegistrationNumber(companyDTO.getCompanyRegistrationNumber())) {
                return ResponseEntity.badRequest()
                        .body("Company with registration number " + companyDTO.getCompanyRegistrationNumber() + " already exists");
            }

            Company savedCompany = companyService.saveCompany(company);
            return ResponseEntity.ok(convertToDTO(savedCompany));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error creating company: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCompany(@PathVariable Long id, @Valid @RequestBody CompanyDTO companyDTO) {
        try {
            Optional<Company> existingCompany = companyService.getCompanyById(id);
            if (existingCompany.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            Company company = convertToEntity(companyDTO);
            company.setId(id);

            Company updatedCompany = companyService.saveCompany(company);
            return ResponseEntity.ok(convertToDTO(updatedCompany));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error updating company: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompanyDTO> getCompany(@PathVariable Long id) {
        Optional<Company> company = companyService.getCompanyById(id);
        return company.map(c -> ResponseEntity.ok(convertToDTO(c)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<Page<CompanyDTO>> getAllCompanies(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Company> companies = companyService.getAllCompanies(pageable);
        Page<CompanyDTO> companyDTOs = companies.map(this::convertToDTO);

        return ResponseEntity.ok(companyDTOs);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<CompanyDTO>> searchCompanies(
            @RequestParam(required = false) String companyName,
            @RequestParam(required = false) String registrationNumber,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String state,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Company> companies = companyService.searchCompanies(
                companyName, registrationNumber, city, state, pageable);
        Page<CompanyDTO> companyDTOs = companies.map(this::convertToDTO);

        return ResponseEntity.ok(companyDTOs);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCompany(@PathVariable Long id) {
        if (companyService.getCompanyById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        companyService.deleteCompany(id);
        return ResponseEntity.ok().build();
    }

    private Company convertToEntity(CompanyDTO dto) {
        return Company.builder()
                .companyName(dto.getCompanyName())
                .companyEstablishedOn(dto.getCompanyEstablishedOn())
                .companyRegistrationNumber(dto.getCompanyRegistrationNumber())
                .companyWebsite(dto.getCompanyWebsite())
                .companyAddress1(dto.getCompanyAddress1())
                .companyAddress2(dto.getCompanyAddress2())
                .city(dto.getCity())
                .state(dto.getState())
                .zipCode(dto.getZipCode())
                .primaryContactFirstName(dto.getPrimaryContactFirstName())
                .primaryContactLastName(dto.getPrimaryContactLastName())
                .primaryContactEmail(dto.getPrimaryContactEmail())
                .primaryContactMobile(dto.getPrimaryContactMobile())
                .build();
    }

    private CompanyDTO convertToDTO(Company company) {
        CompanyDTO dto = new CompanyDTO();
        dto.setId(company.getId());
        dto.setCompanyName(company.getCompanyName());
        dto.setCompanyEstablishedOn(company.getCompanyEstablishedOn());
        dto.setCompanyRegistrationNumber(company.getCompanyRegistrationNumber());
        dto.setCompanyWebsite(company.getCompanyWebsite());
        dto.setCompanyAddress1(company.getCompanyAddress1());
        dto.setCompanyAddress2(company.getCompanyAddress2());
        dto.setCity(company.getCity());
        dto.setState(company.getState());
        dto.setZipCode(company.getZipCode());
        dto.setPrimaryContactFirstName(company.getPrimaryContactFirstName());
        dto.setPrimaryContactLastName(company.getPrimaryContactLastName());
        dto.setPrimaryContactEmail(company.getPrimaryContactEmail());
        dto.setPrimaryContactMobile(company.getPrimaryContactMobile());
        return dto;
    }
}
