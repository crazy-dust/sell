package com.demotwo.sell.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: liudongyang
 * @Date: 2018/9/15 20:49
 * @Desc:
 */
@Data
public class ResultVO<T> {

    private int code;

    private String msg;

    private T data;
}

