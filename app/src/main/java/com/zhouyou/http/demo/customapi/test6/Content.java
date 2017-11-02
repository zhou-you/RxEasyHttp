package com.zhouyou.http.demo.customapi.test6;

/**
 * <p>描述：（这里用一句话描述这个类的作用)</p>
 * 作者： zhouyou<br>
 * 日期： 2017/11/1 20:35 <br>
 * 版本： v1.0<br>
 */
public class Content {
    private String from;
    private String to;
    private String vendor;
    private String out;
    private int errNo;

    public String getFrom() {
        return from;
    }

    public Content setFrom(String from) {
        this.from = from;
        return this;
    }

    public String getVendor() {
        return vendor;
    }

    public Content setVendor(String vendor) {
        this.vendor = vendor;
        return this;
    }

    public String getOut() {
        return out;
    }

    public Content setOut(String out) {
        this.out = out;
        return this;
    }

    public int getErrNo() {
        return errNo;
    }

    public Content setErrNo(int errNo) {
        this.errNo = errNo;
        return this;
    }

    @Override
    public String toString() {
        return "Content{" +
                "from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", vendor='" + vendor + '\'' +
                ", out='" + out + '\'' +
                ", errNo=" + errNo +
                '}';
    }
}
