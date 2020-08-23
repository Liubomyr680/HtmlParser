package com.htmlparser.entity;

import lombok.Builder;
import lombok.Data;

import java.util.Date;
@Data
@Builder
public class XBetCoupon {

    private String category;
    private String date;
    private String player1;
    private String player2;
    private String price1;
    private String price2;
    private Date parseDate;
}
