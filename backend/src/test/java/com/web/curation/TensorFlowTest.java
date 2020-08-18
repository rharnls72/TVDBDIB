package com.web.curation;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import org.tensorflow.Graph;
import org.tensorflow.SavedModelBundle;
import org.tensorflow.Session;
import org.tensorflow.Tensor;
import org.tensorflow.TensorFlow;

import joinery.DataFrame;

public class TensorFlowTest {
    public static void main(String[] args) throws Exception {

        String s = null;

        Process process = Runtime.getRuntime().exec("python model_load.py");
        BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
        BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));
        while ((s = stdInput.readLine()) != null){
            System.out.println(s);
        }
        while ((s = stdError.readLine()) != null){
            System.out.println(s);
        }



        // //모델에서 받는 input 데이터가 int64 타입으로 되어있어서 int가 아니라 long으로 만들어야
        // DataFrame df = DataFrame.readCsv(new FileInputStream("testData.csv"));

        // // rating이 value로 들어가는 행렬 형태로 변환. (유저ID = 행, 프로그램ID = 열, 평점 = 값)
        // DataFrame df_pivoted = df.pivot(0, 1, 2);

        // long login_user_id = 136;
        // int user_size = df_pivoted.length();
        // int colsize = df_pivoted.size();
        // System.out.println(colsize);

        // int main_row_num = -1;
        // for (int i=0; i<user_size; i++){
        //     if ((long)df_pivoted.get(i, 0) == login_user_id){
        //         main_row_num = i;
        //         break;
        //     }
        // }

        // System.out.println(main_row_num);
        // List main_row = df_pivoted.row(main_row_num); // 추천 대상 유저의 시청 정보 row.
        // ArrayList<Long> not_watched_col_nums = new ArrayList<Long>();

        // System.out.println(main_row);
        
        // // 맨 첫 column은 userId니까 빼고 세어야
        // for (int i=1; i<colsize; i++){
        //     if (main_row.get(i) == null){
        //       not_watched_col_nums.add((long)i-1);
        //     }
        // }

        // long[][] input = new long[not_watched_col_nums.size()][2];
        // for (int i=0; i<not_watched_col_nums.size(); i++){
        //     input[i][0] = main_row_num;
        //     input[i][1] = not_watched_col_nums.get(i);
        // }

        // for (int i=0; i<not_watched_col_nums.size(); i++){
        //   System.out.println(input[i][0] + " " + input[i][1]);
        // }

        // // long[][] input = {
        // //     { 3,20},
        // //     { 3,42},
        // //     { 3,99},
        // //     { 3, 8},
        // //     { 3,89},
        // //     { 3,19},
        // //     { 3,27},
        // //     { 3,78},
        // //     { 3,70},
        // //     { 3,33},
        // //     { 3,59},
        // //     { 3,77},
        // //     { 3,57},
        // //     { 3,69},
        // //     { 3,75},
        // //     { 3,79},
        // //     { 3,49},
        // //     { 3,39},
        // //     { 3,24},
        // //     { 3, 9},
        // //     { 3,80},
        // //     { 3,60},
        // //     { 3, 5},
        // //     { 3,36},
        // //     { 3,98},
        // //     { 3,62},
        // //     { 3,52},
        // //     { 3,55},
        // //     { 3,73}
        // // };

        // // 만들어놨던 모델 불러오기
        // try (SavedModelBundle b = SavedModelBundle.load("test_model_3", "serve")){
        //     // byte[] 를 제대로 출력하려면 String 생성자의 매개변수로 넣어주는 방법이 있었음
        //     System.out.println(new String(b.metaGraphDef())); // input, output 노드 이름 알아내기 위함.
        //     Session sess = b.session();
        //     Tensor result = sess.runner()
        //     .feed("serving_default_input_1:0", Tensor.create(input)) // 노드 이름 알아내서 넣어줘야 했다
        //     .fetch("StatefulPartitionedCall:0")
        //     .run()
        //     .get(0); // 리턴타입이 기본적으로 List<Tensor> 라서

        //     // IdentityIdentity:output:0

        //     HashMap<Long, Float> ratings = new HashMap<Long, Float>();

        //     System.out.println(result);
        //     // 텐서가 6x1짜리로 오는데 행이 1개라도 어쨌든 2차원 배열로 선언해줘야 함
        //     float[][] result_arr = (float[][]) result.copyTo(new float[input.length][1]); 
        //     for (int i=0; i<result_arr.length; i++){
        //         for (int j=0; j<result_arr[i].length; j++){
        //             System.out.println(i + " " + input[i][1] + " " + result_arr[i][j]);
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

        //     Object[] program_ids = df_pivoted.columns().toArray();

        //     //System.out.println(df_pivoted.columns());
        //     //System.out.println(df_pivoted.columns().size());

        //     for (int i=0; i<10; i++){
        //       long col_num = keySetList.get(i) + 1; // 맨앞엔 userId라는 값이 붙어있음
        //       System.out.println(program_ids[(int)col_num]);
        //     }

        // }

        // System.out.println(TensorFlow.version());

      }
}