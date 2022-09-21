package com.spring.attandance.config.aop;

import com.spring.attandance.domain.enums.GroupRole;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/***
 * 스터디 그룹 권한 체크 애노테이션
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface GroupAuth {
    GroupRole[] value();
}
