//package com.jwell.classifiedProtection.commons.aop;
//
//import com.alibaba.fastjson.JSONObject;
//import com.jwell.classifiedProtection.commons.ResultObject;
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.Signature;
//import org.aspectj.lang.annotation.*;
//import org.aspectj.lang.reflect.MethodSignature;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//
//import javax.servlet.http.HttpServletRequest;
//import java.lang.reflect.Method;
//import java.util.Arrays;
//import java.util.LinkedHashMap;
//import java.util.List;
//import java.util.Map;
//
///**
// * 对controller中的User统一注入（获取参数后注入）
// */
//@Configuration
//@Aspect
//public class ServiceAspect {
//
//    private static final Logger logger = LoggerFactory.getLogger(ServiceAspect.class);
//
//    private static final String LINE_SEPARATOR = System.lineSeparator();
//
//    private final String ExpAnnotationRequestMappingPonit = "@annotation(org.springframework.web.bind.annotation.RequestMapping)";
//
//    @Pointcut(ExpAnnotationRequestMappingPonit)
//    public void excuteService() {
//
//    }
//
//    //执行方法前的拦截方法
//    @Before("excuteService()")
//    public void doBeforeMethod(JoinPoint joinPoint) {
//
//        //开始打印请求日志
//        this.printParameterLog(joinPoint);
//    }
//
//    /**
//     * printParameterLog
//     *
//     * @param joinPoint
//     */
//    private void printParameterLog(JoinPoint joinPoint) {
//
//        //开始打印请求日志
//        ServletRequestAttributes servletRequestAttributes =
//                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//        HttpServletRequest request = servletRequestAttributes.getRequest();
//
//        //获取@WebLog注解的描述信息
//        String requestMappingDescription = this.getRequestMappingDescription(joinPoint);
//
//        //获取请求参数名和参数值
////        String requestNameAndArgs = this.getRequestNameAndArgs(joinPoint);
//
//        //打印相关的请求参数
//        logger.info("====================================Start====================================");
//        //打印请求URL
//        logger.info("URL                :{}", request.getRequestURL().toString());
//        //打印描述信息
//        logger.info("Description        :{}", requestMappingDescription);
//        //打印HTTP method
//        logger.info("HTTP Method        :{}", request.getMethod());
//        //打印Controller的全路径和执行方法
//        logger.info("Class Method:      :{}.{}", joinPoint.getSignature().getDeclaringType(),
//                joinPoint.getSignature().getName());
//        //打印请求的IP
//        logger.info("IP                 :{}", request.getRemoteAddr());
//        //打印请求参数名
//        logger.info("Requests ArgNames  :{}", JSONObject.toJSONString(
//                ((MethodSignature) joinPoint.getSignature()).getParameterNames())
//        );
//        //打印请求参数值
////        logger.info("Requests ArgValues  :{}", JSONObject.toJSONString(joinPoint.getArgs()));
//        //打印请求参数名和参数值
////        logger.info("Requests Name&Arg:{}", requestNameAndArgs);
//
//    }
//
//    /**
//     * getRequestMappingDescription
//     *
//     * @param joinPoint
//     */
//    private String getRequestMappingDescription(JoinPoint joinPoint) {
//
//        Signature signature = joinPoint.getSignature();
//        Object object = joinPoint.getTarget();
//
//        //MethodSignature是signature的子类
//        MethodSignature methodSignature = (MethodSignature) signature;
//        Method targetMethod = methodSignature.getMethod();
//
//        StringBuilder stringBuilder = new StringBuilder();
//        //判断是否存在requestMapping注释
//        boolean classPresent = object.getClass().isAnnotationPresent(RequestMapping.class);
//        if (classPresent) {
//
//            RequestMapping classRequestMapping = object.getClass().getAnnotation(RequestMapping.class);
//            String[] classRequestMappingValues = classRequestMapping.value();
//
//            if (classRequestMappingValues.length > 0) {
//                stringBuilder.append(classRequestMappingValues[0]);
//            }
//        }
//
//        boolean methodPresent = targetMethod.isAnnotationPresent(RequestMapping.class);
//        if (methodPresent) {
//
//            //得到requestMapping注释
//            RequestMapping methodAnnotation = targetMethod.getAnnotation(RequestMapping.class);
//            String[] methodAnnotationValues = methodAnnotation.value();
//
//            if (methodAnnotationValues.length > 0) {
//                stringBuilder.append(methodAnnotationValues[0]);
//            }
//        }
//        return stringBuilder.toString();
//
//    }
//
//    /**
//     * getRequestNameAndArg
//     *
//     * @param joinPoint
//     */
//    private String getRequestNameAndArgs(JoinPoint joinPoint) {
//
//        //参数值
//        Object[] args = joinPoint.getArgs();
//
//        //首先获取方法名称列表
//        MethodSignature msg = (MethodSignature) joinPoint.getSignature();
//        String[] paramName = msg.getParameterNames();
//        List<String> paramNameList = Arrays.asList(paramName);
//
//        Map dataMap = new LinkedHashMap<>();
//        for (String param : paramNameList) {
//
//            if (param.equals("response") || param.equals("file")) {
//                continue;
//            }
//            //返回参数位置
//            Integer pos = paramNameList.indexOf(param);
//            dataMap.put(param, args[pos]);
//        }
//        return JSONObject.toJSONString(dataMap);
//    }
//
//    /**
//     * 后置返回通知
//     * 这里需要注意的是:
//     * 如果参数中的第一个参数为JoinPoint，则第二个参数为返回值的信息
//     * 如果参数中的第一个参数不为JoinPoint，则第一个参数为returning中对应的参数
//     * returning 限定了只有目标方法返回值与通知方法相应参数类型时才能执行后置返回通知，否则不执行，
//     * 对于returning对应的通知方法参数为Object类型将匹配任何目标返回值
//     */
//    @AfterReturning(value = ExpAnnotationRequestMappingPonit, returning = "keys")
//    public Object doAfterReturningAdvice(JoinPoint joinPoint, Object keys) {
//        logger.info("第一个后置返回通知的返回值：" + keys);
//        if (keys instanceof ResultObject) {
////            ResultObject resultVO = (ResultObject) keys;
////            String message = resultVO.getMsg();
////            resultVO.setMsg("通过AOP把值修改了 " + message);
//        }
//        logger.info("修改完毕-->返回方法为:" + keys);
//        return keys;
//    }
//
//    /**
//     * 异常通知 用于拦截service层记录异常日志
//     *
//     * @param joinPoint
//     * @param e
//     */
//    @AfterThrowing(pointcut = "excuteService()", throwing = "e")
//    public void doAfterThrowing(JoinPoint joinPoint, Throwable e) {
//
//    }
//
//    /**
//     * 后置通知
//     *
//     * @param joinPoint
//     */
//    @After("excuteService()")
//    public void doAfter(JoinPoint joinPoint) throws Throwable {
//
//        //在接口结束后换行，方便分割查看
//        logger.info("====================================End====================================" + LINE_SEPARATOR);
//
//    }
//
//    /**
//     * * 环绕通知：
//     * * 环绕通知非常强大，可以决定目标方法是否执行，什么时候执行，执行时是否需要替换方法参数，执行完毕是否需要替换返回值。
//     * * 环绕通知第一个参数必须是org.aspectj.lang.ProceedingJoinPoint类型
//     */
//    //@Around(ExpGetResultDataPonit)
////    @Around("excuteService()")
////    public Object doAroundAdvice(ProceedingJoinPoint proceedingJoinPoint) {
////        logger.info("--------------进入AOP环绕通知-------------");
////        logger.info("环绕通知的目标方法名：" + proceedingJoinPoint.getSignature().getName());
////
////        long startTime = System.currentTimeMillis();
////
//////        this.processInputArg(proceedingJoinPoint);
////        try {//obj之前可以写目标方法执行前的逻辑
////
////            //args参数修改后重新需要传回去，否则controller中无法获取到修改后的值
////            Object obj = proceedingJoinPoint.proceed(proceedingJoinPoint.getArgs());//调用执行目标方法
//////            Object object = this.processOutPutObj(obj);
////
////            logger.info("Response Args          :{}", JSONObject.toJSONString(obj));
////            return obj;
////
////        } catch (Throwable throwable) {
////            throwable.printStackTrace();
////        }
////
////        logger.info("Time-Consuming         :{}", System.currentTimeMillis() - startTime);
////
////
////        return null;
////    }
//
//
//    /**
//     * 处理返回对象
//     */
////    private Object processOutPutObj(Object obj) {
////        if (obj != null) {
////            logger.info("OBJ 原本为：" + obj.toString());
////        }
////
////        if (obj instanceof ResultObject) {
////            //ResultObject resultVO = (ResultObject) obj;
////            //resultVO.setMsg("哈哈，我把值修改了" + resultVO.getMsg());
////            //logger.info(resultVO.getCode());
////            return obj;
////        }
////        return null;
////    }
//
//    /**
//     * 保存登录和退出的日志
//     */
////    private void saveLog(JoinPoint joinPoint) throws Exception {
////
////        //开始打印请求日志
////        ServletRequestAttributes servletRequestAttributes =
////                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
////        HttpServletRequest request = servletRequestAttributes.getRequest();
////
//////        HttpSession session = request.getSession(false);
//////        //读取session中的用户
//////        User user = (User) session.getAttribute("user");
////
////        String userName = "";
////
////        logger.info("======================保存登录和退出的日志============================");
////        Log log = new Log();
////        log.setModuleName("权限管理模块");
////        String Authorization = request.getHeader("Authorization");
////
////        Claims c = JwtUtils.parseJWT(Authorization);
////        User userObject = JSONObject.parseObject(c.get("userJsonString", String.class), new TypeReference<User>() {
////        });
////        if (userObject != null) {
////            userName = userObject.getUserName();
////        }
////
////    }
//
//    /**
//     * 处理输入参数
//     *
//     * @param proceedingJoinPoint 入参方法
//     * @return
//     */
////    private void processInputArg(ProceedingJoinPoint proceedingJoinPoint) {
////
////        Object[] args = proceedingJoinPoint.getArgs();
////
////        //首先获取方法名称列表
////        MethodSignature msg = (MethodSignature) proceedingJoinPoint.getSignature();
////        String[] paramName = msg.getParameterNames();
////        List<String> paramNameList = Arrays.asList(paramName);
////
////        //如果有userId这个参数
////        if (paramNameList.contains("areaId")) {
////
////            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
////            HttpSession session = request.getSession(false);
////
////        }
////    }
//}