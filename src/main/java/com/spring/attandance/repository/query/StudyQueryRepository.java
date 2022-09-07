package com.spring.attandance.repository.query;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.spring.attandance.domain.Study;
import com.spring.attandance.domain.cond.StudySearchCondition;
import com.spring.attandance.domain.enums.PassedStudy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

import static com.spring.attandance.domain.QStudy.study;
import static com.spring.attandance.domain.enums.PassedStudy.*;

@Repository
@RequiredArgsConstructor
public class StudyQueryRepository {

    private final JPAQueryFactory queryFactory;

    public List<Study> searchStudy(StudySearchCondition condition) {
        return queryFactory
                .selectFrom(study)
                .where(
                        ownerId(condition.getOwnerId()),
                        ownerNameLike(condition.getName()),
                        passed(condition.getPassedStudy())
                )
                .fetch();

    }

    private BooleanExpression ownerId(Long ownerId) {
        return ownerId != null ? study.owner.id.eq(ownerId) : null;
    }

    private BooleanExpression ownerNameLike(String name) {
        return name != null ? study.owner.name.like(name) : null;
    }

    // study passed check
    private BooleanExpression passed(PassedStudy passedFlag) {
        if (passedFlag == null) {
            return null;
        } else if (passedFlag == PASSED) {
            return study.endTime.before(LocalDateTime.now());
        } else if (passedFlag == ON_GOING) {
            return study.startTime.before(LocalDateTime.now())
                    .and(study.endTime.after(LocalDateTime.now()));
        } else if (passedFlag == NOT_OPENED) {
            return study.startTime.after(LocalDateTime.now())
                    .and(study.endTime.after(LocalDateTime.now()));
        } else {
            throw new IllegalArgumentException("PassedStudy is not valid");
        }
    }

}
