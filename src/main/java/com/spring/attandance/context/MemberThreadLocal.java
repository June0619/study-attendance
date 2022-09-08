package com.spring.attandance.context;


import com.spring.attandance.domain.Member;

/***
 * Request 별 회원 정보 관리를 위한 ThreadLocal 객체 클래스
 */
public class MemberThreadLocal {

    private static final ThreadLocal<Member> store = new ThreadLocal<>();

    public static Member getUserContext() {
        return store.get();
    }

    public static void setMemberContext(Member member) {
        store.set(member);
    }
}
