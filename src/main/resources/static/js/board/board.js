var userIsManager = $("#frstRegisterId").val() === $("#loginEsntlId").val();

$(function() {
	
	var categoryNo = $("#categoryNo").val();

	if(!userIsManager){
		$(".boardAddMember").css("display","none");
	}
	
	$(".boardBtn").click(function() {

		var board = new Object()
		board.title = $("#title").val();
		board.content = editor.getHTML();
		board.categoryNo = $("#categoryNo").val();
		
		var formData = new FormData();
		var inputFile = $("input[name='uploadFile']");
		var text = $(".boardBtn").text();
		var count = 0;
		
		//수정 or 새로 입력 
		if(text == "수정" && confirm("수정하시겠습니까?")){
			var isNull = nullCheck(board);

			if (isNull) {
				alert("빈 칸이 있습니다.");
			} else {
				
				board.noticeAt = $("#noticeAt:checked").val()
				board.boardNo = $("#boardNo").val();
				formData = returnFormData(inputFile, count);

				var url = $(".hiddenFile").val();

				//파일이 있다면
				if (formData != null) {
					
					//기존 파일이 있다면
					if(url != undefined){
						var arr = url.split(",");
						var fileId = arr[0];
					
						formData.append("fileId", fileId);		
						updateFile(formData);
					
					//기존 파일이 없다면	
					}else{
						formData.append("boardNo", board.boardNo);	
						updateInsertFile(formData);
					}

					updateBoard(board);

				} else {
					updateBoard(board);
				}
				return;
			}
			
		}else if(text == "글쓰기" && confirm("작성하시겠습니까?")){
	
			var isNull = nullCheck(board);

			if (isNull) {
				alert("빈 칸이 있습니다.");
			} else {
				formData = returnFormData(inputFile, count);
				board.noticeAt = $("#noticeAt:checked").val();
			
				//파일이 있다면 파일 먼저 insert
				if (formData != null) {
					insertFile(formData, board);

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
				$(".board-info-p").text(info);
				$("#boardNo").val(board.boardNo);
				
				var loginEmail = $(".loginMemberEmail").val();
				var authorEmail = board.email;
				
				if(board.noticeAt == "Y"){
					$("#noticeAt").attr("checked", true);
				}
				
				if (loginEmail == authorEmail) {
					$(".board-update-btn").removeClass("hidden");
					$(".boardDeleteBtn").removeClass("hidden");
				}
				
				//파일
				$.each(files, function(index, elem){

					var aTag = "<li>";
					
					aTag += "<a class=\"fileA\" href=\"/board/file/download/?fileStore=" 
							+ encodeURI(elem.fileStore)
							+ '&fileId='
							+ elem.fileId
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
				
						
				return new Promise(function(resolve, reject){
					$("#view-board-modal").modal("show");
				})
			},
		});
	});
	
	$(".close").click(function(){
		location.replace('/board/list?category=' + categoryNo)
	});
	
	$(".board-update-btn").click(function(){
		
		var boardNo = $("#boardNo").val();

		$.ajax({
			type: "POST",
			url: "/board/detail",
			data: {
				"boardNo":boardNo
			},
			success(result){

				var board = result.board;
				var fileListMap = result.fileListMap;
				
				$(".title-input").val(board.boardTitle);
				editor.setHTML(board.boardContent);
				
				if(fileListMap.length > 0){

					$(".input-file").empty();			
	
					$.each(fileListMap, function(index, elem){
		
						var html = "<div class='input-group input-file'>";
						
						html += "<div class='custom-file'>";
						html += "<label class='custom-file-label file1'>" +  elem.originalFileName +" </label>";
						html += "<input type='hidden' class='hiddenFile' value='" + + elem.fileId + "," + elem.fileSn + "'/>"
						html += "</div>";
						html += "<div class='input-group-append' name='file-del-btn'>";
						html += "<button class='btn btn-outline-secondary' type='button' id='inputGroupFileAddon04'>파일삭제</button>";
						html += "</div>";
						html += "</div>";
		
						$(".modal-footer").append(html);
					});
				}
									

				
				return new Promise(function(resolve, reject){
					$(".boardBtn").text("수정");
					$('#view-board-modal').modal('hide');
					$('#write-board-modal').modal('show');
				})
			},
		});
	});
	
	$(".modal-footer").on("click", ".btn-outline-secondary", function() {
		var input = $($(this).parent().prev()).children()[1];

		if ($(".boardBtn").text() == '수정' && confirm("파일을 삭제하시겠습니까? 취소할 수 없습니다.")) {

			var value = ($($($($(this).parent().parent()).children()[0]).children()[1]).val()).split(",");
			console.log(value);
			$($(this).parent().parent()).empty();

			$.ajax({
				type: "POST",
				url: "/board/file/delete",
				data:{
					"fileId":value[0],
					"fileSn":value[1],
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
	
	$(".add-file-input").click(function(){

        var num = ($(".btn-outline-secondary:visible").length);

        if(num >= 5){
            alert("파일 첨부는 최대 5개 입니다.");
            return;

        }else{
            var fileinput = "<div class='input-group input-file'><div class='custom-file'><label class='custom-file-label file1'>파일을 첨부하세요.</label> <input type='file' class='custom-file-input file-input'name='uploadFile'><hr /></div><div class='input-group-append' name='file-del-btn'><button class='btn btn-outline-secondary' type='button' id='inputGroupFileAddon04'>파일삭제</button></div></div>";
            $(".modal-footer").append(fileinput);
        }
    });

	$(".boardDeleteBtn").click(function(){
	
		var boardNo = $("#boardNo").val();
	
		$.ajax({
			type: "POST",
			url: "/board/delete",
			data: {"boardNo":boardNo},
			success(result) {

				if (result == "ok") {
					alert("삭제되었습니다.");
					location.replace('/board/list?category=' + categoryNo)

				} else {
					alert("삭제를 실패하였습니다.");
					return;
				}
			},
		});
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
			"noticeAt": board.noticeAt,
			"categoryNo":board.categoryNo
		},
		success(result) {
	
			if (result == "ok") {
				alert("작성되었습니다.");
				location.replace('/board/list?category=' + board.categoryNo)

			} else {
				alert("작성에 실패하였습니다.");
				return;
			}
		},
	});
}

function insertFile(formData,board){

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
}

//게시글을 업데이트 하는데 기존의 파일이 없는 경우
function updateInsertFile(formData){
	console.log(formData);
	$.ajax({
		url: "/board/file/updateinsert",
		processData: false,
		contentType: false,
		data: formData,
		type: 'POST',
		success: function(result) {
			console.log(result);
		}
	});
}

function updateFile(formData){

	$.ajax({
		url: "/board/file/update",
		processData: false,
		contentType: false,
		data: formData,
		type: 'POST',
		success: function(result) {

		}
	});
}

function updateBoard(board){
	
	$.ajax({
		type: "POST",
		url: "/board/update",
		data: {
			"title": board.title,
			"content": board.content,
			"noticeAt": board.noticeAt,
			"boardNo":board.boardNo
		},
		success(result) {
			console.log(result);
			if (result == "ok") {
				alert("수정되었습니다.");
				location.replace('/board/list?category=' + board.categoryNo)

			} else {
				alert("수정을 실패하였습니다.");
				return;
			}
		},
	});
}

function returnFormData(inputFile){
  	
	var formData = new FormData();
	var haveFile = false;

	$.each(inputFile, function(index, elem) {

		if (elem.files.length > 0) {
			
			formData.append("uploadFile", $(elem)[0].files[0]);
			haveFile = true;
		}
	});
	
	if(haveFile){
		return formData;		
	}else{
		return null;
	}
}


function showBoardMember(){
	
	$(".boardMemberDiv").css("display","block");
	$(".boardAddMemberDiv").css("display","none");
	$(".boardInfoDiv").css("display","none");
	
	$(".boardInfo").removeClass("active");
	$(".boardAddMember").removeClass("active");
	$(".boardMember").addClass("active");
	
	var categoryNo = $("#categoryNo").val();
	
	$.ajax({
		type: "POST",
		url: "/board/member/list",
		data: {
			"categoryNo": categoryNo,
		},
		success(result) {
			
			$(".boardMemberUl").empty();
			
			$.each(result, function(index, elem) {
				
				if(elem.esntlId === $("#loginEsntlId").val()){
					return true;
				}
				
				var html = '<li class="list-group-item" data-esntlid="';
				html += elem.esntlId;
				html += '">';
				html += elem.name;
				html += '(';
				html += elem.email;
				html += ')';
				html += '<span class="badge badge-pill badge-primary">';
				html += elem.departmentName;
				html += '</span>';
				html += '<span class="badge badge-pill badge-info">';
				html += elem.positionName;
				html += '</span>';

				if(userIsManager){
					
					html += '<button type="button" class="btn btn-light deleteMember" onclick="outBoardManager(this);">';
					html += '<i class="fas fa-times"></i>';					
				}
				
				html += '</li>';
				
				$(".boardMemberUl").append(html);				
			});
		},
	});
}

function showBoardInfo(){
	
	$(".boardInfoDiv").css("display","block");
	$(".boardMemberDiv").css("display","none");
	$(".boardAddMemberDiv").css("display","none");
	
	$(".boardAddMember").removeClass("active");
	$(".boardMember").removeClass("active");
	$(".boardInfo").addClass("active");	
}

function showAddMember(){
	
	
	$(".boardInfoDiv").css("display","none");
	$(".boardMemberDiv").css("display","none");
	$(".boardAddMemberDiv").css("display","block");
	
	$(".boardInfo").removeClass("active");
	$(".boardMember").removeClass("active");
	$(".boardAddMember").addClass("active");
	
	$.ajax({
		type: "POST",
		url: "/category/board/memberlist",
		data:{"categoryNo":$("#categoryNo").val()},
		dataType: "json",
		success(result) {
			
			$(".boardAddMemberUl").empty();
			
			if(result.length > 0){
				
				var html = '<li class="list-group-item">';
				html += '<div class="form-check">';
				html += '<input class="form-check-input" type="checkbox" value="" id="checkedAll2" onchange="changeCheck(this);">';
		  		html += '<label class="form-check-label" for="checkedAll2" id="checkedAllLabel">';
		    	html += '전체 선택';
				html += '</label>';
				html += '</div>';
				html += '</li>';
				
				$(".boardAddMemberUl").append(html);
			}
			
			$.each(result, function(index, elem) {
				
				var html = '<li class="list-group-item">';

				html += '<p>' ;
				html += elem.name; 
				html += '('; 
				html += elem.email
				html += ')';
				html += '<input class="categoryAddMemberCheck" type="checkbox" value="' + elem.esntlId + '">';
				html += '</p>';
				html += '</li>';

				$(".boardAddMemberUl").append(html);
			});
		},
	});
}

function addBoardMember(){
	
	var esntlIdList = getEsntlId();
	
	if(esntlIdList.length == 0){
		alert("회원을 선택하세요.");
		return false;
	}

	var categoryNo = $("#categoryNo").val();
	
	$.ajax({
		type: "POST",
		url: "/category/board/add/member",
		data: {
			"esntlIdList": esntlIdList,
			"categoryNo": categoryNo
		},
		success(result) {

			if (result == "ok") {

				alert("추가되었습니다.");
				location.replace("/board/list?category=" + categoryNo);

			} else {
				alert("추가를 실패했습니다.");
			}
		}
	});		
}
