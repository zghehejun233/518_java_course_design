package org.fatmansoft.teach.repository;


import java.util.Optional;

import org.fatmansoft.teach.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUserName(String userName);
    Optional<User> findByPersonPerNum(String perNum);


    Optional<User> findByUserId(Integer userId);

    Boolean existsByUserName(String userName);
}