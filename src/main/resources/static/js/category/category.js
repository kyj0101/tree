function deleteBoard(){
	
	var categoryNo = $("#categoryNo").val();
	
	if (confirm("게시판을 삭제하시겠습니까?")) {
		$.ajax({
			type: "POST",
			url: "/category/delete",
			data: {
				"categoryNo": categoryNo
			},
			success(result) {

				if (result == "ok") {
					alert("게시판이 삭제되었습니다.");
					location.replace("/board/list");

				} else {
					alert("게시판 삭제를 실패했습니다.");
				}
			}
		});
	}
}

function outBoard(){
	
	var categoryNo = $("#categoryNo").val();
	
	if (confirm("게시판을 나가시겠습니까?")) {
		$.ajax({
			type: "POST",
			url: "/category/out",
			data: {
				"categoryNo": categoryNo
			},
			success(result) {

				if (result == "ok") {
					location.replace("/board/list");

				} else {
					alert("게시판 나가기를 실패했습니다.");
				}
			}
		});
	}
}

//매니저가 게시판에서 회원 강제 퇴장시키는 함수
function outBoardManager(e){
	var esntlId = $($(e).parent()).data("esntlid");
	var categoryNo = $("#categoryNo").val();
	
	if (confirm("회원을 게시판에서 내보냅니다.")) {
		$.ajax({
			type: "POST",
			url: "/category/out/manager",
			data: {
				"esntlId": esntlId,
				"categoryNo":categoryNo
			},
			success(result) {

				if (result == "ok") {
					location.replace("/board/list?category=" + categoryNo);

				} else {
					alert("내보내기를 실패했습니다.");
				}
			}
		});
	}
	
}

function showAddCategoryBoardModal(e){
	
	var projectId = $($(e).parent().parent().parent()).attr("id").replace("id_","");
	
	$("#projectId").val(projectId);
	$('#add-modal-label').text("프로젝트 게시판 추가");	
	$(".add-board-btn").removeClass("hidden");
	$(".add-chat-btn").addClass("hidden");
	
	showAddCategoryModal(projectId);
}


function addCategoryBoard(){
	
	var esntlIdList = getEsntlId();
	var title = $(".title-input").val();
	var projectId = $("#projectId").val();

	if(title.length <= 0){
		alert("제목을 입력하세요.");
			
	}else if(esntlIdList.length <= 0){
		alert("회원을 선택하세요.");
	
	}else{
		$.ajax({
			type: "POST",
			url: "/category/insert",
			data: {
				"title" : title,
				"esntlIdList" : esntlIdList,
				"projectId" : projectId
			},
			success(result) {

				if(result == "ok"){
						
					alert("게시판이 생성되었습니다.");
					$('#addModal').modal('hide');
					location.replace("/board/list");
						
				}else{
					alert("게시판 생성을 실패했습니다.");
				}
			}
		});			
	}
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
		url: "/category/memberlist",
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
		url: "/category/add/member",
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



