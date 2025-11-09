package com.companydetails.controller;

import com.companydetails.dto.DriverDTO;
import com.companydetails.entities.Driver;
import com.companydetails.service.DriverService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/drivers")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
public class DriverController {

    private final DriverService driverService;

    @PostMapping
    public ResponseEntity<?> createDriver(@Valid @RequestBody DriverDTO driverDTO) {
        try {
            Driver driver = convertToEntity(driverDTO);

            if (driverDTO.getLicenseNumber() != null &&
                    driverService.existsByLicenseNumber(driverDTO.getLicenseNumber())) {
                return ResponseEntity.badRequest()
                        .body("Driver with license number " + driverDTO.getLicenseNumber() + " already exists");
            }

            Driver savedDriver = driverService.saveDriver(driver);
            return ResponseEntity.ok(convertToDTO(savedDriver));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error creating driver: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateDriver(@PathVariable Long id, @Valid @RequestBody DriverDTO driverDTO) {
        try {
            Optional<Driver> existingDriver = driverService.getDriverById(id);
            if (existingDriver.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            Driver driver = convertToEntity(driverDTO);
            driver.setId(id);

            Driver updatedDriver = driverService.saveDriver(driver);
            return ResponseEntity.ok(convertToDTO(updatedDriver));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error updating driver: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<DriverDTO> getDriver(@PathVariable Long id) {
        Optional<Driver> driver = driverService.getDriverById(id);
        return driver.map(d -> ResponseEntity.ok(convertToDTO(d)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<Page<DriverDTO>> getAllDrivers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Driver> drivers = driverService.getAllDrivers(pageable);
        Page<DriverDTO> driverDTOs = drivers.map(this::convertToDTO);

        return ResponseEntity.ok(driverDTOs);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<DriverDTO>> searchDrivers(
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) String licenseNumber,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String state,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Driver> drivers = driverService.searchDrivers(
                firstName, lastName, licenseNumber, city, state, pageable);
        Page<DriverDTO> driverDTOs = drivers.map(this::convertToDTO);

        return ResponseEntity.ok(driverDTOs);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDriver(@PathVariable Long id) {
        if (driverService.getDriverById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        driverService.deleteDriver(id);
        return ResponseEntity.ok().build();
    }

    private Driver convertToEntity(DriverDTO dto) {
        return Driver.builder()
                .driverFirstName(dto.getDriverFirstName())
                .driverLastName(dto.getDriverLastName())
                .email(dto.getEmail())
                .mobileNumber(dto.getMobileNumber())
                .dateOfBirth(dto.getDateOfBirth())
                .licenseNumber(dto.getLicenseNumber())
                .experienceYears(dto.getExperienceYears())
                .address1(dto.getAddress1())
                .address2(dto.getAddress2())
                .city(dto.getCity())
                .state(dto.getState())
                .zipCode(dto.getZipCode())
                .build();
    }

    private DriverDTO convertToDTO(Driver driver) {
        DriverDTO dto = new DriverDTO();
        dto.setId(driver.getId());
        dto.setDriverFirstName(driver.getDriverFirstName());
        dto.setDriverLastName(driver.getDriverLastName());
        dto.setEmail(driver.getEmail());
        dto.setMobileNumber(driver.getMobileNumber());
        dto.setDateOfBirth(driver.getDateOfBirth());
        dto.setLicenseNumber(driver.getLicenseNumber());
        dto.setExperienceYears(driver.getExperienceYears());
        dto.setAddress1(driver.getAddress1());
        dto.setAddress2(driver.getAddress2());
        dto.setCity(driver.getCity());
        dto.setState(driver.getState());
        dto.setZipCode(driver.getZipCode());
        return dto;
    }
}
