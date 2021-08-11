$(function(){
	
});

//라디오 버튼 체크될 떄 이벤트
function radioCheck(e){
	var reasonCode = $(e).val();
	
	if(reasonCode == 'RN005'){
		$("#reasonInput").attr("disabled", false);
	
	}else{
		$("#reasonInput").attr("disabled", true);
	}
}

//탈퇴 버튼 이벤트
function withdrawBtn(){
	
	var password = $("#password").val();
	var reasonCode = $(".form-check-input:checked").val();
	
	//직접 입력일때
	if(reasonCode == 'RN005'){
		
		reasonCode = $("#reasonInput").val();
		
		//직접 입력하지 않았다면
		if(reasonCode == ""){
			alert("사유를 입력하세요.");
			return;
		
		}else{
			
		}
		
	}else if(reasonCode == "" || password == ""){	
		alert("사유 또는 비밀번호를 입력하세요.");
		return;
	}
	
	$.ajax({
		type: "post",
		url: "/member/mypage/withdraw",
		data: {
			"password": password,
			"reasonCode": reasonCode
		},
		success(result) {

			if (result == "ok") {

				alert("탈퇴되었습니다.");
				location.replace("/")

			} else {
				alert("비밀번호가 일치하지 않습니다.");
			}
		},
	});
}