package com.companydetails.service;

import com.companydetails.entities.Company;
import com.companydetails.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;

    public Company saveCompany(Company company) {
        return companyRepository.save(company);
    }

    public Optional<Company> getCompanyById(Long id) {
        return companyRepository.findById(id);
    }

    public Page<Company> getAllCompanies(Pageable pageable) {
        return companyRepository.findAll(pageable);
    }

    public Page<Company> searchCompanies(String companyName, String registrationNumber,
                                         String city, String state, Pageable pageable) {
        return companyRepository.searchCompanies(companyName, registrationNumber, city, state, pageable);
    }

    public void deleteCompany(Long id) {
        companyRepository.deleteById(id);
    }

    public boolean existsByRegistrationNumber(String registrationNumber) {
        return companyRepository.existsByCompanyRegistrationNumber(registrationNumber);
    }
}
