package hello.hellospring.controller;
import hello.hellospring.domain.Brand;
import hello.hellospring.domain.EmploymentType;
import hello.hellospring.domain.Member;
import hello.hellospring.domain.Post;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.PostRepository;
import hello.hellospring.service.BrandService;
import hello.hellospring.service.LikeService;
import hello.hellospring.service.PostService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@Slf4j
public class PostController {


    private final PostService postService;
    private final BrandService brandService;
    private final MemberRepository memberRepository; // 추가
    private final PostRepository postRepository; // 추가

    private final LikeService likeService; // 추가


    @Autowired
    public PostController(PostService postService, BrandService brandService,
                          MemberRepository memberRepository, PostRepository postRepository,
                          LikeService likeService) {
        this.postService = postService;
        this.brandService = brandService;
        this.memberRepository = memberRepository; // 주입
        this.postRepository = postRepository; // 주입
        this.likeService = likeService; // 주입
    }

    @PostMapping("/brands/{name}/posts/employee/new")
    public ResponseEntity<String> createemployeePost(@PathVariable("name") String brandName, @RequestBody PostForm form,
                                             HttpSession session) {
        // 로그인한 회원 확인
        Member loggedInMember = (Member) session.getAttribute("loggedInMember");
        if (loggedInMember == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
        }

        // 고용 유형이 직원(employee)이 아닌 경우 게시글 작성 불가
        if (loggedInMember.getEmploymentType() != EmploymentType.EMPLOYEE) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("아르바이트생 회원만 게시글을 작성할 수 있습니다.");
        }
        // 브랜드 찾기
        Brand brand = brandService.findBrandByName(brandName)
                .orElseThrow(() -> new IllegalArgumentException("Invalid brand name: " + brandName));

        // 게시글 생성
        Post post = new Post();
        post.setTitle(form.getTitle());
        post.setContent(form.getContent());
        post.setCreatedAt(LocalDateTime.now());
        post.setAuthor(loggedInMember);
        post.setBrand(brand);
        post.setEmploymentType(loggedInMember.getEmploymentType());
        post.setLikeCount(0);
        post.setViewCount(0);

        postService.createPost(post);

        return ResponseEntity.status(HttpStatus.CREATED).body("게시글이 성공적으로 작성되었습니다.");
    }

    @PostMapping("/brands/{name}/posts/boss/new")
    public ResponseEntity<String> createbossPost(@PathVariable("name") String brandName, @RequestBody PostForm form,
                                             HttpSession session) {
        // 로그인한 회원 확인
        Member loggedInMember = (Member) session.getAttribute("loggedInMember");
        if (loggedInMember == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
        }

        // 고용 유형이 직원(employee)이 아닌 경우 게시글 작성 불가
        if (loggedInMember.getEmploymentType() != EmploymentType.BOSS) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("자영업자 회원만 게시글을 작성할 수 있습니다.");
        }
        // 브랜드 찾기
        Brand brand = brandService.findBrandByName(brandName)
                .orElseThrow(() -> new IllegalArgumentException("Invalid brand name: " + brandName));

        // 게시글 생성
        Post post = new Post();
        post.setTitle(form.getTitle());
        post.setContent(form.getContent());
        post.setCreatedAt(LocalDateTime.now());
        post.setAuthor(loggedInMember);
        post.setBrand(brand);
        post.setEmploymentType(loggedInMember.getEmploymentType());
        post.setLikeCount(0);
        post.setViewCount(0);

        postService.createPost(post);

        return ResponseEntity.status(HttpStatus.CREATED).body("게시글이 성공적으로 작성되었습니다.");
    }

    @PostMapping("/posts/new")
    public ResponseEntity<String> createPost(@RequestBody PostForm form, HttpSession session) {
        Member loggedInMember = (Member) session.getAttribute("loggedInMember");
        if (loggedInMember == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
        }

        Post post = new Post();
        post.setTitle(form.getTitle());
        post.setContent(form.getContent());
        post.setCreatedAt(LocalDateTime.now());
        post.setAuthor(loggedInMember);
        post.setBrand(null);  // 통합 게시판에서는 브랜드를 설정하지 않음
        post.setEmploymentType(null);  // 통합 게시판에서는 EmploymentType을 사용하지 않음
        post.setLikeCount(0);
        post.setViewCount(0);

        postService.createPost(post);

        return ResponseEntity.status(HttpStatus.CREATED).body("게시글이 성공적으로 작성되었습니다.");
    }
    @GetMapping("/brands/{name}/posts/new")
    public ResponseEntity<String> showCreatePostFormForBrand(@PathVariable("name") String name) {
        // 해당 브랜드에서 게시글 작성 폼 보여주기
        return ResponseEntity.ok("게시글 작성 폼을 보여줍니다.");
    }

    @GetMapping("/posts")
    public ResponseEntity<List<Post>> getAllPosts() {
        List<Post> posts = postService.getPostsForUnifiedBoard();
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/brands/{name}/posts/employee")
    public ResponseEntity<List<Post>> listPostsByBrandAndEmployee(@PathVariable("name") String name) {
        List<Post> posts = postService.findPostsByBrandNameAndEmploymentType(name, EmploymentType.EMPLOYEE);
        return ResponseEntity.ok(posts);
    }
    @GetMapping("/brands/{name}/posts/boss")
    public ResponseEntity<List<Post>> listPostsByBrandAndBoss(@PathVariable("name") String name) {
        List<Post> posts = postService.findPostsByBrandNameAndEmploymentType(name, EmploymentType.BOSS);
        return ResponseEntity.ok(posts);
    }
    @GetMapping("/posts/{id}")
    public ResponseEntity<Post> showPost(@PathVariable("id") Long postId) {
        // 게시글 상세 정보 조회 및 조회수 증가
        return postService.getPostById(postId)
                .map(post -> {
                    postService.increaseViewCount(postId);  // 조회수 증가
                    post.getComments().size(); // 강제로 댓글을 로드
                    return ResponseEntity.ok(post);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    @DeleteMapping("/posts/{id}")
    public ResponseEntity<String> deletePost(@PathVariable("id") Long postId, HttpSession session) {
        // 로그인한 회원 확인
        Member loggedInMember = (Member) session.getAttribute("loggedInMember");
        if (loggedInMember == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
        }

        Optional<Post> postOptional = postRepository.findById(postId);
        if (postOptional.isPresent()) {
            Post post = postOptional.get();
            if (post.getAuthor().getId().equals(loggedInMember.getId())||
                    loggedInMember.getEmploymentType() == EmploymentType.MASTER) {
                postRepository.deleteById(postId);
                return ResponseEntity.ok("게시글이 성공적으로 삭제되었습니다.");
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("게시글 작성자만 삭제할 수 있습니다.");
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 ID의 게시물을 찾을 수 없습니다.");
        }
    }


    @PutMapping("/posts/{id}")
    public void updatePost(@PathVariable("id") Long postId, HttpSession session, @RequestBody Map<String, String> requestBody) {
        // 로그인한 회원 확인
        Member loggedInMember = (Member) session.getAttribute("loggedInMember");
        if (loggedInMember == null) {
            throw new IllegalArgumentException("로그인이 필요합니다.");
        }

        Optional<Post> postOptional = postRepository.findById(postId);
        if (postOptional.isPresent()) {
            Post post = postOptional.get();
            if (post.getAuthor().getId().equals(loggedInMember.getId())) {
                String title = requestBody.get("title");
                String content = requestBody.get("content");

                // 수정할 내용이 없으면 그대로 두기
                if (title != null) {
                    post.setTitle(title);
                }
                if (content != null) {
                    post.setContent(content);
                }

                postRepository.save(post);
            } else {
                throw new IllegalArgumentException("게시글 작성자만 수정할 수 있습니다.");
            }
        } else {
            throw new IllegalArgumentException("해당 ID의 게시물을 찾을 수 없습니다.");
        }
    }

    // 좋아요 기능
    @PostMapping("/posts/{postId}/like")
    public ResponseEntity<String> likePost(@PathVariable("postId") Long postId, HttpSession session) {
        // 로그인 여부 확인
        Member loggedInMember = (Member) session.getAttribute("loggedInMember");
        if (loggedInMember == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
        }

        // 좋아요 기능 처리
        likeService.likePost(postId, loggedInMember);
        return ResponseEntity.ok("Liked");
    }

}



