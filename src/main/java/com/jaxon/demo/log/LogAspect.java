package com.jaxon.demo.log;

import com.alibaba.fastjson.JSONObject;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

//@Aspect
//@Component //先不启用aop日志，用
public class LogAspect {

    private Logger logger = null;
    /**
     * 是否打印接口输入输出参数、时间耗时日志
     * 打印参数可以只用在开发和测试环境，生产环境可以配置为false，减少服务器压力，和日志打印
     * 配置文件注入，默认false
     */
    private Boolean showParam = true;

    /**
     * 项目名称
     * 配置文件注入，必填
     */
    private String projectName="superSB";

    /**
     * 方法类型：0-action调用，1-service调用，2-dao调用
     * <p>
     * 配置文件注入，必填
     */
    private String methodType ="0";

    /**
     * 调用方式：0-调用自己系统方法  1-调用其他系统方法
     * <p>
     * 配置文件注入， 必填
     */
    private String invokeType;


    /**
     * 参数打印最长限制
     */
    private int paramLengthLimit = 3000;

    /**
     * 参数太长展示的截取长度
     */
    private int interceptLength = 300;

    @Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public void pointCut(){}

    @Around(value = "pointCut()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {

        String method = pjp.getSignature().getName();
        Object[] params = pjp.getArgs();
        String className = pjp.getTarget().getClass().getSimpleName();
        logger = LoggerFactory.getLogger(className);

        long invokeTime = System.currentTimeMillis();

        //在执行前打印入参，防止异常不打印日志
        try {
            if (showParam) {//打印参数可以只用在开发和测试环境，生产环境可以配置为false，减少服务器压力，和日志打印
                if (methodType.equals("0")) {//controller调用
                    if (params != null) {
                        for (Object object : params) {
                            if (object instanceof HttpServletRequest) {
                                HttpServletRequest request = (HttpServletRequest) object;
                                if (request != null) {
                                    String parameters = getParameters(request);
                                    String queryString = request.getQueryString();
                                    logger.info("【"+invokeTime+" " + method + "  Controller入参】:" + parameters);
                                }
                                break;
                            }
                        }
                    } else {
                        logger.info("【"+invokeTime+" " + method + "  Controller入参】:" + params);
                    }

                } else if (methodType.equals("1") || methodType.equals("2")) {//service 或者 dao
                    String s = JSONObject.toJSONString(params);
                    if(s.length() > paramLengthLimit){
                        String paramStr = null;
                        for(int i = 0;i<params.length;i++){
                            paramStr = JSONObject.toJSONString(params[i]);
                            if(paramStr.length() > paramLengthLimit){
                                params[i]= "参数太长进行截取:"+paramStr.substring(0,interceptLength);
                            }
                        }
                        s = JSONObject.toJSONString(params);
                        logger.info("【"+invokeTime+" " + method + "  入参】:" +s );
                    }else{

                        logger.info("【"+invokeTime+" " + method + "  入参】:" +s );
                    }
                }
            }
        } catch (Exception e) {
            logger.error("【AOP日志: " + method + "  入参打印异常！】", e);
        }

        long t = System.currentTimeMillis();
        Object result = pjp.proceed();
        long timeCounsume = System.currentTimeMillis() - t;
        String ip = null;//ip = NetworkUtil.getIpAddress(request);
        String agent = null;

        try {
            if (showParam) {//打印参数可以只用在开发和测试环境，生产环境可以配置为false，减少服务器压力，和日志打印
                if (methodType.equals("0")) {//controller调用
                    if(result instanceof  String){
                        String strResutl = (String)result;
                        if(strResutl.length() > paramLengthLimit){

                            logger.info("【"+invokeTime+" " + method + "  Controller结果】:" + "结果太长进行截取，前:"+strResutl.substring(0,interceptLength) +"，后:"+strResutl.substring(strResutl.length()-interceptLength) );
                        }else{
                            logger.info("【"+invokeTime+" " + method + "  Controller结果】:" + strResutl);
                        }

                    }else{
                        String s = JSONObject.toJSONString(result);
                        if(s.length() > paramLengthLimit){
                            logger.info("【"+invokeTime+" " + method + "  Controller结果】:" +"结果太长进行截取，前:"+s.substring(0,interceptLength)+"，后:"+s.substring(s.length()-interceptLength) );
                        }else{

                            logger.info("【"+invokeTime+" " + method + "  Controller结果】:" + s);
                        }
                    }

                    logger.info("【"+invokeTime+" " + method + "  Controller耗时】:" + timeCounsume);

                } else if (methodType.equals("1") || methodType.equals("2")) {//service 或者 dao
                    String s = JSONObject.toJSONString(result);
                    if(s.length() > paramLengthLimit){

                        logger.info("【"+invokeTime+" " + method + "  结果】:" + "结果太长进行截取，前:"+s.substring(0,interceptLength) +"，后:"+s.substring(s.length()-interceptLength) );
                    }else{

                        logger.info("【"+invokeTime+" " + method + "  结果】:" + JSONObject.toJSONString(result));
                    }
                    logger.info("【"+invokeTime+" " + method + "  耗时】:" + timeCounsume);
                }
            }
        } catch (Exception e) {
            logger.error("【AOP日志: " + method + "  接口异常！】", e);
        }
        return result;

    }

    private String getParameters(HttpServletRequest request) {
        try {
            Enumeration<String> parameterNames = request.getParameterNames();
            StringBuilder sb = new StringBuilder();
            String param = null;
            if (parameterNames != null) {
                String key = null;
                while (parameterNames.hasMoreElements()) {
                    key = parameterNames.nextElement();
                    param = request.getParameter(key);
                    if(param.length() > paramLengthLimit){
                        sb.append(key);
                        sb.append(":参数太长进行截取 ");
                        sb.append(param.substring(0,interceptLength));
                        sb.append(",");
                    }else{

                        sb.append(key );
                        sb.append(":");
                        sb.append(param);
                        sb.append( " , ");
                    }
                }
            }
            return new String(sb);
        } catch (Exception e) {
            logger.error("打印接口参数异常!", e);
        }
        return "";
    }
}
