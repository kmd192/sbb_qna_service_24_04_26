package com.exam.sbb;

public class SignupEmailDuplicatedExcetion extends RuntimeException {

    public SignupEmailDuplicatedExcetion(String message){
        super(message);
    }
}
