package com.web.curation;

import java.io.IOException;
import java.net.MalformedURLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.LogManager;

import com.web.curation.dao.program.ProgramDao;
import com.web.curation.model.program.Program;

import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import io.github.bonigarcia.wdm.WebDriverManager;

@Service
class ProgramSaveTest2 {
    @Autowired
    ProgramDao dao;

    public void saveProgram(Program p) {
        dao.addNewProgram(p);
    }
}

@SpringBootApplication
public class NetflixCrawlingAndSaveTest {
    public static void main(String[] args) {
        System.out.println("Hello");
        ApplicationContext applicationContext = SpringApplication.run(NetflixCrawlingAndSaveTest.class, args);
        ProgramSaveTest2 tester = applicationContext.getBean(ProgramSaveTest2.class);

        // 분류: '넷플릭스 오리지널'
        String url_original = "https://www.netflix.com/kr/browse/genre/839338";
        String broadcaster = "Netflix";

        WebDriver driver;
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get(url_original); // URL은 크롬 드라이버에 주고,
        Document doc = null;

        try {
            doc = Jsoup.parse(driver.getPageSource()); // 크롬 드라이버가 페이지를 완전히 로딩한 다음 Jsoup에 넘겨준다.
        } catch (Exception e){
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

            String thumbnail = program.select("img.nm-collections-title-img").attr("src");
            //javax.xml.bind.DatatypeConverter.parseBase

            try { // 링크 타고 세부 페이지 들어가서 세부정보 긁기.
                doc_detail = Jsoup.connect(program_link).get();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Elements metadata = doc_detail.select("div.title-info-metadata-wrapper");
            Iterator<Element> ie2 = metadata.select("span.title-info-metadata-item").iterator();

            // 한국 영화 -> 제목 - 연도 - 관람등급 - 러닝타임 - 장르 순으로 출력.
            // 넷플릭스 오리지널 -> 제목 - 연도 - 관람등급 - 시즌개수 - 장르 순으로 출력.
            
            Program p = new Program();

            String title = program.text();
            System.out.print(title + " / ");

            ArrayList<String> metadata_list = new ArrayList<String>();

            while (ie2.hasNext()){
                String text = ie2.next().text();
                System.out.print(text + " / ");
                metadata_list.add(text);
            }

            //System.out.print(metadata_list.get(0) + " / " + metadata_list.get(1) + " / " + metadata_list.get(2));

            String year = metadata_list.get(0);
            String rating = metadata_list.get(1);
            String seasons = metadata_list.get(2);
            String genre = metadata.select("a.item-genre").text();

            if (!seasons.contains("시즌"))
                seasons = "1";
            else{
                seasons = seasons.split("시즌 ")[1].split("개")[0];
            }

            String description = doc_detail.select("div.title-info-synopsis").text();
            Elements talents = doc_detail.select("div.title-info-talent");
            String actors = talents.select("div.item-starring > span.title-data-info-item-list").text();
            String creators = talents.select("div.item-creators > span.title-data-info-item-list").text();
                        
            p.setName(title);
            p.setBroadcaster(broadcaster);
            p.setAlias("");
            p.setGenre(genre);
            p.setSeason(Integer.parseInt(seasons));
            p.setStart_date(LocalDateTime.of(Integer.parseInt(year), 1, 1, 9, 0, 0)); // 서버 시차 이슈때문에 9시로 설정);
            p.setEnd_date(null);
            p.setDirector(creators);
            p.setCast(actors);
            p.setDescription(description);
            p.setBroadcast_time("");
            p.setThumbnail(thumbnail);

            tester.saveProgram(p);
        }


    }
}