# 📜 To Do Project 📜

### SeSAC Semi Project

기간: 24.09.10 ~

### 💁‍♀️ 기능 소개

1. 회원가입, JWT 인증 방식의 로그인(Spring Security 사용)
3. 할 일 추가
4. 할 일 조회
5. 할 일 수정
6. 할 일 삭제

<br>

## 🛠️ 기술 스택

<img src="https://img.shields.io/badge/Java-007396?style=flat-square&logo=OpenJDK&logoColor=white">&nbsp;
<img src="https://img.shields.io/badge/Spring-6DB33F?style=flat-square&logo=spring&logoColor=white">&nbsp;
<img src="https://img.shields.io/badge/Spring Boot-6DB33F?style=flat-square&logo=springboot&logoColor=white">&nbsp;
<img src="https://img.shields.io/badge/Spring Security-6DB33F?style=flat-square&logo=springsecurity&logoColor=white">&nbsp;

<img src="https://img.shields.io/badge/MySQL-4479A1?style=flat-square&logo=mysql&logoColor=white">&nbsp;
<img src="https://img.shields.io/badge/Gradle-02303A?style=flat-square&logo=gradle&logoColor=white">&nbsp;
<img src="https://img.shields.io/badge/Notion-000000?style=flat-square&logo=notion&logoColor=white">&nbsp;

<br>

## 🎯 트러블 슈팅

###### 해당 항목을 클릭하면 트러블 슈팅 과정을 정리한 블로그로 이동합니다.

* [CORS란? Spring Security에 따른 CORS 설정](https://argente29.tistory.com/151)
* [WebMvcTest 403 Forbidden 해결하기(feat. CSRF)](https://argente29.tistory.com/152)

<br>

## 🖥️ 설치 및 실행

* 프로젝트 클론

```bash
git clone https://github.com/JisooPyo/SeSAC-Semi-Project-Back.git

cd SeSAC-Semi-Project-Back
```

* 프로젝트 설정 파일 추가
  * `src/main/resources` 폴더 생성
  * `src/main/resources/application.properties` 파일 생성

```bash
# DB
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.url=jdbc:h2:mem:test
spring.datasource.username=sa
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console

# Hibernate
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.properties.hibernate.show_sql=true

jwt.secret.key={Base64로 Encoding된 문자열}
```

* 프로젝트 실행

```bash
./gradlew build

./gradlew bootRun
```

<br>

## 🔄️ React와 연동

[React Github Repository](https://github.com/JisooPyo/SeSAC-Semi-Project-Front)

|회원가입|로그인|
|:---:|:---:|
|<img src="https://github.com/user-attachments/assets/c8955092-8c29-4499-ae15-73d095b8f482">|![image](https://github.com/user-attachments/assets/5fa41195-d0ab-4dbd-b5f9-8c1d1241895e)|
|To-Do||
|![image](https://github.com/user-attachments/assets/0b1d64d5-8345-4452-9302-6c79b090cd41)||

<br>

## 📜 REST API Docs

### To Do

|Description|URL|Method|
|---|---|---|
|할 일 등록|/api/todos|POST|
|할 일 조회|/api/todos|GET|
|할 일 수정|/api/todos/{id}|PUT|
|할 일 삭제|/api/todos/{id}|DELETE|

### Member

|Description|Method|URL|
|---|---|---|
|회원가입|/api/members/signup|POST|
|로그인|/api/members/login|POST|
|닉네임 불러오기|/api/members/nickname|GET|
