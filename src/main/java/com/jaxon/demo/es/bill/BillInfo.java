package com.jaxon.demo.es.bill;

import java.util.Date;

public class BillInfo {

    private String id;

    private String billId;

    private String billType;

    private String billFileName;

    private Date saleDate;

    private Double amount;


    public BillInfo(String id,String billId, String billType, String billFileName, Date saleDate, Double amount) {
        this.id = id;
        this.billId = billId;
        this.billType = billType;
        this.billFileName = billFileName;
        this.saleDate = saleDate;
        this.amount = amount;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBillId() {
        return billId;
    }

    public void setBillId(String billId) {
        this.billId = billId;
    }

    public String getBillType() {
        return billType;
    }

    public void setBillType(String billType) {
        this.billType = billType;
    }

    public String getBillFileName() {
        return billFileName;
    }

    public void setBillFileName(String billFileName) {
        this.billFileName = billFileName;
    }

    public Date getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(Date saleDate) {
        this.saleDate = saleDate;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
