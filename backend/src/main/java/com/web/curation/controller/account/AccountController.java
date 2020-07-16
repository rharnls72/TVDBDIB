package com.web.curation.controller.account;

import java.util.Map;

import javax.servlet.http.HttpSession;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@ApiResponses(
    value = {
        @ApiResponse(code = 401, message = "Unauthorized", response = BasicResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = BasicResponse.class),
        @ApiResponse(code = 404, message = "Not Found", response = BasicResponse.class),
        @ApiResponse(code = 500, message = "Failure", response = BasicResponse.class)
    }
)

// http://localhost:3000
@CrossOrigin(origins = { "*" })
@RestController
public class AccountController {

    @Autowired
    UserDao userDao;

    @GetMapping("/account/login")
    @ApiOperation(value = "로그인")
    public Object login(@RequestParam(required = true) final String email,
            @RequestParam(required = true) final String password,
            HttpSession session) {

        // 이메일과 비밀번호로 유저 찾아보고 있으면 User 객체 반환
        // 없으면 Null 이 반환
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user = userDao.findUserByEmailAndPassword(user);

        ResponseEntity<BasicResponse> response = null;

        // 존재하는 유저면 로그인 성공
        if (user != null) {
            // =============================================== 세션 문제 해결 필요
            // 세션에 로그인 정보 넣기
            // session.setAttribute("userInfo", user);
            // ===============================================

            // 성공했다는 응답 객체 준비하기
            final BasicResponse result = new BasicResponse();
            result.status = true;
            result.msg = "success";
            result.data = user;
            response = new ResponseEntity<>(result, HttpStatus.OK);
            System.out.println("Login 성공 !");
        }
        // 존재하는 유저가 없으면 로그인 실패
        // 이 경우도 SQL 쿼리는 성공한거니까 result 줄게요
        else {
            final BasicResponse result = new BasicResponse();
            result.status = false;
            result.msg = "이메일 또는 비밀번호가 잘못되었습니다.";
            response = new ResponseEntity<>(result, HttpStatus.OK);
            System.out.println("이메일 또는 비밀번호 잘못됨!");
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
            result.msg = "이메일 중복";
            return new ResponseEntity<>(result, HttpStatus.OK);
        }

        // 닉네임도 중복 확인 해보기
        user = userDao.getUserByNickName(request.getNickname());
        if(user != null) {
            final BasicResponse result = new BasicResponse();
            result.status = false;
            result.msg = "닉네임 중복";
            return new ResponseEntity<>(result, HttpStatus.OK);
        }

        // 이메일이 중복되지 않았다면 회원가입 진행
        int n = userDao.addNewUser(request);

        final BasicResponse result = new BasicResponse();

        // 단 하나의 수정이 일어났다면 가입 된거
        if(n == 1) {
            result.status = true;
            result.msg = "success";
        }
        // 아니면 오류가 난거
        else {
            result.status = false;
            result.msg = "데이터베이스 갱신 : " + n;
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping("/account/modifypw")
    @ApiOperation(value = "비밀번호 변경")
    public Object modifyPassword(@RequestBody Map<String, Object> req, HttpSession session) {
        // 요청으로 받은 기존 비밀번호와 새 비밀번호 가져오기
        String currentPassword = (String) req.get("password");
        String newPassword = (String) req.get("newPassword");
        String email = (String) req.get("email");

        // ======================================== 세션 문제 해결 필요
        // 세션에서 유저 정보 가져오기
        // User user = (User) session.getAttribute("userInfo");
        // ========================================

        // 결과 반환에 쓰일 객체
        final BasicResponse result = new BasicResponse();

        // ======================================== 세션 문제 해결 필요
        // 세션에 유저 정보가 없으면 오류 발생
        // if(user == null) {
        //     result.status = false;
        //     result.data = "세션에 유저 정보가 없습니다.";
        //     return new ResponseEntity<>(result, HttpStatus.OK);
        // }
        // ========================================

        User user = new User();
        // 기존 비밀번호가 일치하는지 확인
        user.setEmail(email);
        user.setPassword(currentPassword);
        int n = userDao.checkPassword(user);

        // 반환 값이 1이 아니면 오류가 발생한거(비밀번호 불일치)
        if(n != 1) {
            result.status = false;
            result.msg = "비밀번호가 일치하지 않습니다. (" + n + ")";
            return new ResponseEntity<>(result, HttpStatus.OK);
        }

        // 이제 비밀번호 바꾸기
        user.setPassword(newPassword);
        n = userDao.modifyPassword(user);

        // 반환 값이 1이 아니면 오류 발생(수정 실패)
        if(n != 1) {
            result.status = false;
            result.msg = "비밀번호 변경 실패. (" + n + ")";
            return new ResponseEntity<>(result, HttpStatus.OK);
        }

        // 여기까지 왔으면 비밀번호 변경 성공
        result.status = true;
        result.msg = "success";
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}