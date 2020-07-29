package com.web.curation.dao.program;

import com.web.curation.model.program.Program;
import com.web.curation.model.program.ProgramRequest;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ProgramDao {
    Program getLikeReplyInfo(ProgramRequest req);
}