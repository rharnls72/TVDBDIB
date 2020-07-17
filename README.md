#회원 정보 관리 모바일 웹 소스

웹 서비스에서 주로 사용되는 로그인, 로그아웃, 비밀번호 변경 등
회원 정보 관리 기능을 MariaDB, Spring-boot, Vue.js 를 사용하여
모바일 웹 화면으로 구성한 소스 파일입니다.

## Before You Begin
시작하기 앞서 아래 세 가지 기술에 대해 알아보는 것을 추천드립니다 :
* MariaDB - MariaDB 회사에서 개발한 RDBMS로 MySQL의 유료화에 반발해 MySQL 코드를 기반으로 만든 오픈소스 RDBMS 입니다.
Web site : [MariaDB](https://mariadb.org/)
* Spring Boot - Spring 프로젝트가 제공하는 다양한 라이브러리와 프레임워크로 빠르게 엔터프라이즈 어플리케이션을 개발 할 수 있게 하는 프레임워크로 본 프로젝트에서는 RestAPI 를 사용하는 웹 백엔드 개발에 사용됩니다.
Web site : [Spring Boot](https://spring.io/projects/spring-boot)
* Vue.js - 웹 프론트엔드를 SPA 기반으로 개발하기 위한 프레임워크로 Angular.js의 데이터 바인딩 특성과 React의 가상 돔 기반 렌더링 특징을 모두 가지고 있습니다.
Web site : [Vue.js](https://vuejs.org/)


## Prerequisites
이 프로젝트를 다운받고 실행시키기 위해 아래 프로그램들이 필요합니다 :
* Git - 프로젝트를 로컬에 클론하기 위한 툴
[Download & Install Git](https://git-scm.com/downloads)
* Node.js - 웹 프론트엔드 개발에 필요한 패키지들의 의존성 관리 및 빌드와 실행 등을 위해 필요한 툴
[Download & Install Node.js](https://nodejs.org/en/download/)
* Yarn - npm 보다 빠른 빌드 등을 지원하는 툴
* MariaDB - 데이터베이스 관리를 위한 RDBMS
[Download & Install MariaDB](https://mariadb.org/download/)
* Spring Boot - 웹 백엔드 개발 및 서버 실행을 위한 툴


## Quick Install
프론트엔드 폴더내에서 아래 명령어를 실행하면 프론트엔드 실행에 필요한 패키지들이 의존성에 맞게 설치됩니다

```bash
$ yarn install
```

## Running Your Application

아래 명령어를 사용해 프론트엔드를 실행합니다:

```bash
$ yarn run serve
```
아래 명령어를 사용해 백엔드를 실행합니다(vscode 기준):

```bash
$ ./mvnw spring-boot:run
```