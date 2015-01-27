package com.hummingbird.common.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.JsonParseException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hummingbird.common.exception.ValidateException;
import com.hummingbird.common.util.RequestUtil;
import com.hummingbird.common.vo.ResultModel;
import com.hummingbird.common.vo.StatusCheckResult;

@Controller
public class BaseController {
	protected  final Log log = LogFactory.getLog(getClass());
	
	/**
	 * 返回日志，拦截器调用
	 * @return
	 */
	public Log getLog(){
		return log;
	}
	
	 /** 
     * 异常错误提示
     *  
     * @param runtimeException 
     * @return 
     */  
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Map<String,Object> runtimeExceptionHandler(Exception ex) {  
    	log.error(ex.getLocalizedMessage(),ex);  
        if(ex instanceof JsonParseException) {  
        	return new ResultModel(1201, "参数错误");
        } else if (ex instanceof HttpMessageNotReadableException){
        	return new ResultModel(1201, "参数错误");
        }

        return new ResultModel(1100, "未知错误，请联系管理员");
    }
    
    public @ResponseBody Object testToNotify(@RequestBody Map param)
    {
    	return null;
    }
    
    /**
     * 状态报告
     * @return
     */
    @RequestMapping("/statuscheck")
    public @ResponseBody Object statusCheck(){
    	if (log.isDebugEnabled()) {
			log.debug(String.format("状态报告开始"));
		}
    	
    	return new StatusCheckResult();
    }
    
	/**
	 * 从map中获取值
	 * @param map
	 * @param key
	 * @return
	 */
	protected String getValueFromMap(Map map,String key)
	{
		return ObjectUtils.toString(map.get(key));
	}
	
	@RequestMapping("/helloworld")
    public @ResponseBody Object helloworld(HttpServletRequest request){
    	if (log.isDebugEnabled()) {
			log.debug(String.format("helloworld开始"));
		}
    	ResultModel rm = new ResultModel();
		try {
			String jsonstr = RequestUtil.getRequestPostData(request);
			rm.setErrmsg("从payload中得到字符串是:"+jsonstr);
			if(StringUtils.isBlank(jsonstr)){
				jsonstr = RequestUtil.getFromFormData(request);
				rm.setErrmsg("从formdata中得到字符串是:"+jsonstr);
			}
		} catch (Exception e) {
			log.error(String.format("获取订单参数出错"),e);
			rm.mergeException(ValidateException.ERROR_PARAM_FORMAT_ERROR.cloneAndAppend(null, "订单参数"));
			return rm;
		}
    	return new StatusCheckResult();
    }
	
}

