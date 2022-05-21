package com.spring.attandance.repository;

import com.spring.attandance.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JpaUserRepository implements UserRepository {

    private final EntityManager em;

    @Override
    public void save(User user) {
        em.persist(user);
    }

    @Override
    public Optional<User> findOne(Long userId) {
        return Optional.of(em.find(User.class, userId));
    }

    @Override
    public List<User> findList() {
        return em.createQuery("select u from User u", User.class)
                .getResultList();
    }
}
