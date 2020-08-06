package com.web.curation.controller.search;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.web.curation.dao.search.SearchDao;
import com.web.curation.model.BasicResponse;
import com.web.curation.model.feed.Feed;
import com.web.curation.model.feed.Tag;
import com.web.curation.model.search.SearchRequest;
import com.web.curation.model.user.User;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.beans.factory.annotation.Autowired;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiOperation;


@ApiResponses(
    value = {
        @ApiResponse(code = 401, message = "Unauthorized", response = BasicResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = BasicResponse.class),
        @ApiResponse(code = 404, message = "Not Found", response = BasicResponse.class),
        @ApiResponse(code = 500, message = "Failure", response = BasicResponse.class)
    }
)

// http://localhost:3000
@CrossOrigin(origins = { "*" })
@RestController
public class SearchController {

    @Autowired
    SearchDao searchDao;

    static String BASE_URL = "https://api.themoviedb.org/3/";
    static String IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500";
    static String API_KEY = "1436d388221956af7b6cd27a6a7ec9d8";

    ////////////////////////////////////////
    //              User 영역
    ////////////////////////////////////////

    @GetMapping("/search/nickname")
    @ApiOperation(value = "닉네임 검색")
    public Object searchByNickName(@RequestParam(required = true) final String str){
        List<User> list = searchDao.searchByNickName(str);
        final BasicResponse result = new BasicResponse();
        result.status = true;
        result.msg = "success";
        result.data = list;
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/search/all/user")
    @ApiOperation(value = "모든 유저 목록 검색")
    public Object searchAllUser() {
        List<User> list = searchDao.getAllUser();
        final BasicResponse result = new BasicResponse();
        result.status = true;
        result.msg = "success";
        result.data = list;
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/search/history/save")
    @ApiOperation(value = "유저 검색 히스토리 저장")
    public Object saveHistory(@RequestBody SearchRequest req, HttpServletRequest request) {
        int uno = ((User) request.getAttribute("User")).getUno();
        req.setUno(uno);

        int n = searchDao.addHistory(req);

        if(n != 1) {
            final BasicResponse result = new BasicResponse();
            result.status = false;
            result.msg = "히스토리 저장 중 문제 발생(" + n + ")";
            return new ResponseEntity<>(result, HttpStatus.OK);
        }

        final BasicResponse result = new BasicResponse();
        result.status = true;
        result.msg = "success";
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/search/history/list")
    @ApiOperation(value = "유저 검색 히스토리 목록 조회")
    public Object getHistory(HttpServletRequest request) {
        int uno = ((User) request.getAttribute("User")).getUno();
        List<User> list = searchDao.getHistoryList(uno);

        final BasicResponse result = new BasicResponse();
        result.status = true;
        result.msg = "success";
        result.data = list;
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/search/history/delete")
    @ApiOperation(value = "유저 검색 히스토리 삭제")
    public Object deleteHistory(@RequestBody SearchRequest req) {
        int n = searchDao.deleteHistory(req);

        if(n != 1) {
            final BasicResponse result = new BasicResponse();
            result.status = false;
            result.msg = "히스토리 삭제 중 문제 발생(" + n + ")";
            return new ResponseEntity<>(result, HttpStatus.OK);
        }

        final BasicResponse result = new BasicResponse();
        result.status = true;
        result.msg = "success";
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/search/history/deleteall")
    @ApiOperation(value = "유저 검색 히스토리 목록 조회")
    public Object deleteAllHistory(HttpServletRequest request) {
        int uno = ((User) request.getAttribute("User")).getUno();
        searchDao.deleteAllHistory(uno);

        final BasicResponse result = new BasicResponse();
        result.status = true;
        result.msg = "success";
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    ////////////////////////////////////////
    //              Feed 영역
    ////////////////////////////////////////

    @GetMapping("/search/taglist")
    @ApiOperation(value = "피드 목록 검색")
    public Object searchByTagList(){
        //태그 목록 조회 -> 태그 디비 만들기
        List<Tag> list = searchDao.searchTagList();
        final BasicResponse result = new BasicResponse();
        result.status = true;
        result.msg = "success";
        result.data = list;
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/search/feed")
    @ApiOperation(value = "피드 태그 검색")
    public Object searchByFeedTag(@RequestParam(required = true) final String str, 
                                    @RequestParam(required = true) final int startnum,
                                    HttpServletRequest request){

        System.out.println(str + " " + startnum);
        
        // 피드 타임라인 볼 때 유저번호랑, 몇번 피드부터 불러올지 (무한스크롤 때문?) 알아야 함
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("uno", ((User)request.getAttribute("User")).getUno());
        map.put("num", startnum);
        map.put("str", str);

        List<Feed> list = searchDao.searchByFeedTag(map);
        final BasicResponse result = new BasicResponse();
        result.status = true;
        result.msg = "success";
        result.data = list;
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    ////////////////////////////////////////
    //              Episode 영역
    ////////////////////////////////////////

    @GetMapping("/search/episode")
    @ApiOperation(value = "에피소드 검색")
    public Object searchByEpisode(@RequestParam(required = true) final String str){
        final BasicResponse result = new BasicResponse();
        
        result.status = true;
        result.msg = "success";
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    
}