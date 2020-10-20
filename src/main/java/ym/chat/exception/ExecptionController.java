package ym.chat.exception;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.extern.slf4j.Slf4j;
import ym.chat.Response;


/**
 * @Author: ym
 * @Description: 统一处理异常
 * @ControllerAdvice:是一个注解，用于开启全局处理异常。捕获到异常才会走这里，不然不会，所以正确的时候没有code和msg
 * @Date: 2019/11/29 5:02 下午
 * @Version:
 */
@ControllerAdvice
@Slf4j
public class ExecptionController {
    /**
     * 将错误日志打印到控制台,可用sl4j简化写法
     */
    private static final Logger logger = LoggerFactory.getLogger(ExecptionController.class);

    /**
     * 处理自定义异常
     * @ExceptionHandler拦截异常类
     * @param
     * @param e
     * @return
     */
    @ExceptionHandler(MyException.class)
    @ResponseBody
    public Response bizExceptionHandler(MyException e){
        log.error("发生业务异常！原因是：{}",e.getErrorMsg());
        return Response.fail(e.getErrorCode(),e.getErrorMsg());
    }

    /**
     * 处理空指针异常
     * @return
     */
    @ExceptionHandler(NullPointerException.class)
    @ResponseBody
    public Response nullPointExceptionHandler(NullPointerException e){
        logger.error("发生空指针异常！原因是：{}",e);
        return Response.fail(MyError.NOT_FOUND);
    }

    /**
     * 处理不明确的错误
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Response exceptionHandler(Exception e) {
        logger.error("发生未知异常！原因是：{}", e);
        return Response.fail(MyError.INTERNAL_SERVER_ERROR);
    }
}
