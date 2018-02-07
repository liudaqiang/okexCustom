package com.lq.bite.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lq.bite.base.BaseController;
import com.lq.bite.common.Constant;
import com.lq.bite.common.MD5Util;
import com.lq.bite.entity.User;
import com.lq.bite.service.UserService;
import com.lq.bite.utils.StringUtils;

@RestController
@RequestMapping("user")
public class UserController extends BaseController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private UserService userService;
	/**
	 * 用户登录请求
	 * @param user
	 * @param request
	 * @return
	 */
	@RequestMapping("login")
	public Map<String, Object> login(User user,HttpServletRequest request) {
		if (!StringUtils.isALLNotBlank(user.getPassword(), user.getUserName())) {
			logger.info("password=" + user.getPassword() + "    userName=" + user.getUserName());
			return returnFaild(Constant.EXCEPTION_PARAM, Constant.FAILD_PARAM);
		}
		// 校验字段长度
		if (user.getUserName().length() > 32 || user.getUserName().length()<8) {
			logger.info("userName---length" + user.getUserName().length());
			return returnFaild(Constant.EXCEPTION_VOLID, Constant.FAILD_PARAM_NUM);
		}
		List<User> userList  = userService.get(user);
		if(userList == null || userList.size() == 0){
			return returnFaild(Constant.EXCEPTION_FAILD,Constant.USER_NOT_EXISTS);
		}
		if(!MD5Util.string2MD5(user.getPassword()).equals(userList.get(0).getPassword())){
			return returnFaild(Constant.EXCEPTION_FAILD,Constant.USER_PASSWORD);
		}
		//将用户名存入session
		request.getSession().setAttribute("userName", user.getUserName());
		return returnSuccess(user.getUserName(),Constant.USER_LOGIN_SUCCESS);
	}

	@RequestMapping("saveUser")
	public Map<String, Object> saveUser(User user) {
		if (user.getId() == 0) {
			// 注册
			if (!StringUtils.isALLNotBlank(user.getEmail(), user.getPassword(), user.getUserName())) {
				logger.info("email=" + user.getEmail() + "    password=" + user.getPassword() + "    userName="
						+ user.getUserName());
				return returnFaild(Constant.EXCEPTION_VOLID, Constant.FAILD_PARAM);
			}
			// 校验字段长度
			if (user.getUserName().length() > 32 || user.getEmail().length() > 64) {
				logger.info("userName---length" + user.getUserName().length());
				logger.info("email---length" + user.getEmail().length());
				return returnFaild(Constant.EXCEPTION_VOLID, Constant.FAILD_PARAM_NUM);
			}
			try {
				// password加密
				String enCodePassword = MD5Util.string2MD5(user.getPassword());
				logger.info("password----" + user.getPassword());
				logger.info("enCodePassword----" + enCodePassword);
				user.setPassword(enCodePassword);
				List<User> users = userService.get(user);
				if (users.size() > 0) {
					logger.info("用户名已经存在");
					return returnFaild(Constant.EXCEPTION_FAILD, Constant.USER_NAME_EXISTS);
				}
				// 保存
				userService.insert(user);
				return returnSuccess(null, Constant.SUCCESS_INSERT);
			} catch (Exception e) {
				logger.error("error:"+e.getMessage());
				return returnFaild(Constant.EXCEPTION_FAILD, Constant.CONNECT_ADMIN);
			}
		} else {
			// 修改用户
			// 校验字段长度
			if (user.getUserName().length() > 32 || user.getEmail().length() > 64) {
				logger.info("userName---length" + user.getUserName().length());
				logger.info("email---length" + user.getEmail().length());
				return returnFaild(Constant.EXCEPTION_VOLID, Constant.FAILD_PARAM_NUM);
			}
			User queryUser = userService.getOne(user.getId());
			// 校验传入id是否存在
			if (queryUser == null || queryUser.getId() == 0) {
				return returnFaild(Constant.EXCEPTION_FAILD, Constant.USER_NOT_EXISTS);
			}
			try {
				// password加密
				String enCodePassword = MD5Util.string2MD5(user.getPassword());
				logger.info("password----" + user.getPassword());
				logger.info("enCodePassword----" + enCodePassword);
				user.setPassword(enCodePassword);
				// 保存
				userService.update(user);
				return returnSuccess(null, Constant.SUCCESS_UPDATE);
			} catch (Exception e) {
				logger.error("error:"+e.getMessage());
				return returnFaild(Constant.EXCEPTION_FAILD, Constant.CONNECT_ADMIN);
			}
		}
	}
}
