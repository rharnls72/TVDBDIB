package com.web.curation.dao.feed;

import java.util.List;

import com.web.curation.model.feed.Feed;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface FeedDao {
    // Create
    int addNewFeed(Feed feed);
    
    // Read
    List<Feed> getFeedList();
    Feed getFeedDetail(int fno);

    // Update
    int modifyFeed(Feed feed);

    // Delete
    int deleteFeed(int fno);
}