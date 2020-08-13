package com.web.curation.controller.vote;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.servlet.http.HttpServletRequest;

import com.web.curation.dao.vote.VoteDao;
import com.web.curation.model.BasicResponse;
import com.web.curation.model.user.User;
import com.web.curation.model.vote.Vote;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;

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
public class VoteController {
    
    @Autowired
    private VoteDao dao;

    @PostMapping("/vote/create")
    @ApiOperation(value = "투표 정보 테이블에 기록")
    public Object createVote(@RequestBody Vote vote, HttpServletRequest httpReq) {
        // 반환할 응답 객체
        final BasicResponse result = new BasicResponse();

        // 로그인한 유저 정보 가져오기
        vote.setUno(((User) httpReq.getAttribute("User")).getUno());

        // 투표 정보 추가
        int n = dao.addVote(vote);

        // n 이 1 이 아니면 쿼리 수행 결과에 이상이 있는 것
        if(n != 1) {
            result.status = false;
            result.msg = "Insert 쿼리 수행 결과에 이상이 발생했습니다.(" + n + ")";
            return new ResponseEntity<>(result, HttpStatus.OK);
        }

        // 완료
        result.status = true;
        result.msg = "success";
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/vote/delete/{fno}")
    @ApiOperation(value = "투표 정보 테이블에서 제거")
    public Object deleteVote(@PathVariable("fno") int fno, HttpServletRequest httpReq) {
        // 반환할 응답 객체
        final BasicResponse result = new BasicResponse();

        Vote vote = new Vote();
        vote.setFno(fno);

        // 로그인한 유저 정보 가져오기
        vote.setUno(((User) httpReq.getAttribute("User")).getUno());

        // 투표 정보 삭제
        int n = dao.deleteVote(vote);

        // n 이 1 이 아니면 쿼리 수행 결과에 이상이 있는 것
        if(n != 1) {
            result.status = false;
            result.msg = "Delete 쿼리 수행 결과에 이상이 발생했습니다.(" + n + ")";
            return new ResponseEntity<>(result, HttpStatus.OK);
        }

        // 완료
        result.status = true;
        result.msg = "success";
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}