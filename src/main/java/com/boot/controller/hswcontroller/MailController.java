package com.boot.controller.hswcontroller;

import com.boot.pojo.User;
import com.boot.service.Impl.MailServiceImpl;
import com.boot.service.Impl.UserServiceImpl;
import com.boot.utils.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author hsw
 * @date 2021/8/13
 */
@RestController
@RequestMapping("/api/mail")
public class MailController {
    @Autowired
    private MailServiceImpl mailService;

    @ResponseBody
    @PostMapping("/sendmail")
    public String sendmail(@RequestBody Map<String,String> map) {
        String email = map.get("email");
        RandomUtil randomUtil = new RandomUtil();
        String s = randomUtil.achieveCode();
        String context = "<html>\n" +
                "<body>\n" +
                "<h3>您好</h3>\n" +
                "输入验证码" + "<strong>" + s + "</strong>" + "在您的邮件验证码上 </br>" +
                "如果你收到了这封邮件通过错误,请删除此邮件,不回复此邮件。\n" +
                "</body>\n" +
                "</html>";
        mailService.sendVertifyCode(email, "天天资讯", context);
        return s;
    }
}
