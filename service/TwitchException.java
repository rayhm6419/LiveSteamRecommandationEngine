package com.aoshine.demo.service;

//主要作用，如果调用期间有什么errors，这个class会帮助判断哪里出现错误

public class TwitchException extends RuntimeException {
    public TwitchException(String errorMessage) {
        super(errorMessage);
    }
}

