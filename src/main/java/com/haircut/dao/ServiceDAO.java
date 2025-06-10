package com.haircut.dao;

import com.haircut.models.HaircutService;
import java.util.List;
import java.util.Optional;

public interface ServiceDAO {
    HaircutService save(HaircutService service);
    Optional<HaircutService> findById(int id);
    List<HaircutService> findAll();
    List<HaircutService> findByActive(boolean active);
    List<HaircutService> findByNameContaining(String name);
    List<HaircutService> findByPriceLessThanEqual(double price);
    void deleteById(int id);
    void delete(HaircutService service);
    boolean existsById(int id);
    boolean existsByName(String name);
} 