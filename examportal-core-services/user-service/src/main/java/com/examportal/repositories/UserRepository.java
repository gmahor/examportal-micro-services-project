package com.examportal.repositories;

import com.examportal.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query(nativeQuery = true, value = "select * from users u where u.username like %:username or u.email like %:username")
    Optional<User> getByUsernameOrEmail(@Param("username") String username);


}
