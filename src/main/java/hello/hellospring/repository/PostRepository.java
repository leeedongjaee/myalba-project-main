package hello.hellospring.repository;

import hello.hellospring.domain.EmploymentType;
import hello.hellospring.domain.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    // 게시글 저장 메서드 추가
    Post save(Post post);

    List<Post> findByBrandId(Long id);

    List<Post> findByBrandName(String brandName);
    List<Post> findByBrandNameAndEmploymentType(String brandName, EmploymentType employmentType);
    List<Post> findByAuthorId(Long authorId);

    List<Post> findByEmploymentTypeIsNull();
}


