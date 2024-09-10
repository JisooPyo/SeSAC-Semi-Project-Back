package sesac.semiProject.common.dto;

import lombok.Getter;

@Getter
public class ApiResponseDto {
    private int statusCode;
    private String message;

    public ApiResponseDto(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }
}
