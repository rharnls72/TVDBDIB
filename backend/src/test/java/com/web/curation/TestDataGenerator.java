package com.web.curation;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.web.curation.model.program.Program;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class TestDataGenerator {
    // TMDB API로부터 적당히 프로그램 ID들 받아온 다음 그걸로 테스트 데이터 생성
    static String BASE_URL = "https://api.themoviedb.org/3/";
    static String IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500";
    static String API_KEY = "1436d388221956af7b6cd27a6a7ec9d8";

    public static void main(String[] args) throws Exception {

        // user 생성
        OutputStreamWriter os = new OutputStreamWriter(new FileOutputStream("TestDataSql.sql"));

        for (int i=1000; i<1250; i++){
            os.write("insert into user values (" + i + ", '" + "test" + i + "@gmail.com', 'test', null, 'test" + i + "', null, null, null, 1);\n" );
        }

        // program_follow 생성
        
        RestTemplate restTemplate = new RestTemplate();
        List<Program> programList = new ArrayList<Program>();

        for (int page=1; page<=10; page++){
            ResponseEntity<String> re = 
            restTemplate.getForEntity(BASE_URL + "trending/tv/day?page=" + page + "&api_key=" + API_KEY, String.class);
            JSONObject recommended_program = new JSONObject(re.getBody());
            JSONArray programs = recommended_program.optJSONArray("results");

            for (int i=1; i<=programs.length(); i++){
                Program p = new Program();
                JSONObject programJson = programs.optJSONObject(i-1);
                int id = programJson.optInt("id");
                p.setPno(id);
              
                if (!programList.contains(id))
                    programList.add(p);
            }
        }

        Random r = new Random();
        for (int i=1000; i<1250; i++){
            for (int j=0; j<programList.size(); j++){
                if (r.nextInt(3) < 1)
                    os.write("insert into program_follow values (" + i + "," + programList.get(j).getPno() + ");\n");
                if (r.nextInt(3) >= 2)
                    os.write("insert into program_reply values(" + (i*10000+j) + ", " + programList.get(j).getPno() + ", null, " + i + ", 'test', current_timestamp(), null);\n");
                if (r.nextInt(3) < 1)
                    os.write("insert into program_like values(" + (i*10000+j) + ", " + i + ", " + programList.get(j).getPno() + ", current_timestamp());\n");
            }
        }

    }
}