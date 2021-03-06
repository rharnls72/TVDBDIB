package com.web.curation.controller.program;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

import com.web.curation.dao.program.ProgramDao;
import com.web.curation.model.BasicResponse;
import com.web.curation.model.program.Program;
import com.web.curation.model.program.ProgramRequest;
import com.web.curation.model.user.User;

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
    private ProgramDao dao;

    static String BASE_URL = "https://api.themoviedb.org/3/";
    static String IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500";
    static String API_KEY = "1436d388221956af7b6cd27a6a7ec9d8";

    @GetMapping("/program/detail/{pno}")
    @ApiOperation(value = "프로그램 상세 정보 조회")
    public Object getProgramDetail(@PathVariable("pno") int pno, HttpServletRequest req) {
        // 반환할 응답 객체
        final BasicResponse result = new BasicResponse();

        ////////////////////////////////////////////////////////////////////////////////////////
        // 로그인 한 유저 정보 (유저 번호만) 가져오기
        int uno = ((User) req.getAttribute("User")).getUno();
        ////////////////////////////////////////////////////////////////////////////////////////

        ////////////////////////////////////////////////////////////////////////////////////////
        // 프로그램 상세 정보 조회 (API 사용해서 가져오는 영역), Program.java 파일을 API 에 맞게 수정해서 사용
        // 프로그램 상세 조회 실패 시 어떤 처리를 할 지도...
        Program programInfo = new Program();

        RestTemplate restTemplate = new RestTemplate();
        // 일단 프로그램 (시즌 말고 그보다 더 상위인 프로그램) 정보가 필요하다. 프로그램 이름은 띄워줘야 하잖아...
        ResponseEntity<String> re = restTemplate.getForEntity(BASE_URL + "tv/" + Integer.toString(pno) + "?api_key=" + API_KEY + "&language=ko", String.class);
        programInfo.setProgramDetail(re.getBody());

        // 추가 정보 더 받기
        re = restTemplate.getForEntity(BASE_URL + "tv/" + Integer.toString(pno) + "/episode_groups" + "?api_key=" + API_KEY + "&language=ko", String.class);
        programInfo.setEpisodeGroup(re.getBody());
        ////////////////////////////////////////////////////////////////////////////////////////

        re = restTemplate.getForEntity(BASE_URL + "tv/" + Integer.toString(pno) + "/similar" + "?api_key=" + API_KEY + "&language=ko", String.class);
        programInfo.setSimilar(re.getBody());
        // JSONObject program = new JSONObject(re.getBody());
        // JSONArray episodes = program.optJSONArray("results");
        // programInfo.setSimilar(episodes);

        // DB 조회 전 ProgramRequest 설정하기
        ProgramRequest programReq = new ProgramRequest();
        programReq.setPno(pno);
        programReq.setUno(uno);

        // 좋아요 수, 좋아요 여부, 댓글 수, 댓글 1개 가져오기
        Program res = dao.getLikeReplyInfo(programReq);

        // 프로그램 상세정보에 좋아요 수, 좋아요 여부, 댓글 수, 댓글 1개 정보 붙이기
        programInfo.setLikeReplyInfo(res);

        // 프로그램 상세정보를 포함한 응답 객체 반환
        result.status = true;
        result.msg = "success";
        result.data = programInfo;
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}