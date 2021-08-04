package com.vtex.tree.board.controller;

import static com.vtex.tree.common.util.FileUtil.getFileMap;
import static com.vtex.tree.common.util.PageBar.getPageBar;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.vtex.tree.board.service.BoardService;
import com.vtex.tree.board.vo.BoardVO;
import com.vtex.tree.home.service.HomeService;
import com.vtex.tree.member.vo.MemberVO;

@RequestMapping("/board")
@Controller
public class BoardController {
	
	private final int NUMPERPAGE = 10;
	
	@Autowired
	private BoardService boardService;

	@Autowired
	private HomeService homeService;

	@Autowired
	private ServletContext servletContext;

	@RequestMapping("/list")
	public String getBoardList(@RequestParam(defaultValue = "1") int cPage,HttpServletRequest request, @RequestParam(defaultValue = "1") int category, Model model) throws Exception {
		
		Map<String, Object> param = new HashMap<>();

		param.put("numPerPage", NUMPERPAGE);
		param.put("cPage", cPage);
		
		int offset = (cPage - 1) * NUMPERPAGE;		
		RowBounds rowBounds = new RowBounds(offset, NUMPERPAGE);
		
		int totalContents = boardService.getBoardListCnt(category);
		String url = request.getRequestURI();
		String pageBar = getPageBar(totalContents, cPage, NUMPERPAGE, url);
		
		
		List<BoardVO> boardList = boardService.getBoardList(category, rowBounds);
		model.addAttribute("boardList", boardList);
		model.addAttribute("pageBar", pageBar);
		return "board/board";
	}

	@ResponseBody
	@RequestMapping("/file/insert")
	public int insertFile(MultipartFile[] uploadFile) throws Exception {

		String saveDirectory = "C:\\Users\\kangyujeong98\\Documents\\workspace\\git\\tree\\tree\\src\\main\\resources\\static\\upload\\board";
		Map<String, Object> resultMap = new HashMap<>();
		
		boardService.insertFile(resultMap);
		
		int resultCnt = 0;
		int fileId = Integer.parseInt(resultMap.get("no") + "");
		int fileSn = 0;

		for (MultipartFile upFile : uploadFile) {

			fileSn++;

			Map<String, Object> fileMap = getFileMap(upFile, saveDirectory, fileId, fileSn);
			resultCnt = boardService.insertFileDetail(fileMap);
		}

		if (resultCnt > 0) {
			return fileId;
		}

		return 0;
	}

	@RequestMapping("/insert")
	public ResponseEntity<String> insertBoard(String title, 
												String content, 
												String noticeAt,
												String fileId,
												HttpServletRequest request,
												MultipartFile[] uploadFile) throws Exception {

		HttpSession session = request.getSession();
		MemberVO member = (MemberVO) session.getAttribute("loginMember");
		Map<String, Object> param = new HashMap<>();
		System.out.println(fileId);
		
		param.put("title", title);
		param.put("content", content);
		param.put("email", member.getEmail());
		param.put("fileId", fileId);
		param.put("noticeAt", noticeAt != null ? "Y" : "N");

		int resultCnt = boardService.insertBoard(param);

		if (resultCnt > 0) {
			return new ResponseEntity<String>("ok", HttpStatus.OK);
		}
		return new ResponseEntity<String>("fail", HttpStatus.BAD_REQUEST);
	}
	
	@RequestMapping("/detail")
	public void getBoardDetail(int boardNo) {
		
	}

}
