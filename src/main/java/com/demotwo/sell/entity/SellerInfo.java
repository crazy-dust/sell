package com.demotwo.sell.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @Author: liudongyang
 * @Date: 2018/9/28 16:08
 * @Desc:
 */
@Entity
@Data
@DynamicUpdate
public class SellerInfo {

    @Id
    private String sellerId;

    private String openId;

    private String username;

    private String password;
}