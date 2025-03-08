package com.ximple.library.service;

import com.ximple.library.dto.ReviewDTO;
import com.ximple.library.model.Review;
import com.ximple.library.repository.ReviewRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private static final Logger log = LoggerFactory.getLogger(ReviewService.class);

    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
        log.info("ReviewService instantiated");
    }

    public List<ReviewDTO> findAll() {
        log.debug("Fetching all reviews");
        return reviewRepository.findAll().stream()
                .map(Review::toDTO)
                .collect(Collectors.toList());
    }
}
