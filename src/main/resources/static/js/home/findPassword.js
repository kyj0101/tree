function checkEmail(){
	
	var email = $("#email").val();
	var domain  = $(".domain :selected").val();
	email = (email + domain).trim();
	
	if(!emailRegExp(email)){
		
		$("#email-help").text("올바른 이메일이 아닙니다.");
		$("#email-help").removeClass("hide");
	
	}else{
		$("#email-help").addClass("hide");
	}
}

function findPassword(){
	
	var isHelpDisplay = helpDisplayCheck();
	var email = $("#email").val();
	var domain  = $(".domain :selected").val();
	email = (email + domain).trim();
	
	if(isHelpDisplay){
	
		alert("올바른 이메일을 입력하세요.");
		return;
	}else{
		
		$.ajax({
			type: "get",
			url: "/find/password",
			data: { "email": email },
	
			success(result) {
				if(result == "ok"){
					alert("임시 비밀번호가 발송되었습니다.");
				}else{
					alert("임시 비밀번호가 발송안됐습니다")
				}
				
				location.replace("/login");
			},
	
			error(xhr, status, err) {
				console.log(xhr, status, err);
			}
		})
	}
}