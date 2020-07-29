package com.web.curation.controller.episode;

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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.web.curation.dao.episode.EpisodeDao;
import com.web.curation.dao.following.FollowingDao;
import com.web.curation.model.BasicResponse;
import com.web.curation.model.episode.EpisodeResponse;
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
public class EpisodeController {

    @Autowired
    EpisodeDao dao;
    
    @Autowired
    FollowingDao followdao;

    static String BASE_URL = "https://api.themoviedb.org/3/";
    static String IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500";
    static String API_KEY = "1436d388221956af7b6cd27a6a7ec9d8";

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
        List<Program> programList = new ArrayList<Program>();

        // 각 프로그램에 대한 객체 만들어서 기본 정보 몇 개만 세팅한 다음 programList에 삽입
        for (int i=1; i<=programs.length(); i++){
            Program p = new Program();
            JSONObject programJson = programs.optJSONObject(i-1);
            int id = programJson.optInt("id");
            String name = programJson.optString("name");
            int season = 1;
            String thumbnail = programJson.optString("backdrop_path");
            p.setPno(id);
            p.setPname(name);
            p.setSeason(season);
            if (thumbnail != null && thumbnail.length() > 1) p.setThumbnail(IMAGE_BASE_URL + thumbnail);
            programList.add(p);
        }

        List<EpisodeResponse> episodeList = new ArrayList<EpisodeResponse>();
        // 팔로우중인 프로그램 각각에 대하여 에피소드 목록 조회 (TMDB API)
        for (Program program: programList){
            int pno = program.getPno();
            int season = program.getSeason();

            // 프로그램 ID와 시즌 번호로 API 요청
            re = restTemplate.getForEntity(BASE_URL + "tv/" + Integer.toString(pno) + "/season/" + season + "?api_key=" + API_KEY + "&language=ko", String.class);
            JSONObject programInfo = new JSONObject(re.getBody());
            JSONArray episodes = programInfo.optJSONArray("episodes");

            re = restTemplate.getForEntity(BASE_URL + "tv/" + Integer.toString(pno) + "/season/" + season + "?api_key=" + API_KEY, String.class);
            JSONObject programInfo_eng = new JSONObject(re.getBody());
            JSONArray episodes_eng = programInfo_eng.optJSONArray("episodes");

            program.setSeason_name(programInfo.optString("name"));

            // 결과 데이터 중 episodes Array의 각 요소에 접근하여 episode 정보 추출
            for (int i=1; i<=episodes.length(); i++){
                JSONObject detail = episodes.optJSONObject(i-1);
                String overview_eng = episodes_eng.optJSONObject(i-1).optString("overview");
                EpisodeResponse e = episodeSetter(program, i, detail, overview_eng); // 'detail' 을 파싱하여 Episode 객체 세팅해서 리턴해주는 메서드
                
                if (e.getBroadcast_date() != null)
                    episodeList.add(e);
                //System.out.println(e.getBroadcast_date());
            }
        }

        // 추출한 episode들을 최신순 정렬
        Collections.sort(episodeList, new Comparator<EpisodeResponse>() {
			public int compare(EpisodeResponse o1, EpisodeResponse o2) {
                LocalDate t2 = o1.getBroadcast_date();
                LocalDate t1 = o2.getBroadcast_date();
                //if (t1 == null || t2 == null)
                //    return 0;
                //else
                    return t1.compareTo(t2);
			}
        });
        
        // 에피소드 목록을 포함한 응답 객체 반환
        result.status = true;
        result.msg = "success";
        result.data = episodeList;
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // Episode JSON 파싱
    private EpisodeResponse episodeSetter(Program program, int epno, JSONObject detail, String overview_eng){
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

        EpisodeResponse e = new EpisodeResponse();

        if (episode_air_date != null) {
            String[] episode_start_date = episode_air_date.split("-");
            if (episode_start_date.length == 3){
                LocalDate start_date = LocalDate.of(Integer.parseInt(episode_start_date[0]), 
                                                                    Integer.parseInt(episode_start_date[1]), 
                                                                    Integer.parseInt(episode_start_date[2]));
                e.setBroadcast_date(start_date);
            }
        }

        int pno = program.getPno(); 
        int sno = program.getSeason();

        // 한글로 된 설명이 없는 경우: 영어로 된 설명 받아오기 위해 요청을 한번 더 보낸다...
        if (summary == null || summary.length() <= 1){
            if (overview_eng == null || overview_eng.length() <= 1)
                summary = "에피소드에 대한 설명이 없습니다.";
            else
                summary = overview_eng;
        }
        
        e.setPno(pno);
        e.setPname(program.getPname());
        e.setSeason(sno);
        e.setSeason_name(program.getSeason_name());

        e.setEpisode(epno);
        e.setSummary(summary);
        e.setCrew(crew_arr);
        e.setGuest(guest_arr);

        if (thumbnail != null && thumbnail.length() > 1)
            e.setThumbnail(IMAGE_BASE_URL + thumbnail);
        else if (program.getThumbnail() != null)
            e.setThumbnail(program.getThumbnail());

        return e;
    }

    @GetMapping("/episode/{pno}/{season}/{epno}")
    @ApiOperation(value = "에피소드 상세정보 조회")
    public Object getEpisodeDetailFromAPI(@PathVariable("pno") int pno, @PathVariable("season") int season,
                                                                        @PathVariable("epno") int epno) {
        // final BasicResponse result = new BasicResponse();
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

        Program program = new Program();
        program.setPno(program_id);
        program.setPname(program_name);
        program.setSeason(season_num);
        program.setSeason_name(season_name);

        JSONObject detail = episodes.optJSONObject(epno-1); // episodes 배열 중에서 찾는 에피소드 번호에 해당하는 부분만 있음 된다.
        EpisodeResponse e = episodeSetter(program, epno, detail, " "); // 그 부분 찾았으면 episodeSetter 똑같이 써서 에피소드 추출

        return e;
    }
    

}