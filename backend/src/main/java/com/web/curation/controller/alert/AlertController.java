package com.web.curation.controller.alert;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.beans.factory.annotation.Autowired;


import com.web.curation.model.BasicResponse;
import com.web.curation.service.FirebaseDao;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PutMapping;


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
public class AlertController {
    @Autowired
    FirebaseDao firebase;

    // Update
    @PutMapping("/alert/readall/{uno}")
    @ApiOperation(value = "모든 알림 읽음 처리")
    public Object setReadAllAlert(@PathVariable int uno) {
        // 반환할 응답 객체
        final BasicResponse result = new BasicResponse();

        if(!firebase.setReadAllAlert(uno)) {
            result.status = false;
            result.msg = "알림 읽음 처리 실패";
            return new ResponseEntity<>(result, HttpStatus.OK);
        }

        // 알림 읽음 처리 완료
        result.status = true;
        result.msg = "success";
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/alert/read/{ano}")
    @ApiOperation(value = "알림 읽음 처리")
    public Object setReadAlert(@PathVariable String ano) {
        // 반환할 응답 객체
        final BasicResponse result = new BasicResponse();

        if(!firebase.setReadAlert(ano)) {
            result.status = false;
            result.msg = "알림 읽음 처리 실패";
            return new ResponseEntity<>(result, HttpStatus.OK);
        }

        // 알림 읽음 처리 완료
        result.status = true;
        result.msg = "success";
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // Delete
    @DeleteMapping("/alert/deleteall/{uno}")
    @ApiOperation(value = "유저의 모든 알림 삭제")
    public Object deleteAllAlert(@PathVariable int uno) {
        // 반환할 응답 객체
        final BasicResponse result = new BasicResponse();

        if(firebase.deleteAllAlert(uno)) {
            result.status = false;
            result.msg = "알림 삭제 실패";
            return new ResponseEntity<>(result, HttpStatus.OK);
        }

        // 알림 삭제 완료
        result.status = true;
        result.msg = "success";
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/alert/delete/{ano}")
    @ApiOperation(value = "특정 알림 삭제")
    public Object deleteAlert(@PathVariable String ano) {
        // 반환할 응답 객체
        final BasicResponse result = new BasicResponse();

        if(firebase.deleteAlert(ano)) {
            result.status = false;
            result.msg = "알림 삭제 실패";
            return new ResponseEntity<>(result, HttpStatus.OK);
        }

        // 알림 삭제 완료
        result.status = true;
        result.msg = "success";
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    @DeleteMapping("/alert/deletefollow/{ano}")
    @ApiOperation(value = "특정 팔로우 요청 삭제")
    public Object deleteFollowing(@PathVariable String ano) {
        // 반환할 응답 객체
        final BasicResponse result = new BasicResponse();

        if(firebase.deleteFollowing(ano)) {
            result.status = false;
            result.msg = "알림 삭제 실패";
            return new ResponseEntity<>(result, HttpStatus.OK);
        }

        // 알림 삭제 완료
        result.status = true;
        result.msg = "success";
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}