package com.web.curation.controller.episode;

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
public class EpisodeController {

    @Autowired
    EpisodeDao episodeDao;
    
    @Autowired
    FollowingDao followdao;

    static String BASE_URL = "https://api.themoviedb.org/3/";
    static String IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500";
    static String API_KEY = "1436d388221956af7b6cd27a6a7ec9d8";

    @PutMapping("/episode/increase/share")
    @ApiOperation(value = "에피소드 공유 수 증가")
    public Object increaseShare(@RequestBody EpisodeDB req) {
        // 반환할 응답 객체
        final BasicResponse result = new BasicResponse();

        // 공유 수 증가
        int n = episodeDao.increaseShare(req.getEno());

        // n 이 1 이 아니면 쿼리 수행 결과에 이상이 있는 것
        if(n != 1) {
            result.status = false;
            result.msg = "Update 쿼리 수행 결과에 이상이 발생했습니다.(" + n + ")";
            return new ResponseEntity<>(result, HttpStatus.OK);
        }

        // 공유 수 증가 완료
        result.status = true;
        result.msg = "success";
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    /* 이 아래로 TMDB API 이용하는 파트 */
    @GetMapping("/episode/following")
    @ApiOperation(value = "팔로우중인 프로그램의 에피소드 목록을 최신순으로 조회")
    public Object getEpisodeListFromAPI(HttpServletRequest req) {
        // 반환할 응답 객체
        final BasicResponse result = new BasicResponse();
        RestTemplate restTemplate = new RestTemplate();

        // 토큰에서 유저 번호를 뽑아 써야함
        Object obj = req.getAttribute("User");
        if(obj == null) {
            result.status = false;
            result.msg = "토큰에서 유저정보가 추출되지 않았습니다.";
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        int uno = ((User) obj).getUno();

        ResponseEntity<String> re = null;

        // 팔로우중인 프로그램 리스트 조회
        List<Program> programList = followdao.getProgramFollowings(uno);
        for (Program program : programList) {
            re = restTemplate.getForEntity(BASE_URL + "tv/" + Integer.toString(program.getPno()) + "?api_key=" + API_KEY + "&language=ko", String.class);
            JSONObject programInfo = new JSONObject(re.getBody());
            System.out.println(programInfo);
            program.setPname(programInfo.optString("name"));
            program.setSeason(programInfo.optInt("number_of_seasons"));
        }

        // // Step 1: 아직 팔로우 데이터가 없으므로 일단 임시로 프로그램 리스트도 TMDB에서 추천해주는거 들고오는 걸로
        // ResponseEntity<String> re = null;
        // JSONArray programs = null;
        // List<Program> programList = new ArrayList<Program>();
        // try {
        //     re = restTemplate.getForEntity(BASE_URL + "discover/tv?first_air_date_year=2020&sort_by=popularity.desc&api_key=" + API_KEY + "&language=ko",
        //                                     String.class);
        //     JSONObject recommended_program = new JSONObject(re.getBody());
        //     programs = recommended_program.optJSONArray("results");
        // } catch (Exception e) {
        //     result.status = false;
        //     result.msg = "Step 1 - 예외 발생 : " + e.getMessage();
        //     return new ResponseEntity<>(result, HttpStatus.OK);
        // }


        // // Step 2: 각 프로그램에 대한 객체 만들어서 기본 정보 몇 개만 세팅한 다음 programList에 삽입
        // try {
        //     for (int i=1; i<=programs.length(); i++){
        //         Program p = new Program();
        //         JSONObject programJson = programs.optJSONObject(i-1);
        //         int id = programJson.optInt("id");
        //         String name = programJson.optString("name");
        //         int season = 1;
        //         String thumbnail = programJson.optString("backdrop_path");
        //         p.setPno(id);
        //         p.setPname(name);
        //         p.setSeason(season);
        //         if (thumbnail != null && thumbnail.length() > 1) p.setThumbnail(IMAGE_BASE_URL + thumbnail);
        //         programList.add(p);
        //     }
        // } catch (Exception e) {
        //     result.status = false;
        //     result.msg = "Step 2 - 예외 발생 : " + e.getMessage();
        //     return new ResponseEntity<>(result, HttpStatus.OK);
        // }

        List<EpisodeResponse> episodeList = new ArrayList<EpisodeResponse>();

        // Step 3: 팔로우중인 프로그램 각각에 대하여 에피소드 목록 조회 (TMDB API)
        try {
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
                int len = episodes.length();
                if(len > episodes_eng.length()) {
                    System.out.println("episodes 길이와 episodes_eng 길이가 달라요.  " + len + " :: " + episodes_eng.length());
                    len = episodes_eng.length();
                }
                for (int i=1; i<=len; i++){
                    JSONObject detail = episodes.optJSONObject(i-1);
                    String overview_eng = episodes_eng.optJSONObject(i-1).optString("overview");
                    EpisodeResponse e = episodeSetter(program, i, detail, overview_eng); // 'detail' 을 파싱하여 Episode 객체 세팅해서 리턴해주는 메서드
                    
                    if (e.getBroadcast_date() != null)
                        episodeList.add(e);
                    //System.out.println(e.getBroadcast_date());
                }
            }
        } catch (Exception e) {
            result.status = false;
            result.msg = "Step 3 - 예외 발생 : " + e.getMessage();
            return new ResponseEntity<>(result, HttpStatus.OK);
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

        ///
        // 가져오는 Episode 수 만큼 DB 쿼리 최대 3번 최소 2번 요청...
        ///
        /////////////////////////////////////////////////////////////////////////////////////////////////// 정상작동할까요?
        // 각 에피소드들에 대해 DB 에 존재하는지 확인, 존재하지 않으면 DB 에 추가
        // + 추가정보(좋아요 수, 댓글 수 등) 구하기
        for(EpisodeResponse episode : episodeList) {
            // DB 에 존재하는지 확인
            int n = episodeDao.checkDataExist(episode);
            
            // DB 에 존재하지 않으면 추가
            if(n == 0) {
                episodeDao.addNewEpisode(episode);
            }
            
            // 각 에피소드 정보에 유저 번호 추가하기
            episode.setUno(uno);

            // 좋아요 수, 댓글 수 등 정보 구하기
            episode.setAdditionalData((episodeDao.getLikeReplyInfo(episode)).get(0));
        }
        //////////////////////////////////////////////////////////////////////////////////////////////////////
        // Collections.sort(episodeList, new Comparator<Episode2>() {
		// 	public int compare(Episode2 o1, Episode2 o2) {
        //         LocalDate t2 = o1.getBroadcast_date();
        //         LocalDate t1 = o2.getBroadcast_date();
        //         if (t1 != null && t2 != null)
        //             return t1.compareTo(t2);
        //         else
        //             return 0;
		// 	}
        // });
        
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

    // 아래 찜한 에피소드 목록 가져오기에서도 쓰기위해 에피소드 상세정보 조회 함수 따로 뺐어요
    private EpisodeResponse getEpisodeDetailFunc(int pno, int season, int epno) {
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

    @GetMapping("/episode/{pno}/{season}/{epno}")
    @ApiOperation(value = "에피소드 상세정보 조회")
    public Object getEpisodeDetailFromAPI(@PathVariable("pno") int pno, @PathVariable("season") int season,
                                                                        @PathVariable("epno") int epno, HttpServletRequest req) {
        final BasicResponse result = new BasicResponse();
        
        EpisodeResponse e = getEpisodeDetailFunc(pno, season, epno);

        /////////////////////////////////////////////////////////////////////////////////////////////////// 정상작동할까요?
        // 에피소드에 대해 추가정보(좋아요 수, 댓글 수 등) 구하기
        // 각 에피소드 정보에 유저 번호 추가하기
        int uno = ((User) req.getAttribute("User")).getUno();

        e.setUno(uno);

        // 좋아요 수, 댓글 수 등 정보 구하기(있다면)
        // DB 에 존재하는지 확인
        int n = episodeDao.checkDataExist(e);
            
        // DB 에 존재하지 않으면 추가
        if(n == 0) {
            episodeDao.addNewEpisode(e);
        }

        // 좋아요 수, 댓글 수 등 정보 구하기
        e.setAdditionalData((episodeDao.getLikeReplyInfo(e)).get(0));
        //////////////////////////////////////////////////////////////////////////////////////////////////////

        // 에피소드 를 포함한 응답 객체 반환
        result.status = true;
        result.msg = "success";
        result.data = e;
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    
    @PostMapping("/episode/dibs/list")
    @ApiOperation(value = "찜한 에피소드 목록 조회")
    Object getEpisodeDibsList(@RequestBody EpisodeResponse req, HttpServletRequest httpReq) {
        // 반환할 응답 객체
        final BasicResponse result = new BasicResponse();

        // 찜한 에피소드 조회를 위한 유저 번호와 요청 수 정보 가져오기
        int uno = ((User) httpReq.getAttribute("User")).getUno();
        req.setUno(uno);
        req.setNum((req.getNum() - 1) * 20);

        // 찜한 에피소드 목록 조회
        List<EpisodeDB> list = episodeDao.getLikeReplyInfo(req);

        // list 있는 내용을 토대로 EpisodeResponseList 만들기
        List<EpisodeResponse> response = new ArrayList<>();

        // 찜한 에피소드들에 대해 반복
        for(EpisodeDB ep : list) {
            // 찜한 에피소드의 프로그램 번호, 시즌 번호, 에피소드 번호 빼기
            int pno = ep.getPno();
            int season = ep.getSeason();
            int epno = ep.getEpisode();

            // 그 정보들로 API 요청을 통해 에피소드 상세정보 가져오기
            EpisodeResponse epiInfo = getEpisodeDetailFunc(pno, season, epno);

            // 에피소드 상세정보에 댓글, 좋아요 등의 정보 붙이기
            epiInfo.setAdditionalData(ep);

            // 반환할 리스트에 추가
            response.add(epiInfo);
        }

        // 완료
        result.status = true;
        result.msg = "success";
        result.data = response;
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}