package com.web.curation;

import java.time.LocalDateTime;

import com.web.curation.dao.program.ProgramDao;
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
    ProgramDao dao;

    public void saveProgram(Program p) {
        dao.addNewProgram(p);
    }
}

@SpringBootApplication
public class TMDBApiTest {

    static String BASE_URL = "https://api.themoviedb.org/3/";
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
        
        String cast = "";
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
        p.setAlias("");
        p.setStart_date(start_date);
        p.setEnd_date(end_date);
        p.setDirector(director);
        p.setCast(cast);
        p.setDescription(overview);
        p.setBroadcast_time(Integer.toString(runtime));
        p.setThumbnail(backdrop_path);

        tester.saveProgram(p);

    }

    public static void episode_detail(int season, int epno){

    }

}