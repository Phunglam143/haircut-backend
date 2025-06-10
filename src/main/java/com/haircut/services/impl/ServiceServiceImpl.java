package com.haircut.services.impl;

import com.haircut.models.HaircutService;
import com.haircut.repository.HaircutServiceRepository;
import com.haircut.services.ServiceService;
import com.haircut.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ServiceServiceImpl implements ServiceService {

    @Autowired
    private HaircutServiceRepository serviceRepository;

    @Override
    public ApiResponse<HaircutService> createService(HaircutService service) {
        try {
            HaircutService savedService = serviceRepository.save(service);
            return ApiResponse.success("Tạo dịch vụ thành công", savedService);
        } catch (Exception e) {
            return ApiResponse.error("Lỗi khi tạo dịch vụ: " + e.getMessage());
        }
    }

    @Override
    public ApiResponse<HaircutService> getServiceById(Long id) {
        try {
            HaircutService service = serviceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy dịch vụ"));
            return ApiResponse.success("Lấy thông tin dịch vụ thành công", service);
        } catch (Exception e) {
            return ApiResponse.error("Lỗi khi lấy thông tin dịch vụ: " + e.getMessage());
        }
    }

    @Override
    public ApiResponse<HaircutService> updateService(Long id, HaircutService serviceDetails) {
        try {
            HaircutService service = serviceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy dịch vụ"));
            
            service.setName(serviceDetails.getName());
            service.setDescription(serviceDetails.getDescription());
            service.setPrice(serviceDetails.getPrice());
            service.setDurationMinutes(serviceDetails.getDurationMinutes());
            service.setActive(serviceDetails.isActive());

            HaircutService updatedService = serviceRepository.save(service);
            return ApiResponse.success("Cập nhật dịch vụ thành công", updatedService);
        } catch (Exception e) {
            return ApiResponse.error("Lỗi khi cập nhật dịch vụ: " + e.getMessage());
        }
    }

    @Override
    public ApiResponse<Void> deleteService(Long id) {
        try {
            if (!serviceRepository.existsById(id)) {
                return ApiResponse.error("Không tìm thấy dịch vụ");
            }
            serviceRepository.deleteById(id);
            return ApiResponse.success("Xóa dịch vụ thành công", null);
        } catch (Exception e) {
            return ApiResponse.error("Lỗi khi xóa dịch vụ: " + e.getMessage());
        }
    }

    @Override
    public List<HaircutService> getActiveServices() {
        return serviceRepository.findByActive(true);
    }

    @Override
    public ApiResponse<List<HaircutService>> searchServicesByName(String name) {
        try {
            List<HaircutService> services = serviceRepository.findByNameContainingIgnoreCase(name);
            return ApiResponse.success("Tìm kiếm dịch vụ thành công", services);
        } catch (Exception e) {
            return ApiResponse.error("Lỗi khi tìm kiếm dịch vụ: " + e.getMessage());
        }
    }

    @Override
    public ApiResponse<List<HaircutService>> getAllServices() {
        try {
            List<HaircutService> services = serviceRepository.findAll();
            return ApiResponse.success("Lấy danh sách dịch vụ thành công", services);
        } catch (Exception e) {
            return ApiResponse.error("Lỗi khi lấy danh sách dịch vụ: " + e.getMessage());
        }
    }

    @Override
    public ApiResponse<List<HaircutService>> getServicesByPriceRange(double minPrice, double maxPrice) {
        try {
            List<HaircutService> services = serviceRepository.findByPriceBetween(minPrice, maxPrice);
            return ApiResponse.success("Lấy danh sách dịch vụ theo khoảng giá thành công", services);
        } catch (Exception e) {
            return ApiResponse.error("Lỗi khi lấy danh sách dịch vụ theo khoảng giá: " + e.getMessage());
        }
    }

    @Override
    public ApiResponse<Void> deactivateService(Long id) {
        try {
            HaircutService service = serviceRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy dịch vụ"));
            service.setActive(false);
            serviceRepository.save(service);
            return ApiResponse.success("Vô hiệu hóa dịch vụ thành công", null);
        } catch (Exception e) {
            return ApiResponse.error("Lỗi khi vô hiệu hóa dịch vụ: " + e.getMessage());
        }
    }
} 