package com.spring.attandance.domain.cond;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class MemberSearchCondition {
    private String name;
    private String mobile;
    private String email;
}
