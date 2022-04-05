package com.infinity.omos.service;

import com.infinity.omos.domain.Report.Report;
import com.infinity.omos.dto.MailDto;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender emailSender;

    private MimeMessage createMessage(String to, String code) throws Exception {

        MimeMessage message = emailSender.createMimeMessage();

        message.addRecipients(MimeMessage.RecipientType.TO, to); //보내는 대상
        message.setSubject("OMOS 이메일 주소 확인"); //제목

        String msg = "";
        msg += "<h1 style=\"font-size: 30px; padding-right: 30px; padding-left: 30px;\">이메일 주소 확인</h1>";
        msg += "<p style=\"font-size: 17px; padding-right: 30px; padding-left: 30px;\">아래 확인 코드를 OMOS 앱에 입력하세요.</p>";
        msg += "<div style=\"padding-right: 30px; padding-left: 30px; margin: 32px 0 40px;\"><table style=\"border-collapse: collapse; border: 0; background-color: #F4F4F4; height: 70px; table-layout: fixed; word-wrap: break-word; border-radius: 6px;\"><tbody><tr><td style=\"text-align: center; vertical-align: middle; font-size: 30px;\">";
        msg += code;
        msg += "</td></tr></tbody></table></div>";
        msg += "<style=\"text-decoration: none; color: #434245;\" rel=\"noreferrer noopener\" target=\"_blank\">OMOS</a>";

        message.setText(msg, "utf-8", "html"); //내용
        message.setFrom(new InternetAddress("noreply@omos.com", "OMOS")); //보내는 사람

        return message;
    }

    // 인증코드 만들기
    public static String createKey() {
        StringBuilder key = new StringBuilder();
        Random rnd = new Random();

        for (int i = 0; i < 6; i++) { // 인증코드 6자리
            key.append((rnd.nextInt(10)));
        }
        return key.toString();
    }

    public HashMap<String, String> sendSimpleMessage(MailDto mailDto) throws Exception {

        String code = createKey();
        HashMap<String,String> map = new HashMap<>();
        map.put("code",code);

        try {//예외처리
            emailSender.send(createMessage(mailDto.getEmail(), code));
            return map;
        } catch (MailException es) {
            es.printStackTrace();
            throw new IllegalArgumentException();
        }
    }

    private MimeMessage createReportMessage(Report report) throws Exception {

        MimeMessage message = emailSender.createMimeMessage();

        message.addRecipients(MimeMessage.RecipientType.TO, "officialproject111@gmail.com"); //보내는 대상
        message.setSubject("OMOS 신고문의"); //제목

        String toUserId = null;
        if(report.getToUserId()!=null){
            toUserId = report.getToUserId().getId().toString();
        }

        String postId = null;
        if(report.getPostId()!=null){
            postId = report.getPostId().getId().toString();
        }


        String msg = "";
        msg += "<h1 style=\"font-size: 30px; padding-right: 30px; padding-left: 30px;\">OMOS 신고문의</h1>";
        msg += "<p style=\"font-size: 17px; padding-right: 30px; padding-left: 30px;\">"+"유형:"+report.getReportType().toString()+"</p>";
        msg += "<p style=\"font-size: 17px; padding-right: 30px; padding-left: 30px;\">"+"신고자ID:"+report.getFromUserId().getId().toString()+"</p>";
        msg += "<p style=\"font-size: 17px; padding-right: 30px; padding-left: 30px;\">"+"레코드ID:"+postId+"</p>";
        msg += "<p style=\"font-size: 17px; padding-right: 30px; padding-left: 30px;\">"+"신고당한ID:"+toUserId+"</p>";
        msg += "<p style=\"font-size: 17px; padding-right: 30px; padding-left: 30px;\">"+"신고사유:"+report.getReportReason()+"</p>";
        msg += "<style=\"text-decoration: none; color: #434245;\" rel=\"noreferrer noopener\" target=\"_blank\">OMOS</a>";

        message.setText(msg, "utf-8", "html"); //내용
        message.setFrom(new InternetAddress("noreply@omos.com", "OMOS")); //보내는 사람

        return message;
    }

    public void sendSimpleReportMessage(Report report) throws Exception {

        try {//예외처리
            emailSender.send(createReportMessage(report));
        } catch (MailException es) {
            es.printStackTrace();
            throw new IllegalArgumentException();
        }
    }

}

