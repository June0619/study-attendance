package com.spring.attandance.config.aop;

import com.spring.attandance.context.MemberThreadLocal;
import com.spring.attandance.controller.dto.member.LoginMemberDTO;
import com.spring.attandance.controller.dto.member.LoginMemberDTO.GroupMemberInnerDTO;
import com.spring.attandance.domain.enums.GroupRole;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.http.server.RequestPath;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.util.ServletRequestPathUtils;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Aspect
@Component
public class GroupAdvice {

    //특정 그룹에 대한 요청인지 파악한다
    private Long getGroupId() {

        HttpServletRequest request
                = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        Map<String, String> attribute = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        String pathVariable = attribute.get("id");

        //1. Group ID 를 Path 로 요청하는 경우 (Delete, Assign)
        if (StringUtils.hasText(pathVariable)) {
            return Long.valueOf(pathVariable);
        }

        //2. Group ID 를 DTO 로 요청하는 경우 (Update)
        String groupId = request.getParameter("id");

        if (StringUtils.hasText(groupId)) {
            return Long.valueOf(groupId);
        }

        //3. Group ID 요청이 존재하지 않는 경우
        return null;
    }

    private void checkAuthority(ProceedingJoinPoint joinPoint) {

        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();

        GroupAuth groupAuth = method.getAnnotation(GroupAuth.class);

        Long groupId = getGroupId();

        //권한 설정이 별도로 존재하지 않거나, 특정 그룹에 대한 요청이 아닌 경우
        if(groupAuth == null || groupId == null) {
            return;
        }

        //할당 된 권한 목록
        GroupRole[] roles = groupAuth.value();

        //접근 권한
        List<GroupMemberInnerDTO> groups = MemberThreadLocal.get().getGroups();

        groups.stream()
                .filter(dto -> dto.getGroupId().equals(groupId))
                .filter(dto -> Arrays.stream(roles).anyMatch(role -> role.equals(dto.getRole())))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("권한이 없습니다."));
    }

    @Around("execution(* com.spring.attandance.controller.GroupController.*(..))")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        checkAuthority(joinPoint);
        return joinPoint.proceed();
    }


}
