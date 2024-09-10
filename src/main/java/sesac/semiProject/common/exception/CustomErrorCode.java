package sesac.semiProject.common.exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import sesac.semiProject.common.constants.ErrorMsgConstants;

@Getter
@AllArgsConstructor
public enum CustomErrorCode {
    TODO_NOT_FOUND(HttpStatus.NOT_FOUND.value(), ErrorMsgConstants.TODO_NOT_FOUND);

    private final int statusCode;
    private final String message;
}
