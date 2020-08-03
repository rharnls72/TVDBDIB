package com.web.curation.controller.dibs;

import javax.servlet.http.HttpServletRequest;

import com.web.curation.dao.dibs.DibsDao;
import com.web.curation.model.BasicResponse;
import com.web.curation.model.dibs.Dibs;
import com.web.curation.model.user.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
public class DibsController {
    
    @Autowired
    DibsDao dao;

    // Create
    @PostMapping("/dibs/feed/create")
    @ApiOperation(value = "피드 찜 추가")
    public Object createFeedDibs(@RequestBody Dibs dibs, HttpServletRequest httpReq) {
        // 반환할 응답 객체
        final BasicResponse result = new BasicResponse();

        // 로그인한 유저 정보 가져오기
        dibs.setUno(((User) httpReq.getAttribute("User")).getUno());

        // 피드 찜 추가
        int n = dao.createFeedDibs(dibs);

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

    @PostMapping("/dibs/episode/create")
    @ApiOperation(value = "Episode 찜 추가")
    public Object createEpisodeDibs(@RequestBody Dibs dibs, HttpServletRequest httpReq) {
        // 반환할 응답 객체
        final BasicResponse result = new BasicResponse();

        // 로그인한 유저 정보 가져오기
        dibs.setUno(((User) httpReq.getAttribute("User")).getUno());

        // 피드 찜 추가
        int n = dao.createEpisodeDibs(dibs);

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

    // Delete
    @DeleteMapping("/dibs/feed/delete/{fno}")
    @ApiOperation(value = "피드 찜 취소")
    public Object deleteFeedDibs(@PathVariable("fno") int fno, HttpServletRequest httpReq) {
        // 반환할 응답 객체
        final BasicResponse result = new BasicResponse();

        Dibs dibs = new Dibs();
        dibs.setTno(fno);

        // 로그인한 유저 정보 가져오기
        dibs.setUno(((User) httpReq.getAttribute("User")).getUno());

        // 피드 찜 취소
        int n = dao.deleteFeedDibs(dibs);

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

    @DeleteMapping("/dibs/episode/delete/{eno}")
    @ApiOperation(value = "Episode 찜 취소")
    public Object deleteEpisodeDibs(@PathVariable("eno") int eno, HttpServletRequest httpReq) {
        // 반환할 응답 객체
        final BasicResponse result = new BasicResponse();

        Dibs dibs = new Dibs();
        dibs.setTno(eno);

        // 로그인한 유저 정보 가져오기
        dibs.setUno(((User) httpReq.getAttribute("User")).getUno());

        // 피드 찜 취소
        int n = dao.deleteEpisodeDibs(dibs);

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