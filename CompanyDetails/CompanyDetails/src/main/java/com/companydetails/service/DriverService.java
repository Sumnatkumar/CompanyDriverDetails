package com.companydetails.service;

import com.companydetails.entities.Driver;
import com.companydetails.repository.DriverRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DriverService {

    private final DriverRepository driverRepository;

    public Driver saveDriver(Driver driver) {
        return driverRepository.save(driver);
    }

    public Optional<Driver> getDriverById(Long id) {
        return driverRepository.findById(id);
    }

    public Page<Driver> getAllDrivers(Pageable pageable) {
        return driverRepository.findAll(pageable);
    }

    public Page<Driver> searchDrivers(String firstName, String lastName, String licenseNumber,
                                      String city, String state, Pageable pageable) {
        return driverRepository.searchDrivers(firstName, lastName, licenseNumber, city, state, pageable);
    }

    public void deleteDriver(Long id) {
        driverRepository.deleteById(id);
    }

    public boolean existsByLicenseNumber(String licenseNumber) {
        return driverRepository.existsByLicenseNumber(licenseNumber);
    }
}
