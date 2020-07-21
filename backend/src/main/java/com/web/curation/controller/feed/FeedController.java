package com.web.curation.controller.feed;

import java.util.List;

import com.web.curation.dao.feed.FeedDao;
import com.web.curation.model.BasicResponse;
import com.web.curation.model.feed.Feed;

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
    public Object createFeed(@RequestBody Feed feed) {
        // 반환할 응답 객체
        final BasicResponse result = new BasicResponse();

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
    @GetMapping("/feed/list")
    @ApiOperation(value = "피드 목록 조회")
    public Object getFeedList() {
        // 반환할 응답 객체
        final BasicResponse result = new BasicResponse();

        // 피드 리스트 조회
        List<Feed> list = dao.getFeedList();

        // list 가 null 이면 조회에 실패한 것
        if(list == null) {
            result.status = false;
            result.msg = "피드 목록 조회에 실패했습니다.";
            return new ResponseEntity<>(result, HttpStatus.OK);
        }

        // 여기까지 문제 없이 내려왔으면 성공적으로 피드 목록 조회가 완료된 것
        result.status = true;
        result.msg = "success";
        result.data = list;
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/feed/detail/{fno}")
    @ApiOperation(value = "피드 상세 정보 조회")
    public Object getFeedDetail(@PathVariable("fno") int fno) {
        // 반환할 응답 객체
        final BasicResponse result = new BasicResponse();

        // 피드 조회
        Feed feed = dao.getFeedDetail(fno);

        // feed 가 null 이면 조회에 실패한 것
        if(feed == null) {
            result.status = false;
            result.msg = "피드 상세 정보 조회에 실패했습니다.(" + fno + ")";
            return new ResponseEntity<>(result, HttpStatus.OK);
        }

        // 여기까지 문제 없이 내려왔으면 성공적으로 피드 상세정보 조회가 완료된 것
        result.status = true;
        result.msg = "success";
        result.data = feed;
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