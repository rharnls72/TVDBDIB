package com.web.curation;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import com.web.curation.model.program.Program;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import joinery.DataFrame;

public class PearsonCFTest {
    // TMDB API로부터 적당히 프로그램 ID들 받아온 다음 그걸로 테스트 데이터 생성
    static String BASE_URL = "https://api.themoviedb.org/3/";
    static String IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500";
    static String API_KEY = "1436d388221956af7b6cd27a6a7ec9d8";

    public static void main(String[] args) throws Exception {
        
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
                String name = programJson.optString("name");
                String thumbnail = programJson.optString("poster_path");
                p.setPno(id);
                p.setPname(name);
                if (thumbnail != null && thumbnail.length() > 1) p.setThumbnail(IMAGE_BASE_URL + thumbnail);
                programList.add(p);
            }

        }

        OutputStreamWriter os = new OutputStreamWriter(new FileOutputStream("testData.csv"));
        Random r = new Random();

        os.write("userId,programId,rating\n");

        for (int i=1; i<=1000; i++){
            for (int j=0; j<programList.size(); j++){
                int isSkip = r.nextInt(3);
                if (isSkip >= 1) continue;

                String line = i + "," + programList.get(j).getPno() + "," + (r.nextInt(4) + 1) + ".0";
                os.write(line + "\n");
            }
        }

        os.close();

        long login_user_id = 5;
        int user_size = 1000;

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
         
        /*for(Integer key : keySetList) {
            System.out.println(String.format("Key : %s, Value : %s", key, cor_list.get(key)));
        }*/

        int nearest_N = 10;
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

        System.out.println("추천 결과: ");
        System.out.println(recommend);

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
    
    
}