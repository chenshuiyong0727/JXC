package com.ledao.exception;

import com.ledao.model.ResponseMsg;
import com.ledao.model.ServerErrorConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;

/**
 * @author wpq
 * @function 全局异常处理
 * @Date 2021/12/07
 */
@ControllerAdvice
@ResponseBody
@Slf4j
public class WebExceptionHandler extends DefaultHandlerExceptionResolver {

  /**
   * 500 - Internal Server Error
   */
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler(Exception.class)
  public ResponseMsg<Object> handleException(Exception e) {
    ResponseMsg<Object> ret = new ResponseMsg<>();
    if (e instanceof IllegalArgumentException) {
      ret.err(ServerErrorConst.ERR_PARAM_ILLEGAL, e.getMessage());
    } else if (e instanceof OpenSigCheckFailException) {
      ret.err(ServerErrorConst.ERR_OPEN_SIG_CHECKED_FAIL, e.getMessage());
    } else if (e instanceof UserTokenTakeOverException) {
      ret.err(ServerErrorConst.ERR_TOKEN_TAKE_OVER, ServerErrorConst.ERR_TOKEN_TAKE_OVER_MSG);
    } else if (e instanceof ClientUnsupportedAPIException) {
      ret.err(ServerErrorConst.ERR_CLIENT_UNSUPPORTED_API,
          ServerErrorConst.ERR_CLIENT_UNSUPPORTED_API_MSG);
    } else if (e instanceof NotConfiguredException) {
      ret.err(ServerErrorConst.ERR_NOT_CONFIGURED, e.getMessage());
    } else if (e instanceof BusinessException) {
      ret.err(ServerErrorConst.ERR_OTHER, e.getMessage());
    } else if (e instanceof UserTokenException) {
      ret.err(ServerErrorConst.ERR_TOKEN_EMPTY, e.getMessage());
    } else if (e instanceof ServiceException) {
      ServiceException se = (ServiceException) e;
      ret.err(se.getCode(), e.getMessage());
    } else if (e instanceof ServerErrorException) {
      ret.err(((ServerErrorException) e).getThisErrorCode(),
          ((ServerErrorException) e).getThisMessage());
    } else {
      log.error(e.getMessage(), e);
      ret.err(ServerErrorConst.ERR_OTHER, ServerErrorConst.ERR_OTHER_MSG);
    }
    log.error(e.getMessage(), e);
    return ret;
  }

  /**
   * 400 - Bad Request
   */
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(MissingServletRequestParameterException.class)
  public ResponseMsg<Object> handleBadRequestException(Exception e) {
    ResponseMsg<Object> ret = new ResponseMsg<>();
    ret.err(ServerErrorConst.ERR_HTTP_BAD_REQUEST, ServerErrorConst.ERR_HTTP_BAD_REQUEST_MSG);
    return ret;
  }

  /**
   * 404 - Not Found
   */
  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ExceptionHandler(NoHandlerFoundException.class)
  public ResponseMsg<Object> handleNotFoundException(Exception e) {
    ResponseMsg<Object> ret = new ResponseMsg<>();
    ret.err(ServerErrorConst.ERR_HTTP_NOT_FOUND, ServerErrorConst.ERR_HTTP_NOT_FOUND_MSG);
    return ret;
  }

  /**
   * 405 - Method Not Allowed
   */
  @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  public ResponseMsg<Object> handleMethodNotAllowException(Exception e) {
    ResponseMsg<Object> ret = new ResponseMsg<>();
    ret.err(ServerErrorConst.ERR_HTTP_METHOD_NOT_ALLOW,
        ServerErrorConst.ERR_HTTP_METHOD_NOT_ALLOW_MSG);
    return ret;
  }

}
