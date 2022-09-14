package com.spring.attandance.config.auth;

import com.spring.attandance.context.MemberThreadLocal;
import com.spring.attandance.controller.dto.member.LoginMemberDTO;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class LoginMemberArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean isLoginMemberAnnotation = parameter.getParameterAnnotation(LoginMember.class) != null;
        boolean isUserClass = LoginMemberDTO.class.equals(parameter.getParameterType());
        return isLoginMemberAnnotation && isUserClass;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        return MemberThreadLocal.get();
    }
}
