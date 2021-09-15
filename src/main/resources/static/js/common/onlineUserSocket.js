var ws = new WebSocket('wss://' + ip + ":" + port + '/nnn');

ws.onopen = e => {
  	console.log("onopen:",e);
}

ws.onmessage = e => {
	console.log(e.data);
	var data = JSON.parse(e.data);
	
	$(".toast").toast('show');
	$("#videoCallId").val(data.videoCallId);
	$("#callInfo").text(data.name + "님으로부터 전화가 왔습니다.");
}

function pickUpCall(){
	
	var videoCallId = $("#videoCallId").val();
	
	location.replace(`/videocall/view?videoCallId=${videoCallId}&type=guest`);
}