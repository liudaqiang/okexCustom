package com.lq.okex.base;

import java.util.HashMap;
import java.util.Map;

public class BaseController {
	 protected Map<String,Object> returnSuccess(Object object,String message){
	       Map<String,Object> ret = new HashMap<String,Object>();
	       if(object != null){
	           ret.put("data", object);
	       }
	       ret.put("code", 200);
	       ret.put("message", message);
	       return ret;
	   }
	   protected Map<String,Object> returnFaild(String message,String errorMessage){
	       Map<String,Object> ret = new HashMap<String,Object>();
	       ret.put("code", 100);
	       ret.put("message", message);
	       ret.put("errorMessage", errorMessage);
	       return ret;
	   }
}
