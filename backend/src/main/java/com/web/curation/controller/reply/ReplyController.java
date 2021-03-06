package com.web.curation.controller.reply;

import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.web.curation.dao.reply.EpisodeReplyDao;
import com.web.curation.dao.reply.FeedReplyDao;
import com.web.curation.dao.reply.ProgramReplyDao;
import com.web.curation.dao.reply.ReplyDao;
import com.web.curation.model.BasicResponse;
import com.web.curation.model.alert.Alert;
import com.web.curation.model.reply.Reply;
import com.web.curation.model.reply.ReplyRequest;
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
public class ReplyController {
    
    @Autowired
    private EpisodeReplyDao episodeReplyDao;
    @Autowired
    private FeedReplyDao    feedReplyDao;
    @Autowired
    private ProgramReplyDao programReplyDao;
    @Autowired
    private FirebaseDao alertService;
    ////////////////////////////////////////////////////////////////////
    ///////////////////////////// Create ///////////////////////////////
    ////////////////////////////////////////////////////////////////////

    // 댓글 추가에 대해 모든 요청이 공통적으로 수행할 일
    private Object createReply(Reply reply, HttpServletRequest httpReq, ReplyDao dao, ReplyCreateFunc createFunc) {
        // 반환할 응답 객체
        final BasicResponse result = new BasicResponse();

        // 댓글 글쓴이는 토큰에서 뽑아온 정보를 활용
        reply.setWriter_uno(((User) httpReq.getAttribute("User")).getUno());

        // 댓글 추가
        int n = createFunc.createReply(reply, dao);

        // n 이 1 이 아니면 쿼리 수행 결과에 이상이 있는 것
        if(n != 1) {
            result.status = false;
            result.msg = "Insert 쿼리 수행 결과에 이상이 발생했습니다.(" + n + ")";
            return new ResponseEntity<>(result, HttpStatus.OK);
        }

        // 댓글 추가 완료
        result.status = true;
        result.msg = "success";
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    interface ReplyCreateFunc {
        int createReply(Reply reply, ReplyDao dao);
    }

    @PostMapping("/reply/program/create")
    @ApiOperation(value = "프로그램 댓글 추가")
    public Object createProgramReply(@RequestBody Reply reply, HttpServletRequest httpReq) {
        // 대댓글일때
        if(reply.getParent_reply() != 0){
            Alert alert = new Alert();
            alert.setSubject_no(((User) httpReq.getAttribute("User")).getUno());

            if(alert.getSubject_no() != reply.getWriter_uno()) {
                alert.setUno(reply.getWriter_uno());
                // 알림 타입(1: 좋아요, 2: 댓글, 3: 언급)
                alert.setAtype(2);
                // 글 타입(1: 피드, 2: 피드댓글, 3: 프로그램댓글, 4: 에피소드댓글)
                alert.setCtype(3);
                // alert.setCno(reply.getParent_reply());
                alert.setCno(reply.getNo()); // 댓글의 경우 부모 댓글의 번호가 아니라 그 글의 번호를 줌
                alert.setRead(false);
                alert.setTime(LocalDateTime.now());
                alertService.addAlert(alert);
            }
        }
        return createReply(reply, httpReq, programReplyDao, (param_reply, dao) -> dao.createReply(param_reply));
    }

    @PostMapping("/reply/episode/create")
    @ApiOperation(value = "에피소드 댓글 추가")
    public Object createEpisodeReply(@RequestBody Reply reply, HttpServletRequest httpReq) {
        if(reply.getParent_reply() != 0){
            Alert alert = new Alert();
            alert.setSubject_no(((User) httpReq.getAttribute("User")).getUno());

            if(alert.getSubject_no() != reply.getWriter_uno()) {
                alert.setUno(reply.getWriter_uno());
                // 알림 타입(1: 좋아요, 2: 댓글, 3: 언급)
                alert.setAtype(2);
                // 글 타입(1: 피드, 2: 피드댓글, 3: 프로그램댓글, 4: 에피소드댓글)
                alert.setCtype(4);
                // alert.setCno(reply.getParent_reply());
                alert.setCno(reply.getNo());
                alert.setRead(false);
                alert.setTime(LocalDateTime.now());
                alertService.addAlert(alert);
            }
        }
        
        return createReply(reply, httpReq, episodeReplyDao, (param_reply, dao) -> dao.createReply(param_reply));
    }

    @PostMapping("/reply/feed/create")
    @ApiOperation(value = "피드 댓글 추가")
    public Object createFeedReply(@RequestBody Reply reply, HttpServletRequest httpReq) {
        Alert alert = new Alert();
        alert.setSubject_no(((User) httpReq.getAttribute("User")).getUno());

        if(alert.getSubject_no() != reply.getWriter_uno()) {
            alert.setUno(reply.getWriter_uno());
            // 알림 타입(1: 좋아요, 2: 댓글, 3: 언급)
            alert.setAtype(2);
            alert.setCno(reply.getNo());
            
            // 대댓일때
            if(reply.getParent_reply() != 0){
                // 글 타입(1: 피드, 2: 피드댓글, 3: 프로그램댓글, 4: 에피소드댓글)
                alert.setCtype(2);
            }
            // 댓글일때
            else{
                // 글 타입(1: 피드, 2: 피드댓글, 3: 프로그램댓글, 4: 에피소드댓글)
                alert.setCtype(1);
            }
            alert.setRead(false);
            alert.setTime(LocalDateTime.now());
            alertService.addAlert(alert);
        }

        return createReply(reply, httpReq, feedReplyDao, (param_reply, dao) -> dao.createReply(param_reply));
    }

    ////////////////////////////////////////////////////////////////////
    ///////////////////////////// Read /////////////////////////////////
    ////////////////////////////////////////////////////////////////////
    private Object readReply(ReplyRequest replyReq, HttpServletRequest httpReq, ReplyDao dao, ReplyReadFunc readFunc) {
        // 반환할 응답 객체
        final BasicResponse result = new BasicResponse();

        // req 에 유저 번호 정보 넣기
        replyReq.setUno(((User) httpReq.getAttribute("User")).getUno());

        // start point 계산(limit)
        replyReq.setNum((replyReq.getNum() - 1) * 20);

        // 댓글 조회
        List<Reply> list = readFunc.readReply(replyReq, dao);
        
        // 댓글 조회 완료
        result.status = true;
        result.msg = "success";
        result.data = list;
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    interface ReplyReadFunc {
        List<Reply> readReply(ReplyRequest req, ReplyDao dao);
    }

    @PostMapping("/reply/program/read")
    @ApiOperation(value = "프로그램 댓글 조회")
    public Object readProgramReply(@RequestBody ReplyRequest replyReq, HttpServletRequest httpReq) {
        return readReply(replyReq, httpReq, programReplyDao, (param, dao) -> dao.getReplyList(param));
    }

    @PostMapping("/reply/episode/read")
    @ApiOperation(value = "에피소드 댓글 조회")
    public Object readEpisodeReply(@RequestBody ReplyRequest replyReq, HttpServletRequest httpReq) {
        return readReply(replyReq, httpReq, episodeReplyDao, (param, dao) -> dao.getReplyList(param));
    }

    @PostMapping("/reply/feed/read")
    @ApiOperation(value = "피드 댓글 조회")
    public Object readFeedReply(@RequestBody ReplyRequest replyReq, HttpServletRequest httpReq) {
        return readReply(replyReq, httpReq, feedReplyDao, (param, dao) -> dao.getReplyList(param));
    }

    ////////////////////// 위는 댓글 조회, 아래는 대댓글 조회 ///////////////////////////

    @PostMapping("/rereply/program/read")
    @ApiOperation(value = "프로그램 대댓글 조회")
    public Object readProgramReReply(@RequestBody ReplyRequest replyReq, HttpServletRequest httpReq) {
        return readReply(replyReq, httpReq, programReplyDao, (param, dao) -> dao.getReReplyList(param));
    }

    @PostMapping("/rereply/episode/read")
    @ApiOperation(value = "에피소드 대댓글 조회")
    public Object readEpisodeReReply(@RequestBody ReplyRequest replyReq, HttpServletRequest httpReq) {
        return readReply(replyReq, httpReq, episodeReplyDao, (param, dao) -> dao.getReReplyList(param));
    }

    @PostMapping("/rereply/feed/read")
    @ApiOperation(value = "피드 대댓글 조회")
    public Object readFeedReReply(@RequestBody ReplyRequest replyReq, HttpServletRequest httpReq) {
        return readReply(replyReq, httpReq, feedReplyDao, (param, dao) -> dao.getReReplyList(param));
    }

    ////////////////////////////////////////////////////////////////////
    ///////////////////////////// Update ///////////////////////////////
    ////////////////////////////////////////////////////////////////////
    private Object updateReply(Reply reply, ReplyDao dao, ReplyUpdateFunc updateFunc) {
        // 반환할 응답 객체
        final BasicResponse result = new BasicResponse();

        // 댓글 수정
        int n = updateFunc.updateReply(reply, dao);

        // n 이 1 이 아니면 쿼리 수행 결과에 이상이 있는 것
        if(n != 1) {
            result.status = false;
            result.msg = "Update 쿼리 수행 결과에 이상이 발생했습니다.(" + n + ")";
            return new ResponseEntity<>(result, HttpStatus.OK);
        }

        // 댓글 수정 완료
        result.status = true;
        result.msg = "success";
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    interface ReplyUpdateFunc {
        int updateReply(Reply reply, ReplyDao dao);
    }

    ////////////////////////////////////////////// 댓글 수정 파트 ///////////////////////////////////////
    @PostMapping("/reply/program/update")
    @ApiOperation(value = "프로그램 댓글 수정")
    public Object updateProgramReply(@RequestBody Reply reply) {
        return updateReply(reply, programReplyDao, (param_reply, dao) -> dao.modifyReply(param_reply));
    }

    @PostMapping("/reply/episode/update")
    @ApiOperation(value = "에피소드 댓글 수정")
    public Object updateEpisodeReply(@RequestBody Reply reply) {
        return updateReply(reply, episodeReplyDao, (param_reply, dao) -> dao.modifyReply(param_reply));
    }

    @PostMapping("/reply/feed/update")
    @ApiOperation(value = "피드 댓글 수정")
    public Object updateFeedReply(@RequestBody Reply reply) {
        return updateReply(reply, feedReplyDao, (param_reply, dao) -> dao.modifyReply(param_reply));
    }
    //////////////////////////////////////////// 댓글 숨김 파트 ////////////////////////////////////////
    @PostMapping("/reply/program/hide")
    @ApiOperation(value = "프로그램 댓글 숨김/해제")
    public Object setHideProgramReply(@RequestBody Reply reply) {
        return updateReply(reply, programReplyDao, (param_reply, dao) -> dao.setHideReply(param_reply));
    }

    @PostMapping("/reply/episode/hide")
    @ApiOperation(value = "에피소드 댓글 숨김/해제")
    public Object setHideEpisodeReply(@RequestBody Reply reply) {
        return updateReply(reply, episodeReplyDao, (param_reply, dao) -> dao.setHideReply(param_reply));
    }

    @PostMapping("/reply/feed/hide")
    @ApiOperation(value = "피드 댓글 숨김/해제")
    public Object setHideFeedReply(@RequestBody Reply reply) {
        return updateReply(reply, feedReplyDao, (param_reply, dao) -> dao.setHideReply(param_reply));
    }

    ////////////////////////////////////////////////////////////////////
    ///////////////////////////// Delete ///////////////////////////////
    ////////////////////////////////////////////////////////////////////
    private Object deleteReply(int no, ReplyDao dao, ReplyDeleteFunc deleteFunc) {
        // 반환할 응답 객체
        final BasicResponse result = new BasicResponse();

        // 댓글 삭제
        int n = deleteFunc.deleteReply(no, dao);

        // n 이 1 이 아니면 쿼리 수행 결과에 이상이 있는 것
        if(n != 1) {
            result.status = false;
            result.msg = "Delete 쿼리 수행 결과에 이상이 발생했습니다.(" + n + ")";
            return new ResponseEntity<>(result, HttpStatus.OK);
        }

        // 댓글 삭제 완료
        result.status = true;
        result.msg = "success";
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    interface ReplyDeleteFunc {
        int deleteReply(int no, ReplyDao dao);
    }

    @PostMapping("/reply/program/delete")
    @ApiOperation(value = "프로그램 댓글 삭제")
    public Object deleteProgramReply(@RequestBody Reply reply) {
        return deleteReply(reply.getNo(), programReplyDao, (no, dao) -> dao.deleteReply(no));
    }

    @PostMapping("/reply/episode/delete")
    @ApiOperation(value = "에피소드 댓글 삭제")
    public Object deleteEpisodeReply(@RequestBody Reply reply) {
        return deleteReply(reply.getNo(), episodeReplyDao, (no, dao) -> dao.deleteReply(no));
    }

    @PostMapping("/reply/feed/delete")
    @ApiOperation(value = "피드 댓글 삭제")
    public Object deleteFeedReply(@RequestBody Reply reply) {
        return deleteReply(reply.getNo(), feedReplyDao, (no, dao) -> dao.deleteReply(no));
    }
}