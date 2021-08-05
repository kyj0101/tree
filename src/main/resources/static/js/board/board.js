$(function() {
	$(".boardBtn").click(function() {

		var board = new Object()
		board.title = $("#title").val();
		board.content = $("#content").val();

		var formData = new FormData();
		var inputFile = $("input[name='uploadFile']");
		var count = 0;
		
		if(confirm("작성하시겠습니까?")){
			var isNull = nullCheck(board);
			
			if(isNull){
				alert("빈 칸이 있습니다.");
			}else{
				$.each(inputFile, function(index, elem) {

					if (elem.files.length > 0) {
						count++;
						formData.append("uploadFile", $(elem)[0].files[0]);
					}
				});

				board.noticeAt = $("#noticeAt:checked").val();

				if (count > 0) {
					$.ajax({
						url: "/board/file/insert",
						processData: false,
						contentType: false,
						data: formData,
						type: 'POST',
						success: function(result) {
							insertBoard(board, result);
						}
					});
					
				} else {
					insertBoard(board, null);
				}
			}
			
		}
		
	});

	$(".boardTitle").click(function() {
		var siblings = $(this).siblings();
		var boardNo = 0;
		
		//3개라면 일반 게시글 이상이라면 공지
		if (siblings.length == 3) {
			boardNo = $($(siblings[0])).text();

		} else {
			boardNo = $($(siblings[1])).val();
		}

		$.ajax({
			type: "POST",
			url: "/board/detail",
			data: {
				"boardNo":boardNo
			},
			success(result) {
				var board = result.board;
				var files = result.fileListMap;

				//게시글
				var info = "";

				info += "작성자 : ";
				info += board.name;
				info += " 조회수 : ";
				info += board.boardView;

				$(".boardDetailTitle").text(board.boardTitle);
				$(".textDiv").append(board.boardContent);
				$(".textDiv").append("<hr>");
				$(".board-info-p").text(info);

				var loginEmail = $(".loginMemberEmail").val();
				var authorEmail = board.email;

				if (loginEmail == authorEmail) {
					$(".board-update-btn").removeClass("hidden");
				}

				//파일
				$.each(files, function(index, elem){

					var aTag = "<li>";
					
					aTag += "<a class=\"fileA\" href=\"/board/file/download/?fileStore=" 
							+ encodeURI(elem.fileStore)
							+ '&renamedFileName=' 
							+ elem.renamedFileName
							+ '&originalFileName='
							+ elem.originalFileName + "\""
							+ '>'; 					
					aTag += elem.originalFileName;
					aTag += "</a>";
					aTag += "</li>";
					
					$(".file-download-ul").append(aTag);				
				});
			},
		});
	});
	
	$(".close").click(function(){
		location.replace('/board/list') 
	});
	
	$(".board-update-btn").click(function(){
		
		var title = $(".boardDetailTitle").text();
		var content = $(".textDiv").text();
		var files = [];
		
		if($(".fileA").length > 0){

			$(".input-file").empty();			

			$.each($(".fileA"), function(index, elem){

				var html = "<div class='input-group input-file'>";
				
				html += "<div class='custom-file'>";
				html += "<label class='custom-file-label file1'>" +  $(elem).text() +" </label>";
				html += "<input type='hidden' value='" + ($(elem).attr("href")).replace("/board/file/download/", "/board/file/delete") + "'/>"
				html += "</div>";
				html += "<div class='input-group-append' name='file-del-btn'>";
				html += "<button class='btn btn-outline-secondary' type='button' id='inputGroupFileAddon04'>파일삭제</button>";
				html += "</div>";
				html += "</div>";

				$(".modal-footer").append(html);
			});
		}
			
		$(".title-input").val(title);
		$(".board-content-textarea").val(content);
		$(".boardBtn").text("수정");

		$('#view-board-modal').modal('hide');
		$('#write-board-modal').modal('show');
	});
	
	$(".modal-footer").on("click", ".btn-outline-secondary", function() {
		var input = $($(this).parent().prev()).children()[1];

		if ($(".boardBtn").text() == '수정' && confirm("파일을 삭제하시겠습니까? 취소할 수 없습니다.")) {

			var url = $($($($(this).parent().parent()).children()[0]).children()[1]).val();
			var indexStore = url.indexOf("fileStore=");
			var indexRename = url.indexOf("&renamedFileName=");
			var indexOriginal = url.indexOf("&origin");

			var fileStore = url.substring(indexStore, indexRename).replace("fileStore=", "");
			var renamedFile = url.substring(indexRename, indexOriginal).replace("&renamedFileName=", "");
			
			$($(this).parent().parent()).empty();

			$.ajax({
				type: "POST",
				url: "/board/file/delete",
				data: {
					"renamedFile": renamedFile,
					"fileStore": fileStore
				},

				success(result) {
					if(result == "ok"){
						alert("삭제되었습니다.");
						
					}else{
						alert("삭제를 실패했습니다.");
					}
				},
			});

		} else {
			$(input).prev().text("파일을 첨부하세요.");
			$(input).val("");
		}

	});
});

function insertBoard(board, result) {
	$.ajax({
		type: "POST",
		url: "/board/insert",
		data: {
			"title": board.title,
			"content": board.content,
			"fileId": result,
			"noticeAt": board.noticeAt
		},
		success(result) {
			console.log(result);
			if (result == "ok") {
				alert("작성되었습니다.");
				location.replace('/board/list')

			} else {
				alert("작성에 실패하였습니다.");
				return;
			}
		},
	});
}