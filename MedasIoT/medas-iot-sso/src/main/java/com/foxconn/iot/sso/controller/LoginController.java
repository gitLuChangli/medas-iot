package com.foxconn.iot.sso.controller;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.foxconn.iot.sso.dto.ChangePwdDto;
import com.foxconn.iot.sso.model.RespBean;
import com.foxconn.iot.sso.security.VerificationCode;
import com.foxconn.iot.sso.service.UserService;

@RestController
public class LoginController {
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	@Autowired
	private UserService userService;
	
	@RequestMapping("/")
    public void index(HttpServletResponse response) throws IOException {
        response.sendRedirect("/index.html");
    }
	
	@GetMapping("/login")
    public RespBean login() {
        return RespBean.error("尚未登录，请登录!");
    }
	
	@GetMapping("/vcode")
    public void verifyCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        VerificationCode code = new VerificationCode();
        BufferedImage image = code.getImage();
        String text = code.getText();
        HttpSession session = request.getSession(true);
        session.setAttribute("vcode", text);
        VerificationCode.output(image, response.getOutputStream());
        response.setContentType("application/json;charset=utf-8");
		response.setHeader("Access-Control-Allow-Credentials","true");
		response.setHeader("Access-Control-Allow-Methods","POST, GET, PUT, OPTIONS, DELETE, PATCH");
		response.setHeader("Access-Control-Max-Age", "3600");
		response.setHeader("Access-Control-Allow-Headers","token,Origin, X-Requested-With, Content-Type, Accept,mid,X-Token");
    }
	
	@PostMapping(value = "/auth/pwd")
	public RespBean changePwd(@Valid @RequestBody ChangePwdDto user, BindingResult result) {
		String old = userService.queryPwd(user.getUsername());
		if (encoder.matches(user.getPassword(), old)) {
			userService.changePwd(user.getUsername(), encoder.encode(user.getNewpwd()));
			return RespBean.ok("修改成功");
		} else {
			return RespBean.error("修改失败");
		}
	}
}
