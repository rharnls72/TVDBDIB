package com.web.curation.controller.program;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import com.web.curation.dao.program.ProgramDao;
import com.web.curation.model.BasicResponse;
import com.web.curation.model.program.Program;

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
public class ProgramController {

    @Autowired
    ProgramDao dao;
    
    // Create
    @PostMapping("/program/create")
    @ApiOperation(value = "프로그램 정보 생성")
    public Object createNewProgram(@RequestBody Program program) {
        // 반환할 응답 객체
        final BasicResponse result = new BasicResponse();

        // 프로그램 정보 추가
        int n = dao.addNewProgram(program);

        // n 이 1 이 아니면 쿼리 수행 결과에 이상이 있는 것
        if(n != 1) {
            result.status = false;
            result.msg = "Insert 쿼리 수행 결과에 이상이 발생했습니다.(" + n + ")";
            return new ResponseEntity<>(result, HttpStatus.OK);
        }

        // 프로그램 정보 추가 완료
        result.status = true;
        result.msg = "success";
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // Read
    @GetMapping("/program/list")
    @ApiOperation(value = "프로그램 목록 조회")
    public Object getProgramList() {
        // 반환할 응답 객체
        final BasicResponse result = new BasicResponse();

        // 프로그램 목록 조회
        List<Program> list = dao.getProgramList();

        // 프로그램 목록을 포함한 응답 객체 반환
        result.status = true;
        result.msg = "success";
        result.data = list;
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/program/detail/{pno}")
    @ApiOperation(value = "프로그램 상세 정보 조회")
    public Object getProgramDetail(@PathVariable("pno") int pno) {
        // 반환할 응답 객체
        final BasicResponse result = new BasicResponse();

        // 프로그램 상세 정보 조회
        Program program = dao.getProgramDetail(pno);

        // program 이 null 이면 문제 발생
        if(program == null) {
            result.status = false;
            result.msg = "프로그램 상세 정보 조회에 실패했습니다.(" + pno + ")";
            return new ResponseEntity<>(result, HttpStatus.OK);
        }

        // 프로그램 상세정보를 포함한 응답 객체 반환
        result.status = true;
        result.msg = "success";
        result.data = program;
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // Update
    @PutMapping("/program/update")
    @ApiOperation(value = "프로그램 정보 수정")
    public Object modifyProgram(@RequestBody Program program) {
        // 반환할 응답 객체
        final BasicResponse result = new BasicResponse();

        // 프로그램 정보 수정
        int n = dao.modifyProgram(program);

        // n 이 1 이 아니면 쿼리 수행 결과에 이상이 있는 것
        if(n != 1) {
            result.status = false;
            result.msg = "Update 쿼리 수행 결과에 이상이 발생했습니다.(" + n + ")";
            return new ResponseEntity<>(result, HttpStatus.OK);
        }

        // 프로그램 정보 수정 완료
        result.status = true;
        result.msg = "success";
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // Delete
    @DeleteMapping("/program/delete/{pno}")
    @ApiOperation(value = "프로그램 정보 삭제")
    public Object deleteProgram(@PathVariable int pno) {
        // 반환할 응답 객체
        final BasicResponse result = new BasicResponse();

        // 프로그램 정보 삭제
        int n = dao.deleteProgram(pno);

        // n 이 1 이 아니면 쿼리 수행 결과에 이상이 있는 것
        if(n != 1) {
            result.status = false;
            result.msg = "Delete 쿼리 수행 결과에 이상이 발생했습니다.(" + n + ")";
            return new ResponseEntity<>(result, HttpStatus.OK);
        }

        // 프로그램 정보 삭제 완료
        result.status = true;
        result.msg = "success";
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}