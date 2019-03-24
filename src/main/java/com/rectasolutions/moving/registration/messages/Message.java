package com.rectasolutions.moving.registration.messages;

public enum Message {
    SUCCESSFUL_USER_CREATION(101, "User was created successfully"),
    USER_ALREADY_EXISTS(501, "User with the specified email already exists, please try another email or try to login"),
    NOT_ALL_MANDATORY_FIELDS(502, "Not all mandatory fields are specified"),
    USER_NOT_EXISTS(503, "Incorrect username or password");

    private String messageText;
    private int messageCode;
    Message(int code, String message){
        this.messageCode=code;
        this.messageText=message;

    }

    public String getMessageText() {
        return messageText;
    }

    public int getMessageCode() {
        return messageCode;
    }
}
