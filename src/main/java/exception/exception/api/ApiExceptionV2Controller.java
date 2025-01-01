package exception.exception.api;

import exception.exception.exHandler.ErrorResult;
import exception.exception.exception.UserException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @ExceptionHandler는 자신이 포함된 Controller 클래스 내부에서만 적용된다.
 */
@Slf4j
@RestController
public class ApiExceptionV2Controller {

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


    @GetMapping("/api2/members/{id}")
    public MemberDto getMember(@PathVariable("id") String id) {
        if (id.equals("ex")) {
            throw new RuntimeException("잘못된 사용자");
        }
        if (id.equals("bad")) {
            throw new IllegalArgumentException("잘못된 입력 값");
        }
        if (id.equals("user-ex")) {
            throw new UserException("사용자 오류");
        }

        return new MemberDto(id, "hello " + id);
    }

    @Data
    @AllArgsConstructor
    static class MemberDto {
        private String memberId;
        private String name;
    }
}
