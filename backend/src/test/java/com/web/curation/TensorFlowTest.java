package com.web.curation;

import java.io.FileInputStream;
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

        // 모델에서 받는 input 데이터가 int64 타입으로 되어있어서 int가 아니라 long으로 만들어야
        DataFrame df = DataFrame.readCsv(new FileInputStream("testData.csv"));

        // rating이 value로 들어가는 행렬 형태로 변환. (유저ID = 행, 프로그램ID = 열, 평점 = 값)
        DataFrame df_pivoted = df.pivot(0, 1, 2);

        long login_user_id = 870;
        int user_size = 1000;
        int colsize = df_pivoted.size();
        System.out.println(colsize);

        int main_row_num = -1;
        for (int i=0; i<user_size; i++){
            if ((long)df_pivoted.get(i, 0) == login_user_id){
                main_row_num = i;
                break;
            }
        }

        System.out.println(main_row_num);
        List main_row = df_pivoted.row(main_row_num); // 추천 대상 유저의 시청 정보 row.
        ArrayList<Long> not_watched_col_nums = new ArrayList<Long>();

        System.out.println(main_row);
        
        // 맨 첫 column은 userId니까 빼고 세어야
        for (int i=1; i<colsize; i++){
            if (main_row.get(i) == null){
              not_watched_col_nums.add((long)i-1);
            }
        }

        long[][] input = new long[not_watched_col_nums.size()][2];
        for (int i=0; i<not_watched_col_nums.size(); i++){
            input[i][0] = main_row_num;
            input[i][1] = not_watched_col_nums.get(i);
        }

        //for (int i=0; i<not_watched_col_nums.size(); i++){
          //System.out.println(input[i][0] + " " + input[i][1]);
        //}

        // long[][] input = {
        //   {642, 31},
        //   {642,176},
        //   {642,181},
        //   {642, 69},
        //   {642, 74},
        //   {642,135},
        //   {642,120},
        //   {642, 52},
        //   {642,106},
        //   {642,196},
        //   {642,130},
        //   {642, 86},
        //   {642,186},
        //   {642, 78},
        //   {642, 63},
        //   {642,155},
        //   {642,140},
        //   {642, 89},
        //   {642,125},
        //   {642, 48},
        //   {642,177},
        //   {642,185},
        //   {642, 98},
        //   {642,129},
        //   {642,158},
        //   {642,150},
        //   {642,171},
        //   {642, 16},
        //   {642,151},
        //   {642, 20},
        //   {642, 71},
        //   {642,103},
        //   {642,173},
        //   {642, 43},
        //   {642, 39},
        //   {642, 37},
        //   {642, 46},
        //   {642, 45},
        //   {642, 72},
        //   {642, 80},
        //   {642, 28},
        //   {642,157},
        //   {642, 95},
        //   {642, 10},
        //   {642,116},
        //   {642,110},
        //   {642, 27},
        //   {642, 67},
        //   {642,118},
        //   {642, 64},
        //   {642,126},
        //   {642, 40},
        //   {642, 25},
        //   {642,  0},
        //   {642,139},
        //   {642,132},
        //   {642,105},
        //   {642,179},
        //   {642,168},
        //   {642, 33},
        //   {642,163},
        //   {642, 51},
        //   {642,170},
        //   {642, 35},
        //   {642, 29},
        //   {642,114},
        //   {642,122},
        //   {642,152},
        //   {642, 12},
        //   {642,191},
        //   {642,144},
        //   {642,165},
        //   {642,142},
        //   {642,184},
        //   {642,161},
        //   {642,148},
        //   {642,111},
        //   {642, 66},
        //   {642,104},
        //   {642, 18},
        //   {642, 77},
        //   {642,194},
        //   {642,192},
        //   {642,149},
        //   {642, 17},
        //   {642, 57},
        //   {642,195},
        //   {642, 53},
        //   {642,189},
        //   {642, 22},
        //   {642, 54},
        //   {642, 55},
        //   {642,  2},
        //   {642, 23},
        //   {642,  8},
        //   {642,164},
        //   {642, 81},
        //   {642,115},
        //   {642,109},
        //   {642, 50},
        //   {642, 49},
        //   {642, 75},
        //   {642, 65},
        //   {642, 73},
        //   {642, 96},
        //   {642,108},
        //   {642,159},
        //   {642,188},
        //   {642, 19},
        //   {642, 47},
        //   {642,101},
        //   {642,143},
        //   {642,107},
        //   {642,162},
        //   {642,167},
        //   {642,  4},
        //   {642, 38},
        //   {642, 44},
        //   {642,112},
        //   {642,154},
        //   {642, 26},
        //   {642,121},
        //   {642, 61},
        //   {642,131},
        //   {642,  5},
        //   {642,145},
        //   {642,137},
        //   {642,183},
        //   {642, 60},
        //   {642,175},
        //   {642, 97},
        //   {642, 68},
        //   {642, 94},
        //   {642,178},
        //   {642, 30},
        //   {642, 87}
        // };

        // 만들어놨던 모델 불러오기
        try (SavedModelBundle b = SavedModelBundle.load("test_model_2", "serve")){
            // byte[] 를 제대로 출력하려면 String 생성자의 매개변수로 넣어주는 방법이 있었음
            System.out.println(new String(b.metaGraphDef())); // input, output 노드 이름 알아내기 위함.
            Session sess = b.session();
            Tensor result = sess.runner()
            .feed("serving_default_input_1:0", Tensor.create(input)) // 노드 이름 알아내서 넣어줘야 했다
            .fetch("StatefulPartitionedCall:0")
            .run()
            .get(0); // 리턴타입이 기본적으로 List<Tensor> 라서

            // IdentityIdentity:output:0

            HashMap<Long, Float> ratings = new HashMap<Long, Float>();

            System.out.println(result);
            // 텐서가 6x1짜리로 오는데 행이 1개라도 어쨌든 2차원 배열로 선언해줘야 함
            float[][] result_arr = (float[][]) result.copyTo(new float[input.length][1]); 
            for (int i=0; i<result_arr.length; i++){
                for (int j=0; j<result_arr[i].length; j++){
                    System.out.println(input[i][1] + " " + result_arr[i][j]);
                    ratings.put(input[i][1], result_arr[i][j]);
                }
            }

            List<Long> keySetList = new ArrayList<>(ratings.keySet());

            Collections.sort(keySetList, new Comparator<Long>() {
                @Override
                public int compare(Long o1, Long o2) {
                    return ratings.get(o2).compareTo(ratings.get(o1));
                }
            });

            Object[] program_ids = df_pivoted.columns().toArray();

            //System.out.println(df_pivoted.columns());
            //System.out.println(df_pivoted.columns().size());

            for (int i=0; i<10; i++){
              long col_num = keySetList.get(i) + 1; // 맨앞엔 userId라는 값이 붙어있음
              System.out.println(program_ids[(int)col_num]);
            }

        }

        System.out.println(TensorFlow.version());

      }
}