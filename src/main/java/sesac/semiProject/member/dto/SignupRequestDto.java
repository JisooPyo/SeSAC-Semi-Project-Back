package sesac.semiProject.member.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import sesac.semiProject.common.constants.ValidationConstants;

@Getter
@Setter
@Builder
public class SignupRequestDto {
    @Email(
        regexp = "^.+@.+\\..+$",
        message = ValidationConstants.INVALID_EMAIL
    )
    @NotBlank(message = ValidationConstants.EMPTY_CONTENT)
    private String email;

    @Size(min = 8, message = ValidationConstants.TOO_SHORT_PASSWORD)
    @Pattern(
        regexp = ValidationConstants.PASSWORD_REGULAR_EXPRESSION,
        message = ValidationConstants.INVALID_PASSWORD)
    @NotBlank(message = ValidationConstants.EMPTY_CONTENT)
    private String password;

    @NotBlank(message = ValidationConstants.EMPTY_CONTENT)
    private String nickname;
}
