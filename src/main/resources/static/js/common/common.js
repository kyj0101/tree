function nullCheck(obj){
	for(var key in obj){
		var prop = obj[key];
		
		if(prop == null || prop.length < 1){
			return true;
		}
	}
}

function emailDuplicationCheck(email){

	$.ajax({
		type:"get",
		url:"http://localhost:9090/email/duplication/check",
		data:{"email":email},
		
		success(val){
			if(val == "true"){
				$("#email-help").text("중복된 이메일 입니다.");
				$("#email-help").removeClass("hide");
			}
		},
		error(xhr, status, err){
			console.log(xhr, status, err);
		},
	});
}