package com.web.curation.controller.message;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

import com.web.curation.model.BasicResponse;
import com.web.curation.model.message.ChatRoom;
import com.web.curation.service.FirebaseDao;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PutMapping;
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
    

    @PostMapping("/message/userinfo")
    @ApiOperation(value = "유저 정보 가져오기")
    public Object setReadAllAlert(@RequestBody List<ChatRoom> roomlist) {
        // 반환할 응답 객체
        final BasicResponse result = new BasicResponse();

        System.out.println();

        // if(!firebase.setReadAllAlert(uno)) {
        //     result.status = false;
        //     result.msg = "알림 읽음 처리 실패";
        //     return new ResponseEntity<>(result, HttpStatus.OK);
        // }

        // 알림 읽음 처리 완료
        result.status = true;
        result.msg = "success";
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}