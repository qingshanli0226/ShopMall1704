package com.example.framework.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class AddressBarBean {

    @Id(autoincrement = true)
    private Long id;

    //用户
    private String name;
    //收货人
    private String consignee;
    //手机号码
    private String cell_phone_number;
    //所在地区
    private String location;
    //详细地址
    private String detailed_address;
    //标签
    private String tag;

    @Generated(hash = 1648776116)
    public AddressBarBean(Long id, String name, String consignee,
            String cell_phone_number, String location, String detailed_address,
            String tag) {
        this.id = id;
        this.name = name;
        this.consignee = consignee;
        this.cell_phone_number = cell_phone_number;
        this.location = location;
        this.detailed_address = detailed_address;
        this.tag = tag;
    }
    @Generated(hash = 939423815)
    public AddressBarBean() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getConsignee() {
        return this.consignee;
    }
    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }
    public String getCell_phone_number() {
        return this.cell_phone_number;
    }
    public void setCell_phone_number(String cell_phone_number) {
        this.cell_phone_number = cell_phone_number;
    }
    public String getLocation() {
        return this.location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public String getDetailed_address() {
        return this.detailed_address;
    }
    public void setDetailed_address(String detailed_address) {
        this.detailed_address = detailed_address;
    }
    public String getTag() {
        return this.tag;
    }
    public void setTag(String tag) {
        this.tag = tag;
    }
    

}