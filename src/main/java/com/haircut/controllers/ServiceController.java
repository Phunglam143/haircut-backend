package com.haircut.controllers;

import com.haircut.models.HaircutService;
import com.haircut.services.ServiceService;
import com.haircut.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/services")
@CrossOrigin(originPatterns = "*")
public class ServiceController {

    @Autowired
    private ServiceService serviceService;

    @PostMapping
    public ResponseEntity<ApiResponse<HaircutService>> createService(@RequestBody HaircutService service) {
        return ResponseEntity.ok(serviceService.createService(service));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<HaircutService>> getServiceById(@PathVariable Long id) {
        return ResponseEntity.ok(serviceService.getServiceById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<HaircutService>> updateService(
            @PathVariable Long id,
            @RequestBody HaircutService serviceDetails) {
        return ResponseEntity.ok(serviceService.updateService(id, serviceDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteService(@PathVariable Long id) {
        return ResponseEntity.ok(serviceService.deleteService(id));
    }

    @GetMapping("/active")
    public ResponseEntity<List<HaircutService>> getActiveServices() {
        return ResponseEntity.ok(serviceService.getActiveServices());
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<HaircutService>>> searchServicesByName(@RequestParam String name) {
        return ResponseEntity.ok(serviceService.searchServicesByName(name));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<HaircutService>>> getAllServices() {
        return ResponseEntity.ok(serviceService.getAllServices());
    }

    @GetMapping("/price-range")
    public ResponseEntity<ApiResponse<List<HaircutService>>> getServicesByPriceRange(
            @RequestParam double minPrice,
            @RequestParam double maxPrice) {
        return ResponseEntity.ok(serviceService.getServicesByPriceRange(minPrice, maxPrice));
    }
} 