//package com.jwell.classifiedProtection.components.controllerAdvice;
//
//import com.jwell.classifiedProtection.commons.ResultObject;
//import com.jwell.classifiedProtection.commons.exceptions.InvalidException;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.propertyeditors.CustomDateEditor;
//import org.springframework.validation.BindingResult;
//import org.springframework.validation.FieldError;
//import org.springframework.web.bind.MethodArgumentNotValidException;
//import org.springframework.web.bind.WebDataBinder;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.InitBinder;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.text.SimpleDateFormat;
//import java.time.LocalDateTime;
//
///**
// * 通用Controller拦截异常统一处理
// **/
//@ControllerAdvice(basePackages ="com.jwell.classifiedProtection.controller")
//@ResponseBody
//public class GlobalExceptionHandler {
//
//    private static Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
//
//    @InitBinder
//    protected void initBinder(WebDataBinder binder) {
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        binder.registerCustomEditor(LocalDateTime.class, new CustomDateEditor(dateFormat, true));
//    }
//
//    /**
//     * 参数格式异常
//     *
//     * @param req
//     * @param e
//     * @return
//     * @throws InvalidException
//     */
//    @ExceptionHandler(value = InvalidException.class)
//    public ResultObject handleInvalidException(HttpServletRequest req, InvalidException e) throws InvalidException {
//
//        logger.error("请求参数格式异常……");
//        ResultObject r = new ResultObject();
//        r.setCode(HttpServletResponse.SC_BAD_REQUEST);
//        r.setMsg(e.getMessage());
//        logger.info("InvalidException", e.getMessage());
//        return r;
//    }
//
//    /**
//     * 方法参数异常
//     *
//     * @param req
//     * @param e
//     * @return
//     * @throws MethodArgumentNotValidException
//     */
//    @ExceptionHandler(value = MethodArgumentNotValidException.class)
//    public ResultObject handleMethodArgumentNotValidException(HttpServletRequest req, MethodArgumentNotValidException e) throws MethodArgumentNotValidException {
//
//        ResultObject r = new ResultObject();
//        BindingResult bindingResult = e.getBindingResult();
//        String errorMesssage = "Invalid Request:\n";
//
//        for (FieldError fieldError : bindingResult.getFieldErrors()) {
//            errorMesssage += fieldError.getDefaultMessage() + "\n";
//        }
//        r.setCode(HttpServletResponse.SC_BAD_REQUEST);
//        r.setMsg(errorMesssage);
//        logger.info("MethodArgumentNotValidException", e);
//        return r;
//    }
//
//    /**
//     * 全局异常
//     *
//     * @param req
//     * @param e
//     * @return
//     * @throws Exception
//     */
//    @ExceptionHandler(value = Exception.class)
//    public ResultObject handleException(HttpServletRequest req, Exception e) throws Exception {
//
//        logger.info("通用异常……");
//        ResultObject r = new ResultObject();
//        r.setCode(HttpServletResponse.SC_BAD_REQUEST);
//        r.setMsg(e.getMessage());
//        logger.error(e.getMessage(), e);
//        return r;
//    }
//}