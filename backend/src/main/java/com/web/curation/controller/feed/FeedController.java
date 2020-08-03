package com.web.curation.controller.feed;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.web.curation.dao.feed.FeedDao;
import com.web.curation.model.BasicResponse;
import com.web.curation.model.feed.Feed;
import com.web.curation.model.feed.FeedRequest;
import com.web.curation.model.user.User;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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

@CrossOrigin(origins = { "*" })
@RestController
public class FeedController {
    
    @Autowired
    FeedDao dao;

    // Create
    @PostMapping("/feed/create")
    @ApiOperation(value = "피드 생성")
    public Object createFeed(@RequestBody Feed feed, HttpServletRequest request) {
        
        System.out.println("피드 생성 백엔드 코드 들어옴!");

        // 반환할 응답 객체
        final BasicResponse result = new BasicResponse();

        // 유저 정보 조회
        User user = (User) request.getAttribute("User");
        System.out.println(user);

        // 생성할 피드 정보 중 유저 번호는 토큰을 통해 알아낸 값으로 넣기
        feed.setUno(user.getUno());

        // 피드 생성
        int n = dao.addNewFeed(feed);

        // n 이 1 이 아니면 쿼리 수행 결과에 이상이 있는 것
        if(n != 1) {
            result.status = false;
            result.msg = "Insert 쿼리 수행 결과에 이상이 발생했습니다.(" + n + ")";
            return new ResponseEntity<>(result, HttpStatus.OK);
        }

        // 여기까지 문제 없이 내려왔으면 성공적으로 피드 생성이 완료된 것
        result.status = true;
        result.msg = "success";
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // Read
    @PostMapping("/feed/list")
    @ApiOperation(value = "피드 목록 조회")
    public Object getFeedList(@RequestBody FeedRequest feedRequest, HttpServletRequest request) {
        // 반환할 응답 객체
        final BasicResponse result = new BasicResponse();

        // 유저 정보 가져와서 그 번호를 feedRequest 에 넣기
        feedRequest.setUno(((User)request.getAttribute("User")).getUno());

        // 피드 리스트 조회를 시작할 위치 설정
        feedRequest.setNum((feedRequest.getNum() - 1) * 20);

        // 피드 리스트 조회
        List<Feed> list = dao.getFeedList(feedRequest);

        // 조회된 피드 목록 반환
        result.status = true;
        result.msg = "success";
        result.data = list;
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/test/feedcnt/{uno}")
    @ApiOperation(value = "작성한 피드 수 조회(API 로 호출 안하는 메서드)")
    public Object getFeedCount(@PathVariable("uno") int uno) {
        // 반환할 응답 객체
        final BasicResponse result = new BasicResponse();

        // 조회
        int cnt = dao.getFeedCount(uno);

        // 완
        result.status = true;
        result.msg = "success";
        result.data = cnt;
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // Update
    @PutMapping("/feed/update")
    @ApiOperation(value = "피드 수정")
    public Object updateFeed(@RequestBody Feed feed) {
        // 반환할 응답 객체
        final BasicResponse result = new BasicResponse();

        // 피드 생성
        int n = dao.modifyFeed(feed);

        // n 이 1 이 아니면 쿼리 수행 결과에 이상이 있는 것
        if(n != 1) {
            result.status = false;
            result.msg = "Update 쿼리 수행 결과에 이상이 발생했습니다.(" + n + ")";
            return new ResponseEntity<>(result, HttpStatus.OK);
        }

        // 여기까지 문제 없이 내려왔으면 성공적으로 피드 수정이 완료된 것
        result.status = true;
        result.msg = "success";
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // Delete
    @DeleteMapping("/feed/delete/{fno}")
    @ApiOperation(value = "피드 제거")
    public Object deleteFeed(@PathVariable("fno") int fno) {
        // 반환할 응답 객체
        final BasicResponse result = new BasicResponse();

        // 피드 번호로 피드 삭제하기
        int n = dao.deleteFeed(fno);

        // n 이 1 이 아니면 삭제 쿼리에서 오류가 있는거
        if(n != 1) {
            result.status = false;
            result.msg = "Delete 쿼리 수행 결과에 이상이 발생했습니다.(" + n + ")";
            return new ResponseEntity<>(result, HttpStatus.OK);
        }

        // 여기까지 문제 없이 내려왔으면 성공적으로 삭제가 완료된 것
        result.status = true;
        result.msg = "success";
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}