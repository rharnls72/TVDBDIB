package com.web.curation.controller.message;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

import com.web.curation.dao.message.MessageDao;
import com.web.curation.model.BasicResponse;
import com.web.curation.model.message.Message;
import com.web.curation.model.user.User;
import com.web.curation.service.MessageService;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestBody;


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
public class MessageController {
    @Autowired
    MessageDao messageDao;
    @Autowired
    MessageService messageService;

     //메시지 전송
     @PostMapping("/message/send")
     @ApiOperation(value = "메시지 전송")
     public Object sendMessage(@RequestBody Message msg) {
        final BasicResponse result = new BasicResponse();
        msg.setTime(LocalDateTime.now());
        
        if(!messageService.sendMessage(msg)) {
            result.status = false;
            result.msg = "메시지 전송 실패";
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        result.status = true;
        result.msg = "success";
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/message/userinfo")
    @ApiOperation(value = "유저 정보 가져오기")
    public Object getUserInfo(@RequestBody List<Integer> unolist) {
        // 반환할 응답 객체
        final BasicResponse result = new BasicResponse();
        List<User> userlist = null;
        if(unolist.size()>0){
            userlist =  messageDao.getUserInfo(unolist);
            if(userlist.size()==0) {
                result.status = false;
                result.msg = "유저 목록 조회 실패";
                return new ResponseEntity<>(result, HttpStatus.OK);
            }
        }

        result.status = true;
        result.data = userlist;
        result.msg = "success";
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    @DeleteMapping("/message/delete/chatroom/{cno}")
    @ApiOperation(value = "채팅룸 제거")
    public Object deleteChatroom(@PathVariable("cno") String cno) {
        final BasicResponse result = new BasicResponse();

        if(!messageService.deleteChatroom(cno)) {
            result.status = false;
            result.msg = "채팅룸 삭제 실패";
            return new ResponseEntity<>(result, HttpStatus.OK);
        }

        result.status = true;
        result.msg = "success";
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    @DeleteMapping("/message/delete/message/{mno}")
    @ApiOperation(value = "메시지 삭제")
    public Object deleteMessage(@PathVariable("mno") String mno) {
        final BasicResponse result = new BasicResponse();

        if(!messageService.deleteMessage(mno)) {
            result.status = false;
            result.msg = "메시지 삭제 실패";
            return new ResponseEntity<>(result, HttpStatus.OK);
        }

        result.status = true;
        result.msg = "success";
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}