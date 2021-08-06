$(function(){
	$("#newPassword").change(function(){
		
		var newPassword = $(this).val();
		
		$("#newPasswordHelp").addClass("hide");
		
		if(!passwordRegExp(newPassword)){
			$("#newPasswordHelp").text("올바른 비밀번호가 아닙니다.(문자, 특수문자가 포함된 숫자의 조합, 최소 8자)");
			$("#newPasswordHelp").removeClass("hide");
			$("#newPasswordHelp").css("color","red");
		}
	});
	
	$("#checkPassword").change(function(){
		
		var newPassword = $("#newPassword").val();
		var checkPassword = $(this).val();
		
		$("#checkPasswordHelp").addClass("hide");
		
		if(newPassword != checkPassword){
			$("#checkPasswordHelp").removeClass("hide");
		}
	});
	
	$(".passwordUpdateBtn").click(function(){
		
		var passwordObj = new Object();

		passwordObj.newPassword = $("#newPassword").val();
		passwordObj.checkPassword = $("#checkPassword").val();
		
		var isNull = nullCheck(passwordObj);
		var isHelpDisplay = helpDisplayCheck();
		
		if(confirm("비밀번호를 변경하시겠습니까?")){
			
			if(isNull){
				alert("빈 칸이 있습니다.")
				return;
			
			}else if(isHelpDisplay){
				alert("잘못 입력된 칸이 있습니다.");
				return;	
			
			}else{
				$("#passwordUpdateForm").attr("method", "POST");
				$("#passwordUpdateForm").attr("action", "/member/mypage/update/password");
				$("#passwordUpdateForm").submit();
			}
		}
	})
});