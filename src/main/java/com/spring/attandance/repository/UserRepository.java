package com.spring.attandance.repository;

import com.spring.attandance.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    public void save(User user);
    public Optional<User> findOne(Long userId);
    public List<User> findList();

}
