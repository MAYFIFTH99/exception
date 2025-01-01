package exception.exception.exHandler.advice;

import exception.exception.exHandler.ErrorResult;
import exception.exception.exception.UserException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice//(Target 설정 가능, 생략 시 글로벌(모든 컨트롤러)에 적용
public class ExControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class) // 200 OK
    public ErrorResult illegalExHandler(IllegalArgumentException ex) {
        log.error("[@ExceptionHandler] ", ex);
        return new ErrorResult("illegal_argument_exception", ex.getMessage());
    }

    @ExceptionHandler// 매개변수로 들어오는 Exception Class와 같은 경우 생략 가능
    public ResponseEntity<ErrorResult> userExHandler(UserException ex) {
        log.error("[@ExceptionHandler] ", ex);
        ErrorResult userException = new ErrorResult("user_exception", ex.getMessage());
        return new ResponseEntity<>(userException, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public ErrorResult exHandler(Exception ex) {
        log.error("[@ExceptionHandler] ", ex);
        return new ErrorResult("EX", "내부 오류");
    }
}
