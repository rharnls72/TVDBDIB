package com.web.curation.controller.recommend;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.tensorflow.SavedModelBundle;
import org.tensorflow.Session;
import org.tensorflow.Tensor;

import ch.qos.logback.core.joran.conditional.ElseAction;

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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import com.web.curation.dao.episode.EpisodeDao;
import com.web.curation.dao.following.FollowingDao;
import com.web.curation.dao.recommend.RecommendDao;
import com.web.curation.dao.user.UserDao;
import com.web.curation.model.BasicResponse;
import com.web.curation.model.episode.EpisodeDB;
import com.web.curation.model.episode.EpisodeResponse;
import com.web.curation.model.program.Program;
import com.web.curation.model.recommend.RecommendData;
import com.web.curation.model.user.User;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import joinery.DataFrame;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized", response = BasicResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = BasicResponse.class),
        @ApiResponse(code = 404, message = "Not Found", response = BasicResponse.class),
        @ApiResponse(code = 500, message = "Failure", response = BasicResponse.class) })

@CrossOrigin(origins = { "*" })
@RestController
public class TrainingDataController {

    @Autowired
    private RecommendDao recommendDao;

    // @GetMapping("/recommend/testdatainsert")
    // @ApiOperation(value = "추천 테스트를 위해 임의로 유저-프로그램 데이터 생성하여 DB에 삽입")
    // public Object generateData(HttpServletRequest request) throws Exception {
        
    //     for (int i=1000; i<1250; i++){
    //         User u = new User();
    //         u.setUno(i);
    //         u.setEmail("test" + i + "@gmail.com");
    //         u.setPassword("test");
    //         u.setNick_name("test" + i);
    //         recommendDao.insertTestUser(u);
    //     }

    //     // program_follow 생성
        
    //     RestTemplate restTemplate = new RestTemplate();
    //     List<Program> programList = new ArrayList<Program>();

    //     for (int page=1; page<=10; page++){

    //         ResponseEntity<String> re = 
    //         restTemplate.getForEntity(BASE_URL + "trending/tv/day?page=" + page + "&api_key=" + API_KEY, String.class);
    //         JSONObject recommended_program = new JSONObject(re.getBody());
    //         JSONArray programs = recommended_program.optJSONArray("results");

    //         for (int i=1; i<=programs.length(); i++){
    //             Program p = new Program();
    //             JSONObject programJson = programs.optJSONObject(i-1);
    //             int id = programJson.optInt("id");
    //             p.setPno(id);

    //             if (!programList.contains(id))
    //                 programList.add(p);
    //         }
    //     }

    //     Random r = new Random();
    //     for (int i=1000; i<1500; i++){
    //         for (int j=0; j<programList.size(); j++){
    //             if (r.nextInt(3) < 1)
    //                 os.write("insert into program_follow values (" + i + "," + programList.get(j).getPno() + ");\n");
    //             if (r.nextInt(3) >= 2)
    //                 os.write("insert into program_reply values(" + (i*10000+j) + ", " + programList.get(j).getPno() + ", null, " + i + ", 'test', current_timestamp(), null);\n");
    //         }
    //     }
    
    // }

    @GetMapping("/recommend/testdatafile")
    @ApiOperation(value = "DB 데이터 기반으로 학습에 사용될 testData.csv 파일 생성하기")
    public Object generateFileByData(HttpServletRequest request) throws Exception {

        System.out.println("accessing...");

        List<RecommendData> list1 = recommendDao.getProgramFollow();
        List<RecommendData> list2 = recommendDao.getProgramReply();
        List<RecommendData> list3 = recommendDao.getProgramLike();

        HashMap<Integer, Float> ratings = new HashMap<Integer, Float>();
        int list1_size = list1.size();
        int list2_size = list2.size();
        int list3_size = list3.size();

        for (int i=0; i<list1_size; i++){
            RecommendData d = list1.get(i);
            int uid = d.getUno();
            int pid = d.getPno();
            ratings.put(uid * 1000000 + pid, 1.0f); // 유저ID, 프로그램ID를 변수 하나에 저장
        }
        for (int i=0; i<list2_size; i++){
            RecommendData d = list2.get(i);
            int uid = d.getUno();
            int pid = d.getPno();
            int key = uid * 1000000 + pid;
            if (ratings.containsKey(key))
                ratings.put(key, ratings.get(key) + 1.0f);
        }
        for (int i=0; i<list3_size; i++){
            RecommendData d = list3.get(i);
            int uid = d.getUno();
            int pid = d.getPno();
            int key = uid * 1000000 + pid;
            if (ratings.containsKey(key))
                ratings.put(key, ratings.get(key) + 1.0f);
        }

        System.out.println("generating data.csv...");

        OutputStreamWriter os = new OutputStreamWriter(new FileOutputStream("tvility/testData.csv"));
        //OutputStreamWriter os = new OutputStreamWriter(new FileOutputStream("testData.csv"));
        os.write("userId,programId,rating\n");

        for (int key: ratings.keySet()){
            int uid = key / 1000000;
            int pid = key % 1000000;
            float rating = ratings.get(key);
            os.write(uid + "," + pid + "," + rating + "\n");
        }

        os.close();

        final BasicResponse result = new BasicResponse();
        result.status = true;
        result.msg = "success";
        result.data = "";
        return new ResponseEntity<>(result, HttpStatus.OK);

    }

}