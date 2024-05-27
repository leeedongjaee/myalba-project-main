package hello.hellospring.service;

import hello.hellospring.domain.EmploymentType;
import hello.hellospring.domain.Member;
import hello.hellospring.domain.Post;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.PostRepository;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;
@Service
@Transactional
public class PostService {
    private final PostRepository postRepository;
    private final MemberRepository memberRepository; // 사용자 정보를 가져오기 위한 Repository
    @Autowired
    public PostService(PostRepository postRepository, MemberRepository memberRepository) {
        this.postRepository = postRepository;
        this.memberRepository = memberRepository;
    }

    public void createPost(Post post) {
        postRepository.save(post);
    }

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Optional<Post> getPostById(Long postId) {
        return postRepository.findById(postId);
    }
    public List<Post> findPostsByBrandId(Long brandId) {
        return postRepository.findByBrandId(brandId);
    }
    public List<Post> findPostsByBrandName(String brandName) {
        List<Post> posts = postRepository.findByBrandName(brandName);
        // 프록시 객체 초기화
        for (Post post : posts) {
            Hibernate.initialize(post.getAuthor());
            Hibernate.initialize(post.getBrand());
        }
        return posts;
    }
    public void deletePost(Long postId, String nickname, String password) {
        // 작성자 닉네임을 통해 사용자 정보 조회
        Optional<Member> author = memberRepository.findByNickname(nickname);
        if (author.isPresent() && author.get().getPassword1().equals(password)) {
            // 비밀번호가 일치하면 게시글 삭제
            postRepository.deleteById(postId);
        } else {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
    }
    public void updatePost(Long postId, String title, String content) {
        Optional<Post> postOptional = postRepository.findById(postId);
        if (postOptional.isPresent()) {
            Post post = postOptional.get();
            post.setTitle(title);
            post.setContent(content);
            postRepository.save(post);
        } else {
            throw new IllegalArgumentException("해당 ID의 게시물을 찾을 수 없습니다.");
        }
    }
    public List<Post> findPostsByBrandNameAndEmploymentType(String brandName, EmploymentType employmentType) {
        return postRepository.findByBrandNameAndEmploymentType(brandName, employmentType);
    }

    public List<Post> findPostsByAuthorId(Long authorId) {
        return postRepository.findByAuthorId(authorId);
    }
    public List<Post> getPostsForUnifiedBoard() {
        return postRepository.findByEmploymentTypeIsNull();
    }
    @Transactional
    public void increaseViewCount(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("Invalid post ID: " + postId));
        post.setViewCount(post.getViewCount() + 1);
        postRepository.save(post);
    }
    public EmploymentType getPostAuthorEmploymentType(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid post ID"));
        return post.getAuthor().getEmploymentType();
    }
}
