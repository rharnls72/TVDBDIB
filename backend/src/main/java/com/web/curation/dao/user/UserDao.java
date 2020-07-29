
package com.web.curation.dao.user;

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
    User getUserByNickName(String nick_name);

    User findUserByEmailAndPassword(User user);

    int checkPassword(User user);
    int checkNickName(String nick_name);

    int addNewUser(SignupRequest request);

    int modifyPassword(User user);
    int modifyProfile(User user);
    int emailConfirm(String email);
}
