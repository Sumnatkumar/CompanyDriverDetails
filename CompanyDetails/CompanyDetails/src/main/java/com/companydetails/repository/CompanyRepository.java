package com.companydetails.repository;

import com.companydetails.entities.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

    @Query("SELECT c FROM Company c WHERE " +
            "(:companyName IS NULL OR c.companyName LIKE %:companyName%) AND " +
            "(:registrationNumber IS NULL OR c.companyRegistrationNumber LIKE %:registrationNumber%) AND " +
            "(:city IS NULL OR c.city LIKE %:city%) AND " +
            "(:state IS NULL OR c.state LIKE %:state%)")
    Page<Company> searchCompanies(
            @Param("companyName") String companyName,
            @Param("registrationNumber") String registrationNumber,
            @Param("city") String city,
            @Param("state") String state,
            Pageable pageable);

    boolean existsByCompanyRegistrationNumber(String registrationNumber);
}
