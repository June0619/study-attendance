package com.spring.attandance.config;

import com.spring.attandance.context.MemberThreadLocal;
import com.spring.attandance.controller.dto.member.LoginMemberDTO;
import com.spring.attandance.domain.Member;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

/** 인터셉터 클래스 */
@Component
@RequiredArgsConstructor
public class ControllerInterceptor implements HandlerInterceptor {

    private final AuthorizationParser parser;
    private final Logger logger = LoggerFactory.getLogger("ControllerInterceptor");

    /** 컨트롤러에 요청이 들어오기 전에 처리된다. **/
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        //임시 Auth Header => 전화번호
        String authorization = request.getHeader("Authorization");
        LoginMemberDTO loginMember = parser.parse(authorization);
        MemberThreadLocal.set(loginMember);
        return true;
    }
}
