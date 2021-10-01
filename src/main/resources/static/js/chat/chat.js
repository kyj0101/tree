var socket = new SockJS('/stomp');
var userIsManager = $("#frstRegisterId").val() === $("#loginEsntlId").val();

socket.onmessage = function(e) {
  	console.log("onmessage:",e);
}

var stompClient = Stomp.over(socket);

function chatSend(name, email, msg){

	var nameText = " <li class='user'><p><i class='fas fa-user-circle'></i>" + makeChatName(name, email) + "</p>"
	var chatText = "<span>" + msg + "</span></li>";
	var time = "<p class='chat-time-p user-p'>" + getNow() + "</p>"

	$(".chat-ul > li:last").append(nameText + time + chatText);
	$("#chat-textarea").val("")
}

function chatReceive(name, email, time, msg){
	
	var nameText = " <li class='other'><p><i class='fas fa-user-circle'></i>" + makeChatName(name, email) + "</p>"
	var chatText = "<span>" + msg + "</span></li>";
	var timeText = "<p class='chat-time-p user-p' style='text-align: right;'>" + time + "</p>"

	$(".chat-ul > li:last").append(nameText + timeText + chatText);
	$("#chat-textarea").val("")
}


function chatNotice(time, msg){

	var chatText = "<li class='notice'><span>" + msg + " (" + time + " )" + "</span></li>";
	
	$(".chat-ul > li:last").append(chatText);
}

function sendMessage() {

	var chat = {
		chatRoomNumber : category,
		chatRoomName : $("#categoryName").text(),
		name : myName,
		email : myEmail,
		time : getNow(),
		content : $("#message").val(),
		chatType: "chat"
	}
	
	stompClient.send(`/chat/send/active/${category}`, {}, JSON.stringify(chat));
	chatSend(myName,myEmail,$("#message").val());
	scrollTop();
	$('#message').val('');
}

function scrollTop(){
	
	setTimeout(() => {
	 	$('.chat-div').scrollTop($(".chat-div").prop('scrollHeight'));	
	}, 200)
}

function showChatMember(){

	$(".chatMember").addClass("active");
	$(".chatAddMember").removeClass("active");
	$(".chatInfo").removeClass("active");
	
	$(".chatMemberDiv").css("display","block");
	$(".chatInfoDiv").css("display","none");
	$(".chatAddMemberDiv").css("display","none");

	$.ajax({
		type: "POST",
		url: "/chat/member/list",
		data: {
			"categoryNo": category,
		},
		success(result) {
			
			$(".chatMemberUl").empty();
			
			$.each(result, function(index, elem) {
				
				if(elem.esntlId === $("#loginEsntlId").val()){
					return true;
				}
				
				var html = '<li class="list-group-item" data-esntlid="';
				html += elem.esntlId;
				html += '">';
				html += elem.name;
				html += '(';
				html += elem.email;
				html += ')';
				html += '<span class="badge badge-pill badge-primary">';
				html += elem.departmentName;
				html += '</span>';
				html += '<span class="badge badge-pill badge-info">';
				html += elem.positionName;
				html += '</span>';

				if(userIsManager){
					
					html += '<button type="button" class="btn btn-light deleteMember" onclick="outChatManager(this);">';
					html += '<i class="fas fa-times"></i>';					
				}
				
				html += '</li>';
				
				$(".chatMemberUl").append(html);				
			});
		},
	});
}

function showChatInfo(){
	
	$(".chatInfo").addClass("active");
	$(".chatMember").removeClass("active");
	$(".chatAddMember").removeClass("active");
	
	$(".chatInfoDiv").css("display","block");
	$(".chatMemberDiv").css("display","none");
	$(".chatAddMemberDiv").css("display","none");
}

function makeChatName(name, email){
	return name + "(" + email + ")";
}

function getNow(){
	
	var now = new Date();
	
	return  now.getFullYear()
				+ "-"
				+ ((now.getMonth() + 1) < 10 ? ( "0" + (now.getMonth() + 1)) : (now.getMonth() + 1))
				+ "-"
				+ now.getDate()
				+ " "
				+ now.getHours()
				+ ":"
				+ now.getMinutes()
				+ ":"
				+ now.getSeconds();
}


stompClient.connect({}, function(frame) {

	if(!connect){
		
		var chat = {
			chatRoomNumber : category,
			name : myName,
			email : myEmail,
			time : getNow(),
			content : makeChatName(myName, myEmail) + "님이 입장했습니다.",
			chatType: "notice"
		}

		//전역변수 stompClient를 통해 메세지 전송 
		stompClient.send(`/chat/send/active/${category}`, {}, JSON.stringify(chat));	
		scrollTop();
		chatNotice(chat.time, makeChatName(myName, myEmail) + "님이 입장했습니다.");
		
		connect = true;
	}
	

	stompClient.subscribe("/chat/receive/topic/" + category, (frame) => {

		var msgObj = JSON.parse(frame.body);
		var {chatRoomNumber, name, email, time, content, chatType} = msgObj;

		if(chatType == 'chat' && myEmail != email){
			console.log("chat");
			chatReceive(name, email, time, content);	
			scrollTop();
		}
		
		if(chatType == 'notice'){
			
			chatNotice(time, content);
			scrollTop();
		}
		
	});
	
});


$(function(){
	scrollTop();
	$("#message").keydown(function(key) {
		if (key.keyCode == 13) {// 엔터
			sendMessage();
		}
	});
	
	$("#chat-send").on("click",sendMessage);

});
