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

    @GetMapping("/recommend/testdata")
    @ApiOperation(value = "DB 데이터 기반으로 학습에 사용될 testData.csv 파일 생성하기")
    public Object getTastyRecommendation(HttpServletRequest request) throws Exception {
        List<RecommendData> list1 = recommendDao.getProgramFollow();
        List<RecommendData> list2 = recommendDao.getProgramReply();


        HashMap<String, Object> ratings = new HashMap<String, Object>();
        int list1_size = list1.size();
        int list2_size = list2.size();
        for (int i=0; i<list1_size; i++){
            RecommendData d = list1.get(i);
            int uid = d.getUno();
            int pid = d.getPno();
            ratings.put("userId", uid);
            ratings.put("programId", pid);
        }
        for (int i=0; i<list2_size; i++){

        }

        final BasicResponse result = new BasicResponse();
        result.status = true;
        result.msg = "success";
        result.data = "";
        return new ResponseEntity<>(result, HttpStatus.OK);

    }

}