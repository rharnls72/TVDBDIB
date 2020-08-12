package com.web.curation.controller.recommend;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

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
public class RecommendController {

    @Autowired
    private RecommendDao recommendDao;

    static final int WATCH_HOUR = 72;

    static String BASE_URL = "https://api.themoviedb.org/3/";
    static String IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500";
    static String API_KEY = "1436d388221956af7b6cd27a6a7ec9d8";

    @GetMapping("/recommend/tasty")
    @ApiOperation(value = "협업 필터링을 이용한 사용자 맞춤 추천 결과 받아오기")
    public Object getTastyRecommendation(HttpServletRequest request) throws Exception {

        int uno = ((User) request.getAttribute("User")).getUno();

        RestTemplate restTemplate = new RestTemplate();
        List<Program> programList = new ArrayList<Program>();

        for (int page = 1; page <= 5; page++) {
            ResponseEntity<String> re = restTemplate
                    .getForEntity(BASE_URL + "trending/tv/day?page=" + page + "&api_key=" + API_KEY, String.class);
            JSONObject recommended_program = new JSONObject(re.getBody());
            JSONArray programs = recommended_program.optJSONArray("results");

            for (int i = 1; i <= programs.length(); i++) {
                Program p = new Program();
                JSONObject programJson = programs.optJSONObject(i - 1);
                int id = programJson.optInt("id");
                String name = programJson.optString("name");
                String thumbnail = programJson.optString("poster_path");
                p.setPno(id);
                p.setPname(name);
                if (thumbnail != null && thumbnail.length() > 1)
                    p.setThumbnail(IMAGE_BASE_URL + thumbnail);
                programList.add(p);
            }
        }

        OutputStreamWriter os = new OutputStreamWriter(new FileOutputStream("testData.csv"));
        Random r = new Random();
        os.write("userId, programId, rating\n");

        for (int i = 1; i <= 100; i++) {
            for (int j = 0; j < programList.size(); j++) {
                int isSkip = r.nextInt(3);
                if (isSkip >= 2)
                    continue;

                String line = i + "," + programList.get(j).getPno() + "," + (r.nextInt(4) + 1) + ".0";
                os.write(line + "\n");
            }
        }

        os.close();

        List<Long> recommend_result = collaborative_filtering(uno);
        ArrayList<Program> final_result = new ArrayList<Program>();

        for (Program p: programList){
            long pno = p.getPno();
            if (recommend_result.contains(pno))
                final_result.add(p);
        }

        System.out.println(final_result.size());

        final BasicResponse result = new BasicResponse();
        result.status = true;
        result.msg = "success";
        result.data = final_result;
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    private List collaborative_filtering(int uno) throws Exception {
        long login_user_id = uno;
        int user_size = 100;

        // joinery로 데이터 읽기.
        DataFrame df = DataFrame.readCsv(new FileInputStream("testData.csv"));

        // rating이 value로 들어가는 행렬 형태로 변환. (유저ID = 행, 프로그램ID = 열, 평점 = 값)
        DataFrame df_pivoted = df.pivot(0, 1, 2);

        int main_row_num = -1;
        for (int i=0; i<user_size; i++){
            if ((long)df_pivoted.get(i, 0) == login_user_id){
                main_row_num = i;
                break;
            }
        }

        System.out.println(main_row_num);
        HashMap<Integer, Double> cor_list = new HashMap<Integer, Double>();

        // 상관계수 계산.
        List main_row = df_pivoted.row(main_row_num); // 다른 유저들과의 상관계수를 비교할 축
        int colsize = df_pivoted.size();

        for (int i=0; i<user_size; i++) {
            if (i == main_row_num) continue;
            List row2 = df_pivoted.row(i);

            double sumx = 0.0;
            double sumy = 0.0;
            double sumxy = 0.0;
            double sumx_pow = 0.0;
            double sumy_pow = 0.0;
            int n = 0;

            for (int j=1; j<colsize; j++){
                Object a = main_row.get(j);
                Object b = row2.get(j);
                //System.out.println(a + " " + b);
                if (a != null && b != null){ // A, B가 모두 본 프로그램에 대해서만.
                    n++;
                    sumx += (double) a;
                    sumy += (double) b;
                    sumxy += (double) a * (double) b;
                    sumx_pow += (double) a * (double) a;
                    sumy_pow += (double) b * (double) b;
                }
            }

            double up = n*sumxy - sumx*sumy;
            double down = (n*sumx_pow - (sumx * sumx)) * (n*sumy_pow - (sumy * sumy));
            double cor = up / Math.sqrt(down);

            cor_list.put(i, cor);
        }

        System.out.println(cor_list);

        // 상관계수 내림차순으로 정렬해서 제일 비슷한 유저 N명을 찾는다
        List<Integer> keySetList = new ArrayList<>(cor_list.keySet());

        Collections.sort(keySetList, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return cor_list.get(o2).compareTo(cor_list.get(o1));
            }
        });

        int nearest_N = 5;
        ArrayList<Double> means = new ArrayList<Double>();
        double mymean = getMean(main_row);

        // 자신과 비슷한 유저 N명에 대해 각각 매긴 평점의 평균을 구함
        for (int i=0; i<nearest_N; i++){
            List row = df_pivoted.row(keySetList.get(i));
            means.add(getMean(row));
        }

        Object[] program_ids = df_pivoted.columns().toArray();
        ArrayList<Long> recommend = new ArrayList<Long>();

        // 비슷한 유저 N명의 평점정보를 기반으로 내가 아직 안본 프로그램에 대한 평점 예측
        for (int i=1; i<colsize; i++){
            if (main_row.get(i) == null){ // 내가 아직 안본 프로그램이라면.
                // 채워질 값 = 내 평균 + k * sigma (유사도 * (상대유저가 매긴 평점 - 상대유저의 평균))
                double sigma = 0.0;
                double k = 0.0;

                for (int j=0; j<nearest_N; j++){
                    int user_row_num = keySetList.get(j);
                    if (df_pivoted.get(user_row_num, i) != null){
                        sigma += cor_list.get(user_row_num) * ((double) df_pivoted.get(user_row_num, i) - means.get(j));
                        k += Math.abs(cor_list.get(user_row_num));
                    }
                }

                k = 1/k;
                double rating = mymean + (k * sigma);
                System.out.println("프로그램 " + program_ids[i] + "에 대한 내 평점 예측: " + rating);
                if (rating >= mymean){
                    recommend.add((Long)program_ids[i]);
                }
            }
        }
        return recommend;
    }

    private static double getMean(List row){
        double sum = 0.0;
        int size = row.size();
        int count = 0;
        System.out.println(row);
        for (int i=1; i<size; i++){
            Object obj = row.get(i);
            if (obj != null){
                sum += (double) obj;
                count++;
            }
        }
        return sum / count;
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
        float mean = getMean_float(new ArrayList<Float>(score_table.values())); 
        float stddev = getStddev(new ArrayList<Float>(score_table.values()), mean);
        for (int id: score_table.keySet()){
            float this_score = score_table.get(id);
            float t_score = 10 * ((this_score - mean) / stddev) + 50;
            score_table.put(id, t_score);
        }

        // 데이터 있는 프로그램들에 대해 TMDB 요청
        ArrayList<Program> programs = new ArrayList<Program>();
        RestTemplate restTemplate = new RestTemplate();
        ArrayList<Float> ratings = new ArrayList<Float>();

        for (int id: score_table.keySet()){
            Program p = new Program();

            ResponseEntity<String> re = 
            restTemplate.getForEntity(BASE_URL + "tv/" + id + "?api_key=" + API_KEY + "&language=ko", String.class);
            
            JSONObject programJson = new JSONObject(re.getBody());
            //int pid = programJson.optInt("id");
            String name = programJson.optString("name");
            String thumbnail = programJson.optString("poster_path");
            float popularity = programJson.optFloat("popularity");

            //popularity *= 100;
            
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
            System.out.println(std_score + " " + score_table.get(p.getPno()));
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