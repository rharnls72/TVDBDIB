# 티비디비딥!

http://i3a106.p.ssafy.io/   
   
TV + DB + Dib (찜하다)   
TV 프로그램 큐레이션 서비스와 SNS를 결합한 신개념 플랫폼!   

* TV 큐레이션   
여러 방송사의 TV 프로그램에 대한 정보를 찾아보고 팔로우하여 최신 에피소드를 구독할 수 있습니다.   
* SNS   
좋아하는 프로그램에 대한 글을 올리고 친구들과 공유할 수 있습니다.   

## Before You Begin   

시작하기 앞서 아래 기술에 대해 알아보는 것을 추천드립니다:
* MariaDB - MariaDB 회사에서 개발한 RDBMS로 MySQL의 유료화에 반발해 MySQL 코드를 기반으로 만든 오픈소스 RDBMS 입니다.
<br />Web site : [MariaDB](https://mariadb.org/)
* Spring Boot - Spring 프로젝트가 제공하는 다양한 라이브러리와 프레임워크로 빠르게 엔터프라이즈 어플리케이션을 개발 할 수 있게 하는 프레임워크로 본 프로젝트에서는 RestAPI 를 사용하는 웹 백엔드 개발에 사용됩니다.
<br />Web site : [Spring Boot](https://spring.io/projects/spring-boot)
* Vue.js - 웹 프론트엔드를 SPA 기반으로 개발하기 위한 프레임워크로 Angular.js의 데이터 바인딩 특성과 React의 가상 돔 기반 렌더링 특징을 모두 가지고 있습니다.
<br />Web site : [Vue.js](https://vuejs.org/)


## 로컬 환경에서 실행하기   

* frontend 환경 세팅
    * Node.js 설치: https://nodejs.org/en/
    * yarn 설치: https://classic.yarnpkg.com/en/docs/install#windows-stable
    * vue-cli 설치
        <pre><code>yarn global add @vue/cli</code></pre>
    * 모듈 다운로드
        <pre><code>yarn install</code></pre>
    
* backend 환경 세팅
    * Java 설치 (OpenJDK 14): https://jdk.java.net/14/
    * Java 환경 변수 설정
    * Maven 설치: http://maven.apache.org/download.cgi
    * Maven 환경 변수 설정
    * VSCode 설치: https://code.visualstudio.com/
    * VSCode Spring Boot Extension Pack 설치
    * Docker 설치: https://www.docker.com/get-started
    * MariaDB 컨테이너 실행
        <pre><code>docker run --name maria-db -p 3306:3306 -e MYSQL_ROOT_PASSWORD={패스워드} -d mariadb   
        docker exec -it maria-db mysql -u root -p</code></pre>
    * DB 유저 생성 및 권한 설정
        <pre><code>create database tvility;
        create user 'ability'@'%' identified by 'ability!';
        grant all privileges on tvility.* to ability;</code></pre>
    * DB 테이블 생성: backend/tvility_tables.sql 파일 내의 SQL 실행

* 이외에도 <취향저격 프로그램> 추천 기능을 로컬 환경에서 테스트하기 위해서는 Python, Flask 환경 세팅이 필요합니다.
    

## 이용 방법

![티비디비딥_사용법](/uploads/6f8d31efa877655e60cb665bbf4673e4/티비디비딥_사용법.PNG)

* A. 피드 작성 버튼   
피드 (글) 를 작성합니다. 일반 글, 투표, 카운트다운 3가지의 글 유형 중 선택하여 글을 작성할 수 있습니다.   
   
* B. 메시지 버튼   
메시지 화면으로 이동합니다. 1:1 또는 단체 대화방을 만들어 대화를 나눌 수 있습니다.   
   
* 1. 피드 타임라인 탭   
내가 팔로우하고 있는 유저들이 작성한 피드를 모아서 볼 수 있습니다.
* 2. 검색 탭   
유저, 프로그램, 피드를 검색할 수 있으며 인기 섹션에서는 프로그램을 추천받을 수 있습니다.   
* 3. 프로그램 타임라인 탭   
내가 팔로우하고 있는 프로그램들의 최신 에피소드를 모아서 볼 수 있습니다.   
* 4. 알림 탭   
내 글에 대한 댓글, 좋아요 등 반응을 모아서 볼 수 있으며 팔로우 요청을 확인할 수 있습니다.   
* 5. 내 정보 탭   
내 프로필을 조회, 수정할 수 있으며 내가 쓴 글을 모아서 볼 수 있습니다.   
