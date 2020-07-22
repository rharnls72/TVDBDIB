package com.web.curation;

import java.io.IOException;
import java.util.Iterator;

import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class NaverTVCrawlingTest {
    public static void main(String[] args) {

        System.out.println("Hello");

        // 네이버 TV 크롤링 테스트
        // URL 형식: tv.naver.com/b/방송사명/channels
        String url_original = "https://tv.naver.com/b/mbn/channels";
        Document doc = null;

        try { // JSoup connect
            doc = Jsoup.connect(url_original).get();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // CSS Selector와 Iterator를 적절하게 사용하여 내용 추출.
        // Elements랑 Element의 차이를 확인하자. select() 는 Elements를 리턴함
        Elements elem = doc.select("ul.ch_list");
        //System.out.println(elem);

        Iterator<Element> ie = elem.select(
                "div.thm > a.thm_a")
                .iterator();

        while (ie.hasNext()) {
            String program_link = ie.next().attr("href").replace("/playlists", ""); // 프로그램 하나에 대한 정보
            Document doc_detail = null;

            try { // 링크 타고 세부 페이지 들어가서 세부정보 긁기.
                doc_detail = Jsoup.connect(program_link).get();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Elements title_area = doc_detail.select("dl.tit_ar");
            String title = title_area.select("h2").html().split("<a")[0].replace("\"", "");
            String broad = title_area.select("span.brod_inf >  a.brod").text();
            String time = title_area.select("span.brod_inf > span.b_time").text();

            // 방송 다시보기 버튼 href 가져오기 (네이버 시리즈ON으로 이동)
            String vod_url = doc_detail.select("div.like_ar > a.btn_store").attr("href");

            System.out.println(title + " / " + broad + " / " + time + " / " + vod_url);
        }
        
    }
}