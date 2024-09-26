package com.example.learning_spring_boot.exception;

public enum ErrorCode {
    INVALID_KEY(1005,"Invalid key"),
    UNCATEGORIZED_EXCEPTION(999,"Uncategorized exception"),
    USER_EXISTED(1001, "User already exist!"),
    USERNAME_INVALID(1002,"Username is not valid"),
    INVALID_PASSWORD(1004,"Password is not valid"),
    IVALID_EMAIL(1001,"Email is empty or invalid")
    ;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    private int code;
    private String message;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
