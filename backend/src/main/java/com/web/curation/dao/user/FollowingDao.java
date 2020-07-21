package com.web.curation.dao.user;

import java.util.List;

import com.web.curation.model.program.Program;
import com.web.curation.model.user.ProgramFollowing;
import com.web.curation.model.user.User;
import com.web.curation.model.user.UserFollowing;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface FollowingDao {

    /*
        User Following
    */
    // Create
    int addUserFollowing(UserFollowing uf);

    // Read
    int getUserFollowingsCount(int uno);
    List<User> getUserFollowings(int uno);
    
    int getUserFollowersCount(int uno);
    List<User> getUserFollowers(int uno);

    // Delete
    int deleteUserFollowing(UserFollowing uf);

    //==================================================
    //==================================================

    /*
        Program Following
    */
    // Create
    int addProgramFollowing(ProgramFollowing pf);

    // Read
    int getProgramFollowingCount(int uno);
    List<Program> getProgramFollowings(int uno);

    int getProgramFollowersCount(int pno);
    List<User> getProgramFollowers(int pno);

    // Delete
    int deleteProgramFollowing(ProgramFollowing pf);
}