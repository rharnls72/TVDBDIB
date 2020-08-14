package com.web.curation.controller.account;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import com.web.curation.dao.user.UserDao;
import com.web.curation.model.BasicResponse;
import com.web.curation.model.following.FollowCnt;
import com.web.curation.model.user.SignupRequest;
import com.web.curation.model.user.User;
import com.web.curation.service.JWTService;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.beans.factory.annotation.Autowired;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.EnableAsync;

import java.io.File;
import java.io.FileOutputStream;
import java.security.MessageDigest;

@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized", response = BasicResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = BasicResponse.class),
        @ApiResponse(code = 404, message = "Not Found", response = BasicResponse.class),
        @ApiResponse(code = 500, message = "Failure", response = BasicResponse.class) })

// http://localhost:3000
@CrossOrigin(origins = { "*" })
@RestController
@EnableAsync
public class AccountController {

    @Autowired
    UserDao userDao;

    @Autowired
    JWTService jwtService;

    @Autowired
    private JavaMailSender sender;

    @Autowired
    private MailConfig mailConfig;

    @PostMapping("/account/login")
    @ApiOperation(value = "로그인")
    public Object login(@RequestBody Map<String, Object> req) {

        // 이메일과 비밀번호로 유저 찾아보고 있으면 User 객체 반환
        // 없으면 Null 이 반환
        User user = new User();
        user.setEmail((String) req.get("email"));
        try {
            user.setPassword(SHA256((String) req.get("password")));
        } catch (Exception e) {
            final BasicResponse result = new BasicResponse();
            result.status = false;
            result.msg = "비밀번호 암호화 실패";
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        user = userDao.findUserByEmailAndPassword(user);

        ResponseEntity<BasicResponse> response = null;

        String save_profile_pic = "";
        // 존재하는 유저면 로그인 성공
        if (user != null) {
            final BasicResponse result = new BasicResponse();

            if (user.isIs_certification()) {
                // 성공했다는 응답 객체 준비하기
                // 응답 으로 유저 객체와 토큰 둘 다 제공

                // 토큰 주기 전에 profile_pic 부터 좀 어떻게 해줘야함 ㅠ
                save_profile_pic = user.getProfile_pic();
                user.setProfile_pic(null);

                HashMap<String, Object> responseData = new HashMap<>();
                responseData.put("token", jwtService.makeToken(user));
                
                // 토큰 만들었으면 profile_pic 리스토어
                user.setProfile_pic(save_profile_pic);
                responseData.put("user", user);

                result.status = true;
                result.msg = "success";
                result.data = responseData;
                response = new ResponseEntity<>(result, HttpStatus.OK);
                System.out.println("Login 성공 !");
            } else {
                result.status = false;
                result.msg = "이메일 인증을 완료해주세요.";
                response = new ResponseEntity<>(result, HttpStatus.OK);
                System.out.println("이메일 인증 안함!");
            }
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

    @GetMapping("/account/social/login/{email}")
    @ApiOperation(value = "Login for social")
    public Object loginForSocial(@PathVariable String email) {
        User user = userDao.getUserByEmail(email);
        final BasicResponse result = new BasicResponse();

        // 토큰 주기 전에 profile_pic 부터 좀 어떻게 해줘야함 ㅠ
        String save_profile_pic = user.getProfile_pic();
        user.setProfile_pic(null);

        HashMap<String, Object> responseData = new HashMap<>();
        responseData.put("token", jwtService.makeToken(user));
        
        // 토큰 만들었으면 profile_pic 리스토어
        user.setProfile_pic(save_profile_pic);
        responseData.put("user", user);

        result.status = true;
        result.msg = "success";
        result.data = responseData;
        System.out.println("Social Login 성공 !");
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/account/signup")
    @ApiOperation(value = "가입하기")
    public Object signup(@Valid @RequestBody SignupRequest request) {
        // 이메일, 닉네임 중복처리 필수 => 프론트에서 했서요
        // 회원가입단을 생성해 보세요.
        try {
            request.setPassword(SHA256(request.getPassword()));
        } catch (Exception e) {
            final BasicResponse result = new BasicResponse();
            result.status = false;
            result.msg = "비밀번호 암호화 실패";
            return new ResponseEntity<>(result, HttpStatus.OK);
        }

        // 이메일이 중복되지 않았다면 회원가입 진행
        int n = userDao.addNewUser(request);

        final BasicResponse result = new BasicResponse();

        // 단 하나의 수정이 일어났다면 가입 된거
        if (n == 1) {
            result.status = true;
            result.msg = "success";
            mailConfig.sendJoinMail(sender, request.getEmail(), request.getNick_name());
        }
        // 아니면 오류가 난거
        else {
            result.status = false;
            result.msg = "데이터베이스 갱신 : " + n;
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/account/social/join")
    @ApiOperation(value = "Social join")
    public Object joinWithSocial(@RequestBody User user) {
        // 이메일이 중복되지 않았다면 회원가입 진행
        int n = userDao.addNewUserWithSocial(user);
        final BasicResponse result = new BasicResponse();

        // Why errer bb
        System.out.println(user);

        // 단 하나의 수정이 일어났다면 가입 된거
        if (n == 1) {
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

    @PostMapping("/account/sendjoinEmail")
    @ApiOperation(value = "가입메일 재전송하기")
    public Object sendjoinEmail(@RequestBody SignupRequest request) {

        final BasicResponse result = new BasicResponse();
        mailConfig.sendJoinMail(sender, request.getEmail(), request.getNick_name());
        result.status = true;
        result.msg = "success";
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping("/account/modifypw")
    @ApiOperation(value = "비밀번호 변경")
    public Object modifyPassword(@RequestBody Map<String, Object> req) {
        // 요청으로 받은 기존 비밀번호와 새 비밀번호 가져오기
        String currentPassword = (String) req.get("password");
        String newPassword = (String) req.get("newPassword");
        String email = (String) req.get("email");

        // 결과 반환에 쓰일 객체
        final BasicResponse result = new BasicResponse();

        User user = new User();
        // 기존 비밀번호가 일치하는지 확인
        user.setEmail(email);
        try {
            user.setPassword(SHA256(currentPassword));
        } catch (Exception e) {
            result.status = false;
            result.msg = "비밀번호 암호화 실패";
            return new ResponseEntity<>(result, HttpStatus.OK);
        }

        int n = userDao.checkPassword(user);

        // 반환 값이 1이 아니면 오류가 발생한거(비밀번호 불일치)
        if (n != 1) {
            result.status = false;
            result.msg = "비밀번호가 일치하지 않습니다. (" + n + ")";
            return new ResponseEntity<>(result, HttpStatus.OK);
        }

        // 이제 비밀번호 바꾸기
        try {
            user.setPassword(SHA256(newPassword));
        } catch (Exception e) {
            result.status = false;
            result.msg = "비밀번호 암호화 실패";
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        n = userDao.modifyPassword(user);

        // 반환 값이 1이 아니면 오류 발생(수정 실패)
        if (n != 1) {
            result.status = false;
            result.msg = "비밀번호 변경 실패. (" + n + ")";
            return new ResponseEntity<>(result, HttpStatus.OK);
        }

        // 여기까지 왔으면 비밀번호 변경 성공
        result.status = true;
        result.msg = "success";
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping("/account/modifypwemail")
    @ApiOperation(value = "비밀번호 이메일 변경")
    public Object modifyPasswordEmail(@RequestBody Map<String, Object> req) {
        String newPassword = (String) req.get("newPassword");
        String email = (String) req.get("email");

        // 결과 반환에 쓰일 객체
        final BasicResponse result = new BasicResponse();

        User user = new User();
        user.setEmail(email);
        try {
            user.setPassword(SHA256(newPassword));
        } catch (Exception e) {
            result.status = false;
            result.msg = "비밀번호 암호화 실패";
            return new ResponseEntity<>(result, HttpStatus.OK);
        }

        int n = userDao.modifyPassword(user);

        // 반환 값이 1이 아니면 오류 발생(수정 실패)
        if (n != 1) {
            result.status = false;
            result.msg = "비밀번호 변경 실패. (" + n + ")";
            return new ResponseEntity<>(result, HttpStatus.OK);
        }

        // 여기까지 왔으면 비밀번호 변경 성공
        result.status = true;
        result.msg = "success";
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/account/emailconfirm")
    @ApiOperation(value = "이메일 인증")
    public Object emailconfirm(@RequestParam(required = true) final String email) {
        // 결과 반환에 쓰일 객체
        final BasicResponse result = new BasicResponse();
        int n = userDao.emailConfirm(email);

        if (n != 1) {
            result.status = false;
            result.msg = "이메일 인증 실패";
            return new ResponseEntity<>(result, HttpStatus.OK);
        }

        // 여기까지 왔으면 비밀번호 변경 성공
        result.status = true;
        result.msg = "success";
        return new ResponseEntity<>(result, HttpStatus.OK);

    }

    @GetMapping("/account/findemail")
    @ApiOperation(value = "이메일 찾기")
    public Object findEmail(@RequestParam(required = true) final String email) {
        // 결과 반환에 쓰일 객체
        final BasicResponse result = new BasicResponse();
        User user = userDao.getUserByEmail(email);

        // 반환 값이 1이 아니면 오류가 발생한거(비밀번호 불일치)
        if (user == null) {
            result.status = false;
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            result.status = true;
            return new ResponseEntity<>(result, HttpStatus.OK);
        }

    }

    @GetMapping("/account/fnidpw")
    @ApiOperation(value = "비밀번호 찾기")
    public Object findPassword(@RequestParam(required = true) final String email) {
        final BasicResponse result = new BasicResponse();
        User user = userDao.getUserByEmail(email);
        if (user == null) {
            result.status = false;
            result.msg = "이메일 찾기 실패";
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        mailConfig.sendFindPwMail(sender, email, user.getNick_name());
        result.status = true;
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/account/checkNick")
    @ApiOperation(value = "닉네임 확인")
    public Object checkNick(@RequestParam(required = true) final String nick_name,
            @RequestParam(required = true) final String new_nick_name) {
        final BasicResponse result = new BasicResponse();
        if (new_nick_name.equals("")
                || (!nick_name.equals(new_nick_name) && userDao.checkNickName(new_nick_name) == 1)) {
            result.status = false;
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        result.status = true;
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping("/account/modifyprofile")
    @ApiOperation(value = "프로필 변경")
    public Object modifyProfile(@RequestBody User user) {
        final BasicResponse result = new BasicResponse();

        try {
            String pre_path = "/tvility";
            String post_path = "/" + user.getUno() + ".profile_pic";
            String full_path = pre_path + post_path;
            File file = new File(full_path);
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(user.getProfile_pic().getBytes());
            fos.close();

            user.setProfile_pic_without_convert(post_path);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            result.status = false;
            result.msg = "프로필 사진 저장 실패";
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        
        int n = userDao.modifyProfile(user);
        if(n != 1) {
            result.status = false;
            result.msg = "프로필 변경 실패";
            return new ResponseEntity<>(result, HttpStatus.OK);
        }

        result.status = true;
        result.msg = "success";
        result.data = userDao.getUserByEmail(user.getEmail());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    @GetMapping("/account/followcnt")
    @ApiOperation(value = "팔로우 수")
    public Object getFollowCount(@RequestParam(required = true) final String my_nick_name,
                @RequestParam(required = true) final String other_nick_name){
        User profile = userDao.getUserByNickName(other_nick_name);
        User mine = userDao.getUserByNickName(my_nick_name);

        FollowCnt cnt = userDao.getFollowCnt(profile.getUno(), mine.getUno());
        
        final BasicResponse result = new BasicResponse();
        
        if(cnt != null){
            HashMap<String, Object> responseData = new HashMap<>();
            responseData.put("userInfo", profile);
            responseData.put("followCnt", cnt);
            result.status = true;
            result.msg = "success";
            result.data = responseData;
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        result.status = false;
        result.msg = "프로필 조회 실패";
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    public String SHA256(String msg) throws Exception{
		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		byte[] hash = digest.digest(msg.getBytes("UTF-8"));
		StringBuffer hexString = new StringBuffer();

		for (int i = 0; i < hash.length; i++) {
			String hex = Integer.toHexString(0xff & hash[i]);
			if(hex.length() == 1) hexString.append('0');
			hexString.append(hex);
		}
        return hexString.toString();
    }
}