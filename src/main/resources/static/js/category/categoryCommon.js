function showAddCategoryModal(projectId){

	$.ajax({
		type: "POST",
		url: "/category/memberlist",
		data:{"projectId":projectId},
		dataType: "json",
		success(result) {

			$(".member-list-ul").empty();

			$.each(result, function(index, elem) {

				var html = '<li class="member-list-li">';

				html += '<p>이름 : ' + elem.name + '</p>';
				html += '<p>부서 : ' + elem.departmentName + '</p>';
				html += '<p>직급 : ' + elem.positionName + '</p>';
				
				if(elem.loginAt == 'Y'){
					html += '<p><i class="fas fa-user-alt" style="color:blue;"></i></p>';
				
				}else{
					html += '<p><i class="fas fa-user-slash" ></i></p>';
				}
				
				html += '<input class="categoryAddMemberCheck" type="checkbox" value="' + elem.esntlId + '">';
				html += '</li>';
				html += '<hr />';

				$(".member-list-ul").append(html);
			});

			return new Promise(function(resolve, reject) {
				$("#addModal").modal("show");
			});
		},
	});
}

function getEsntlId(){
	
	var checkedArr = $(".categoryAddMemberCheck:checked");
	var esntlIdList = [];
	
	//체크박스에 있는 email value를 array에 넣음
	$.each(checkedArr, function(index, elem){
		esntlIdList.push($(elem).val());
	});
	
	return esntlIdList;
}