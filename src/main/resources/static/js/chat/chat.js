//웹소켓 선언 및 연결
//1.최초 웹소켓 생성 url: /stomp
var socket = new SockJS('/stomp');

socket.onmessage = function(e) {
  	console.log("onmessage:",e);
}

var stompClient = Stomp.over(socket);

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


function chatNotice(name,msg){
	var now = new Date();
	var time = + now.getFullYear()
				+ "-" 
				+ "0" 
				+ (now.getMonth() + 1)  
				+ "-" 
				+ now.getDate()                                            
				+ " "
				+ now.getHours()
				+ ":"
				+ now.getMinutes()
				+ ":"
				+ now.getSeconds();
	
	var chatText = "<li class='notice'><span>" + msg + " (" + time + " )" + "</span></li>";
	$(".chat-ul > li:last").append(chatText);
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

function sendMessage() {

	let data = {
		chatId :  chatName,
		memberId : chatName,
		msg : $("#message").val(),
		time : Date.now(),
		type: "string",
		chatType: "chat"
	}
	
	//전역변수 stompClient를 통해 메세지 전송 
	stompClient.send(`/chat/active/${category}`, {}, JSON.stringify(data));
	chatSend(chatName,$("#message").val());
	scrollTop();
	//message창 초기화
	$('#message').val('');
}

function scrollTop(){
	//스크롤처리
 	$('.chat-div').scrollTop($(".chat-div").prop('scrollHeight'));
}

stompClient.connect({}, function(frame) {
	console.log('connected stomp over sockjs');
	
	if(!connect){
		let data = {
				chatId :  chatName,
				memberId : chatName,
				msg : chatName + "님이 입장했습니다.",
				time : Date.now(),
				type: "string",
				chatType: "notice"
			}
			
		//전역변수 stompClient를 통해 메세지 전송 
		stompClient.send(`/chat/active/${category}`, {}, JSON.stringify(data));	
		scrollTop();
		chatNotice(chatName,chatName + "님이 입장했습니다.");
		
		connect = true;
	}
	

	stompClient.subscribe("/chat/active/" + category, (frame) => {
		
		var msgObj = JSON.parse(frame.body);
		var {chatId, to, msg, type, time, chatType} = msgObj;
		
		if(chatType == 'chat' && chatId !=  chatName){
			chatReceive(chatId,msg);	
			scrollTop();
		}
		
		if(chatType == 'notice'){
			chatNotice(chatId,msg);
			scrollTop();
		}
		
	});
	
});


$(function(){

	$("#message").keydown(function(key) {
		if (key.keyCode == 13) {// 엔터
			sendMessage();
		}
	});
	
	$("#chat-send").on("click",sendMessage);

});