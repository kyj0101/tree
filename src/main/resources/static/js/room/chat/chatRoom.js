//채팅방 추가할 때 현재 접속한 사용자 리스트 가져오는 함수
function getLoginMemberList(){
	
	$('#add-modal-label').text("채팅방 추가");
	$(".add-board-btn").css("display","none");
	$(".add-chat-btn").css("display","inline");
	
	$.ajax({
		type: "POST",
		url: "/category/chat/memberlist",
		dataType: "json",
		success(result) {

			$(".member-list-ul").empty();

			$.each(result, function(index, elem) {

				var html = '<li class="member-list-li">';

				html += '<p>이름 : ' + elem.name + '</p>';
				html += '<p>부서 : ' + elem.departmentName + '</p>';
				html += '<p>직급 : ' + elem.positionName + '</p>';
				
				if(elem.loginAt == 'Y'){
					html += '<p><i class="fas fa-user-alt" style="color:blue;"></i></p>';
				
				}else{
					html += '<p><i class="fas fa-user-slash" ></i></p>';
				}
				
				html += '<input class="categoryAddMemberCheck" type="checkbox" value="' + elem.email + '">';
				html += '</li>';
				html += '<hr />';

				$(".member-list-ul").append(html);
			});

			return new Promise(function(resolve, reject) {
				$("#addModal").modal("show");
			});
		},

		error(xhr, status, err) {
			console.log(xhr, status, err);
		},
	});
}

function showAddCategoryChatModal(e){
	
	$(".modal-title").text("채팅방 추가");
	$(".add-board-btn").addClass("hidden");
	$(".add-meeting-btn").addClass("hidden");
	$(".add-chat-btn").removeClass("hidden");
	
	var projectId = $($(e).parent().parent().parent()).attr("id").replace("id_","");
	
	$("#projectId").val(projectId);
	showAddCategoryModal(projectId);
}

function addChatRoom(){
		
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
			url: "/category/chat/insert",
			data: {
				"title" : title,
				"esntlIdList" : esntlIdList,
				"projectId" : projectId
			},
			success(result) {

				if(result == "ok"){
						
					alert("채팅방이 생성되었습니다.");
					$('#addModal').modal('hide');
					location.replace("/board/list");
						
				}else{
					alert("채팅방 생성을 실패했습니다.");
				}
			}
		});			
	}
}

//매니저가 채팅방에서 회원 강제 퇴장시키는 함수
function outChatManager(e){
	
	var esntlId = $($(e).parent()).data("esntlid");

	if (confirm("회원을 게시판에서 내보냅니다.")) {
		$.ajax({
			type: "POST",
			url: "/category/chat/out/manager",
			data: {
				"esntlId": esntlId,
				"category":category
			},
			success(result) {

				if (result == "ok") {
					location.replace("/chat/room?category=" + category);

				} else {
					alert("내보내기를 실패했습니다.");
				}
			}
		});
	}
}
