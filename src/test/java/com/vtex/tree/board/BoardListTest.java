package com.vtex.tree.board;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.vtex.tree.board.controller.BoardController;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = BoardController.class)
public class BoardListTest {
	
	@Autowired
	private MockMvc mvc;
	
	@Test
	public void test() throws Exception {
		mvc.perform(get("/board/list"))
			.andExpect(status().isOk())
			.andExpect(content().string("board/board"));
	}

}
