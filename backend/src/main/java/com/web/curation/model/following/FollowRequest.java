package com.web.curation.model.following;

import com.web.curation.model.user.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FollowRequest {
    // follow_request Attribute
    private int fno;
    private int uno;
    private int follower;
    
    // follower Attribute
    private User user;

    // Setter Getter
    public void setFno(int fno) {
        this.fno = fno;
    }
    public int getFno() {
        return fno;
    }
    public void setUno(int uno) {
        this.uno = uno;
    }
    public int getUno() {
        return uno;
    }
    public void setFollower(int follower) {
        this.follower = follower;
    }
    public int getFollower() {
        return follower;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public User getUser() {
        return user;
    }
}