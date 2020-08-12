package com.web.curation.model.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

import com.google.cloud.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoom {
    private String cno;
    private String usersToString;
    private List<Integer> users;
    private Timestamp time;

    public String getCno() {
        return cno;
    }

    public void setCno(String cno) {
        this.cno = cno;
    }

    public String getUsersToString() {
        return usersToString;
    }

    public void setUsersToString(String usersToString) {
        this.usersToString = usersToString;
    }

    public List<Integer> getUsers() {
        return users;
    }

    public void setUsers(List<Integer> users) {
        this.users = users;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    
}