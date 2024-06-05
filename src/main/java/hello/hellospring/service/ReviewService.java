package hello.hellospring.service;

import hello.hellospring.domain.Brand;
import hello.hellospring.domain.Member;
import hello.hellospring.domain.Review;
import hello.hellospring.repository.AllowVerifiedMemberRepository;
import hello.hellospring.repository.BrandRepository;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.ReviewRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final BrandRepository brandRepository;
    private final MemberRepository memberRepository;
    private final AllowVerifiedMemberRepository allowVerifiedMemberRepository;

    public ReviewService(ReviewRepository reviewRepository, BrandRepository brandRepository, MemberRepository memberRepository, AllowVerifiedMemberRepository allowVerifiedMemberRepository) {
        this.reviewRepository = reviewRepository;
        this.brandRepository = brandRepository;
        this.memberRepository = memberRepository;
        this.allowVerifiedMemberRepository=allowVerifiedMemberRepository;
    }

    public Review saveReview(Long brandId, Long memberId, String content, double rating) {
        if (!allowVerifiedMemberRepository.existsByMemberIdAndBrandId(memberId, brandId)) {
            throw new IllegalArgumentException("인증된 사용자만 리뷰를 작성할 수 있습니다.");
        }

        Member member = memberRepository.findById(memberId).orElseThrow(() -> new IllegalArgumentException("Invalid member ID"));
        Brand brand = brandRepository.findById(brandId).orElseThrow(() -> new IllegalArgumentException("Invalid brand ID"));

        Review review = new Review();
        review.setBrand(brand);
        review.setAuthor(member);
        review.setContent(content);
        review.setRating(rating);
        updateBrandAverageRating(brandId);
        return reviewRepository.save(review);
    }

    @Transactional
    public void updateBrandAverageRating(Long brandId) {
        List<Review> reviews = reviewRepository.findByBrandId(brandId);
        double averageRating = reviews.stream().mapToDouble(Review::getRating).average().orElse(0.0);

        Brand brand = brandRepository.findById(brandId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid brand ID"));
        brand.setAverageRating(averageRating);
        brandRepository.save(brand); // 업데이트된 평점 저장
    }

    public List<Review> findReviewsByBrandId(Long brandId) {
        return reviewRepository.findByBrandId(brandId);
    }
}