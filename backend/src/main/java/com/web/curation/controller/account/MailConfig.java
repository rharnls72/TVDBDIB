package com.web.curation.controller.account;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class MailConfig {
    @Value("${myvue.url}")
    private String vueUrl;
    
    @Async
    public void sendJoinMail(JavaMailSender sender, String email, String nick_name){
        try {
            MimeMessage message = sender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(email);
            helper.setSubject("[tvility] 이메일 인증");
            String str = "<h3>안녕하세요 "+nick_name+"님! TVility 회원이 되신것을 진심으로 환영합니다. "+
            "<br/>아래 버튼을 클릭하여 회원가입을 완료해주세요. </h3><br/><br/>"+
            "<a href='"+vueUrl+"/user/emailconfirm/"+email+"'>"+
            "<button type='button' style='width: 150px;background: #000;color: "+
            "#fff;height: 50px;text-align: center;line-height: 50px;font-weight: 600;"+
            "border-radius: 5px;'>이메일 인증</button></a>";
            helper.setText(str, true);
            sender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Async
    public void sendFindPwMail(JavaMailSender sender, String email, String nick_name){
        try {
            MimeMessage message = sender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(email);
            helper.setSubject("[tvility] 비밀번호 변경");
            String str = "<h3>안녕하세요 "+nick_name+"님!"+
            "<br/>아래 버튼을 클릭하여 비밀번호를 변경해주세요. </h3><br/><br/>"+
            "<a href='"+vueUrl+"/user/editpw/"+email+"'>"+
            "<button type='button' style='width: 150px;background: #000;color: "+
            "#fff;height: 50px;text-align: center;line-height: 50px;font-weight: 600;"+
            "border-radius: 5px;'>비밀번호 변경</button></a>";
            helper.setText(str, true);
            sender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}