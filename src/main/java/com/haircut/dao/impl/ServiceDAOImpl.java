package com.haircut.dao.impl;

import com.haircut.dao.ServiceDAO;
import com.haircut.models.HaircutService;
import com.haircut.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class ServiceDAOImpl implements ServiceDAO {

    @Autowired
    private ServiceRepository serviceRepository;

    @Override
    public HaircutService save(HaircutService service) {
        return serviceRepository.save(service);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<HaircutService> findById(int id) {
        return serviceRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<HaircutService> findAll() {
        return serviceRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<HaircutService> findByActive(boolean active) {
        return serviceRepository.findByActive(active);
    }

    @Override
    @Transactional(readOnly = true)
    public List<HaircutService> findByNameContaining(String name) {
        return serviceRepository.findByNameContainingIgnoreCase(name);
    }

    @Override
    @Transactional(readOnly = true)
    public List<HaircutService> findByPriceLessThanEqual(double price) {
        return serviceRepository.findByPriceLessThanEqual(price);
    }

    @Override
    public void deleteById(int id) {
        serviceRepository.deleteById(id);
    }

    @Override
    public void delete(HaircutService service) {
        serviceRepository.delete(service);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(int id) {
        return serviceRepository.existsById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByName(String name) {
        return !serviceRepository.findByNameContainingIgnoreCase(name).isEmpty();
    }
} 