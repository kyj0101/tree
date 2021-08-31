$(function(){
	
	//게시판 추가 버튼 누를때 이벤트
	$(".add-board").click(function() {

		$('#addModal').trigger('focus');
		$('#add-modal-label').text("게시판 추가");
		$(".add-chat-btn").css("display", "none")
		$(".add-board-btn").css("display", "inline-block")
		$(".form-check").css("display", "block")
		
		var boardNo = $("#boardNo").val();	
		
		//멤버 목록 가져옴
		$.ajax({
			type: "POST",
			url: "/category/board/memberlist",
			data: {"boardNo": boardNo},
			dataType:"json",
			success(result) {
				
				$(".member-list-ul").empty();
				
				$.each(result, function(index, elem){

					var html = '<li class="member-list-li">';
					
					html += '<p>이름 : ' +  elem.name + '</p>';
					html += '<p>부서 : ' +  elem.departmentName + '</p>';
					html += '<p>직급 : ' +  elem.positionName + '</p>';
					html += '<input class="categoryAddMemberCheck" type="checkbox" value="' + elem.email + '">';
					html += '</li>';
					html += '<hr />';
					
					$(".member-list-ul").append(html);
				});
				
			},
			error(xhr, status, err) {
				console.log(xhr, status, err);
			}
		});
	});
	
	//게시판 생성 이벤트
	$(".add-board-btn").click(function(){
		
		var checkedArr = $(".categoryAddMemberCheck:checked");
		var emailList = [];
		var title = $(".title-input").val();
		console.log(title);
		console.log($(".title-input"));
		
		//체크박스에 있는 email value를 array에 넣음
		$.each(checkedArr, function(index, elem){
			emailList.push($(elem).val());
		});
		
		if(title.length <= 0){
			alert("제목을 입력하세요.");

			
		}else if(emailList.length <= 0){
			alert("회원을 선택하세요.");
	
		}else{
			$.ajax({
				type: "POST",
				url: "/category/board/insert",
				data: {
					"title":title,
					"emailList": emailList
				},
				success(result) {
					console.log(result);
					if(result == "ok"){
						alert("게시판이 생성되었습니다.");
						$('#addModal').modal('hide');
						location.replace("/board/list");
					}else{
						alert("게시판 생성을 실패했습니다.");
					}
				},
				
				error(xhr, status, err) {
					console.log(xhr, status, err);
				}
			});			
		}
	});
	
	//카테고리 삭제
	$(".categoryDelete").click(function(){
		var url = $($(this).prev()).attr("href");
		var indexCategory = url.indexOf("?category=");
		var categoryNo = url.substring(indexCategory).replace("?category=","");
		
		if(confirm("게시판을 삭제하시겠습니까?")){
			$.ajax({
				type: "POST",
				url: "/project/insert",
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
				},

				error(xhr, status, err) {
					console.log(xhr, status, err);
				}
			});	
		}
	});
});