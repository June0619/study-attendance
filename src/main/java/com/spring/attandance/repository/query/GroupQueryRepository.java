package com.spring.attandance.repository.query;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.spring.attandance.controller.dto.group.GroupResponseDTO;
import com.spring.attandance.domain.Group;
import com.spring.attandance.domain.cond.GroupSearchCondition;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

import static com.spring.attandance.domain.QGroup.group;
import static com.spring.attandance.domain.QGroupMember.groupMember;
import static com.spring.attandance.domain.QMember.member;

@Repository
@RequiredArgsConstructor
public class GroupQueryRepository {

    private final JPAQueryFactory queryFactory;

    public Page<GroupResponseDTO> searchGroupList(GroupSearchCondition condition, Pageable pageable) {

        List<Group> groupList = queryFactory
                .selectFrom(group)
                .leftJoin(group.groupMembers, groupMember).fetchJoin()
                .where(
                    nameEq(condition.getName())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        List<GroupResponseDTO> content = groupList.stream()
                .map(GroupResponseDTO::new)
                .collect(Collectors.toList());

        JPAQuery<Long> countQuery = queryFactory
                .select(Wildcard.count)
                .from(group)
                .where(
                    nameEq(condition.getName())
                );

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    private BooleanExpression nameEq(String name) {
        return name != null ? group.name.eq(name) : null;
    }
}
