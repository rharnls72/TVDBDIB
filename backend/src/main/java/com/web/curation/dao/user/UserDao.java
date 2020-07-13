
package com.web.curation.dao.user;

import java.util.Optional;

import com.web.curation.model.user.User;
import com.web.curation.model.user.SignupRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserDao extends JpaRepository<User, String> {
    User getUserByEmail(String email);

    Optional<User> findUserByEmailAndPassword(String email, String password);
    int addNewUser(SignupRequest request);
}
