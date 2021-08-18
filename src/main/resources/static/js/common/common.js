var ws = new WebSocket('ws://localhost:9090/nnn');
ws.onopen = e => {
  	console.log("onopen:",e);
}

function nullCheck(obj){
	console.log(obj);
	for(var key in obj){
		
		var prop = obj[key];

		if(prop == null || prop.length < 1){
			
			return true;
		}
	}
}

function helpDisplayCheck(){
	
	var helps = $(".help");
	
	for(var key in helps){
		
		if(isNaN(key)){
			break;
		}
		if(!$(helps[key]).attr("class").includes("hide")){
			console.log($(helps[key]).attr("class"));
			return true;
		}
	}
	
	return false;
}


function emailDuplicationCheck(email){

	$.ajax({
		type:"get",
		url:"http://localhost:9090/login/success",
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

