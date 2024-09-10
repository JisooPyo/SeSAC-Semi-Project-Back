package sesac.semiProject.common.exception;

import org.springframework.http.ResponseEntity;
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
}
