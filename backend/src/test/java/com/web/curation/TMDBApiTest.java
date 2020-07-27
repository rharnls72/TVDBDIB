/*
package com.web.curation;

import java.time.LocalDateTime;

import com.web.curation.dao.episode.EpisodeDao;
import com.web.curation.dao.program.ProgramDao;
import com.web.curation.model.episode.Episode;
import com.web.curation.model.program.Program;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
class ProgramSaveTest3 {
    @Autowired
    ProgramDao pdao;
    @Autowired
    EpisodeDao eDao;

    public void saveProgram(Program p) {
        pdao.addNewProgram(p);
    }

    public void saveEpisode(Episode e){
        eDao.addNewEpisode(e);
    }
}

@SpringBootApplication
public class TMDBApiTest {

    static String BASE_URL = "https://api.themoviedb.org/3/";
    static String IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500";
    static String API_KEY = "1436d388221956af7b6cd27a6a7ec9d8";
    static ProgramSaveTest3 tester;

    public static void main(String[] args) {
        RestTemplate restTemplate = new RestTemplate();

        ApplicationContext applicationContext = SpringApplication.run(TMDBApiTest.class, args);
        tester = applicationContext.getBean(ProgramSaveTest3.class);

        // 인기 TV 프로그램의 리스트를 가져옴
        // 조건 : 2020년에 첫 방영된 & 인기 내림차순으로 정렬
        ResponseEntity<String> re = 
        restTemplate.getForEntity(BASE_URL + "discover/tv?first_air_date_year=2020&language=kr&sort_by=popularity.desc&api_key=" + API_KEY, 
        String.class);

        JSONObject json = new JSONObject(re.getBody());
        JSONArray result_arr = json.optJSONArray("results");

        // 각 프로그램마다 detail 출력 및 에피소드 출력으로 들어간다
        for (int i=0; i<result_arr.length(); i++){
            System.out.println(result_arr.optJSONObject(i).optString("original_name"));
            int program_id = result_arr.optJSONObject(i).getInt("id");
            program_detail(program_id);
        }
    }

    public static void program_detail(int id){
        RestTemplate restTemplate = new RestTemplate();
        // 각 프로그램의 상세정보를 가져옴
        ResponseEntity<String> re = 
        restTemplate.getForEntity(BASE_URL + "tv/" + Integer.toString(id) + "?api_key=" + API_KEY, String.class);
        JSONObject detail = new JSONObject(re.getBody());

        // cast 정보 가져옴
        re = restTemplate.getForEntity(BASE_URL + "tv/" + Integer.toString(id) + "/credits?api_key=" + API_KEY, String.class);
        JSONObject cast_persons = new JSONObject(re.getBody());
        JSONArray cast_persons_arr = cast_persons.optJSONArray("cast");
        StringBuilder cast = new StringBuilder();
        if (cast_persons_arr != null && cast_persons_arr.length() >= 1){
            for (int i=0; i<cast_persons_arr.length(); i++){
                JSONObject person = cast_persons_arr.optJSONObject(i);
                String name = person.optString("name");
                if (name != null){
                    cast.append(name);
                    cast.append(",");
                }
            }
        }
        
        String name = detail.optString("name");
        String broadcaster = detail.optJSONArray("networks").optJSONObject(0).optString("name");

        JSONArray genre_arr = detail.optJSONArray("genres");
        String genre = "";
        if (genre_arr != null && genre_arr.length() >= 1) genre = genre_arr.optJSONObject(0).optString("name");

        int season = detail.optJSONArray("seasons").length();
        String first_air_date = detail.optString("first_air_date");
        String last_air_date = detail.optString("last_air_date");

        JSONArray director_arr = detail.optJSONArray("created_by");
        String director = "";
        if (director_arr != null && director_arr.length() >= 1) director = director_arr.optJSONObject(0).optString("name");

        String overview = detail.optString("overview");

        Object runtime_obj = detail.optJSONArray("episode_run_time").opt(0);
        int runtime = 0;
        if (runtime_obj != null) runtime = (Integer) runtime_obj;

        String backdrop_path = detail.optString("backdrop_path");

        LocalDateTime start_date = null;
        if (first_air_date != null){
            String[] start_date_arr = first_air_date.split("-");
            start_date = LocalDateTime.of(Integer.parseInt(start_date_arr[0]), 
                                                                Integer.parseInt(start_date_arr[1]), 
                                                                Integer.parseInt(start_date_arr[2]), 9, 0, 0);
        }

        LocalDateTime end_date = null;
        if (last_air_date != null){
            String[] end_date_arr = last_air_date.split("-");
            if (end_date_arr.length >= 3){
                end_date = LocalDateTime.of(Integer.parseInt(end_date_arr[0]), 
                                                                    Integer.parseInt(end_date_arr[1]), 
                                                                    Integer.parseInt(end_date_arr[2]), 9, 0, 0);
            }
        }

        Program p = new Program();
        p.setName(name);
        p.setBroadcaster(broadcaster);
        p.setGenre(genre);
        p.setSeason(season);
        if (name.length() < 20) p.setAlias(name);
        p.setStart_date(start_date);
        p.setEnd_date(end_date);
        p.setDirector(director);
        p.setCast(cast.toString());
        p.setDescription(overview);
        p.setBroadcast_time(Integer.toString(runtime));
        if (backdrop_path != null) p.setThumbnail(IMAGE_BASE_URL + backdrop_path);

        //tester.saveProgram(p);
        for (int i=1; i<=season; i++){
            season_detail(id, i, p);
        }

    }

    public static void season_detail(int pid, int season, Program program){
        RestTemplate restTemplate = new RestTemplate();
        // 각 시즌의 상세정보를 가져옴
        ResponseEntity<String> re = 
        restTemplate.getForEntity(BASE_URL + "tv/" + Integer.toString(pid) + "/season/" + season + "?api_key=" + API_KEY, String.class);
        JSONObject detail = new JSONObject(re.getBody());

        // 추가로 가져올만한 정보: 시즌 name, air_date, poster_path
        // 없으면 그냥 기존에 program에서 세팅했던 대로...

        String season_name = detail.optString("name");
        String season_air_date = detail.optString("air_date");
        String season_poster = detail.optString("poster_path");

        // 이러면 'name' 은 프로그램명에 시즌명이 붙은 형태가 되고 alias는 프로그램명만 들어가게 됨
        if (season_name != null) program.setName(program.getName() + " - " + season_name);
        if (season_air_date != null) {
            String[] season_start_date = season_air_date.split("-");
            LocalDateTime start_date = LocalDateTime.of(Integer.parseInt(season_start_date[0]), 
                                                                Integer.parseInt(season_start_date[1]), 
                                                                Integer.parseInt(season_start_date[2]), 9, 0, 0);
            program.setStart_date(start_date);
        }
        if (season_poster != null) program.setThumbnail(IMAGE_BASE_URL + season_poster);

        // pno를 auto_increment 대신 임의로 정해주자 일단... DB에 저장하는 시점에서 episode에 붙여줘야 할 pno를 바로 알 수 없다
        // ex: id가 8080인 프로그램의 시즌 12면 8080012
        int pno = pid * 1000 + season;
        program.setPno(pno);

        tester.saveProgram(program);

        int episodes = detail.optJSONArray("episodes").length();
        for (int i=1; i<=episodes; i++){
            episode_detail(pid, pno, season, i);
        }

    }

    public static void episode_detail(int pid, int pno, int season, int epno){
        RestTemplate restTemplate = new RestTemplate();
        // 각 시즌의 상세정보를 가져옴
        ResponseEntity<String> re = 
        restTemplate.getForEntity(BASE_URL + "tv/" + Integer.toString(pid) + "/season/" + season + "/episode/" + epno + "?api_key=" + API_KEY, String.class);
        JSONObject detail = new JSONObject(re.getBody());

        //String epname = detail.optString("name");
        String summary = detail.optString("overview");
        //String date_str = detail.optString("broadcast_date");
        
        JSONArray guests = detail.optJSONArray("guest_stars");
        StringBuilder guest_str = new StringBuilder();
        if (guests != null && guests.length() >= 1){
            for (int i=0; i<guests.length(); i++){
                String guest = guests.optJSONObject(i).optString("name");
                guest_str.append(guest);
                guest_str.append(",");
            }
        }

        String thumbnail = detail.optString("still_path");
        String episode_air_date = detail.optString("air_date");

        Episode e = new Episode();

        if (episode_air_date != null) {
            String[] episode_start_date = episode_air_date.split("-");
            if (episode_start_date.length >= 3){
                LocalDateTime start_date = LocalDateTime.of(Integer.parseInt(episode_start_date[0]), 
                                                                    Integer.parseInt(episode_start_date[1]), 
                                                                    Integer.parseInt(episode_start_date[2]), 9, 0, 0);
                e.setBroadcast_date(start_date);
            }
        }
        
        e.setPno(pno);
        e.setEpisode(epno);
        e.setSummary(summary);
        e.setGuest(guest_str.toString());
        if (thumbnail != null) e.setThumbnail(IMAGE_BASE_URL + thumbnail);
        e.setReplay_link("");

        tester.saveEpisode(e);

    }

}
*/