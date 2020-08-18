package com.web.curation.controller.recommend;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;


import javax.servlet.http.HttpServletRequest;

import com.web.curation.dao.recommend.RecommendDao;
import com.web.curation.model.BasicResponse;
import com.web.curation.model.program.Program;
import com.web.curation.model.recommend.RecommendData;
import com.web.curation.model.user.User;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiOperation;

@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized", response = BasicResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = BasicResponse.class),
        @ApiResponse(code = 404, message = "Not Found", response = BasicResponse.class),
        @ApiResponse(code = 500, message = "Failure", response = BasicResponse.class) })

@CrossOrigin(origins = { "*" })
@RestController
public class RecommendController {

    @Autowired
    private RecommendDao recommendDao;

    static final int WATCH_HOUR = 72;
    static final int RECOMMEND_SIZE = 20;

    static String BASE_URL = "https://api.themoviedb.org/3/";
    static String IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500";
    static String API_KEY = "1436d388221956af7b6cd27a6a7ec9d8";

    @GetMapping("/recommend/tasty")
    @ApiOperation(value = "협업 필터링을 이용한 사용자 맞춤 추천 결과 받아오기")
    public Object getTastyRecommendation(HttpServletRequest request) throws Exception {

        int uno = ((User) request.getAttribute("User")).getUno();

        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        /*
        Process process = Runtime.getRuntime().exec("/tvility/python3.8 /tvility/model_load.py " + uno);
        //Process process = Runtime.getRuntime().exec("python model_load.py " + uno);
        BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
        //BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));
        while ((s = stdInput.readLine()) != null){
            python_output = s;
        }
        */
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        URL url = new URL("http://localhost:8888/ai/recommend?uno=" + uno);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("GET");

        int responseCode = conn.getResponseCode();
        System.out.println("Recommend Python server response code: " + responseCode);

        if(responseCode == 200) {
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            // ㅠㅠ으아아 파이썬에서 uno 는 제대로 받는다
            System.out.println();
            System.out.println("Received Data From Python: " + sb.toString());
            System.out.println();

            JSONObject responseJson = new JSONObject(sb.toString());
            JSONArray data = responseJson.getJSONArray("data");

            ArrayList<Program> final_result = new ArrayList<Program>();
            RestTemplate restTemplate = new RestTemplate();
            
            // 추천 결과 Program ID들로 TMDB API에 요청 보내서 포스터, 프로그램명 가져오기.
            for (int i=0; i<RECOMMEND_SIZE; i++){
                Program p = new Program();
                ResponseEntity<String> re = 
                restTemplate.getForEntity(BASE_URL + "tv/" + data.getInt(i) + "?api_key=" + API_KEY + "&language=ko", String.class);
                JSONObject programJson = new JSONObject(re.getBody());
                int id = programJson.optInt("id");
                String name = programJson.optString("name");
                String thumbnail = programJson.optString("poster_path");
                p.setPno(id);
                p.setPname(name);
                if (thumbnail != null && thumbnail.length() > 1) p.setThumbnail(IMAGE_BASE_URL + thumbnail);
                    final_result.add(p);
            }

            final BasicResponse result = new BasicResponse();
            result.status = true;
            result.msg = "success";
            result.data = final_result;
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            final BasicResponse result = new BasicResponse();
            result.status = false;
            result.msg = "Response code: " + responseCode;
            return new ResponseEntity<>(result, HttpStatus.OK);
        }

        // int uno = ((User) request.getAttribute("User")).getUno();
        // // 모델에서 받는 input 데이터가 int64 타입으로 되어있어서 int가 아니라 long으로 만들어야

        // //DataFrame df = DataFrame.readCsv(new FileInputStream("tvility/testData.csv"));
        // DataFrame df = DataFrame.readCsv(new FileInputStream("testData.csv"));

        // // rating이 value로 들어가는 행렬 형태로 변환. (유저ID = 행, 프로그램ID = 열, 평점 = 값)
        // DataFrame df_pivoted = df.pivot(0, 1, 2);

        // long login_user_id = (long) uno;
        // int user_size = df_pivoted.length();
        // System.out.println(user_size);
        // int colsize = df_pivoted.size();
        // //System.out.println(colsize);

        // int main_row_num = -1;
        // for (int i=0; i<user_size; i++){
        //     if ((long)df_pivoted.get(i, 0) == login_user_id){
        //         main_row_num = i;
        //         break;
        //     }
        // }

        // if (main_row_num == -1){
        //     final BasicResponse result = new BasicResponse();
        //     result.status = true;
        //     result.msg = "failed";
        //     return new ResponseEntity<>(result, HttpStatus.OK);
        // }

        // //System.out.println(main_row_num);
        // List main_row = df_pivoted.row(main_row_num); // 추천 대상 유저의 시청 정보 row.
        // ArrayList<Long> not_watched_col_nums = new ArrayList<Long>();

        // //System.out.println(main_row);
        
        // // 맨 첫 column은 userId니까 빼고 세어야
        // for (int i=1; i<colsize; i++){
        //     if (main_row.get(i) == null){
        //         not_watched_col_nums.add((long)i-1);
        //     }
        // }

        // long[][] input = new long[not_watched_col_nums.size()][2];
        // for (int i=0; i<not_watched_col_nums.size(); i++){
        //     input[i][0] = main_row_num;
        //     input[i][1] = not_watched_col_nums.get(i);
        // }

        // Object[] program_ids = df_pivoted.columns().toArray();
        // long[] recommends = new long[RECOMMEND_SIZE];

        // // 만들어놨던 모델 불러오기
        // //try (SavedModelBundle b = SavedModelBundle.load("tvility/test_model_2", "serve")){
        // try (SavedModelBundle b = SavedModelBundle.load("test_model_2", "serve")){

        //     // byte[] 를 제대로 출력하려면 String 생성자의 매개변수로 넣어주는 방법이 있었음
        //     //System.out.println(new String(b.metaGraphDef())); // input, output 노드 이름 알아내기 위함.
        //     Session sess = b.session();
        //     Tensor result = sess.runner()
        //     .feed("serving_default_input_1:0", Tensor.create(input)) // 노드 이름 알아내서 넣어줘야 했다
        //     .fetch("StatefulPartitionedCall:0")
        //     .run()
        //     .get(0); // 리턴타입이 기본적으로 List<Tensor> 라서

        //     // IdentityIdentity:output:0

        //     HashMap<Long, Float> ratings = new HashMap<Long, Float>();

        //     //System.out.println(result);
        //     // 텐서가 6x1짜리로 오는데 행이 1개라도 어쨌든 2차원 배열로 선언해줘야 함
        //     float[][] result_arr = (float[][]) result.copyTo(new float[input.length][1]); 
        //     for (int i=0; i<result_arr.length; i++){
        //         for (int j=0; j<result_arr[i].length; j++){
        //             //System.out.println(input[i][1] + " " + result_arr[i][j]);
        //             ratings.put(input[i][1], result_arr[i][j]);
        //         }
        //     }

        //     List<Long> keySetList = new ArrayList<>(ratings.keySet());

        //     Collections.sort(keySetList, new Comparator<Long>() {
        //         @Override
        //         public int compare(Long o1, Long o2) {
        //             return ratings.get(o2).compareTo(ratings.get(o1));
        //         }
        //     });

        //     //System.out.println(df_pivoted.columns());
        //     //System.out.println(df_pivoted.columns().size());

        //     for (int i=0; i<RECOMMEND_SIZE; i++){
        //         long col_num = keySetList.get(i) + 1; // 맨앞엔 userId라는 값이 붙어있음
        //         //System.out.println(program_ids[(int)col_num]);
        //         recommends[i] = (long) program_ids[(int)col_num];
        //     }

        // }
    }

    // time sliding - 지난 시간에 따라 점수 계산
    private float cal_score(LocalDateTime time){
        LocalDateTime toDate = LocalDateTime.now();
        long gone_hours = ChronoUnit.HOURS.between(time, toDate);
        return (float) Math.pow (WATCH_HOUR - gone_hours, 2);
    }

    // 각 좋아요, 댓글, 찜 데이터에 대해 점수 계산 메서드 호출하면서 프로그램 점수 갱신
    private HashMap<Integer, Float> add_score(HashMap<Integer, Float> map, List<RecommendData> list, float weight){
        for (RecommendData p: list){
            if (map.containsKey(p.getPno())) // 이미 있으면 점수를 갱신
                map.put(p.getPno(), map.get(p.getPno()) + cal_score(p.getCreate_date()) * weight);
            else // 없으면 새롭게 점수 삽입
                map.put(p.getPno(), cal_score(p.getCreate_date()) * weight);
        }
        return map;
    }

    private float getMean_float (List<Float> list){
        float sum = 0;
        for (float val: list){
            sum += val;
        }
        return sum / list.size();
    }
	
	private float getStddev(List<Float> list, float mean) {
        float total = 0;
        for (float val: list){
            float diff = Math.abs(val - mean);
            total += Math.pow(diff, 2);
        }
        float variance = total / list.size();
		return (float) Math.sqrt(variance); // 분산의 제곱근 = 표준편차
	}


    @GetMapping("/recommend/trending")
    @ApiOperation(value = "TMDB API로부터 요즘 뜨는 (trending) 프로그램 받아오기")
    public Object getTrendingPrograms() {

        // DAO에서 데이터 받아오기: Program ID + 날짜 이렇게 옴
        List<RecommendData> programLikeData = recommendDao.getProgramLike();
        List<RecommendData> programReplyData = recommendDao.getProgramReply();

        List<RecommendData> episodeDibData = recommendDao.getEpisodeDibs();
        List<RecommendData> episodeLikeData = recommendDao.getEpisodeLike();
        List<RecommendData> episodeReplyData = recommendDao.getEpisodeReply();

        HashMap<Integer, Float> score_table = new HashMap<Integer, Float>();
        score_table = add_score(score_table, programLikeData, 1.0f);
        score_table = add_score(score_table, programReplyData, 1.0f);
        score_table = add_score(score_table, episodeDibData, 0.5f);
        score_table = add_score(score_table, episodeLikeData, 0.5f);
        score_table = add_score(score_table, episodeReplyData, 0.5f);

        // 여기까지 tvdbdib score 산출 (표준화 안된 상태)
        // 표준화
        // 그냥 (List) 이렇게 변환하려고 하면 에러난다. new ArrayList 하고 그 파라미터로 Collection을 주면 됨.

        // 평균, 표준편차 계산할때 라이브러리 쓰면 좀 더 빠를까 싶었는데 아니었다
        // double[] converted_arr = Arrays.stream(score_table.values().toArray())
        //                         .mapToDouble(num -> Double.parseDouble(num.toString())).toArray();
        // float mean = (float) StatUtils.mean(converted_arr);
        // float stddev = (float) Math.sqrt(StatUtils.populationVariance(converted_arr));
        float mean = getMean_float(new ArrayList<Float>(score_table.values())); 
        float stddev = getStddev(new ArrayList<Float>(score_table.values()), mean);
        for (int id: score_table.keySet()){
            float this_score = score_table.get(id);
            float t_score = 20 * ((this_score - mean) / stddev) + 100;
            score_table.put(id, t_score);
        }

        // 데이터 있는 프로그램들에 대해 TMDB 요청
        // 너무 요청이 많아서 여기서 시간이 엄청 걸린다. 그냥 TMDB 점수는 뺄까?
        ArrayList<Program> programs = new ArrayList<Program>();
        RestTemplate restTemplate = new RestTemplate();
        ArrayList<Float> ratings = new ArrayList<Float>();

        System.out.println(score_table.size());
        for (int id: score_table.keySet()){
            Program p = new Program();

            ResponseEntity<String> re = 
            restTemplate.getForEntity(BASE_URL + "tv/" + id + "?api_key=" + API_KEY + "&language=ko", String.class);
            
            JSONObject programJson = new JSONObject(re.getBody());
            //int pid = programJson.optInt("id");
            String name = programJson.optString("name");
            String thumbnail = programJson.optString("poster_path");
            float popularity = programJson.optFloat("popularity");

            p.setPno(id);
            p.setPname(name);
            if (thumbnail != null && thumbnail.length() > 1) p.setThumbnail(IMAGE_BASE_URL + thumbnail);
            
            p.setRating(popularity);
            ratings.add(popularity);
            programs.add(p);
        }

        mean = getMean_float(ratings); 
        stddev = getStddev(ratings, mean);
        for (Program p: programs){
            float std_score = 10 * ((p.getRating() - mean) / stddev) + 50;
            //System.out.println(std_score + " " + score_table.get(p.getPno()));
            p.setRating(std_score + score_table.get(p.getPno()));
        }

        // 점수순 정렬
        programs.sort(new Comparator<Program>(){
            @Override
            public int compare(Program o1, Program o2) {
                return Float.compare(o2.getRating(), o1.getRating());
            }
        });        

        // 반환할 응답 객체
        final BasicResponse result = new BasicResponse();
        result.status = true;
        result.msg = "success";
        if (programs.size() > 20)
            result.data = programs.subList(0, 20);
        else
            result.data = programs;
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