package com.haircut.repository;

import com.haircut.models.HaircutService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ServiceRepository extends JpaRepository<HaircutService, Integer> {
    List<HaircutService> findByActive(boolean active);
    List<HaircutService> findByNameContainingIgnoreCase(String name);
    List<HaircutService> findByPriceLessThanEqual(double price);
} 