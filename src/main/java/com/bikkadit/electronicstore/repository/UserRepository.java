package com.bikkadit.electronicstore.repository;

import com.bikkadit.electronicstore.dto.UserDto;
import com.bikkadit.electronicstore.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {



  Optional<User>   findUserByEmail(String email);

// User findUserByEmailandPassworg(String password,String email);
List<User> findUserByNameContaining(String keyword);

}
