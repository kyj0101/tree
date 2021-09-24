function emailCheck(){
	
	var email = $("#email").val();
	var domain  = $(".domain :selected").val();
	email = (email + domain).trim();
	

	if(!emailRegExp(email)){
		
		$("#email-help").text("올바른 이메일이 아닙니다.");
		$("#email-help").removeClass("hide");
	
	}else{
		$("#email-help").addClass("hide");
	}

	emailDuplicationCheck(email);
}

function nameCheck(){
	
	var name = $("#name").val();

	if(!nameRegExp(name)){
		$("#name-help").removeClass("hide");
	
	}else{
		$("#name-help").addClass("hide");		
	}
}

function phoneCheck(){
	
	var phone = $("#phone").val();

	if(!phoneRegExp(phone)){
		$("#phone-help").removeClass("hide");
	
	}else{
		$("#phone-help").addClass("hide");	
	}
}

function passwordCheck(){
	
	var password = $("#password").val();
	
	if(!passwordRegExp(password)){
		$("#password-help").text("올바른 비밀번호가 아닙니다. (문자, 특수문자가 포함된 숫자의 조합, 최소 8자)")
		$("#password-help").css("color","red");
	
	}else{
		$("#password-help").text("");
	}
}

function passwordCheckCheck(){
	
	var password = $("#password").val();
	var passwordCheck = $("#password-check").val();

	if(password != passwordCheck){
		$("#password-check-help").removeClass("hide");
	
	}else{
		$("#password-check-help").addClass("hide");	
	}
}

$(function(){
	
	$("#email").on("change", emailCheck);
	$(".domain").on("change", emailCheck);
	
	$("#name").on("change", nameCheck);
	
	$("#phone").on("change", phoneCheck);
	
	$("#password").on("change", passwordCheck);
	$("#password-check").on("change", passwordCheckCheck);

	$(".btn-primary").click(function(){
		
		var member = new Object();
		member.email = $("#email").val();
		member.name = $("#name").val();
		member.phoen = $("#phone").val();
		member.birth = $("#birth").val();
		member.password = $("#password").val();
		member.department = $("#department option:selected").val();
		member.position = $("#position option:selected").val();
		member.zipCode = $("#zipCode").val();
		member.address = $("#address").val();
		member.detailAddress = $("#detailAddress").val();
		
		var isNull = nullCheck(member);
		var isHelpDisplay = helpDisplayCheck();
		
		if(isNull){
			alert("입력되지 않은 칸이 있습니다.");
			return;
		
		}else if(isHelpDisplay){
			alert("올바른 값을 입력해주세요.");
			return;
		
		}else{
			alert("인증 이메일이 발송되었습니다. 인증 완료 후 로그인해주세요.");
			$("#signup-form").submit();		
		}
		
	});
});


