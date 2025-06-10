package com.haircut.dao.impl;

import com.haircut.dao.StaffDAO;
import com.haircut.models.Staff;
import com.haircut.repository.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Repository
@Transactional
public class StaffDAOImpl implements StaffDAO {

    @Autowired
    private StaffRepository staffRepository;

    @Override
    public Optional<Staff> findByPhoneNumber(String phoneNumber) {
        return staffRepository.findByPhoneNumber(phoneNumber);
    }

    @Override
    public Optional<Staff> findByEmail(String email) {
        return staffRepository.findByEmail(email);
    }

    @Override
    public List<Staff> findByActive(boolean active) {
        return staffRepository.findByActive(active);
    }

    @Override
    public List<Staff> findByPosition(String position) {
        return staffRepository.findByPosition(position);
    }

    @Override
    public List<Staff> findByFullNameContainingIgnoreCase(String fullName) {
        return staffRepository.findByFullNameContainingIgnoreCase(fullName);
    }

    @Override
    public boolean existsByPhoneNumber(String phoneNumber) {
        return staffRepository.existsByPhoneNumber(phoneNumber);
    }

    @Override
    public boolean existsByEmail(String email) {
        return staffRepository.existsByEmail(email);
    }

    @Override
    public Staff save(Staff staff) {
        return staffRepository.save(staff);
    }

    @Override
    public <S extends Staff> List<S> saveAll(Iterable<S> staffs) {
        return staffRepository.saveAll(staffs);
    }

    @Override
    public Optional<Staff> findById(Long id) {
        return staffRepository.findById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return staffRepository.existsById(id);
    }

    @Override
    public List<Staff> findAll() {
        return staffRepository.findAll();
    }

    @Override
    public List<Staff> findAllById(Iterable<Long> ids) {
        return staffRepository.findAllById(ids);
    }

    @Override
    public long count() {
        return staffRepository.count();
    }

    @Override
    public void deleteById(Long id) {
        staffRepository.deleteById(id);
    }

    @Override
    public void delete(Staff staff) {
        staffRepository.delete(staff);
    }

    @Override
    public void deleteAllById(Iterable<? extends Long> ids) {
        staffRepository.deleteAllById(ids);
    }

    @Override
    public void deleteAll(Iterable<? extends Staff> staffs) {
        staffRepository.deleteAll(staffs);
    }

    @Override
    public void deleteAll() {
        staffRepository.deleteAll();
    }

    @Override
    public List<Staff> findAll(Sort sort) {
        return staffRepository.findAll(sort);
    }

    @Override
    public Page<Staff> findAll(Pageable pageable) {
        return staffRepository.findAll(pageable);
    }

    @Override
    public <S extends Staff> S saveAndFlush(S staff) {
        return staffRepository.saveAndFlush(staff);
    }

    @Override
    public <S extends Staff> List<S> saveAllAndFlush(Iterable<S> staffs) {
        return staffRepository.saveAllAndFlush(staffs);
    }

    @Override
    public void deleteAllInBatch(Iterable<Staff> staffs) {
        staffRepository.deleteAllInBatch(staffs);
    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> ids) {
        staffRepository.deleteAllByIdInBatch(ids);
    }

    @Override
    public void deleteAllInBatch() {
        staffRepository.deleteAllInBatch();
    }

    @Override
    public Staff getReferenceById(Long id) {
        return staffRepository.getReferenceById(id);
    }

    @Override
    public <S extends Staff> Optional<S> findOne(Example<S> example) {
        return staffRepository.findOne(example);
    }

    @Override
    public <S extends Staff> List<S> findAll(Example<S> example) {
        return staffRepository.findAll(example);
    }

    @Override
    public <S extends Staff> List<S> findAll(Example<S> example, Sort sort) {
        return staffRepository.findAll(example, sort);
    }

    @Override
    public <S extends Staff> Page<S> findAll(Example<S> example, Pageable pageable) {
        return staffRepository.findAll(example, pageable);
    }

    @Override
    public <S extends Staff> long count(Example<S> example) {
        return staffRepository.count(example);
    }

    @Override
    public <S extends Staff> boolean exists(Example<S> example) {
        return staffRepository.exists(example);
    }

    @Override
    public <S extends Staff, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return staffRepository.findBy(example, queryFunction);
    }

    @Override
    public void flush() {
        staffRepository.flush();
    }

    @Override
    public Staff getById(Long id) {
        return staffRepository.getReferenceById(id);
    }

    @Override
    public Staff getOne(Long id) {
        return staffRepository.getReferenceById(id);
    }
} 