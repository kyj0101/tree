



function getLoginMemberList(){
		//멤버 목록 가져옴
		$.ajax({
			type: "POST",
			url: "/category/chat/memberlist",
			dataType:"json",
			success(result) {
				$.each(result, function(index, elem){

					var html = '<li class="member-list-li">';
					
					html += '<p>이름 : ' +  elem.name + '</p>';
					html += '<p>부서 : ' +  elem.departmentName + '</p>';
					html += '<p>직급 : ' +  elem.positionName + '</p>';
					html += '<input class="categoryAddMemberCheck" type="checkbox" value="' + elem.email + '">';
					html += '</li>';
					html += '<hr />';
					
					$(".member-list-ul").append(html);
				});
			},
			
			error(xhr, status, err) {
				console.log(xhr, status, err);
			}
		});
}