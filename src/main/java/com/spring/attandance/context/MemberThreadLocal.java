package com.spring.attandance.context;


import com.spring.attandance.controller.dto.member.LoginMemberDTO;
import com.spring.attandance.domain.Member;

/***
 * Request 별 회원 정보 관리를 위한 ThreadLocal 객체 클래스
 */
public class MemberThreadLocal {

    private static final ThreadLocal<LoginMemberDTO> store = new ThreadLocal<>();

    public static LoginMemberDTO get() {
        return store.get();
    }

    public static void set(LoginMemberDTO loginMemberDTO) {
        store.set(loginMemberDTO);
    }
}
