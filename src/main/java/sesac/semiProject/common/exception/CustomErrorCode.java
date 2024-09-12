package sesac.semiProject.common.exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import sesac.semiProject.common.constants.ErrorMsgConstants;

@Getter
@AllArgsConstructor
public enum CustomErrorCode {
    TODO_NOT_FOUND(HttpStatus.NOT_FOUND.value(), ErrorMsgConstants.TODO_NOT_FOUND),
    ALREADY_ACCOUNT_EXISTS(HttpStatus.BAD_REQUEST.value(), ErrorMsgConstants.ALREADY_ACCOUNT_EXISTS),
    INVALID_ACCESS(HttpStatus.BAD_REQUEST.value(), ErrorMsgConstants.INVALID_ACCESS);

    private final int statusCode;
    private final String message;
}
