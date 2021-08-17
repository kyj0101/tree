package com.vtex.tree.common.listener;

import java.util.Hashtable;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import com.vtex.tree.home.mapper.HomeMapper;
import com.vtex.tree.home.service.HomeService;
import com.vtex.tree.home.service.impl.HomeServiceImpl;

import lombok.Setter;
 
@Component
public class SessionListener implements HttpSessionBindingListener{

	private static HomeService homeService;
	
    private static SessionListener sessionListener = null;
    
    private static Hashtable loginUsers = new Hashtable();
    
    public static SessionListener getInstance(HomeService homeeService) {
    	homeService  = homeeService;
        if(sessionListener == null) {
            sessionListener = new SessionListener();
        }
        return sessionListener;
    }
    

    @Override
    public void valueBound(HttpSessionBindingEvent event) {
    	String name = event.getName();
        loginUsers.put(event.getSession(), name);
        System.out.println(name + " 로그인 완료");
        System.out.println("현재 접속자 수 : " +  getUserCount());

        homeService.setLogin(name);
    }

    // 세션이 끊겼을시 호출
    @Override
    public void valueUnbound(HttpSessionBindingEvent event) {
        // TODO Auto-generated method stub
        loginUsers.remove(event.getSession());
        System.out.println(event.getName() + " 로그아웃 완료");
        System.out.println("현재 접속자 수 : " +  getUserCount());
    }

	public int getUserCount(){
	    return loginUsers.size();
	}
	
	   /*
	    * 로그인을 완료한 사용자의 아이디를 세션에 저장하는 메소드
	    */
	   public void setSession(HttpSession session, String userId){
	       //이순간에 Session Binding이벤트가 일어나는 시점
	       //name값으로 userId, value값으로 자기자신(HttpSessionBindingListener를 구현하는 Object)
	       session.setAttribute(userId, this);//login에 자기자신을 집어넣는다.
	   }


	
 
}
