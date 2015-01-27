package com.hummingbird.common.ext;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.springframework.util.StopWatch;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.hummingbird.common.controller.BaseController;

/**
 * controller 拦截器
 * @author huangjiej_2
 * 2014年10月25日 下午9:57:10
 */
public class ControllerMethodInterceptor extends HandlerInterceptorAdapter {

	private static ThreadLocal<StopWatch> tl = new ThreadLocal<StopWatch>();
//	Log log;
//	/**
//	 * 日志记录的内容
//	 */
//	String logMethodName;
//	/**
//	 * 执行方法名
//	 */
//	String methodName;
//	
//	/**
//	 * 执行类名
//	 */
//	String className;
	
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
			String logMethodName = annotation.methodName();
			if(StringUtils.hasText(logMethodName)){
				log = bc.getLog();
				if(log.isDebugEnabled()){
					log.debug(logMethodName+"完成");
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
			String logMethodName = annotation.methodName();
			if(StringUtils.hasText(logMethodName)){
				
				log = bc.getLog();
				if(log.isDebugEnabled()){
					log.debug(logMethodName+"开始");
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
		// TODO Auto-generated method stub
		super.afterConcurrentHandlingStarted(request, response, handler);
	}
	
	

	
	
	
}
