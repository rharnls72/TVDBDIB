package com.web.curation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.web.curation.model.feed.TagRequest;
import com.web.curation.service.KomoranService;

public class KomoranTest {
    public static void main(String[] args) {
        /*
        KomoranService komoranService = new KomoranService();

        String content = "이 프로그램 정말 핵노잼! 친구들이나 가족들과 함께 봐요 주말드라마!!";
        TagRequest req = new TagRequest();
        req.setContent(content);

        String result = komoranService.getTags(req);

        System.out.println(result);
        */
        ObjectMapper mapper = new ObjectMapper();

        String jsonTags = "[\"kkk\",\"aaa\",\"qqq\",\"xxx\"]";
        try {
            String[] tagList = mapper.readValue(jsonTags, String[].class);

            for(String str : tagList) {
                System.out.println(str);
            }
        } catch (Exception e) {
            System.out.println("JSON to String[] error: " + e.getMessage());
        }
    }
}