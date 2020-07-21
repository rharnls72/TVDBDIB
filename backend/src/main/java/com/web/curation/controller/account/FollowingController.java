package com.web.curation.controller.account;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;

import com.web.curation.dao.user.FollowingDao;
import com.web.curation.model.BasicResponse;
import com.web.curation.model.program.Program;
import com.web.curation.model.user.ProgramFollowing;
import com.web.curation.model.user.User;
import com.web.curation.model.user.UserFollowing;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@ApiResponses(
    value = {
        @ApiResponse(code = 401, message = "Unauthorized", response = BasicResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = BasicResponse.class),
        @ApiResponse(code = 404, message = "Not Found", response = BasicResponse.class),
        @ApiResponse(code = 500, message = "Failure", response = BasicResponse.class)
    }
)

@CrossOrigin(origins = { "*" })
@RestController
public class FollowingController {
    
    @Autowired
    FollowingDao dao;

    /*
        User Following
    */
    // Create
    @PostMapping("/following/user/add")
    @ApiOperation(value = "유저 팔로잉 추가")
    public Object addUserFollowing(@RequestBody UserFollowing uf) {
        // 반환할 응답 객체
        final BasicResponse result = new BasicResponse();

        // 유저 팔로잉 추가
        int n = dao.addUserFollowing(uf);

        // n 이 1 이 아니면 쿼리 수행 결과에 이상이 있는 것
        if(n != 1) {
            result.status = false;
            result.msg = "Insert 쿼리 수행 결과에 이상이 발생했습니다.(" + n + ")";
            return new ResponseEntity<>(result, HttpStatus.OK);
        }

        // 여기까지 문제 없이 내려왔으면 성공적으로 유저 팔로잉 추가가 완료된 것
        result.status = true;
        result.msg = "success";
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // Read
    @GetMapping("/following/user/followingcnt/{uno}")
    @ApiOperation(value = "유저가 팔로잉하는 사람과 프로그램 수 조회")
    public Object getUserFollowingCount(@PathVariable("uno") int uno) {
        // 반환할 응답 객체
        final BasicResponse result = new BasicResponse();

        // 팔로잉하는 유저 수 조회
        int un = dao.getUserFollowingsCount(uno);

        // 팔로잉 하는 프로그램 수 조회
        int pn = dao.getProgramFollowingCount(uno);

        // 조회된 팔로잉 수를 객체에 넣기
        HashMap<String, Object> map = new HashMap<>();
        map.put("user_cnt", un);
        map.put("program_cnt", pn);
        map.put("total", un + pn);

        // 조회된 팔로잉 수 반환
        result.status = true;
        result.msg = "success";
        result.data = map;
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/following/user/followings/{uno}")
    @ApiOperation(value = "유저가 팔로잉하는 사람과 프로그램 리스트 조회")
    public Object getUserFollowings(@PathVariable("uno") int uno) {
        // 반환할 응답 객체
        final BasicResponse result = new BasicResponse();

        // 팔로잉하는 유저 리스트 조회
        List<User> ul = dao.getUserFollowings(uno);

        // 팔로잉 하는 프로그램 리스트 조회
        List<Program> pl = dao.getProgramFollowings(uno);

        // 조회된 팔로잉 리스트를 객체에 넣기
        HashMap<String, Object> map = new HashMap<>();
        map.put("user_list", ul);
        map.put("program_list", pl);

        // 조회된 팔로잉 리스트 반환
        result.status = true;
        result.msg = "success";
        result.data = map;
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/following/user/followercnt/{uno}")
    @ApiOperation(value = "유저를 팔로잉하는 사람 수 조회")
    public Object getUserFollowerCount(@PathVariable("uno") int uno) {
        // 반환할 응답 객체
        final BasicResponse result = new BasicResponse();

        // 팔로우 하는 유저 수 조회
        int un = dao.getUserFollowersCount(uno);

        // 조회된 팔로우 수를 객체에 넣기
        HashMap<String, Object> map = new HashMap<>();
        map.put("user_cnt", un);

        // 조회된 팔로우 수 반환
        result.status = true;
        result.msg = "success";
        result.data = map;
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/following/user/followers/{uno}")
    @ApiOperation(value = "유저를 팔로잉하는 사람 리스트 조회")
    public Object getUserFollowers(@PathVariable("uno") int uno) {
        // 반환할 응답 객체
        final BasicResponse result = new BasicResponse();

        // 팔로우 하는 유저 리스트 조회
        List<User> ul = dao.getUserFollowers(uno);

        // 조회된 팔로잉 리스트를 객체에 넣기
        HashMap<String, Object> map = new HashMap<>();
        map.put("user_list", ul);

        // 조회된 팔로잉 리스트 반환
        result.status = true;
        result.msg = "success";
        result.data = map;
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // Delete
    @PostMapping("/following/user/cancel")
    @ApiOperation(value = "유저의 유저 팔로잉 취소")
    public Object cancelUserFollowing(@RequestBody UserFollowing uf) {
        // 반환할 응답 객체
        final BasicResponse result = new BasicResponse();

        // 유저 팔로잉 삭제
        int n = dao.deleteUserFollowing(uf);

        // n 이 1 이 아니면 쿼리 수행 결과에 이상이 있는 것
        if(n != 1) {
            result.status = false;
            result.msg = "Delete 쿼리 수행 결과에 이상이 발생했습니다.(" + n + ")";
            return new ResponseEntity<>(result, HttpStatus.OK);
        }

        // 여기까지 문제 없이 내려왔으면 성공적으로 유저 팔로잉 삭제가 완료된 것
        result.status = true;
        result.msg = "success";
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /*
        Program Following
    */
    // Create
    @PostMapping("/following/program/add")
    @ApiOperation(value = "프로그램 팔로잉 추가")
    public Object addProgramFollowing(@RequestBody ProgramFollowing pf) {
        // 반환할 응답 객체
        final BasicResponse result = new BasicResponse();

        // 프로그램 팔로잉 추가
        int n = dao.addProgramFollowing(pf);

        // n 이 1 이 아니면 쿼리 수행 결과에 이상이 있는 것
        if(n != 1) {
            result.status = false;
            result.msg = "Insert 쿼리 수행 결과에 이상이 발생했습니다.(" + n + ")";
            return new ResponseEntity<>(result, HttpStatus.OK);
        }

        // 여기까지 문제 없이 내려왔으면 성공적으로 프로그램 팔로잉 추가가 완료된 것
        result.status = true;
        result.msg = "success";
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // Read
    @GetMapping("/following/program/followercnt/{pno}")
    @ApiOperation(value = "프로그램을 팔로잉하는 사람 수 조회")
    public Object getProgramFollowerCount(@PathVariable("pno") int pno) {
        // 반환할 응답 객체
        final BasicResponse result = new BasicResponse();

        // 팔로우 하는 유저 수 조회
        int un = dao.getProgramFollowersCount(pno);

        // 조회된 팔로우 수를 객체에 넣기
        HashMap<String, Object> map = new HashMap<>();
        map.put("user_cnt", un);

        // 조회된 팔로우 수 반환
        result.status = true;
        result.msg = "success";
        result.data = map;
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/following/program/followers/{pno}")
    @ApiOperation(value = "프로그램을 팔로잉하는 사람 리스트 조회")
    public Object getProgramFollowers(@PathVariable("pno") int pno) {
        // 반환할 응답 객체
        final BasicResponse result = new BasicResponse();

        // 팔로우 하는 유저 리스트 조회
        List<User> ul = dao.getProgramFollowers(pno);

        // 조회된 팔로잉 리스트를 객체에 넣기
        HashMap<String, Object> map = new HashMap<>();
        map.put("user_list", ul);

        // 조회된 팔로잉 리스트 반환
        result.status = true;
        result.msg = "success";
        result.data = map;
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // Delete
    @PostMapping("/following/program/cancel")
    @ApiOperation(value = "유저의 프로그램 팔로잉 취소")
    public Object cancelProgramFollowing(@RequestBody ProgramFollowing pf) {
        // 반환할 응답 객체
        final BasicResponse result = new BasicResponse();

        // 프로그램 팔로잉 삭제
        int n = dao.deleteProgramFollowing(pf);

        // n 이 1 이 아니면 쿼리 수행 결과에 이상이 있는 것
        if(n != 1) {
            result.status = false;
            result.msg = "Delete 쿼리 수행 결과에 이상이 발생했습니다.(" + n + ")";
            return new ResponseEntity<>(result, HttpStatus.OK);
        }

        // 여기까지 문제 없이 내려왔으면 성공적으로 유저 팔로잉 삭제가 완료된 것
        result.status = true;
        result.msg = "success";
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}