package com.web.curation.controller.search;

import java.util.List;

import com.web.curation.dao.search.SearchDao;
import com.web.curation.model.BasicResponse;
import com.web.curation.model.feed.Feed;
import com.web.curation.model.user.User;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.CrossOrigin;
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

    @GetMapping("/search/taglist")
    @ApiOperation(value = "피드 목록 검색")
    public Object searchByTagList(@RequestParam(required = true) final String str){
        //태그 목록 조회 -> 태그 디비 만들기
        List<Feed> list = null;
        final BasicResponse result = new BasicResponse();
        result.status = true;
        result.msg = "success";
        result.data = list;
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/search/feed")
    @ApiOperation(value = "피드 태그 검색")
    public Object searchByFeedTag(@RequestParam(required = true) final String str){
        List<Feed> list = searchDao.searchByFeedTag(str);
        final BasicResponse result = new BasicResponse();
        result.status = true;
        result.msg = "success";
        // result.data = list;
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/search/episode")
    @ApiOperation(value = "에피소드 검색")
    public Object searchByEpisode(@RequestParam(required = true) final String str){
        final BasicResponse result = new BasicResponse();
        
        result.status = true;
        result.msg = "success";
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    
}