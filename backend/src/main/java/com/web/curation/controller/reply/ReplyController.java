package com.web.curation.controller.reply;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.web.curation.dao.reply.EpisodeReplyDao;
import com.web.curation.dao.reply.FeedReplyDao;
import com.web.curation.dao.reply.ProgramReplyDao;
import com.web.curation.dao.reply.ReplyDao;
import com.web.curation.model.BasicResponse;
import com.web.curation.model.reply.Reply;
import com.web.curation.model.reply.ReplyRequest;
import com.web.curation.model.reply.ReplyResponse;

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

    ////////////////////////////////////////////////////////////////////
    ///////////////////////////// Create ///////////////////////////////
    ////////////////////////////////////////////////////////////////////

    // 댓글 추가에 대해 모든 요청이 공통적으로 수행할 일
    private Object createReply(Reply reply, ReplyDao dao, ReplyCreateFunc createFunc) {
        // 반환할 응답 객체
        final BasicResponse result = new BasicResponse();

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
    public Object createProgramReply(@RequestBody Reply reply) {
        return createReply(reply, programReplyDao, (param_reply, dao) -> dao.createReply(param_reply));
    }

    @PostMapping("/reply/episode/create")
    @ApiOperation(value = "에피소드 댓글 추가")
    public Object createEpisodeReply(@RequestBody Reply reply) {
        return createReply(reply, episodeReplyDao, (param_reply, dao) -> dao.createReply(param_reply));
    }

    @PostMapping("/reply/feed/create")
    @ApiOperation(value = "피드 댓글 추가")
    public Object createFeedReply(@RequestBody Reply reply) {
        return createReply(reply, feedReplyDao, (param_reply, dao) -> dao.createReply(param_reply));
    }

    ////////////////////////////////////////////////////////////////////
    ///////////////////////////// Read /////////////////////////////////
    ////////////////////////////////////////////////////////////////////
    private Object readReply(ReplyRequest req, ReplyDao dao, ReplyReadFunc readFunc) {
        // 반환할 응답 객체
        final BasicResponse result = new BasicResponse();

        // select 조건을 위한 맵
        HashMap<String, Object> param = new HashMap<>();

        // start point 계산, select 조건 생성
        int startPoint = (req.getNum() - 1) * 20;
        param.put("start", startPoint);
        param.put("no", req.getNo());

        // 댓글 조회
        List<Reply> list = readFunc.readReply(param, dao);

        // 조회한 데이터의 크기
        int listSize = list.size();

        // 조회한 데이터가 있다면
        ArrayList<ReplyResponse> response = new ArrayList<>();
        if(listSize > 0) {
            // 댓글 하나의 정보를 담을 객체
            ReplyResponse row = new ReplyResponse();
            // 최대 2명의 좋아요 한 사람만 표시
            int like_cnt = 0;
            
            // 모든 데이터에 대해 반복
            for(Reply reply : list) {
                // 현재 작업 중인 댓글과 동일한 댓글이라면
                if(row.getNo() == reply.getNo()) {
                    // 현재 로그인 한 유저가 좋아요를 누른 댓글인지 확인
                    if(!row.getPress_like() && row.getLike_num() > 0) {
                        if(reply.getLiker_uno() == req.getUno()) {
                            row.setPress_like(true);
                        }
                    }

                    // 좋아요 누른 사람 최대 2명의 닉네임 반환해야함
                    if(row.getLike_num() > 0 && like_cnt < 2) {
                        row.appendNickName(reply.getLiker_nick_name());
                        like_cnt++;
                    }
                }
                // 다른 댓글이라면
                else {
                    // 작업 중이던 이전 댓글이 있다면
                    if(row.getNo() != 0) {
                        // 응답 리스트에 작업중이던 댓글 정보 넣기
                        response.add(row);
                    }

                    // like_cnt 초기화
                    like_cnt = 0;

                    // 새 댓글 객체 만들기
                    row = new ReplyResponse();
                    // 새 댓글 객체에 정보 넣기
                    row.setReply(reply);

                    // 현재 로그인 한 유저가 좋아요를 누른 댓글인지 확인
                    if(!row.getPress_like() && row.getLike_num() > 0) {
                        if(reply.getLiker_uno() == req.getUno()) {
                            row.setPress_like(true);
                        }
                    }

                    // 좋아요 누른 사람 최대 2명의 닉네임 반환해야함
                    if(row.getLike_num() > 0 && like_cnt < 2) {
                        row.appendNickName(reply.getLiker_nick_name());
                        like_cnt++;
                    }
                }
            }
            // 만약 리스트에 안넣은 row 가 남았으면 그거도 넣기
            if(row.getNo() > 0) {
                response.add(row);
            }
        }

        // 댓글 조회 완료
        result.status = true;
        result.msg = "success";
        result.data = response;
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    interface ReplyReadFunc {
        List<Reply> readReply(Map<String, Object> param, ReplyDao dao);
    }

    @PostMapping("/reply/program/read")
    @ApiOperation(value = "프로그램 댓글 조회")
    public Object readProgramReply(@RequestBody ReplyRequest req) {
        return readReply(req, programReplyDao, (param, dao) -> dao.getReplyList(param));
    }

    @PostMapping("/reply/episode/read")
    @ApiOperation(value = "에피소드 댓글 조회")
    public Object readEpisodeReply(@RequestBody ReplyRequest req) {
        return readReply(req, episodeReplyDao, (param, dao) -> dao.getReplyList(param));
    }

    @PostMapping("/reply/feed/read")
    @ApiOperation(value = "피드 댓글 조회")
    public Object readFeedReply(@RequestBody ReplyRequest req) {
        return readReply(req, feedReplyDao, (param, dao) -> dao.getReplyList(param));
    }

    ////////////////////// 위는 댓글 조회, 아래는 대댓글 조회 ///////////////////////////

    @PostMapping("/rereply/program/read")
    @ApiOperation(value = "프로그램 대댓글 조회")
    public Object readProgramReReply(@RequestBody ReplyRequest req) {
        return readReply(req, programReplyDao, (param, dao) -> dao.getReReplyList(param));
    }

    @PostMapping("/rereply/episode/read")
    @ApiOperation(value = "에피소드 대댓글 조회")
    public Object readEpisodeReReply(@RequestBody ReplyRequest req) {
        return readReply(req, episodeReplyDao, (param, dao) -> dao.getReReplyList(param));
    }

    @PostMapping("/rereply/feed/read")
    @ApiOperation(value = "피드 대댓글 조회")
    public Object readFeedReReply(@RequestBody ReplyRequest req) {
        return readReply(req, feedReplyDao, (param, dao) -> dao.getReReplyList(param));
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