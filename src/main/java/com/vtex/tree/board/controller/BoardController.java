package com.vtex.tree.board.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.vtex.tree.board.service.BoardService;

import ch.qos.logback.core.status.Status;

@RequestMapping("/board")
@Controller
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	
	@RequestMapping("/list")
	public String getBoardList() {
		return "board/board";
	}
	
	@RequestMapping("/insert")
	public ResponseEntity<String> insertBoard(String title, String content, MultipartFile[] uploadFile ){
		return new ResponseEntity<String>("ok", HttpStatus.OK);
	}
	
	
}
