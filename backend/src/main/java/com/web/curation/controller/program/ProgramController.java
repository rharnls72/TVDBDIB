package com.web.curation.controller.program;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

import com.web.curation.dao.like.LikeDao;
import com.web.curation.dao.reply.ReplyDao;
import com.web.curation.model.BasicResponse;
import com.web.curation.model.like.Like;
import com.web.curation.model.program.Program;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
public class ProgramController {

    @Autowired
    LikeDao likeDao;

    @Autowired
    ReplyDao replyDao;

    @GetMapping("/program/detail/{pno}")
    @ApiOperation(value = "프로그램 상세 정보 조회")
    public Object getProgramDetail(@PathVariable("pno") int pno, HttpServletRequest req) {
        // 반환할 응답 객체
        final BasicResponse result = new BasicResponse();

        ////////////////////////////////////////////////////////////////////////////////////////
        // 로그인 한 유저 정보 (유저 번호만) 가져오기
        // int uno = ((User) req.getAttribute("User")).getUno();
        // 위에꺼는 로그인해서 테스트 할 때 쓰고 지금은 유저정보 고정값으로 박아두기
        int uno = 1;
        ////////////////////////////////////////////////////////////////////////////////////////

        ////////////////////////////////////////////////////////////////////////////////////////
        // 프로그램 상세 정보 조회 (API 사용해서 가져오는 영역), Program.java 파일을 API 에 맞게 수정해서 사용
        // 프로그램 상세 조회 실패 시 어떤 처리를 할 지도...
        Program programInfo = new Program();
        ////////////////////////////////////////////////////////////////////////////////////////

        // 좋아요 수, 좋아요 여부
        Like like = new Like();
        like.setUno(uno);
        like.setTno(pno);
        like = likeDao.getProgramLikeInfo(like);
        programInfo.setLike_num(like.getLike_num());
        programInfo.setPress_like(like.isPress_like());
        // 댓글 수

        // 댓글 1개

        // 프로그램 상세정보를 포함한 응답 객체 반환
        result.status = true;
        result.msg = "success";
        result.data = programInfo;
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}