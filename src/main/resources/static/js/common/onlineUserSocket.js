var ws = new WebSocket('wss://' + ip + ":" + port + '/nnn');
var callSocket = new WebSocket('wss://' + ip + ":" + port + '/rtc');
var videoCallOtherName;

ws.onopen = e => {
	$("#toastDiv").css("display","none");
  	console.log("onopen:",e);
}

ws.onmessage = e => {

	var data = JSON.parse(e.data);
	
	if(data.type == "call"){
		
		$("#toastDiv").css("display","block");
		$(".toast").toast('show');
		$("#videoCallId").val(data.videoCallId);
		$("#callInfo").text(data.name + "님으로부터 전화가 왔습니다.");
		
		videoCallOtherName = data.name;
	}

	if(data.type == "chat" && $(".chat-div").length == 0){
		
		$("#messageDiv").css("display","block");
		
		var html = "<li style='z-index:1000;'>";
		html += "<div class='toast-header'>";
		html += '<strong class="mr-auto">';
		html += data.chatRoomName;
		html += '</strong>';
		html += '<button type="button" class="ml-2 mb-1 close" data-dismiss="toast" aria-label="Close" onclick="closeMessage(this);">';
		html += '<span aria-hidden="true">&times;</span>';
		html += '</button>';
		html += '</div>';
		html += '<div class="toast-body">';
		html += '<p id="callInfo">';
		html += data.name + " (" + data.email + ")";
		html += '</p>';
		html += '<p id="callInfo">';
		html += data.content;
		html += '</p>';
		html += '<p id="timeInfo">';
		html += data.time;
		html += '</p>';
		html += '</div>';
		html += '</li>';
		
		$("#messageUl").append(html);			
	}	
}


function closeMessage(e){
	$($(e).parent().parent()).addClass("slide-out-right");
	
	setTimeout(() => {
		$($(e).parent().parent()).remove();
		if($("#messageUl").children().length == 0){
			$("#messageDiv").css("display","none");
		}		
	}, 600);
}

function pickUpCall(){
	
	var videoCallId = $("#videoCallId").val();

	openWindowPop(`/videocall/view?videoCallId=${videoCallId}&type=guest`, videoCallOtherName);
	$(".toast").toast('hide');
	$("#toastDiv").css("display","none");
}

function hangUpCall(){
	
	var videoCallId = $("#videoCallId").val();
	
	var data = {
		type : "hangup",
		videoCallId : videoCallId
	};
	
	callSocket.send(JSON.stringify(data));
	$(".toast").toast('hide');
	$("#toastDiv").css("display","none");
}

