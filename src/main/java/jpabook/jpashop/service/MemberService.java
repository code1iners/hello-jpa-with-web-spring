package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    /**
     * If use @RequiredArgsConstructor, have to add <b>'final'</b> keyword.
     */
    private final MemberRepository memberRepository;

    /**
     * Join new member method.
     * @return Member ID
     */
    @Transactional
    public Long join(Member member) {
        validateDuplicationMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    /**
     * Check duplication user in database.
     * @throws IllegalStateException
     */
    private void validateDuplicationMember(Member member) {
        List<Member> foundMembers = memberRepository.findByName(member.getName());
        if (!foundMembers.isEmpty()) {
            throw new IllegalStateException("The user is already exists.");
        }
    }

    /**
     * Find all member list.
     * @return All members.
     */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    /**
     * Find just one member.
     * @return Just one member.
     */
    public Member findOne(Long memberId) {
        return memberRepository.find(memberId);
    }
}
