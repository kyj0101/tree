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
	
	if(reasonCode == 'RN005'){
		reasonCode = $("#reasonInput").val();
	
	}else{	
		
	}
	
	$.ajax({
		type:"post",
		url:"/member/mypage/withdraw",
		data:{
			"password":password,
			"reasonCode":reasonCode
		},
		success(result) {
			
			if(result == "ok"){
			
				alert("탈퇴되었습니다.");
				location.replace("/")
			
			}else{
				alert("탈퇴를 실패했습니다.");				
			}
		},
	})
	
}