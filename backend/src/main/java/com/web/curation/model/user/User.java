// 하단 DB 설정 부분은 Sub PJT II에서 데이터베이스를 구성한 이후에 주석을 해제하여 사용.

package com.web.curation.model.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;
import java.time.LocalDateTime;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User {
    private int uno;

    private String email;
    // password 는 json 응답 객체에 포함하지 않기?
    @JsonIgnore
    private String password;

    private LocalDateTime create_date;

    private String nick_name;

    private String bio;

    private String profile_pic;

    private boolean is_private;
    private boolean is_certification;

    public int getUno() {
        return uno;
    }

    public void setUno(int uno) {
        this.uno = uno;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getCreate_date() {
        return create_date;
    }

    public void setCreate_date(LocalDateTime create_date) {
        this.create_date = create_date;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getProfile_pic() {
        return profile_pic;
    }

    public void setProfile_pic(String profile_pic) {
        this.profile_pic = profile_pic;

        // 파일 경로로 된 profile_pic 이 들어왔다면
        // 이미지 데이터로 바꿔주기
        if(profile_pic != null && profile_pic.charAt(0) == '/') {
            try {
                // String pre_path = "/tvility";
                String pre_path = "./LocalTestProfilePic";
                String full_path = pre_path + profile_pic;
                File file = new File(full_path);

                FileInputStream fis = new FileInputStream(file);
                byte[] bytes = fis.readAllBytes();
                fis.close();

                this.profile_pic = new String(bytes);
            } catch (Exception e) {
                System.out.println("유저 프로필 데이터 로딩 실패: " + e.getMessage());
            }
        }
    }

    public void setProfile_pic_without_convert(String profile_pic) {
        this.profile_pic = profile_pic;
    }

    public boolean isIs_private() {
        return is_private;
    }

    public void setIs_private(boolean is_private) {
        this.is_private = is_private;
    }

    public boolean isIs_certification() {
        return is_certification;
    }

    public void setIs_certification(boolean is_certification) {
        this.is_certification = is_certification;
    }

    public void setUserWithToken(Map<String, Object> map) {
        // uno, email, nick_name, profile_pic, is_private 정보만 가져옴
        uno = (Integer) map.get("uno");
        email = (String) map.get("email");
        nick_name = (String) map.get("nick_name");
        profile_pic = (String) map.get("profile_pic");
        is_private = (Boolean) map.get("is_private");
    }

    @Override
    public String toString() {
        String str =
        "Info: "
        + uno + ", "
        + email + ", "
        + nick_name + ", "
        + create_date + ", "
        + profile_pic + ", "
        + is_private;
        return str;
    }
}
