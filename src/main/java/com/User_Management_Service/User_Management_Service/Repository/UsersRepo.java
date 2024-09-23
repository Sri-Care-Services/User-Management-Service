package com.User_Management_Service.User_Management_Service.Repository;

import com.User_Management_Service.User_Management_Service.Entity.Users;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsersRepo extends JpaRepository<Users, Long> {
    Optional<Users> findByEmail(String email);

    List<Users> findAllByRole(String user);
}
