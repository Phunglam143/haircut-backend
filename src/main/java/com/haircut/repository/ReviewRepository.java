package com.haircut.repository;

import com.haircut.models.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByServiceId(Long serviceId);
    List<Review> findByCustomerId(Long customerId);
    List<Review> findByRating(Integer rating);
} 