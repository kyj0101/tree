# 🌳 Tree

협업툴 JANDI를 벤치마킹한 프로젝트 중심의 협업툴



# 📃 Info

월드버텍 수습기간(2021.8.14 ~ 2021.10.5)에 진행한 개인 프로젝트 입니다.

## ⚙ 기술 스택

+ Back End
  + Java
  + Tibreo
+ Front End
  + Thymeleaf
  + JavaScript
  + Jquery
  + Bootstrap 4.6v
+ Framework
  + Spring Framework ( Spring boot )
  + Spring Security
  + MyBatis
+ Library
  + FullCalendar
  + TOAST editor
+ API
  + DAUM 주소
  + 네아로( 네이버 아이디로 로그인 OAuth2.0 )
+ ETC
  + WebRTC ( 1:1 화상통화 )
  + STOMP ( 채팅 )
  + Google SMTP ( 이메일 인증 )
  + Open SSL ( 브라우저 보안 )
    

## 💻 기능 목록

+ 1:1 화상통화
+ 채팅
+ 게시판
+ 회원 관리
+ 근태 관리
+ 공통 코드 관리
+ 프로젝트 관리
+ 일정 관리

## ✔ application.properties 
### Server

server.servlet.context-path=/

server.port=9090

server.ssl.key-store=kanu-keystore.p12

server.ssl.key-store-type=PKCS12

server.ssl.key-store-password=( *password )

server.ssl.key-alias=kanu-keystore

### DataSource

spring.datasource.url=jdbc:log4jdbc:tibero:thin:@61.252.18.225:8629:tibero

spring.datasource.username=( *username )

spring.datasource.password=( *password )

### Mybatis

mybatis.mapper-locations=classpath*:mapper/**/*.xml

mybatis.configuration.jdbc-type-for-null=NULL

mybatis.configuration.map-underscore-to-camel-case=true

mybatis.type-aliases-package=com.vtex.tree

### Gmail SMTP

spring.mail.host=smtp.gmail.com

spring.mail.port=587

spring.mail.username=( *username )

spring.mail.password=( *password )

spring.mail.properties.mail.smtp.auth=true

spring.mail.properties.mail.smtp.timeout=5000

spring.mail.properties.mail.smtp.starttls.enable=true

### OAuth2 Naver

spring.security.oauth2.client.registration.naver.client-id=( *client-id )

spring.security.oauth2.client.registration.naver.client-secret=( *client-secret )

spring.security.oauth2.client.registration.naver.redirect-uri={baseUrl}/{action}/oauth2/code/{registrationId}

spring.security.oauth2.client.registration.naver.authorization-grant-type=authorization_code

spring.security.oauth2.client.registration.naver.scope=name,email

spring.security.oauth2.client.registration.naver.client-name=Naver

spring.security.oauth2.client.provider.naver.authorization-uri=https://nid.naver.com/oauth2.0/authorize

spring.security.oauth2.client.provider.naver.token-uri=https://nid.naver.com/oauth2.0/token

spring.security.oauth2.client.provider.naver.user-info-uri=https://openapi.naver.com/v1/nid/me

spring.security.oauth2.client.provider.naver.user-name-attribute=response

### ETC.

working.time=09:00

empty.msg=No Data.

ip= ( *ip )

## 👇 아래 노션 링크를 클릭하시면 더 자세한 정보를 보실 수 있습니다.

[자세히 보기](https://lucky-continent-f31.notion.site/Tree-1eb851df2a814b5a9ae2faadb8eea231)
