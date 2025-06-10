package com.haircut.services;

import com.haircut.models.HaircutService;
import com.haircut.utils.ApiResponse;
import java.util.List;

public interface ServiceService {
    ApiResponse<List<HaircutService>> getAllServices();
    ApiResponse<HaircutService> getServiceById(Long id);
    ApiResponse<HaircutService> createService(HaircutService service);
    ApiResponse<HaircutService> updateService(Long id, HaircutService serviceDetails);
    ApiResponse<Void> deleteService(Long id);
    List<HaircutService> getActiveServices();
    ApiResponse<List<HaircutService>> searchServicesByName(String name);
    ApiResponse<List<HaircutService>> getServicesByPriceRange(double minPrice, double maxPrice);
    ApiResponse<Void> deactivateService(Long id);
} 