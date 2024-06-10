package hello.hellospring.service;

import hello.hellospring.domain.Allow;
import hello.hellospring.domain.AllowVerifiedMember;
import hello.hellospring.repository.AllowRepository;
import hello.hellospring.repository.AllowVerifiedMemberRepository;
import hello.hellospring.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AllowService {

    private final AllowRepository allowRepository;
    private final AllowVerifiedMemberRepository allowVerifiedMemberRepository;
    private final MemberRepository memberRepository;

    @Value("${file.upload-dir}")//이미지 파일 경로 지정
    private String uploadDir;

    @Autowired
    public AllowService(AllowRepository allowRepository, AllowVerifiedMemberRepository allowVerifiedMemberRepository, MemberRepository memberRepository) {
        this.allowRepository = allowRepository;
        this.allowVerifiedMemberRepository = allowVerifiedMemberRepository;
        this.memberRepository = memberRepository;
    }

        //근로계약서 인증 글 작성 서비스
        public void createAllow(Allow allow) {
            allowRepository.save(allow);
        }

    public List<Allow> getAllAllows() {
        return allowRepository.findAll();
    }//인증 글 목록 조회 서비스

    public Optional<Allow> getAllowById(Long id) {
        return allowRepository.findById(id);
    }//ID를 통해 인증 글 찾는 서비스

    public void deleteAllow(Long id) {
        allowRepository.deleteById(id);
    }//인증 글 삭제 서비스

    public void approveAllow(Long allowId) {
        Allow allow = allowRepository.findById(allowId).orElseThrow(() -> new IllegalArgumentException("Invalid allow ID"));
        AllowVerifiedMember verifiedMember = new AllowVerifiedMember();
        verifiedMember.setMember(allow.getMember());
        verifiedMember.setBrand(allow.getBrand());
        allowVerifiedMemberRepository.save(verifiedMember);
    }//인증 글 허가 서비스

    public void rejectAllow(Long allowId) {
        allowRepository.deleteById(allowId);
    }//인증 글 거부 서비스
    public boolean isVerifiedMember(Long memberId, Long brandId) {
        return allowVerifiedMemberRepository.existsByMemberIdAndBrandId(memberId, brandId);
    }//회원이 해당 브랜드에 인증되었는지 찾는 서비스

    // 이미지 저장 서비스 (PostService와 동일한 메서드 사용)
    public List<String> saveImages(List<MultipartFile> images) throws IOException {
        List<String> imageUrls = new ArrayList<>();
        for (MultipartFile image : images) {
            String fileName = saveImage(image);
            String fileUrl = "/images/" + fileName; // URL 경로
            imageUrls.add(fileUrl);
        }
        return imageUrls;
    }

    // 이미지 저장 메서드 (PostService와 동일한 메서드 사용)
    private String saveImage(MultipartFile image) throws IOException {
        String fileName = System.currentTimeMillis() + "_" + image.getOriginalFilename();
        Path filePath = Paths.get(uploadDir, fileName);
        File file = new File(filePath.toString());
        if (!file.exists()) {
            file.mkdirs();
        }
        image.transferTo(filePath.toFile());
        return fileName;
    }
}
