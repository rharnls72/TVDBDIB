package com.web.curation.controller.recommend;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.web.curation.dao.episode.EpisodeDao;
import com.web.curation.dao.following.FollowingDao;
import com.web.curation.model.BasicResponse;
import com.web.curation.model.episode.EpisodeDB;
import com.web.curation.model.episode.EpisodeResponse;
import com.web.curation.model.program.Program;
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
public class RecommendController {

    static String BASE_URL = "https://api.themoviedb.org/3/";
    static String IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500";
    static String API_KEY = "1436d388221956af7b6cd27a6a7ec9d8";

    @GetMapping("/recommend/trending")
    @ApiOperation(value = "TMDB API로부터 요즘 뜨는 (trending) 프로그램 받아오기")
    public Object getTrendingPrograms() {
        // 반환할 응답 객체
        final BasicResponse result = new BasicResponse();
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<String> re = 
        restTemplate.getForEntity(BASE_URL + "trending/tv/day?api_key=" + API_KEY, String.class);
        JSONObject recommended_program = new JSONObject(re.getBody());
        JSONArray programs = recommended_program.optJSONArray("results");
        List<Program> programList = new ArrayList<Program>();

        /*
        지금은 그냥 API에서 trending 가져와서 바로 던졌지만
        테스트 데이터 들어가면 tvdbdib 좋아요 수 등 반영해서 점수 매길 예정
        */

        // id, name, poster_path만 삽입
        for (int i=1; i<=programs.length(); i++){
            Program p = new Program();
            JSONObject programJson = programs.optJSONObject(i-1);
            int id = programJson.optInt("id");
            String name = programJson.optString("name");
            String thumbnail = programJson.optString("poster_path");
            p.setPno(id);
            p.setPname(name);
            if (thumbnail != null && thumbnail.length() > 1) p.setThumbnail(IMAGE_BASE_URL + thumbnail);
            programList.add(p);
        }

        result.status = true;
        result.msg = "success";
        result.data = programList;
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/recommend/new")
    @ApiOperation(value = "TMDB API로부터 새로 나온 프로그램 받아오기")
    public Object getNewPrograms() {
        // 반환할 응답 객체
        final BasicResponse result = new BasicResponse();
        RestTemplate restTemplate = new RestTemplate();

        LocalDate currentDay = LocalDate.now();
        LocalDate fromDay = currentDay.minusDays(7);

        String curDayStr = currentDay.toString();
        String fromDayStr = fromDay.toString();

        ResponseEntity<String> re = 
        restTemplate.getForEntity(BASE_URL + "discover/tv?first_air_date.gte=" + fromDayStr + "&first_air_date.lte=" + curDayStr + "&api_key=" + API_KEY + "&popularity.desc", String.class);
        JSONObject recommended_program = new JSONObject(re.getBody());
        JSONArray programs = recommended_program.optJSONArray("results");
        List<Program> programList = new ArrayList<Program>();

        // id, name, poster_path만 삽입
        for (int i=1; i<=programs.length(); i++){
            Program p = new Program();
            JSONObject programJson = programs.optJSONObject(i-1);
            int id = programJson.optInt("id");
            String name = programJson.optString("name");
            String thumbnail = programJson.optString("poster_path");
            p.setPno(id);
            p.setPname(name);
            if (thumbnail != null && thumbnail.length() > 1) p.setThumbnail(IMAGE_BASE_URL + thumbnail);
            programList.add(p);
        }

        result.status = true;
        result.msg = "success";
        result.data = programList;
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


}