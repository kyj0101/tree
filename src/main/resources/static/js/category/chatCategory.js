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

function addChatRoom(){
	
	var checkedArr = $(".categoryAddMemberCheck:checked");
	var emailArr = [];
	var title = $(".title-input").val();

	//체크박스에 있는 email value를 array에 넣음
	$.each(checkedArr, function(index, elem) {
		emailArr.push($(elem).val());
	});
	
	console.log(checkedArr);
	console.log(emailArr);
	console.log(title);

	if (title.length <= 0) {
		alert("제목을 입력하세요.");


	} else if (emailArr.length <= 0) {
		alert("회원을 선택하세요.");

	} else {

		$.ajax({
			type: "POST",
			url: "/category/chat/insert",
			data: {
				"title": title,
				"emailArr": emailArr
			},
			success(result) {
				console.log(result);
				if (result == "ok") {
					alert("채팅방이 생성되었습니다.");
					$('#addModal').modal('hide');
					location.replace("/board/list");
				} else {
					alert("채팅방 생성을 실패했습니다.");
				}
			},

			error(xhr, status, err) {
				console.log(xhr, status, err);
			}
		});
	}
}

//채팅방 나가기(x버튼)
function chatLeave(e){
	
	if(confirm("채팅방을 나가시겠습니까?")){
		var url = $($(e).prev()).attr("href");
		var indexCategory = url.indexOf("?category=");
		var category = url.substring(indexCategory).replace("?category=","");
		
		$.ajax({
			type: "POST",
			url: "/chat/leave",
			data: {"category":category},
			success(result) {
				location.replace("/board/list");
			},
		});
	}
}