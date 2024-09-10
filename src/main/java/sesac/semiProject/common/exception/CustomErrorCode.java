package sesac.semiProject.common.exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import sesac.semiProject.common.constants.ErrorMsgConstants;

@Getter
@AllArgsConstructor
public enum CustomErrorCode {
    // todo: 예시 코드입니다. 필요하지 않다면 삭제합니다.
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND.value(), ErrorMsgConstants.MEMBER_NOT_FOUND);

    private final int statusCode;
    private final String message;
}
