
function passwordRegExp(password){

    if(/^(?=.*[a-zA-z])(?=.*[0-9])(?=.*[$`~!@$!%*#^?&\\(\\)\-_=+])(?!.*[^a-zA-z0-9$`~!@$!%*#^?&\\(\\)\-_=+]).{8,16}$/.test(password)){
        return true;

    }else{
        return false;
    }
}

function emailRegExp(email){
    if(/^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i.test(email) || email == ""){
		return true;
		
	}else{
		return false;
	}
}

function phoneRegExp(phone){
	
	if( /^[0-9]{11}$/.test(phone)){
		return true;
		
	}else{		
		return false;
	}	
}

function nameRegExp(name){
	
	if(/^[가-힣a-z-A-Z]{2,15}$/.test(name)){
		return true;

	}else{	
		return false;
	}
}