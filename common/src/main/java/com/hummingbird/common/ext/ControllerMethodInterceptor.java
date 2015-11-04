package com.hummingbird.common.ext;

import java.lang.reflect.Method;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.springframework.util.StopWatch;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.hummingbird.common.controller.BaseController;
import com.hummingbird.common.exception.ValidateException;
import com.hummingbird.common.face.AbstractAppLog;
import com.hummingbird.common.util.JsonUtil;
import com.hummingbird.common.util.RegexUtil;
import com.hummingbird.common.util.RequestUtil;
import com.hummingbird.common.vo.ResultModel;

/**
 * controller 拦截器
 * @author huangjiej_2
 * 2014年10月25日 下午9:57:10
 */
public class ControllerMethodInterceptor extends HandlerInterceptorAdapter {

	private static ThreadLocal<StopWatch> tl = new ThreadLocal<StopWatch>();
	
	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		HandlerMethod handlerMethod = (HandlerMethod) handler;
		Method method = handlerMethod.getMethod();
		BaseController bc = (BaseController) handlerMethod.getBean();
		
		String className=handlerMethod.getBeanType().getName();
		String methodName=method.getName();
		Log log=null;
		AccessRequered annotation = method.getAnnotation(AccessRequered.class);
		if (annotation != null) {
			//记录结束日志
			String logMethodName = annotation.methodName();
			if(StringUtils.isNotBlank(logMethodName)){
				log = bc.getLog();
				if(log.isDebugEnabled()){
					log.debug(logMethodName+"完成");
				}
			}
			if(annotation.appLog()){
				ResultModel resultModel = bc.getResultModel();
				if(resultModel!=null){
					String json = JsonUtil.convert2Json(resultModel);
					bc.writeAppLog(json);
				}
				
			}
			
		}
		StopWatch sw = tl.get();
		if(sw!=null){
			if(sw.isRunning()){
				sw.stop();
			}
			String totelmsg = String.format("统计%s执行时间，方法为%s,耗时(s)=%s",className,methodName,sw.getTotalTimeSeconds());
			if(log!=null){
				if(log.isDebugEnabled()){
					log.debug(totelmsg);
				}
			}
			else{
				System.out.println(totelmsg);
			}
		}
		super.postHandle(request, response, handler, modelAndView);
	}

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		HandlerMethod handlerMethod = (HandlerMethod) handler;
		Method method = handlerMethod.getMethod();
		BaseController bc = (BaseController) handlerMethod.getBean();
		String className=handlerMethod.getBeanType().getName();
		String methodName=method.getName();
		Log log=null;
		AccessRequered annotation = method.getAnnotation(AccessRequered.class);
		if (annotation != null) {
			log = bc.getLog();
			String messagebase = annotation.methodName();
			ResultModel rm = new ResultModel();
			rm.setBaseErrorCode(annotation.codebase());
			rm.setErrmsg(messagebase+"成功");
			bc.prepareResultModel(rm);
			String jsonstr="";
			if(annotation.isJson()){
				try {
					//获取参数阶段
					jsonstr = RequestUtil.getRequestPostData(request);
					if(StringUtils.isBlank(jsonstr)){
						log.error("从请求中获取不到参数");
						rm.setErr(ValidateException.ERROR_PARAM_NULL.getErrcode(), "从请求中获取不到参数");
						//TODO 输出错误
					}
					bc.prepareParameter(jsonstr);
				}
				catch(Exception e){
					log.error(String.format("获取%s参数出错",messagebase),e);
					rm.mergeException(ValidateException.ERROR_PARAM_FORMAT_ERROR.cloneAndAppend(null, messagebase+"参数"));
				}
				try {
					//转换对象阶段
					String transorderclassname = annotation.className();
					String subtransorderclassname = annotation.genericClassName();
					if(StringUtils.isBlank(transorderclassname)){
						if (log.isDebugEnabled()) {
							log.debug(String.format("参数转换类不存在,无法转成对象"));
						}
					}
					else{
						Object transorder;
						if(StringUtils.isBlank(subtransorderclassname)){
							Class<?> BaseTransVOClass = Class.forName(transorderclassname);
							transorder = RequestUtil.convertJson2Obj(jsonstr,BaseTransVOClass);
						}
						else{
							Class<?> BaseTransVOClass = Class.forName(transorderclassname);
							Class<?> BaseTransBodyVOClass = Class.forName(subtransorderclassname);
							transorder = RequestUtil.convertJson2Obj(jsonstr,BaseTransVOClass,BaseTransBodyVOClass);
						}
						bc.prepareParameterObject(transorder);
					}
				} catch (Exception e) {
					log.error(String.format("获取%s参数出错",messagebase),e);
					rm.mergeException(ValidateException.ERROR_PARAM_FORMAT_ERROR.cloneAndAppend(null, messagebase+"参数"));
					//输出
				}
			}
			//记录日志
			if(annotation.appLog()){
				String[] extractMatch = RegexUtil.extractMatch(jsonstr.replaceAll("\r", "").replaceAll("\n", ""), "\\{\"app\":\\{.*\"appid\":\"(.+?)\"");
				if(extractMatch!=null&&extractMatch.length>0){
					String appid = extractMatch[0];
					
					AbstractAppLog rnr = new AbstractAppLog();
					rnr.setAppid(appid);
					String requestURI = request.getRequestURI();
					requestURI=requestURI.replace(request.getContextPath(), "");
					rnr.setRequest(jsonstr);
					rnr.setInserttime(new Date());
					rnr.setMethod(requestURI);
					//设置日志信息
					bc.prepareAppLog(rnr);
				}
			}
			
			//记录开始日志
			if(StringUtils.isNotBlank(messagebase)){
				
				log = bc.getLog();
				if(log.isDebugEnabled()){
					log.debug(messagebase+"开始:"+jsonstr);
				}
			}
			
		}
		StopWatch sw = new StopWatch();
		tl.set(sw);
		sw.start();
		return super.preHandle(request, response, handler);
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		super.afterCompletion(request, response, handler, ex);
	}

	@Override
	public void afterConcurrentHandlingStarted(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		super.afterConcurrentHandlingStarted(request, response, handler);
	}
	
	

	
	
	
}
