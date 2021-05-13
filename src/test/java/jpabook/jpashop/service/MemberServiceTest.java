package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

    /**
     * Test case which <b>member join</b>.
     * <ul>
     *  <li><b>'@Rollback(false)'</b> is support flush in JPA persistence for check real data in database.</li>
     * </ul>
     */
    @Test
//    @Rollback(false)
    public void 회원가입() throws Exception {
        // given
        Member member = new Member();
        member.setName("Name");

        // when
        Long savedId = memberService.join(member);

        // then
        assertEquals(member, memberRepository.findById(savedId));
    }

    /**
     * Test case which <b>duplicate username</b> when join.
     * <ul>
     *  <li><b>'expected' property is support to raise exception test.</b></li>
     * </ul>
     * @throws IllegalStateException
     */
    @Test(expected = IllegalStateException.class)
    public void 중복_회원_예외() throws Exception {
        // given
        Member member1 = new Member();
        member1.setName("member");

        Member member2 = new Member();
        member2.setName("member");

        // when
        memberService.join(member1);
        memberService.join(member2); // note. must will throw exception.

        // then
        fail("Must raised error.");
    }
}