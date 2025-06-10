package com.haircut.dao.impl;

import com.haircut.dao.BookingDAO;
import com.haircut.models.Booking;
import com.haircut.models.Booking.BookingStatus;
import com.haircut.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Repository
@Transactional
public class BookingDAOImpl implements BookingDAO {

    @Autowired
    private BookingRepository bookingRepository;

    @Override
    public Booking save(Booking booking) {
        return bookingRepository.save(booking);
    }

    @Override
    public <S extends Booking> List<S> saveAll(Iterable<S> bookings) {
        return bookingRepository.saveAll(bookings);
    }

    @Override
    public Optional<Booking> findById(Long id) {
        return bookingRepository.findById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return bookingRepository.existsById(id);
    }

    @Override
    public List<Booking> findAll() {
        return bookingRepository.findAll();
    }

    @Override
    public List<Booking> findAllById(Iterable<Long> ids) {
        return bookingRepository.findAllById(ids);
    }

    @Override
    public long count() {
        return bookingRepository.count();
    }

    @Override
    public void deleteById(Long id) {
        bookingRepository.deleteById(id);
    }

    @Override
    public void delete(Booking booking) {
        bookingRepository.delete(booking);
    }

    @Override
    public void deleteAllById(Iterable<? extends Long> ids) {
        bookingRepository.deleteAllById(ids);
    }

    @Override
    public void deleteAll(Iterable<? extends Booking> bookings) {
        bookingRepository.deleteAll(bookings);
    }

    @Override
    public void deleteAll() {
        bookingRepository.deleteAll();
    }

    @Override
    public List<Booking> findAll(Sort sort) {
        return bookingRepository.findAll(sort);
    }

    @Override
    public Page<Booking> findAll(Pageable pageable) {
        return bookingRepository.findAll(pageable);
    }

    @Override
    public <S extends Booking> S saveAndFlush(S booking) {
        return bookingRepository.saveAndFlush(booking);
    }

    @Override
    public <S extends Booking> List<S> saveAllAndFlush(Iterable<S> bookings) {
        return bookingRepository.saveAllAndFlush(bookings);
    }

    @Override
    public void deleteAllInBatch(Iterable<Booking> bookings) {
        bookingRepository.deleteAllInBatch(bookings);
    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> ids) {
        bookingRepository.deleteAllByIdInBatch(ids);
    }

    @Override
    public void deleteAllInBatch() {
        bookingRepository.deleteAllInBatch();
    }

    @Override
    public Booking getOne(Long id) {
        return bookingRepository.getOne(id);
    }

    @Override
    public Booking getById(Long id) {
        return bookingRepository.getById(id);
    }

    @Override
    public Booking getReferenceById(Long id) {
        return bookingRepository.getReferenceById(id);
    }

    @Override
    public <S extends Booking> Optional<S> findOne(Example<S> example) {
        return bookingRepository.findOne(example);
    }

    @Override
    public <S extends Booking> List<S> findAll(Example<S> example) {
        return bookingRepository.findAll(example);
    }

    @Override
    public <S extends Booking> List<S> findAll(Example<S> example, Sort sort) {
        return bookingRepository.findAll(example, sort);
    }

    @Override
    public <S extends Booking> Page<S> findAll(Example<S> example, Pageable pageable) {
        return bookingRepository.findAll(example, pageable);
    }

    @Override
    public <S extends Booking> long count(Example<S> example) {
        return bookingRepository.count(example);
    }

    @Override
    public <S extends Booking> boolean exists(Example<S> example) {
        return bookingRepository.exists(example);
    }

    @Override
    public <S extends Booking, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return bookingRepository.findBy(example, queryFunction);
    }

    @Override
    public void flush() {
        bookingRepository.flush();
    }

    @Override
    public List<Booking> findByCustomerId(Long customerId) {
        return bookingRepository.findByCustomerId(customerId);
    }

    @Override
    public List<Booking> findByStaffId(Long staffId) {
        return bookingRepository.findByStaffId(staffId);
    }

    @Override
    public List<Booking> findByServiceId(Long serviceId) {
        return bookingRepository.findByServiceId(serviceId);
    }

    @Override
    public List<Booking> findByStatus(BookingStatus status) {
        return bookingRepository.findByStatus(status);
    }

    @Override
    public List<Booking> findByBookingTimeBetween(LocalDateTime start, LocalDateTime end) {
        return bookingRepository.findByBookingTimeBetween(start, end);
    }

    @Override
    public List<Booking> findByCustomerIdAndStatus(Long customerId, BookingStatus status) {
        return bookingRepository.findByCustomerIdAndStatus(customerId, status);
    }

    @Override
    public boolean existsByTimeSlot(LocalDateTime start, LocalDateTime end) {
        return bookingRepository.existsByBookingTimeBetween(start, end);
    }
} 