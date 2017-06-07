/*
 * Copyright (C) 2017 zhouyou(478319399@qq.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.zhouyou.http.demo.customapi;

public class ResultBean {
    private String province;
    private String city;
    private String areacode;
    private String zip;
    private String company;
    private String card;

    public String getProvince() {
        return province;
    }

    public ResultBean setProvince(String province) {
        this.province = province;
        return this;
    }

    public String getCity() {
        return city;
    }

    public ResultBean setCity(String city) {
        this.city = city;
        return this;
    }

    public String getAreacode() {
        return areacode;
    }

    public ResultBean setAreacode(String areacode) {
        this.areacode = areacode;
        return this;
    }

    public String getZip() {
        return zip;
    }

    public ResultBean setZip(String zip) {
        this.zip = zip;
        return this;
    }

    public String getCompany() {
        return company;
    }

    public ResultBean setCompany(String company) {
        this.company = company;
        return this;
    }

    public String getCard() {
        return card;
    }

    public ResultBean setCard(String card) {
        this.card = card;
        return this;
    }

    @Override
    public String toString() {
        return "ResultBean{" +
                "province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", areacode='" + areacode + '\'' +
                ", zip='" + zip + '\'' +
                ", company='" + company + '\'' +
                ", card='" + card + '\'' +
                '}';
    }
}
