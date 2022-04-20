package com.spcgSM.thymeleafTEST.controller;

import com.spcgSM.thymeleafTEST.model.Board;
import com.spcgSM.thymeleafTEST.repository.BoardRepository;
import com.spcgSM.thymeleafTEST.validator.BoardValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/board")
public class BoardController {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private BoardValidator boardValidator;

    @GetMapping("/list")
    public String list(Model model, @PageableDefault(size=10) Pageable pageable,
                       @RequestParam(required = false, defaultValue = "") String searchText){
        //Page<Board> boards = boardRepository.findAll(pageable);
        Page<Board> boards = boardRepository.findByTitleContainingOrContentContaining(searchText, searchText, pageable);

        int startPage = 1;
        int totalPages = boards.getTotalPages();
        int nowPage = boards.getPageable().getPageNumber();
        long resultA = boards.getTotalElements(); // 검색결과 getTotalElements 가 Page의 기능인데 전체 데이터 갯수를 가져온다

        if(resultA == 0){
            totalPages = startPage;
            model.addAttribute("boardsCheck","empty"); //검색결과 존재하지 않을경우
        }
        model.addAttribute("startPage", startPage);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("boards",boards);
        model.addAttribute("nowPage",nowPage);
        return "board/list";
    }

    @GetMapping("/form")
    public String form(Model model, @RequestParam(required = false) Long id) {
        if(id == null){
            model.addAttribute("board",new Board());
        }else{
            Board board = boardRepository.findById(id).orElse(null);
            model.addAttribute("board", board);
        }
        return "board/form";
    }

    @PostMapping("/form")
    public String greetingSubmit(@Valid Board board, BindingResult bindingResult){
        boardValidator.validate(board, bindingResult);
        if (bindingResult.hasErrors()) {
            return "board/form";
        }
        boardRepository.save(board);
        return "redirect:/board/list";
    }
}
