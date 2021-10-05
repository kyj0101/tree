//192.168.1.206
//192.168.0.80
var ip = "192.168.1.206";
var port = "9090";
var noMsg = 'No Data.';

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
			return true;
		}
	}
	
	return false;
}


function emailDuplicationCheck(email){

	$.ajax({
		type:"get",
		url:"/email/duplication/check",
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

function findZipCode(){
	new daum.Postcode({
        oncomplete: function(data) {
			
			var address = "";
			
			//K : 한글 / E : 영문
			if(data.userLanguageType === 'K'){
				
				//R : 도로명 / J : 지번
				if(data.userSelectedType === 'R'){
					address = data.address;
				
				}else{
					address = data.jibunAddress;
				}
			}else{
				
				//R : 도로명 / J : 지번
				if(data.userSelectedType === 'R'){
					address = data.addressEnglish;
				
				}else{
					address = data.jibunAddressEnglish;
				}
			}
			
			$("#zipCode").val(data.zonecode);
        	$("#address").val(address);
        }
    }).open();
}

function openWindowPop(url, name){

	$("#videoCallOtherName").val(name);

    var options = 'top=10, left=10, width=1350, height=560,resizable=no';
	window.open(url, name, options);
}



