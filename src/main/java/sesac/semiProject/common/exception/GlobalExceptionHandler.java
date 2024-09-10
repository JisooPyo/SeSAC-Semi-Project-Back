package sesac.semiProject.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import sesac.semiProject.common.dto.ApiResponseDto;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({CustomException.class})
    public ResponseEntity<ApiResponseDto> handlerCustomException(CustomException e) {
        ApiResponseDto apiResponseDto = new ApiResponseDto(
            e.getErrorCode().getStatusCode(), e.getMessage()
        );
        return ResponseEntity.badRequest().body(apiResponseDto);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ApiResponseDto> handlerValicationException(MethodArgumentNotValidException ex) {
        ApiResponseDto apiResponseDto = null;
        for (FieldError fieldError : ex.getFieldErrors()) {
            apiResponseDto = new ApiResponseDto(HttpStatus.BAD_REQUEST.value(), fieldError.getDefaultMessage());
            break;
        }
        return ResponseEntity.badRequest().body(apiResponseDto);
    }
}
