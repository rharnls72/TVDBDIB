package com.web.curation.dao.program;

import java.util.List;

import com.web.curation.model.program.Program;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ProgramDao {
    // Create
    int addNewProgram(Program program);

    // Read
    List<Program> getProgramList();
    Program getProgramDetail(int pno);

    // Update
    int modifyProgram(Program program);

    // Delete
    int deleteProgram(int pno);
}