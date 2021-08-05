package com.vtex.tree.board.controller;

import static com.vtex.tree.common.util.FileUtil.getFileMap;
import static com.vtex.tree.common.util.PageBar.getPageBar;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.vtex.tree.board.service.BoardService;
import com.vtex.tree.board.vo.BoardVO;
import com.vtex.tree.common.util.FileUtil;
import com.vtex.tree.home.service.HomeService;
import com.vtex.tree.member.vo.MemberVO;
import java.net.URLEncoder;
@RequestMapping("/board")
@Controller
public class BoardController {
	
	private final int NUMPERPAGE = 10;
	
	@Autowired
	private BoardService boardService;

	@Autowired
	private HomeService homeService;

	@Autowired
	private ResourceLoader resourceLoader;

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
	public ModelAndView getBoardDetail(int boardNo) throws Exception {
		
		//게시글 가져오기
		ModelAndView mav = new ModelAndView();
		BoardVO board = boardService.selectOneBoard(boardNo);
		List<Map<String, Object>> fileListMap = new ArrayList<>();
		
		if(board.getFileId() != 0) {
			fileListMap = boardService.selectBoardFiles(board.getFileId());
		}
		
		//게시글 조회수 증가
		Map<String, Object> param = new HashMap<>();
		int boardView = board.getBoardView() + 1;
		param.put("boardView", boardView);
		param.put("boardNo", boardNo);
		
		boardService.addViewNum(param);
		board.setBoardView(boardView);
		
		mav.addObject("board", board);
		mav.addObject("fileListMap", fileListMap);
		mav.setViewName("jsonView");
		return mav;
	}
	
	@RequestMapping("/file/download")
	public ResponseEntity<Resource> fileDowload(String fileStore, String renamedFileName, String originalFileName) throws UnsupportedEncodingException{
		
		String saveDirectory = fileStore;
		File downFile = new File(saveDirectory, renamedFileName);
		Resource resource = resourceLoader.getResource("file:" + downFile);
		originalFileName = "attachment;fileName=\"" + URLEncoder.encode(originalFileName, "UTF-8") + "\"";
		
		return ResponseEntity.ok()
							.contentType(MediaType.APPLICATION_OCTET_STREAM)
							.header(HttpHeaders.CONTENT_DISPOSITION, originalFileName).body(resource);
	}
	
	@ResponseBody
	@RequestMapping("/file/delete")
	public String fileDelete(String fileStore, String renamedFile) throws Exception {
		fileStore = fileStore.replace("%5C", "\\");
		FileUtil.deleteOneFile(fileStore, renamedFile);
		
		Map<String, Object> param = new HashMap<String, Object>();
		
		param.put("fileStore", fileStore);
		param.put("renamedFile", renamedFile);
		
		int resultCnt = boardService.deleteFile(param);
		
		if(resultCnt > 0) {
			return "ok";	
		}
		
		return "fail";
	}
}
