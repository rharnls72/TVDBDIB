package com.web.curation.controller.episode;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.web.curation.dao.episode.EpisodeDao;
import com.web.curation.dao.following.FollowingDao;
import com.web.curation.model.BasicResponse;
import com.web.curation.model.episode.Episode;
import com.web.curation.model.program.Program;

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
public class EpisodeController {

    @Autowired
    EpisodeDao dao;
    
    @Autowired
    FollowingDao followdao;

    // Create
    @PostMapping("/episode/create")
    @ApiOperation(value = "에피소드 정보 생성")
    public Object createNewEpisode(@RequestBody Episode episode) {
        // 반환할 응답 객체
        final BasicResponse result = new BasicResponse();

        // 에피소드 정보 추가
        int n = dao.addNewEpisode(episode);

        // n 이 1 이 아니면 쿼리 수행 결과에 이상이 있는 것
        if(n != 1) {
            result.status = false;
            result.msg = "Insert 쿼리 수행 결과에 이상이 발생했습니다.(" + n + ")";
            return new ResponseEntity<>(result, HttpStatus.OK);
        }

        // 에피소드 정보 추가 완료
        result.status = true;
        result.msg = "success";
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // Read
    @PostMapping("/episode/alllist")
    @ApiOperation(value = "모든 에피소드 목록 조회 - 메인페이지")
    public Object getAlltEpisodeList(@RequestBody Map<String, Object> req) {
        int uno = (int) req.get("uno");
        int num = (((int) req.get("num"))-1)*20;
        
        // 반환할 응답 객체
        final BasicResponse result = new BasicResponse();

        // 에피소드 목록 조회
        List<Episode> list = dao.getAllEpisodeList(uno, num);

        // 에피소드 목록을 포함한 응답 객체 반환
        result.status = true;
        result.msg = "success";
        result.data = list;
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    @GetMapping("/episode/list/{pno}")
    @ApiOperation(value = "프로그램의 에피소드 목록 조회")
    public Object getEpisodeList(@PathVariable("pno") int pno) {
        // 반환할 응답 객체
        final BasicResponse result = new BasicResponse();

        // 에피소드 목록 조회
        List<Episode> list = dao.getEpisodeList(pno);

        // 에피소드 목록을 포함한 응답 객체 반환
        result.status = true;
        result.msg = "success";
        result.data = list;
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/episode/detail/{eno}")
    @ApiOperation(value = "에피소드 상세 정보 조회")
    public Object getEpisodeDetail(@PathVariable("eno") int eno) {
        // 반환할 응답 객체
        final BasicResponse result = new BasicResponse();

        // 에피소드 상세 정보 조회
        Episode episode = dao.getEpisodeDetail(eno);

        // episode 이 null 이면 문제 발생
        if(episode == null) {
            result.status = false;
            result.msg = "에피소드 상세 정보 조회에 실패했습니다.(" + eno + ")";
            return new ResponseEntity<>(result, HttpStatus.OK);
        }

        // 에피소드 상세정보를 포함한 응답 객체 반환
        result.status = true;
        result.msg = "success";
        result.data = episode;
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // Update
    @PutMapping("/episode/update")
    @ApiOperation(value = "에피소드 정보 수정")
    public Object modifyEpisode(@RequestBody Episode episode) {
        // 반환할 응답 객체
        final BasicResponse result = new BasicResponse();

        // 에피소드 정보 수정
        int n = dao.modifyEpisode(episode);

        // n 이 1 이 아니면 쿼리 수행 결과에 이상이 있는 것
        if(n != 1) {
            result.status = false;
            result.msg = "Update 쿼리 수행 결과에 이상이 발생했습니다.(" + n + ")";
            return new ResponseEntity<>(result, HttpStatus.OK);
        }

        // 에피소드 정보 수정 완료
        result.status = true;
        result.msg = "success";
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // Delete
    @DeleteMapping("/episode/delete/{eno}")
    @ApiOperation(value = "에피소드 정보 삭제")
    public Object deleteProgram(@PathVariable int eno) {
        // 반환할 응답 객체
        final BasicResponse result = new BasicResponse();

        // 에피소드 정보 삭제
        int n = dao.deleteEpisode(eno);

        // n 이 1 이 아니면 쿼리 수행 결과에 이상이 있는 것
        if(n != 1) {
            result.status = false;
            result.msg = "Delete 쿼리 수행 결과에 이상이 발생했습니다.(" + n + ")";
            return new ResponseEntity<>(result, HttpStatus.OK);
        }

        // 에피소드 정보 삭제 완료
        result.status = true;
        result.msg = "success";
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}