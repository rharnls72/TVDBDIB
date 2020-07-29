package com.web.curation.model.following;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FollowCnt {
    private int feed_cnt;
    private int follower_cnt;
    private int following_cnt;
    private int is_follow;

    public int getFeed_cnt() {
        return feed_cnt;
    }

    public void setFeed_cnt(int feed_cnt) {
        this.feed_cnt = feed_cnt;
    }

    public int getFollower_cnt() {
        return follower_cnt;
    }

    public void setFollower_cnt(int follower_cnt) {
        this.follower_cnt = follower_cnt;
    }

    public int getFollowing_cnt() {
        return following_cnt;
    }

    public void setFollowing_cnt(int following_cnt) {
        this.following_cnt = following_cnt;
    }

    public int getIs_follow() {
        return is_follow;
    }

    public void setIs_follow(int is_follow) {
        this.is_follow = is_follow;
    }

}