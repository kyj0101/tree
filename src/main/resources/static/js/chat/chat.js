$(function(){


});

function chatSend(name,msg){
	
	var name = " <li class='user'><p><i class='fas fa-user-circle'></i>" + name + "</p>"
	var chatText = "<span>" + msg + "</span></li>";
	var now = new Date();
	var time = "<p class='chat-time-p user-p'>" + now.getFullYear()
                                             + "-"
                                             + "0" + (now.getMonth() + 1) 
                                             + "-"
                                             + now.getDate()                                            
                                             + " "
                                             + now.getHours()
                                             + ":"
                                             + now.getMinutes()
                                             + ":"
                                             + now.getSeconds()
                                             + "</p>"
	$(".chat-ul > li:last").append(name + time + chatText);
	$("#chat-textarea").val("")
}

function chatReceive(name,msg){
	var name = " <li class='other'><p><i class='fas fa-user-circle'></i>" + name + "</p>"
	var chatText = "<span>" + msg + "</span></li>";
	var now = new Date();
	var time = "<p class='chat-time-p user-p' style='text-align: right;'>" + now.getFullYear()
                                             + "-"
                                             + "0" + (now.getMonth() + 1) 
                                             + "-"
                                             + now.getDate()                                            
                                             + " "
                                             + now.getHours()
                                             + ":"
                                             + now.getMinutes()
                                             + ":"
                                             + now.getSeconds()
                                             + "</p>"
	$(".chat-ul > li:last").append(name + time + chatText);
	$("#chat-textarea").val("")
}

function chatLeave(){
	if(confirm("채팅방을 나가시겠습니까?")){
		
		$.ajax({
			type: "POST",
			url: "/chat/leave",
			data: {"category":category},
			success(result) {
				location.replace("/board/list");
			},
		});
	}
}