# ๐ณ Tree

ํ์ํด JANDI๋ฅผ ๋ฒค์น๋งํนํ ํ๋ก์ ํธ ์ค์ฌ์ ํ์ํด

## โ ๊ธฐ์  ์คํ

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
  + DAUM ์ฃผ์
  + ๋ค์๋ก( ๋ค์ด๋ฒ ์์ด๋๋ก ๋ก๊ทธ์ธ OAuth2.0 )
+ ETC
  + WebRTC ( 1:1 ํ์ํตํ )
  + STOMP ( ์ฑํ )
  + Google SMTP ( ์ด๋ฉ์ผ ์ธ์ฆ )
  + Open SSL ( ๋ธ๋ผ์ฐ์  ๋ณด์ )
    

## ๐ป ๊ธฐ๋ฅ ๋ชฉ๋ก

+ 1:1 ํ์ํตํ
+ ์ฑํ
+ ๊ฒ์ํ
+ ํ์ ๊ด๋ฆฌ
+ ๊ทผํ ๊ด๋ฆฌ
+ ๊ณตํต ์ฝ๋ ๊ด๋ฆฌ
+ ํ๋ก์ ํธ ๊ด๋ฆฌ
+ ์ผ์  ๊ด๋ฆฌ

## โ application.properties 
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

## ๐ ์๋ ๋ธ์ ๋งํฌ๋ฅผ ํด๋ฆญํ์๋ฉด ๋ ์์ธํ ์ ๋ณด๋ฅผ ๋ณด์ค ์ ์์ต๋๋ค.

[์์ธํ ๋ณด๊ธฐ](https://lucky-continent-f31.notion.site/Tree-1eb851df2a814b5a9ae2faadb8eea231)
