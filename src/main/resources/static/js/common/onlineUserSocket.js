var ws = new WebSocket('wss://' + ip + ":" + port + '/nnn');
var callSocket = new WebSocket('wss://' + ip + ":" + port + '/rtc');


ws.onopen = e => {
	$("#toastDiv").css("display","none");
  	console.log("onopen:",e);
}

ws.onmessage = e => {
	console.log(e.data);
	var data = JSON.parse(e.data);
	
	$("#toastDiv").css("display","block");
	$(".toast").toast('show');
	$("#videoCallId").val(data.videoCallId);
	$("#callInfo").text(data.name + "님으로부터 전화가 왔습니다.");
}

function pickUpCall(){
	
	var videoCallId = $("#videoCallId").val();
	
	location.replace(`/videocall/view?videoCallId=${videoCallId}&type=guest`);
}

function hangUpCall(){
	
	var videoCallId = $("#videoCallId").val();
	
	var data = {
		type : "hangup",
		videoCallId : videoCallId
	};
	
	callSocket.send(JSON.stringify(data));
	$(".toast").toast('hide');
}

