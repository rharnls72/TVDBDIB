package com.web.curation.dao.following;

import java.util.List;

import com.web.curation.model.following.FollowRequest;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface FollowRequestDao {
    
    // Create
    int addFollowRequest(FollowRequest req);

    // Read
    List<FollowRequest> getFollowRequestList(int uno);

    // Delete
    int deleteFollowRequest(int fno);
}