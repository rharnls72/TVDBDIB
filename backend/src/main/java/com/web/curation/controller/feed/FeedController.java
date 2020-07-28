package com.web.curation.controller.feed;

import java.util.ArrayList;
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
    public Object getFeedList(@RequestBody FeedRequest req) {
        // 반환할 응답 객체
        final BasicResponse result = new BasicResponse();

        // 피드 리스트 조회
        int start = (req.getNum() - 1) * 20;
        List<Feed> list = dao.getFeedList(start);

        // 조회한 데이터의 크기
        int listSize = list.size();

        // 조회한 데이터가 있다면
        ArrayList<Feed> response = new ArrayList<>();
        if(listSize > 0) {
            // 피드 하나의 정보를 담을 객체
            Feed row = new Feed();
            // 최대 2명의 좋아요 한 사람만 표시
            int like_cnt = 0;
            
            // 모든 데이터에 대해 반복
            for(Feed feed : list) {
                // 현재 작업 중인 피드와 동일한 글이라면
                if(row.getFno() == feed.getFno()) {
                    // 현재 로그인 한 유저가 좋아요를 누른 글인지 확인
                    if(!row.getPress_like() && row.getLike_num() > 0) {
                        if(feed.getLiker_uno() == req.getUno()) {
                            row.setPress_like(true);
                        }
                    }

                    // 좋아요 누른 사람 최대 2명의 닉네임 반환해야함
                    if(row.getLike_num() > 0 && like_cnt < 2) {
                        row.appendNickName(feed.getLiker_nick_name());
                        like_cnt++;
                    }
                }
                // 다른 피드라면
                else {
                    // 작업 중이던 이전 피드가 있다면
                    if(row.getFno() != 0) {
                        // 응답 리스트에 작업중이던 피드 정보 넣기
                        response.add(row);
                    }

                    // like_cnt 초기화
                    like_cnt = 0;

                    // 작업할 피드를 현재 피드로 가리키기
                    row = feed;

                    // 현재 로그인 한 유저가 좋아요를 누른 댓글인지 확인
                    if(!row.getPress_like() && row.getLike_num() > 0) {
                        if(feed.getLiker_uno() == req.getUno()) {
                            row.setPress_like(true);
                        }
                    }

                    // 좋아요 누른 사람 최대 2명의 닉네임 반환해야함
                    if(row.getLike_num() > 0 && like_cnt < 2) {
                        like_cnt++;
                    }
                }
            }
            // 만약 리스트에 안넣은 row 가 남았으면 그거도 넣기
            if(row.getFno() > 0) {
                response.add(row);
            }
        }

        // 조회된 피드 목록 반환
        result.status = true;
        result.msg = "success";
        result.data = response;
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