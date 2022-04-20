package com.spcgSM.thymeleafTEST.repository;

import com.spcgSM.thymeleafTEST.model.Board;
import com.spcgSM.thymeleafTEST.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
