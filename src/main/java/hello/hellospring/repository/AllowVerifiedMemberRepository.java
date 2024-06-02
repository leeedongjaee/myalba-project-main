package hello.hellospring.repository;

import hello.hellospring.domain.AllowVerifiedMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AllowVerifiedMemberRepository extends JpaRepository<AllowVerifiedMember, Long> {
}
