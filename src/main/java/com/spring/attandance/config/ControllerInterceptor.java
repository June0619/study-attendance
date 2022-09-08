package com.spring.attandance.config;

import com.spring.attandance.context.MemberThreadLocal;
import com.spring.attandance.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@RequiredArgsConstructor
public class ControllerInterceptor implements HandlerInterceptor {

    private final AuthorizationParser parser;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        //임시 Auth Header => 전화번호
        String authorization = request.getHeader("Authorization");
        Member accessMember = parser.parse(authorization);
        MemberThreadLocal.setMemberContext(accessMember);
        return true;
    }
}
