package com.web.curation.service;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.web.curation.model.feed.TagRequest;

import kr.co.shineware.nlp.komoran.constant.DEFAULT_MODEL;
import kr.co.shineware.nlp.komoran.core.Komoran;
import kr.co.shineware.nlp.komoran.model.KomoranResult;
import kr.co.shineware.nlp.komoran.model.Token;

@Service
public class KomoranService {
    private Komoran komoran;

    public KomoranService() {
        komoran = new Komoran(DEFAULT_MODEL.FULL);
    }

    public String getTags(TagRequest req) {
        // 배열과 JSON 문자열 상호 변환을 위한 ObjectMapper 객체 생성
        ObjectMapper mapper = new ObjectMapper();

        // 태그 목록을 중복없이 가지고 있기 위한 Set 생성
        Set<String> response = new HashSet<String>();

        // 프론트에서 받아온 현재 태그목록이 있는 Json 문자열 가져오기
        String jsonTags = req.getTags();

        // json 형식으로 된 태그 목록 파싱
        try {
            String[] tagList = mapper.readValue(jsonTags, String[].class);

            // 현재 프론트에 있는 태그들을 Set 에 먼저 넣기
            for(String str : tagList) {
                response.add(str);
            }
        } catch (Exception e) {
            System.out.println("JSON to String[] error: " + e.getMessage());
        }

        // 태그를 추출할 문자열(내용) 가져오기
        String str = req.getContent();

        // 문자열(내용) 형태소 분석
        KomoranResult analyzeResultList = komoran.analyze(str);

        // 분석한 리스트
        List<Token> tokenList = analyzeResultList.getTokenList();

        // 어떻게 분석했는지 plain text 로 보기
        // System.out.println(analyzeResultList.getPlainText());

        // 태그 추출하며 Set 에 담기
        String save = "";
        for (Token token : tokenList) {
            String tk = token.getPos();
            String mp = token.getMorph();

            if(tk.equals("NNP")
                || tk.equals("NNG")
                || tk.equals("MAG")){
                if(mp.length() >= 2) {
                    response.add(token.getMorph());
                }
            }

            // 꿀잼 노잼 Hard Coding
            if(tk.equals("VA") || tk.equals("VV")) {
                if(mp.equals("재미")
                    || mp.equals("재미있")) {
                    response.add("꿀잼");
                }

                if(mp.equals("재미없")) {
                    response.add("노잼");
                }

                if(mp.equals("재")) {
                    if(save.equals("노")) {
                        response.add("노잼");
                    } else {
                        response.add("꿀잼");
                    }
                }

            }
            save = mp;
        }

        // Set 에 담긴 내용을 배열로
        String[] result = response.toArray(new String[response.size()]);

        // 배열을 JSON 문자열로
        // 이 과정에서 오류나도 안난척
        String jsonStr = req.getTags();
        try {
            jsonStr = mapper.writeValueAsString(result);
        } catch (JsonProcessingException e) {
            System.out.println("Error!!:: " + e.getMessage());
        }

        return jsonStr;
    }
}