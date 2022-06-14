package com.mediscreen.central.repository;

import com.mediscreen.central.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

    @Query (value = "SELECT u FROM User u WHERE u.username=?1")
    User findByUsername(String username);
}
