package com.spring.attandance.repository.query;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.spring.attandance.controller.dto.member.MemberResponseDTO;
import com.spring.attandance.domain.Member;
import com.spring.attandance.domain.QGroupMember;
import com.spring.attandance.domain.QMember;
import com.spring.attandance.domain.cond.MemberSearchCondition;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.spring.attandance.domain.QGroupMember.groupMember;
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

    public Page<MemberResponseDTO> searchMemberList(MemberSearchCondition condition, Pageable pageable) {

        List<Member> memberList = queryFactory
                .selectFrom(member)
                .leftJoin(member.groupMembers, groupMember).fetchJoin()
                .where(
                        nameEq(condition.getName()),
                        mobileEq(condition.getMobile()),
                        emailEq(condition.getEmail())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        List<MemberResponseDTO> content = memberList.stream()
                .map(MemberResponseDTO::new)
                .collect(Collectors.toList());

        JPAQuery<Long> countQuery = queryFactory
                .select(Wildcard.count)
                .from(member)
                .where(
                        nameEq(condition.getName()),
                        mobileEq(condition.getMobile()),
                        emailEq(condition.getEmail())
                );

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    private BooleanExpression allEq(MemberSearchCondition condition) {

        return nameEq(condition.getName())
                .and(mobileEq(condition.getMobile()))
                .and(emailEq(condition.getEmail()));
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
