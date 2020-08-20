package com.web.curation.controller.like;

import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;

import com.web.curation.dao.like.LikeDao;
import com.web.curation.model.BasicResponse;
import com.web.curation.model.alert.Alert;
import com.web.curation.model.like.Like;
import com.web.curation.model.user.User;
import com.web.curation.service.FirebaseDao;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.beans.factory.annotation.Autowired;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@ApiResponses(
    value = {
        @ApiResponse(code = 401, message = "Unauthorized", response = BasicResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = BasicResponse.class),
        @ApiResponse(code = 404, message = "Not Found", response = BasicResponse.class),
        @ApiResponse(code = 500, message = "Failure", response = BasicResponse.class)
    }
)

@CrossOrigin(origins = { "*" })
@RestController
public class LikeController {
    
    @Autowired
    private LikeDao dao;
    @Autowired
    private FirebaseDao alertService;

    // Create
    // 좋아요 추가에 대해 모든 요청이 공통적으로 수행할 일
    private Object addLike(Like like, HttpServletRequest httpReq, LikeDaoFunction daoFunc) {
        // 반환할 응답 객체
        final BasicResponse result = new BasicResponse();

        like.setUno(((User) httpReq.getAttribute("User")).getUno());

        // 좋아요 추가
        int n = daoFunc.addLike(like, dao);

        // n 이 1 이 아니면 쿼리 수행 결과에 이상이 있는 것
        if(n != 1) {
            result.status = false;
            result.msg = "Insert 쿼리 수행 결과에 이상이 발생했습니다.(" + n + ")";
            return new ResponseEntity<>(result, HttpStatus.OK);
        }

        // 좋아요 추가 완료
        result.status = true;
        result.msg = "success";
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    interface LikeDaoFunction {
        int addLike(Like like, LikeDao dao);
    }

    @PostMapping("/like/program/create")
    @ApiOperation(value = "프로그램 좋아요 추가")
    public Object addProgramLike(@RequestBody Like like, HttpServletRequest httpReq) {
        return addLike(like, httpReq, (param_like, dao) -> dao.addProgramLike(param_like));
    }

    @PostMapping("/like/preply/create")
    @ApiOperation(value = "프로그램 댓글 좋아요 추가")
    public Object addProgramReplyLike(@RequestBody Like like, HttpServletRequest httpReq) {
        Alert alert = new Alert();
        alert.setSubject_no(((User) httpReq.getAttribute("User")).getUno());

        if(alert.getSubject_no() != like.getUno()) {
            alert.setUno(like.getUno());
            // 알림 타입(1: 좋아요, 2: 댓글, 3: 언급)
            alert.setAtype(1);
            // 글 타입(1: 피드, 2: 피드댓글, 3: 프로그램댓글, 4: 에피소드댓글)
            alert.setCtype(3);
            alert.setCno(like.getAno()); // 좋아요가 달린 글 번호(댓글 번호 아님)
            alert.setRead(false);
            alert.setTime(LocalDateTime.now());
            alertService.addAlert(alert);
        }
        return addLike(like, httpReq, (param_like, dao) -> dao.addProgramReplyLike(param_like));
    }

    @PostMapping("/like/episode/create")
    @ApiOperation(value = "에피소드 좋아요 추가")
    public Object addEpisodeLike(@RequestBody Like like, HttpServletRequest httpReq) {
        return addLike(like, httpReq, (param_like, dao) -> dao.addEpisodeLike(param_like));
    }

    @PostMapping("/like/ereply/create")
    @ApiOperation(value = "에피소드 댓글 좋아요 추가")
    public Object addEpisodeReplyLike(@RequestBody Like like, HttpServletRequest httpReq) {
        Alert alert = new Alert();
        alert.setSubject_no(((User) httpReq.getAttribute("User")).getUno());

        if(alert.getSubject_no() != like.getUno()) {
            alert.setUno(like.getUno());
            // 알림 타입(1: 좋아요, 2: 댓글, 3: 언급)
            alert.setAtype(1);
            // 글 타입(1: 피드, 2: 피드댓글, 3: 프로그램댓글, 4: 에피소드댓글)
            alert.setCtype(4);
            alert.setCno(like.getAno()); // 좋아요가 달린 글 번호(댓글 번호 아님)
            alert.setRead(false);
            alert.setTime(LocalDateTime.now());
            alertService.addAlert(alert);
        }

        return addLike(like, httpReq, (param_like, dao) -> dao.addEpisodeReplyLike(param_like));
    }

    @PostMapping("/like/feed/create")
    @ApiOperation(value = "피드 좋아요 추가")
    public Object addFeedLike(@RequestBody Like like, HttpServletRequest httpReq) {
        Alert alert = new Alert();
        alert.setSubject_no(((User) httpReq.getAttribute("User")).getUno());
        
        if(alert.getSubject_no() != like.getUno()) {
            alert.setUno(like.getUno());
            // 알림 타입(1: 좋아요, 2: 댓글, 3: 언급)
            alert.setAtype(1);
            // 글 타입(1: 피드, 2: 피드댓글, 3: 프로그램댓글, 4: 에피소드댓글)
            alert.setCtype(1);
            alert.setCno(like.getTno());
            alert.setRead(false);
            alert.setTime(LocalDateTime.now());
            alertService.addAlert(alert);
        }

        return addLike(like, httpReq, (param_like, dao) -> dao.addFeedLike(param_like));
    }

    @PostMapping("/like/freply/create")
    @ApiOperation(value = "피드 댓글 좋아요 추가")
    public Object addFeedReplyLike(@RequestBody Like like, HttpServletRequest httpReq) {
        Alert alert = new Alert();
        alert.setSubject_no(((User) httpReq.getAttribute("User")).getUno());

        if(alert.getSubject_no() != like.getUno()) {
            alert.setUno(like.getUno());
            // 알림 타입(1: 좋아요, 2: 댓글, 3: 언급)
            alert.setAtype(1);
            // 글 타입(1: 피드, 2: 피드댓글, 3: 프로그램댓글, 4: 에피소드댓글)
            alert.setCtype(2);
            alert.setCno(like.getAno()); // 좋아요가 달린 글 번호(댓글 번호 아님)
            alert.setRead(false);
            alert.setTime(LocalDateTime.now());
            alertService.addAlert(alert);
        }
        
        return addLike(like, httpReq, (param_like, dao) -> dao.addFeedReplyLike(param_like));
    }

    // Delete
    // 좋아요 취소에 대해 공통적으로 수행할 일
    private Object deleteLike(Like like, HttpServletRequest httpReq, LikeDaoDeleteFunction daoFunc) {
        // 반환할 응답 객체
        final BasicResponse result = new BasicResponse();

        like.setUno(((User) httpReq.getAttribute("User")).getUno());

        // 좋아요 취소
        int n = daoFunc.deleteLike(like, dao);

        // n 이 1 이 아니면 쿼리 수행 결과에 이상이 있는 것
        if(n != 1) {
            result.status = false;
            result.msg = "Delete 쿼리 수행 결과에 이상이 발생했습니다.(" + n + ")";
            return new ResponseEntity<>(result, HttpStatus.OK);
        }

        // 좋아요 취소 완료
        result.status = true;
        result.msg = "success";
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    interface LikeDaoDeleteFunction {
        int deleteLike(Like like, LikeDao dao);
    }

    @PostMapping("/like/program/delete")
    @ApiOperation(value = "프로그램 좋아요 취소")
    public Object deleteProgramLike(@RequestBody Like like, HttpServletRequest httpReq) {
        return deleteLike(like, httpReq, (param_like, dao) -> dao.deleteProgramLike(param_like));
    }

    @PostMapping("/like/preply/delete")
    @ApiOperation(value = "프로그램 댓글 좋아요 취소")
    public Object deleteProgramReplyLike(@RequestBody Like like, HttpServletRequest httpReq) {
        return deleteLike(like, httpReq, (param_like, dao) -> dao.deleteProgramReplyLike(param_like));
    }

    @PostMapping("/like/episode/delete")
    @ApiOperation(value = "에피소드 좋아요 취소")
    public Object deleteEpisodeLike(@RequestBody Like like, HttpServletRequest httpReq) {
        return deleteLike(like, httpReq, (param_like, dao) -> dao.deleteEpisodeLike(param_like));
    }

    @PostMapping("/like/ereply/delete")
    @ApiOperation(value = "에피소드 댓글 좋아요 취소")
    public Object deleteEpisodeReplyLike(@RequestBody Like like, HttpServletRequest httpReq) {
        return deleteLike(like, httpReq, (param_like, dao) -> dao.deleteEpisodeReplyLike(param_like));
    }

    @PostMapping("/like/feed/delete")
    @ApiOperation(value = "피드 좋아요 취소")
    public Object deleteFeedLike(@RequestBody Like like, HttpServletRequest httpReq) {
        return deleteLike(like, httpReq, (param_like, dao) -> dao.deleteFeedLike(param_like));
    }

    @PostMapping("/like/freply/delete")
    @ApiOperation(value = "피드 댓글 좋아요 취소")
    public Object deleteFeedReplyLike(@RequestBody Like like, HttpServletRequest httpReq) {
        return deleteLike(like, httpReq, (param_like, dao) -> dao.deleteFeedReplyLike(param_like));
    }
}