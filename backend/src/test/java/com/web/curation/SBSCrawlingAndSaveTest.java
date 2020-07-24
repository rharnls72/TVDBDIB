package com.web.curation;

import java.io.IOException;
import java.net.MalformedURLException;
import java.time.LocalDateTime;
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
class ProgramSaveTest {
    @Autowired
    ProgramDao dao;

    public void saveProgram(Program p) {
        dao.addNewProgram(p);
    }
}

@SpringBootApplication
public class SBSCrawlingAndSaveTest {
    public static void main(String[] args) {

        System.out.println("Hello");

        // ProgramSaveTest 클래스를 등록하는 과정. Spring에서 얘를 인식해줘야 Autowired가 먹는다.
        ApplicationContext applicationContext = SpringApplication.run(SBSCrawlingAndSaveTest.class, args);
        ProgramSaveTest tester = applicationContext.getBean(ProgramSaveTest.class);
        
        // 셀레니움 이용하기 위해 크롬 드라이버 설치하는 부분
        WebDriver driver;
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();

        // SBS 크롤링 테스트
        // 드라마 - 예능 - 교양 순서대로 긁어본다
        String[] categories = { "drama", "enter", "culture" };

        for (String category : categories) {
            System.out.println(category);

            String url = "https://www.sbs.co.kr/tv/" + category;
            driver.get(url); // URL은 크롬 드라이버에 주고,
            Document doc = null;

            try {
                doc = Jsoup.parse(driver.getPageSource()); // 크롬 드라이버가 페이지를 완전히 로딩한 다음 Jsoup에 넘겨준다.
            } catch (Exception e){
                e.printStackTrace();
            }

            // 그 다음부터는 JSoup 하던대로.
            String broadcaster = "SBS";
            String genre = doc.select("a[href=" + url + "] > span").text();

            System.out.println(genre);

            Elements program_list = doc.select("ul.main_programtab_list");
            //System.out.println(program_list);

            Iterator<Element> programs = program_list.select("li.main_programtab_list_inner").iterator();
            while (programs.hasNext()){
                Element program = programs.next();
                String program_link = program.select("div.module_box_w > a.mb_inner").attr("href");
                String thumbnail_url = program.select("img.mb_image").attr("src");

                System.out.println(program_link);
                driver.get(program_link);

                Document doc_detail = null;
                try { // 링크 타고 세부 페이지로 들어감.
                    doc_detail = Jsoup.parse(driver.getPageSource());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                // 세부 페이지 안에서 한번 더 '프로그램 소개 / 드라마 소개' 버튼을 눌러줘야 한다.
                // 그냥 '프로그램 소개' 누르면 바로 가는 경우도 있고 그 밑에 하위 메뉴가 있어서 그걸 눌러줘야 하는 경우도 있다.
                // '프로그램 소개' 가 첫번째 탭이 아닌 경우도 있다.... 그래서 nth-child(1) 이 아니라 전부 다 보고 '소개' 라는 단어 들어간거 찾아야 할 듯
                Elements intro_search = doc_detail.select("ul.program_lnb");
                Elements intro_button = intro_search.select("li.plnb_inner:nth-child(1)");
                System.out.println(intro_button);
                String intro_page_url = "";

                if (intro_button.select("a.plnb_link").attr("href").equals("#")){ // 하위 메뉴가 있다는 뜻.
                    intro_page_url = intro_button.select("ul.program_snb > li.psnb_inner:nth-child(1) > a").attr("href");
                } else{
                    intro_page_url = intro_button.attr("href");
                }

                System.out.println(intro_page_url);
                driver.get(intro_page_url);

                Document doc_detail_intro = null;
                try { // 프로그램 소개 페이지로 들어감
                    doc_detail_intro = Jsoup.parse(driver.getPageSource());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                Elements title_area = doc_detail_intro.select("div.pheader_inner");

                String title = title_area.select("div > h2 > a > em").text();
                System.out.println(title);

                // 어떤 프로그램은 또 년월일이 아니라 .으로 구분하는 경우도 있고 '~' 가 없는 경우도 있다....
                String[] date = doc_detail_intro.select("ul.pi_detail_list > li.pidl_inner:nth-child(1) > span").text().split("~");
                String start_date_year = date[0].split("년")[0];
                String start_date_month = date[0].split("년")[1].split("월")[0].replace(" ", "");
                String start_date_day = date[0].split("년")[1].split("월")[1].split("일")[0].replace(" ", "");

                // 굳이 시각까지?
                LocalDateTime start_date = LocalDateTime.of(Integer.parseInt(start_date_year), 
                                                            Integer.parseInt(start_date_month), 
                                                            Integer.parseInt(start_date_day), 9, 0, 0); // 서버 시차 이슈때문에 9시로 설정

                LocalDateTime end_date = null;
                if (date.length > 1 && date[1].length() > 1){ // 종영된 프로그램일 경우에만 실행. 아니라면 그냥 null로 둔다
                    String end_date_year = date[1].split("년")[0];
                    String end_date_month = date[1].split("년")[1].split("월")[0];
                    String end_date_day = date[1].split("년")[1].split("월")[1].split("일")[0];

                    end_date = LocalDateTime.of(Integer.parseInt(end_date_year), 
                                                            Integer.parseInt(end_date_month), 
                                                            Integer.parseInt(end_date_day), 9, 0, 0);
                }

                String director = "";
                String cast = "";

                String description = doc_detail_intro.select("div.tabmenu_contents_progintro1 > div.tmc_inner > div.tmc_textarea > div.tmct_text").text();
                String broadcast_time = title_area.select("div > span.pheader_info_date").text();

                Program p = new Program();
                p.setName(title);
                p.setBroadcaster(broadcaster);
                p.setGenre(genre);
                p.setSeason(1);
                p.setAlias("");
                p.setStart_date(start_date);
                p.setEnd_date(end_date);
                p.setDirector(director);
                p.setCast(cast);
                p.setDescription(description);
                p.setBroadcast_time(broadcast_time);
                p.setThumbnail(thumbnail_url);

                tester.saveProgram(p);
            }
        }
        
    }
}