package com.web.curation.controller.alert;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import com.web.curation.dao.alert.AlertDao;
import com.web.curation.model.BasicResponse;
import com.web.curation.model.alert.Alert;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
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

@CrossOrigin(origins = { "*" })
@RestController
public class AlertController {

    @Autowired
    AlertDao dao;
    
    // Create
    @PostMapping("/alert/create")
    @ApiOperation(value = "새 알림 생성")
    public Object createNewAlert(@RequestBody Alert alert) {
        // 반환할 응답 객체
        final BasicResponse result = new BasicResponse();

        // 프로그램 정보 추가
        int n = dao.addNewAlert(alert);

        // n 이 1 이 아니면 쿼리 수행 결과에 이상이 있는 것
        if(n != 1) {
            result.status = false;
            result.msg = "Insert 쿼리 수행 결과에 이상이 발생했습니다.(" + n + ")";
            return new ResponseEntity<>(result, HttpStatus.OK);
        }

        // 알림 추가 완료
        result.status = true;
        result.msg = "success";
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // Read
    @GetMapping("/alert/list/{uno}")
    @ApiOperation(value = "알림 목록 조회")
    public Object getAlertList(@PathVariable int uno) {
        // 반환할 응답 객체
        final BasicResponse result = new BasicResponse();

        // 알림 목록 조회
        List<Alert> list = dao.getAlertList(uno);
        List<Alert> return_list = new ArrayList<Alert>();
        
        for (Alert alert: list){
            Alert return_alert = new Alert();
            switch(alert.getAtype()){
                case 1:
                    return_alert = dao.getInfoByUser(alert.getCno());
                    break;
                case 2:
                    return_alert = dao.getInfoByFeedLike(alert.getCno());
                    break;
                case 3:
                    return_alert = dao.getInfoByFeedReply(alert.getCno());
                    break;
                case 4:
                    return_alert = dao.getInfoByFeed(alert.getCno());
                    break;
            }
            System.out.println(return_alert.getSubject_no());
            return_alert.setAno(alert.getAtype());
            return_alert.setAno(alert.getAno());
            return_list.add(return_alert);
        }

        // 알림 목록을 포함한 응답 객체 반환
        result.status = true;
        result.msg = "success";
        result.data = return_list;
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // Update
    @PutMapping("/alert/readall/{uno}")
    @ApiOperation(value = "모든 알림 읽음 처리")
    public Object setReadAllAlert(@PathVariable int uno) {
        // 반환할 응답 객체
        final BasicResponse result = new BasicResponse();

        // 알림 읽음 처리
        int n = dao.setReadAllAlert(uno);

        // 알림 읽음 처리 완료
        result.status = true;
        result.msg = "success (" + n + ")"; // 처리 된 알림 수 표시
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping("/alert/read/{ano}")
    @ApiOperation(value = "알림 읽음 처리")
    public Object setReadAlert(@PathVariable int ano) {
        // 반환할 응답 객체
        final BasicResponse result = new BasicResponse();

        // 알림 읽음 처리
        int n = dao.setReadAlert(ano);

        // n 이 1 이 아니면 쿼리 수행 결과에 이상이 있는 것
        if(n != 1) {
            result.status = false;
            result.msg = "Update 쿼리 수행 결과에 이상이 발생했습니다.(" + n + ")";
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

        // 유저의 모든 알림 삭제
        int n = dao.deleteAllAlert(uno);

        // n 이 0 이면 쿼리 수행 결과에 이상이 있는 것
        if(n == 0) {
            result.status = false;
            result.msg = "Delete 쿼리 수행 결과에 이상이 발생했습니다.(" + n + ")";
            return new ResponseEntity<>(result, HttpStatus.OK);
        }

        // 모든 알림 삭제 완료
        result.status = true;
        result.msg = "success (" + n + ")"; // 삭제된 알림 수 표시
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/alert/delete/{ano}")
    @ApiOperation(value = "특정 알림 삭제")
    public Object deleteAlert(@PathVariable int ano) {
        // 반환할 응답 객체
        final BasicResponse result = new BasicResponse();

        // 알림 삭제
        int n = dao.deleteAlert(ano);

        // n 이 1 이 아니면 쿼리 수행 결과에 이상이 있는 것
        if(n != 1) {
            result.status = false;
            result.msg = "Delete 쿼리 수행 결과에 이상이 발생했습니다.(" + n + ")";
            return new ResponseEntity<>(result, HttpStatus.OK);
        }

        // 알림 삭제 완료
        result.status = true;
        result.msg = "success";
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}