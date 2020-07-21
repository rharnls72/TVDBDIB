package com.web.curation.model.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserFollowing {
    private int follower;
    private int following;

    public void setFollower(int follower) {
        this.follower = follower;
    }
    public int getFollower() {
        return follower;
    }

    public void setFollowing(int following) {
        this.following = following;
    }
    public int getFollowing() {
        return following;
    }
}