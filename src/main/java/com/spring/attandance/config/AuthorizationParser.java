package com.spring.attandance.config;

import com.spring.attandance.domain.Member;

public interface AuthorizationParser {

    Member parse(String authorization);
}
