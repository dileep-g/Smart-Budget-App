package com.iui.smartbudget.utilities;


import java.util.Date;
public class Record implements Comparable<Record> {
    private int id;
    private Date dateTime;
    private String vendor;
    private String category;
    private float expense;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public float getExpense() {
        return expense;
    }

    public void setExpense(float expense) {
        this.expense = expense;
    }


    @Override
    public int compareTo(Record r) {
        return r.dateTime.compareTo(this.dateTime);
    }
}
