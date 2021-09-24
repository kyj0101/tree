function showVideoCallModal(e){
	
	var projectId = $($(e).parent().parent().parent()).attr("id").replace("id_","");
	
	$("#projectId").val(projectId);

	$.ajax({
		type: "POST",
		url: "/space/onlinememberlist",
		data:{"projectId":projectId},
		dataType: "json",
		 success(result) {
	
			$("#videoCallMemberList").empty();
				

			 if (result.length == 0) {

				 var html = '<li class="list-group-item videoCallLi" style="display: flex">';
				 html += "<p>현재 접속한 사용자가 없습니다.</p>";
				 html += '</li>';

				 $("#videoCallMemberList").append(html);
			 }

			 $.each(result, function(index, elem) {

				var html = '<li class="list-group-item videoCallLi" style="display: flex">';
				html += '<div>';
				html += elem.name;
				html += "(" + elem.email + ")";
				html += '<span class="badge rounded-pill bg-primary">';
				html += elem.departmentName;
				html += '</span>';
				html += '<span class="badge badge-pill badge-info">';
				html += elem.positionName;
				html += '</span>';
				html += '</div>'
				html += '<div>';
				html += '<input class="name" type="hidden" value="' + elem.name + '">';
				html += '<input class="esntlId" type="hidden" value="' + elem.esntlId + '">';
				html += '<a class="btn btn-primary" href="#" role="button" onclick="call(this);">';
				html += '<i class="fas fa-phone-alt"></i>';
				html += '</a>';
				html += '</div>';
				html += '</li>';

				$("#videoCallMemberList").append(html);
			});
			
			return new Promise(function(resolve, reject) {				
				$("#addVideoCallModal").modal("show");
			});
		},
	});
}

function call(e){
	
	var videoCallId = makeVideoCallId();
	var otherName = $($(e).prev().prev()).val();

	var data = {
		esntlId : $($(e).prev()).val(),
		name : $("#myName").text(),
		videoCallId : videoCallId
	}

	ws.send(JSON.stringify(data));

	openWindowPop(`/videocall/view?videoCallId=${videoCallId}&type=host`, otherName);
	$('#addVideoCallModal').modal('hide');
}

function makeVideoCallId(){
	var i = 0;
	var size = 10;
	var callId = ""
	var char = "abcdefghijklmnopqrstuvwxyz!@*1234567890";
	
	while(i++ < size){
	  callId += char.charAt(Math.random() * char.length)		
	}
	
	return callId;
}