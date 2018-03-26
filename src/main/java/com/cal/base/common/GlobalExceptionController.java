package com.cal.base.common;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cal.base.common.enums.ErrorCodeEnum;
import com.cal.base.common.exception.CommonException;
import com.cal.base.common.exception.DaoException;
import com.cal.base.common.exception.ServiceException;
import com.cal.base.common.info.ResponseInfo;

/**
 * Spring MVC统一异常处理
 * @author andyc
 * 2018-3-7
 */
@ControllerAdvice
@ResponseBody
public class GlobalExceptionController {
	 /**
     * 日志记录器
     */
    private Logger log = Logger.getLogger(GlobalExceptionController.class);
    
    /**
     * 运行时异常
     * @param e
     * @return
     * @throws Exception
     */
    @ExceptionHandler(value = RuntimeException.class)
    public ResponseInfo processException(RuntimeException e) throws Exception {
    	log.error(e.getMessage(), e);
        final ResponseInfo response = new ResponseInfo();
        response.code = ErrorCodeEnum.CALL_ERROR.getCode();
        response.msg = ErrorCodeEnum.CALL_ERROR.getMsg();
        return response;
    }
    
   /**
    * 通常异常
    * @param e
    * @return
    * @throws Exception
    */
    @ExceptionHandler(value = CommonException.class)
    public ResponseInfo processException(CommonException e) throws Exception {
    	log.error(e.getMessage(), e);
        final ResponseInfo response = new ResponseInfo();
        response.code = e.getCode();
        response.msg = e.getMsg();
        return response;
    }
    
    /**
     * Dao层异常
     * @param e
     * @return
     * @throws Exception
     */
     @ExceptionHandler(value = DaoException.class)
     public ResponseInfo processException(DaoException e) throws Exception {
     	 log.error(e.getMessage(), e);
         final ResponseInfo response = new ResponseInfo();
         response.code = e.getCode();
         response.msg = e.getMsg();
         return response;
     }
     
     /**
      * Service层异常
      * @param e
      * @return
      * @throws Exception
      */
      @ExceptionHandler(value = ServiceException.class)
      public ResponseInfo processException(ServiceException e) throws Exception {
    	  log.error(e.getMessage(), e);
          final ResponseInfo response = new ResponseInfo();
          response.code = e.getCode();
          response.msg = e.getMsg();
          return response;
      }
      
      /**
       * 超级异常-服务器异常
       * @param e
       * @return
       * @throws Exception
       */
       @ExceptionHandler(value = Exception.class)
       public ResponseInfo processException(Exception e) throws Exception {
     	   log.error(e.getMessage(), e);
           final ResponseInfo response = new ResponseInfo();
           response.code = 500;
           response.msg = e.getMessage();
           return response;
       }
}
