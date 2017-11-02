package com.zhouyou.http.demo.customapi.test6;

import java.util.List;

/**
 * <p>描述：（这里用一句话描述这个类的作用)</p>
 * 作者： zhouyou<br>
 * 日期： 2017/11/1 20:35 <br>
 * 版本： v1.0<br>
 */
public class Content2 {
    private String ph_en;
    private String ph_am;
    private String ph_en_mp3;
    private String ph_am_mp3;
    private String ph_tts_mp3;
    private List<String> word_mean;

    private String from;
    private String to;
    private String out;
    private String vendor;
    private int err_no;

    public void setPh_en(String ph_en) {
        this.ph_en = ph_en;
    }

    public String getPh_en() {
        return ph_en;
    }

    public void setPh_am(String ph_am) {
        this.ph_am = ph_am;
    }

    public String getPh_am() {
        return ph_am;
    }

    public void setPh_en_mp3(String ph_en_mp3) {
        this.ph_en_mp3 = ph_en_mp3;
    }

    public String getPh_en_mp3() {
        return ph_en_mp3;
    }

    public void setPh_am_mp3(String ph_am_mp3) {
        this.ph_am_mp3 = ph_am_mp3;
    }

    public String getPh_am_mp3() {
        return ph_am_mp3;
    }

    public String getPh_tts_mp3() {
        return ph_tts_mp3;
    }

    public Content2 setPh_tts_mp3(String ph_tts_mp3) {
        this.ph_tts_mp3 = ph_tts_mp3;
        return this;
    }

    public List<String> getWord_mean() {
        return word_mean;
    }

    public Content2 setWord_mean(List<String> word_mean) {
        this.word_mean = word_mean;
        return this;
    }

    public String getFrom() {
        return from;
    }

    public Content2 setFrom(String from) {
        this.from = from;
        return this;
    }

    public String getTo() {
        return to;
    }

    public Content2 setTo(String to) {
        this.to = to;
        return this;
    }

    public String getOut() {
        return out;
    }

    public Content2 setOut(String out) {
        this.out = out;
        return this;
    }

    public String getVendor() {
        return vendor;
    }

    public Content2 setVendor(String vendor) {
        this.vendor = vendor;
        return this;
    }

    public int getErr_no() {
        return err_no;
    }

    public Content2 setErr_no(int err_no) {
        this.err_no = err_no;
        return this;
    }

    @Override
    public String toString() {
        return "Content2{" +
                "ph_en='" + ph_en + '\'' +
                ", ph_am='" + ph_am + '\'' +
                ", ph_en_mp3='" + ph_en_mp3 + '\'' +
                ", ph_am_mp3='" + ph_am_mp3 + '\'' +
                ", ph_tts_mp3='" + ph_tts_mp3 + '\'' +
                ", word_mean=" + word_mean +
                ", from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", out='" + out + '\'' +
                ", vendor='" + vendor + '\'' +
                ", err_no=" + err_no +
                '}';
    }
}
