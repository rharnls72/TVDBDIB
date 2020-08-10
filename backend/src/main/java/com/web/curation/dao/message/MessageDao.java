package com.web.curation.dao.message;


import java.util.List;

import com.web.curation.model.user.User;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import org.apache.ibatis.annotations.Param;

@Mapper
@Repository
public interface MessageDao {

    List<User> getUserInfo(List<Integer> unolist);

}