package com.web.curation;

import java.io.IOException;
import java.net.MalformedURLException;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.LogManager;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.web.curation.dao.program.ProgramDao;
import com.web.curation.model.program.Program;

import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;

class ProgramSaveTest {
    @Autowired
    ProgramDao dao;

    public void saveProgram(Program p) {
        dao.addNewProgram(p);
    }
}

public class SBSCrawlingAndSaveTest {
    public static void main(String[] args) {

        System.out.println("Hello");
        java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(java.util.logging.Level.OFF);
        java.util.logging.Logger.getLogger("org.apache.http").setLevel(java.util.logging.Level.OFF);

        // SBS 크롤링 테스트
        // 드라마 - 예능 - 교양 순서대로 긁어본다
        String[] categories = { "drama", "enter", "culture" };
        ProgramSaveTest tester = new ProgramSaveTest();

        for (String category : categories) {
            System.out.println(category);

            String url = "https://www.sbs.co.kr/tv/" + category;
            Document doc = null;

            WebClient webClient = new WebClient(BrowserVersion.CHROME);
            webClient.getOptions().setJavaScriptEnabled(true);
            webClient.getOptions().setThrowExceptionOnScriptError(false);
            webClient.waitForBackgroundJavaScript(3000);
            HtmlPage page = null;
            try {
                page = webClient.getPage(url);
            } catch (Exception e){
                e.printStackTrace();
            }

            doc = Jsoup.parse(page.asXml());

            /*
            try {
                doc = Jsoup.connect(url).get();
            } catch (Exception e) {
                e.printStackTrace();
            }

            System.out.println(doc);
            */

            String broadcaster = "SBS";
            String genre = doc.select("a[href=" + url + "] > span").text();

            System.out.println(genre);

            
            Elements program_list = doc.select("ul.main_programtab_list");

            System.out.println(program_list);

            Iterator<Element> programs = program_list.select("li.main_programtab_list_inner").iterator();
            while (programs.hasNext()){
                Element program = programs.next();
                String program_link = program.select("div.module_box_w > a.mb_inner").attr("href");
                String thumbnail_url = program.select("img.mb_image").attr("src");

                System.out.println(program_link);

                Document doc_detail = null;
                try { // 링크 타고 세부 페이지 들어가서 세부정보 긁기.
                    doc_detail = Jsoup.connect(program_link).get();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Elements title_area = doc_detail.select("div.pheader_inner");

                String title = title_area.select("div > h2 > a > span > em").text();

                String[] date = doc_detail.select("ul.pi_detail_list > li.pidl_inner:nth-child(1) > span").text().split("~");
                String start_date_year = date[0].split("년")[0];
                String start_date_month = date[0].split("년")[1].split("월")[0];
                String start_date_day = date[0].split("년")[1].split("월")[1].split("일")[0];

                LocalDateTime start_date = LocalDateTime.of(Integer.parseInt(start_date_year), 
                                                            Integer.parseInt(start_date_month), 
                                                            Integer.parseInt(start_date_day), 0, 0, 0);

                String end_date_year = date[1].split("년")[0];
                String end_date_month = date[1].split("년")[1].split("월")[0];
                String end_date_day = date[1].split("년")[1].split("월")[1].split("일")[0];

                LocalDateTime end_date = LocalDateTime.of(Integer.parseInt(end_date_year), 
                                                            Integer.parseInt(end_date_month), 
                                                            Integer.parseInt(end_date_day), 0, 0, 0);

                String director = "";
                String cast = "";

                String description = doc_detail.select("div.tabmenu_contents_progintro1 > div.tmc_inner > div.tmc_textarea > div.tmct_text").text();
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