package com.gzrijing.workassistant.data;

import org.litepal.crud.DataSupport;

public class SuppliesData extends DataSupport {
    private int id;
    private String No;          //材料编号
    private String name;        //材料名称
    private String spec;        //材料规格
    private String unit;        //材料单位
    private int num;            //数量
    private String flag;        //创建，申请，领用三种状态
    private String state;       //申请材料状态（申请中，未能准备，领用）
    private BusinessData businessData;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNo() {
        return No;
    }

    public void setNo(String no) {
        No = no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public BusinessData getBusinessData() {
        return businessData;
    }

    public void setBusinessData(BusinessData businessData) {
        this.businessData = businessData;
    }
}
