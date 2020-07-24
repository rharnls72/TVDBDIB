package com.web.curation.controller.following;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import com.web.curation.dao.following.FollowRequestDao;
import com.web.curation.model.BasicResponse;
import com.web.curation.model.following.FollowRequest;

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
public class FollowRequestController {
    
    @Autowired
    FollowRequestDao dao;

    // Create
    @PostMapping("/followrequest/create")
    @ApiOperation(value = "새 팔로우 요청 생성")
    public Object createNewAlert(@RequestBody FollowRequest req) {
        // 반환할 응답 객체
        final BasicResponse result = new BasicResponse();

        // 팔로우 요청 추가
        int n = dao.addFollowRequest(req);

        // n 이 1 이 아니면 쿼리 수행 결과에 이상이 있는 것
        if(n != 1) {
            result.status = false;
            result.msg = "Insert 쿼리 수행 결과에 이상이 발생했습니다.(" + n + ")";
            return new ResponseEntity<>(result, HttpStatus.OK);
        }

        // 팔로우 요청 추가 완료
        result.status = true;
        result.msg = "success";
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // Read
    @GetMapping("/followrequest/list/{uno}")
    @ApiOperation(value = "팔로우 요청 목록 조회")
    public Object getAlertList(@PathVariable int uno) {
        // 반환할 응답 객체
        final BasicResponse result = new BasicResponse();

        // 팔로우 요청 목록 조회
        List<FollowRequest> list = dao.getFollowRequestList(uno);

        // 팔로우 요청 목록을 포함한 응답 객체 반환
        result.status = true;
        result.msg = "success";
        result.data = list;
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // Delete
    @DeleteMapping("/followrequest/delete/{fno}")
    @ApiOperation(value = "팔로우 요청 삭제")
    public Object deleteAlert(@PathVariable int fno) {
        // 반환할 응답 객체
        final BasicResponse result = new BasicResponse();

        // 팔로우 요청 삭제
        int n = dao.deleteFollowRequest(fno);

        // n 이 1 이 아니면 쿼리 수행 결과에 이상이 있는 것
        if(n != 1) {
            result.status = false;
            result.msg = "Delete 쿼리 수행 결과에 이상이 발생했습니다.(" + n + ")";
            return new ResponseEntity<>(result, HttpStatus.OK);
        }

        // 팔로우 요청 삭제 완료
        result.status = true;
        result.msg = "success";
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}