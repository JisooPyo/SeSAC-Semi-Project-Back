package sesac.semiProject.common.constants;

public class ValidationConstants {
    public static final String EMPTY_CONTENT = "빈 칸은 허용되지 않습니다.";
    public static final String INVALID_EMAIL = "올바른 이메일 형식이 아닙니다.";
    public static final String TOO_SHORT_PASSWORD = "비밀번호는 8자 이상이어야 합니다.";
    public static final String PASSWORD_REGULAR_EXPRESSION = "^(?=.*[a-z])(?=.*\\d)(?=.*[!?@#$%^&*_=+-]).+$";
    public static final String INVALID_PASSWORD = "비밀번호는 영소문자, 숫자, 특수문자(!,?,@,#,$,%,^,&,*,_,=,+,-)가 한 개 이상 포함되어야 합니다.";
}
