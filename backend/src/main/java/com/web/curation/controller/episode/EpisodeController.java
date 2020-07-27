package com.web.curation.controller.episode;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.web.curation.dao.episode.EpisodeDao;
import com.web.curation.dao.following.FollowingDao;
import com.web.curation.model.BasicResponse;
import com.web.curation.model.episode.Episode;
import com.web.curation.model.episode.Episode2;
import com.web.curation.model.program.Program;
import com.web.curation.model.program.Program2;

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

    static String BASE_URL = "https://api.themoviedb.org/3/";
    static String IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500";
    static String API_KEY = "1436d388221956af7b6cd27a6a7ec9d8";

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

    /* 이 아래로 TMDB API 이용하는 파트 */
    @GetMapping("/episode/following/{uno}")
    @ApiOperation(value = "팔로우중인 프로그램의 에피소드 목록을 최신순으로 조회")
    public Object getEpisodeListFromAPI(@PathVariable("uno") int uno) {
        // 반환할 응답 객체
        final BasicResponse result = new BasicResponse();

        RestTemplate restTemplate = new RestTemplate();

        // 팔로우중인 프로그램 리스트 조회
        //List<Program> programList = followdao.getProgramFollowings(uno);

        // 아직 팔로우 데이터가 없으므로 일단 임시로 프로그램 리스트도 TMDB에서 추천해주는거 들고오는 걸로
        ResponseEntity<String> re = 
        restTemplate.getForEntity(BASE_URL + "discover/tv?first_air_date_year=2020&sort_by=popularity.desc&api_key=" + API_KEY + "&language=ko",
        String.class);
        JSONObject recommended_program = new JSONObject(re.getBody());
        JSONArray programs = recommended_program.optJSONArray("results");
        List<Program2> programList = new ArrayList<Program2>();

        // 각 프로그램에 대한 객체 만들어서 기본 정보 몇 개만 세팅한 다음 programList에 삽입
        for (int i=1; i<=programs.length(); i++){
            Program2 p = new Program2();
            JSONObject programJson = programs.optJSONObject(i-1);
            int id = programJson.optInt("id");
            String name = programJson.optString("name");
            int season = 1;
            p.setPno(id);
            p.setPname(name);
            p.setSeason(season);
            programList.add(p);
        }

        List<Episode2> episodeList = new ArrayList<Episode2>();
        // 팔로우중인 프로그램 각각에 대하여 에피소드 목록 조회 (TMDB API)
        for (Program2 program: programList){
            int pno = program.getPno();
            int season = program.getSeason();

            // 프로그램 ID와 시즌 번호로 API 요청
            re = restTemplate.getForEntity(BASE_URL + "tv/" + Integer.toString(pno) + "/season/" + season + "?api_key=" + API_KEY + "&language=ko", String.class);
            JSONObject programInfo = new JSONObject(re.getBody());
            JSONArray episodes = programInfo.optJSONArray("episodes");

            program.setSeason_name(programInfo.optString("name"));

            // 결과 데이터 중 episodes Array의 각 요소에 접근하여 episode 정보 추출
            for (int i=1; i<=episodes.length(); i++){
                JSONObject detail = episodes.optJSONObject(i-1);
                Episode2 e = episodeSetter(program, i, detail); // 'detail' 을 파싱하여 Episode 객체 세팅해서 리턴해주는 메서드
                episodeList.add(e);
            }
        }

        // 추출한 episode들을 최신순 정렬
        Collections.sort(episodeList, new Comparator<Episode2>() {
			public int compare(Episode2 o1, Episode2 o2) {
                LocalDate t2 = o1.getBroadcast_date();
                LocalDate t1 = o2.getBroadcast_date();
                if (t1 != null && t2 != null)
                    return t1.compareTo(t2);
                else
                    return 0;
			}
        });
        
        // 에피소드 목록을 포함한 응답 객체 반환
        result.status = true;
        result.msg = "success";
        result.data = episodeList;
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // Episode JSON 파싱
    private Episode2 episodeSetter(Program2 program, int epno, JSONObject detail){
        String summary = detail.optString("overview");
        JSONArray crews = detail.optJSONArray("crew");
        ArrayList<String> crew_arr = new ArrayList<String>();
        if (crews != null && crews.length() >= 1){
            for (int i=0; i<crews.length(); i++){
                String crew = crews.optJSONObject(i).optString("name");
                crew_arr.add(crew);
            }
        }

        JSONArray guests = detail.optJSONArray("guest_stars");
        ArrayList<String> guest_arr = new ArrayList<String>();
        if (guests != null && guests.length() >= 1){
            for (int i=0; i<guests.length(); i++){
                String guest = guests.optJSONObject(i).optString("name");
                guest_arr.add(guest);
            }
        }

        String thumbnail = detail.optString("still_path");
        String episode_air_date = detail.optString("air_date");

        Episode2 e = new Episode2();

        if (episode_air_date != null) {
            String[] episode_start_date = episode_air_date.split("-");
            if (episode_start_date.length >= 3){
                LocalDate start_date = LocalDate.of(Integer.parseInt(episode_start_date[0]), 
                                                                    Integer.parseInt(episode_start_date[1]), 
                                                                    Integer.parseInt(episode_start_date[2]));
                e.setBroadcast_date(start_date);
            }
        }
        
        e.setPno(program.getPno());
        e.setPname(program.getPname());
        e.setSeason(program.getSeason());
        e.setSeason_name(program.getSeason_name());

        e.setEpisode(epno);
        e.setSummary(summary);
        e.setCrew(crew_arr);
        e.setGuest(guest_arr);
        if (thumbnail != null) e.setThumbnail(IMAGE_BASE_URL + thumbnail);

        return e;
    }

    @GetMapping("/episode/{pno}/{season}/{epno}")
    @ApiOperation(value = "에피소드 상세정보 조회")
    public Object getEpisodeDetailFromAPI(@PathVariable("pno") int pno, @PathVariable("season") int season,
                                                                        @PathVariable("epno") int epno) {
        final BasicResponse result = new BasicResponse();
        RestTemplate restTemplate = new RestTemplate();
                                                                            
        // 일단 프로그램 (시즌 말고 그보다 더 상위인 프로그램) 정보가 필요하다. 프로그램 이름은 띄워줘야 하잖아...
        ResponseEntity<String> re = restTemplate.getForEntity(BASE_URL + "tv/" + Integer.toString(pno) + "?api_key=" + API_KEY + "&language=ko", String.class);
        JSONObject programInfo = new JSONObject(re.getBody());
        int program_id = programInfo.optInt("id");
        String program_name = programInfo.optString("name");

        // 시즌 정보 요청. 시즌 정보에 에피소드 정보들도 들어있다
        re = restTemplate.getForEntity(BASE_URL + "tv/" + Integer.toString(pno) + "/season/" + season + "?api_key=" + API_KEY + "&language=ko", String.class);
        JSONObject seasonInfo = new JSONObject(re.getBody());
        JSONArray episodes = seasonInfo.optJSONArray("episodes");
        
        int season_num = seasonInfo.optInt("season_number");
        String season_name = seasonInfo.optString("name");

        Program2 program = new Program2();
        program.setPno(program_id);
        program.setPname(program_name);
        program.setSeason(season_num);
        program.setSeason_name(season_name);

        JSONObject detail = episodes.optJSONObject(epno-1); // episodes 배열 중에서 찾는 에피소드 번호에 해당하는 부분만 있음 된다.
        Episode2 e = episodeSetter(program, epno, detail); // 그 부분 찾았으면 episodeSetter 똑같이 써서 에피소드 추출

        return e;
    }
    

}