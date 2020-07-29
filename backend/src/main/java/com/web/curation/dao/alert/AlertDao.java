package com.web.curation.dao.alert;

import java.util.List;

import com.web.curation.model.alert.Alert;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface AlertDao {
    // Create
    int addNewAlert(Alert alert);

    // Read
    List<Alert> getAlertList(int uno);

    // Update
    int setReadAllAlert(int uno);
    int setReadAlert(int ano);

    // Delete
    int deleteAllAlert(int uno);
    int deleteAlert(int ano);

	Alert getInfoByUser(int cno);
	Alert getInfoByFeedLike(int cno);
	Alert getInfoByFeedReply(int cno);
	Alert getInfoByFeed(int cno);
}