package com.web.curation;

import java.io.IOException;
import java.util.Iterator;

import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class NetflixCrawlingTest {
    public static void main(String[] args) {

        System.out.println("Hello");

        // 로그인하지 않고도 접근 가능한 넷플릭스 컨텐츠 페이지 (한국 영화)
        // 로그인을 안 하니 접근 가능한 정보가 제한적이다 (회차 세부정보 같은건 안 나옴)
        String url_original = "https://www.netflix.com/kr/browse/genre/5685";
        Document doc = null;

        try { // JSoup connect
            doc = Jsoup.connect(url_original).get();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // CSS Selector와 Iterator를 적절하게 사용하여 내용 추출.
        // Elements랑 Element의 차이를 확인하자. select() 는 Elements를 리턴함
        Elements elem = doc.select("section.nm-collections-row");
        //System.out.println(elem);

        Iterator<Element> ie = elem.select(
                "div.nm-content-horizontal-row > ul.nm-content-horizontal-row-item-container > li.nm-content-horizontal-row-item")
                .iterator();

        while (ie.hasNext()) {
            Element program = ie.next(); // 프로그램 하나에 대한 정보
            String program_link = program.select("a.nm-collections-title").attr("href");
            Document doc_detail = null;

            try { // 링크 타고 세부 페이지 들어가서 세부정보 긁기.
                doc_detail = Jsoup.connect(program_link).get();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Elements metadata = doc_detail.select("div.title-info-metadata-wrapper");
            Iterator<Element> ie2 = metadata.select("span.title-info-metadata-item").iterator();

            // 제목 - 연도 - 관람등급 - 러닝타임 - 장르 순으로 출력.
            System.out.print(program.text() + " / ");
            while (ie2.hasNext()){
                System.out.print(ie2.next().text() + " / ");
            }
            String genre = metadata.select("a.item-genre").text();
            System.out.println(genre);
        }
        

    }
}