package com.web.curation.dao.search;

import java.util.List;

import com.web.curation.model.feed.Feed;
import com.web.curation.model.search.SearchRequest;
import com.web.curation.model.user.User;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface SearchDao {
   List<User> searchByNickName(String str);
   List<User> getAllUser();
   int addHistory(SearchRequest req);
   List<User> getHistoryList(int uno);
   int deleteHistory(SearchRequest req);
   int deleteAllHistory(int uno);
   
   List<Feed> searchByFeedTag(String str);
}