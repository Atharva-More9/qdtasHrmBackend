package com.qdtas.repository;

import com.qdtas.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Long> {

    public User findByEmail(String email);

    @Query(value = "SELECT * FROM users WHERE first_name LIKE CONCAT('%' ,:keyword ,'%')" +
            " OR last_name LIKE CONCAT('%' ,:keyword ,'%') order by first_name asc ,last_name asc",nativeQuery = true)
    List<User> findByFirstNameOrLastNameLike(String keyword, Pageable pageable);

}