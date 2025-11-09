package com.companydetails.repository;

import com.companydetails.entities.Driver;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DriverRepository extends JpaRepository<Driver, Long> {

    @Query("SELECT d FROM Driver d WHERE " +
            "(:firstName IS NULL OR d.driverFirstName LIKE %:firstName%) AND " +
            "(:lastName IS NULL OR d.driverLastName LIKE %:lastName%) AND " +
            "(:licenseNumber IS NULL OR d.licenseNumber LIKE %:licenseNumber%) AND " +
            "(:city IS NULL OR d.city LIKE %:city%) AND " +
            "(:state IS NULL OR d.state LIKE %:state%)")
    Page<Driver> searchDrivers(
            @Param("firstName") String firstName,
            @Param("lastName") String lastName,
            @Param("licenseNumber") String licenseNumber,
            @Param("city") String city,
            @Param("state") String state,
            Pageable pageable);

    boolean existsByLicenseNumber(String licenseNumber);
}
