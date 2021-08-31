$(function(){

    $("#chat-send").click(function(){
        var name = " <li class='user'><p><i class='fas fa-user-circle'></i> 김개발자</p>"
        var chatText = "<span>" + $("#chat-textarea").val() + "</span></li>";
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
    });
  });
