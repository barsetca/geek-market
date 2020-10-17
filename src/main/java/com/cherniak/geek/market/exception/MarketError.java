package com.cherniak.geek.market.exception;

import lombok.Data;

import java.util.Date;

@Data
public class MarketError {
    private  int status;
    private String msg;
    private Date date;

    public MarketError(int status, String msg) {
        this.status = status;
        this.msg = msg;
        this.date = new Date();
    }
}
