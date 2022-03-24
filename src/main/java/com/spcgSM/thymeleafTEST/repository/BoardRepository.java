package com.spcgSM.thymeleafTEST.repository;

import com.spcgSM.thymeleafTEST.model.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {

}
