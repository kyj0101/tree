var wss = new WebSocket('wss://' + ip + ":" + port + '/rtc');
var localVideo = document.getElementById("myCam");
var cameraoff = false;
var speakerMute = false;

var myStream;
let myPeerConnection;
var myOffer;

var peer = document.getElementById("remoteVideo");

$("#videoCallMyName").text(opener.document.getElementById('myName').innerText);
$("#titleVal").text(opener.document.getElementById('videoCallOtherName').value + "님과 통화중입니다.");
$("#videoCallOtherName").text(opener.document.getElementById('videoCallOtherName').value);

navigator.mediaDevices.getUserMedia({
	audio: true,
	video: true
}).then(startMedia)
	
.catch(function(e) {
	console.log(e);
});

function startMedia(stream) {	

	setStream(stream);
	makeConnection();
	
	$("#videoCallMyName").css("display","block");
}

function setStream(stream){
	localVideo.srcObject = stream;
	myStream = stream;
}

//RTC code
async function makeConnection() {
	
	myPeerConnection = new RTCPeerConnection({
		iceServers: [
	      {
	        urls: [
	          "stun:stun.l.google.com:19302",
	          "stun:stun1.l.google.com:19302",
	          "stun:stun2.l.google.com:19302",
	          "stun:stun3.l.google.com:19302",
	          "stun:stun4.l.google.com:19302",
	        ],
	      },
	    ],
	});
	
	myPeerConnection.addEventListener("addstream", handleAddStream);
	myPeerConnection.addEventListener("connectionstatechange", changeConnectionState);
	myPeerConnection.addEventListener("icecandidate", handleIce);
		
	myStream
		.getTracks()
		.forEach(async track => await myPeerConnection.addTrack(track, myStream));
		
	myOffer = await myPeerConnection.createOffer();
	myPeerConnection.setLocalDescription(myOffer);
	
	console.log("make connection!");
}

wss.onopen = e => {
	
	if(type === 'host'){
		sendToServer({type:"create", videoCallId:videoCallId})			
	}else{
		sendToServer({type:"join", videoCallId:videoCallId})
	}	
}

wss.onmessage = async e => {

	let msg = JSON.parse(e.data);
	let type = msg.type;

	if(type === "offer") {

		console.log("receive offer!");

		var offer = new RTCSessionDescription();
		offer.sdp = msg.data;
		offer.type = "offer";

		myPeerConnection.setRemoteDescription(offer);
			
		var answer = await myPeerConnection.createAnswer();
				
		myPeerConnection.setLocalDescription(answer);						
		console.log(answer);
		console.log("send answer!");
		sendToServer({type:"answer", data:answer.sdp, videoCallId:videoCallId})

	}else if(type === "answer") {
		
		console.log("receive answer!");

		var answer = new RTCSessionDescription();
		answer.sdp = msg.data;
		answer.type = msg.type;

		myPeerConnection.setRemoteDescription(answer);

	}else if(type === "ice") {

		console.log("receive ice!")
		var dataJson = JSON.parse(msg.data);

		var ice = new RTCIceCandidate({
			candidate: dataJson.candidate,
			sdpMid: dataJson.sdpMid,
			sdpMLineIndex: dataJson.sdpMLineIndex
		});

		myPeerConnection.addIceCandidate(ice);

	}else if(type === 'join') {
		setTimeout(() => {sendOffer(myOffer)}, 3000);
	
	}else if(type === 'hangUp'){
		alertAndReplace("상대방이 통화를 거절했습니다.");	
	}
}


function sendOffer(offer){
	
	console.log("send Offer!");
	sendToServer({type:"offer", data:offer.sdp, videoCallId:videoCallId})
}

function handleIce(data){
	
	console.log("send ice!");
	if(data.candidate){
		sendToServer({type:"ice", data:JSON.stringify(data.candidate), videoCallId:videoCallId})
	}
}

function handleAddStream(data){
	
	$(".call-animation").css("display","none");
	$("#remoteVideo").css("display","block");
	$("#videoCallOtherName").css("display","block");
	
	peer.srcObject = data.stream;		
}

function sendToServer(data){

	let dataJson = JSON.stringify(data);
	
	wss.send(dataJson);
}

function cameraBtn(){
	
	var cameraIcon = $("#cameraIcon");
	
	if(cameraoff){
		
		$(cameraIcon).removeClass("fa-video");
		$(cameraIcon).addClass("fa-video-slash");
		
	}else{
		$(cameraIcon).removeClass("fa-video-slash");
		$(cameraIcon).addClass("fa-video");
	}
	
	myStream
		.getVideoTracks()
		.forEach((track) => (track.enabled = !track.enabled));
	
	cameraoff = !cameraoff;
}

function speakerBtn(){

	var speakerIcon = $("#speakerIcon");
	
	if(speakerMute){

		$(speakerIcon).removeClass("fa-volume-mute");
		$(speakerIcon).addClass("fa-volume-up");

	}else{
		$(speakerIcon).removeClass("fa-volume-up");	
		$(speakerIcon).addClass("fa-volume-mute");
	}
	
	myStream
		.getAudioTracks()
		.forEach((track) => (track.enabled = !track.enabled));
		
	speakerMute = !speakerMute;
}

function outBtn(){
	
	myPeerConnection.close();
	close();
}


function changeConnectionState(event) {

	if(myPeerConnection.iceConnectionState == "disconnected"){
		alertAndReplace("상대방이 통화를 종료했습니다.");		
	}
}

function alertAndReplace(msg){
	
	alert(msg);
	
	return new Promise((resolve, reject) => {
		close();
	})
}

