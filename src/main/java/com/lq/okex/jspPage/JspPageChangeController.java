package com.lq.okex.jspPage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 页面跳转Controller
 * @author l.q
 *
 */
@RequestMapping("page")
@Controller
public class JspPageChangeController {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());  
	
	
	@RequestMapping("/toLogin")
	public String toLogin(){
		return "login";
	}
	
}
