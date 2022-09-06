package com.spring.attandance.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.spring.attandance.domain.Member;
import com.spring.attandance.domain.QMember;
import com.spring.attandance.domain.cond.MemberSearchCondition;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.spring.attandance.domain.QMember.member;

@Repository
@RequiredArgsConstructor
public class MemberQueryRepository {

    private final JPAQueryFactory queryFactory;

    public Optional<Member> searchMember(MemberSearchCondition condition) {
        return Optional.ofNullable(queryFactory
                .selectFrom(QMember.member)
                .where(
                        nameEq(condition.getName()),
                        mobileEq(condition.getMobile()),
                        emailEq(condition.getEmail())
                )
                .fetchOne());
    }

    private BooleanExpression nameEq(String name) {
        return name != null ? member.name.eq(name) : null;
    }

    private BooleanExpression mobileEq(String mobile) {
        return mobile != null ? member.mobile.eq(mobile) : null;
    }

    private BooleanExpression emailEq(String email) {
        return email != null ? member.email.eq(email) : null;
    }
}
