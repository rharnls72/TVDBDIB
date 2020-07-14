package com.web.curation.controller.account;

import javax.validation.Valid;

import com.web.curation.dao.user.UserDao;
import com.web.curation.model.BasicResponse;
import com.web.curation.model.user.SignupRequest;
import com.web.curation.model.user.User;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.beans.factory.annotation.Autowired;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized", response = BasicResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = BasicResponse.class),
        @ApiResponse(code = 404, message = "Not Found", response = BasicResponse.class),
        @ApiResponse(code = 500, message = "Failure", response = BasicResponse.class) })

// http://localhost:3000
@CrossOrigin(origins = { "*" })
@RestController
public class AccountController {

    @Autowired
    UserDao userDao;

    @GetMapping("/account/login")
    @ApiOperation(value = "로그인")
    public Object login(@RequestParam(required = true) final String email,
            @RequestParam(required = true) final String password) {

        // 이메일과 비밀번호로 유저 찾아보고 있으면 User 객체 반환
        // 없으면 Null 이 반환
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user = userDao.findUserByEmailAndPassword(user);

        ResponseEntity<BasicResponse> response = null;

        // 존재하는 유저면 로그인 성공
        if (user != null) {
            final BasicResponse result = new BasicResponse();
            result.status = true;
            result.data = "success";
            response = new ResponseEntity<>(result, HttpStatus.OK);
        }
        // 존재하는 유저가 없으면 로그인 실패
        // 이 경우도 SQL 쿼리는 성공한거니까 result 줄게요
        else {
            final BasicResponse result = new BasicResponse();
            result.status = false;
            result.data = "이메일 또는 비밀번호가 잘못되었습니다.";
            response = new ResponseEntity<>(result, HttpStatus.OK);
        }

        return response;
    }

    @PostMapping("/account/signup")
    @ApiOperation(value = "가입하기")
    public Object signup(@Valid @RequestBody SignupRequest request) {
        // 이메일, 닉네임 중복처리 필수
        // 회원가입단을 생성해 보세요.

        // 먼저 이메일 중복 확인 해보기
        User user = userDao.getUserByEmail(request.getEmail());
        if(user != null) {
            final BasicResponse result = new BasicResponse();
            result.status = false;
            result.data = "이메일 중복";
            return new ResponseEntity<>(result, HttpStatus.OK);
        }

        // 닉네임도 중복 확인 해보기
        user = userDao.getUserByNickName(request.getNickname());
        if(user != null) {
            final BasicResponse result = new BasicResponse();
            result.status = false;
            result.data = "닉네임 중복";
            return new ResponseEntity<>(result, HttpStatus.OK);
        }

        // 이메일이 중복되지 않았다면 회원가입 진행
        int n = userDao.addNewUser(request);

        final BasicResponse result = new BasicResponse();

        // 단 하나의 수정이 일어났다면 가입 된거
        if(n == 1) {
            result.status = true;
            result.data = "success";
        }
        // 아니면 오류가 난거
        else {
            result.status = false;
            result.data = "데이터베이스 갱신 : " + n;
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}