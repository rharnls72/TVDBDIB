
package com.web.curation.dao.user;

import java.util.Optional;

import com.web.curation.model.user.SignupRequest;
import com.web.curation.model.user.User;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

// JpaRepository 안쓰고 SQL 직접 작성하는게 더 편할거같아요
// public interface UserDao extends JpaRepository<User, String> {

@Mapper
@Repository
public interface UserDao {
    User getUserByEmail(String email);
    User getUserByNickName(String nickName);

    User findUserByEmailAndPassword(User user);

    int addNewUser(SignupRequest request);
}
