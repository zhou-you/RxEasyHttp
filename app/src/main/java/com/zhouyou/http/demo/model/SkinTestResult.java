package com.zhouyou.http.demo.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>描述：获取首页肤质测肤数据</p>
 * 作者： zhouyou<br>
 * 日期： 2016/4/27 16:03<br>
 * 版本： v2.0<br>
 */
public class SkinTestResult implements Serializable {
    private Integer testScore;//": 85,
    private Integer waterOilScore;//":76 ,
    private Integer fineLinesScore;//":76,
    private Integer poreScore;//":76，
    private Integer splashScore;//":76 ,
    private Integer sensitiveScore;//":76  ,
    private Integer problemNumber;//":3  ，
    private Integer skinDataCode;//": 1
    private ArrayList<TagItem> tagList;
    private boolean questionFlag;
    private String measureTime;
    private List<SkinTypeListBean> skinTypeList; // 我的肤质信息

    public SkinTestResult() {
    }

    public SkinTestResult(Integer skinDataCode) {
        this.skinDataCode = skinDataCode;
        this.testScore = null;//": 85,
        this.waterOilScore = null;//":76 ,
        this.fineLinesScore = null;//":76,
        this.poreScore = null;//":76，
        this.splashScore = null;//":76 ,
        this.sensitiveScore = null;//":76  ,
        this.problemNumber = null;//":3  ，
        this.skinDataCode = null;//": 1
    }

    public ArrayList<TagItem> getTagList() {
        return tagList;
    }

    public void setTagList(ArrayList<TagItem> tagList) {
        this.tagList = tagList;
    }

    public Integer getTestScore() {
        return testScore;
    }

    public void setTestScore(Integer testScore) {
        this.testScore = testScore;
    }

    public Integer getWaterOilScore() {
        return waterOilScore;
    }

    public void setWaterOilScore(Integer waterOilScore) {
        this.waterOilScore = waterOilScore;
    }

    public Integer getFineLinesScore() {
        return fineLinesScore;
    }

    public void setFineLinesScore(Integer fineLinesScore) {
        this.fineLinesScore = fineLinesScore;
    }

    public Integer getPoreScore() {
        return poreScore;
    }

    public void setPoreScore(Integer poreScore) {
        this.poreScore = poreScore;
    }

    public Integer getSplashScore() {
        return splashScore;
    }

    public void setSplashScore(Integer splashScore) {
        this.splashScore = splashScore;
    }

    public Integer getSensitiveScore() {
        return sensitiveScore;
    }

    public void setSensitiveScore(Integer sensitiveScore) {
        this.sensitiveScore = sensitiveScore;
    }

    public Integer getProblemNumber() {
        return problemNumber;
    }

    public void setProblemNumber(Integer problemNumber) {
        this.problemNumber = problemNumber;
    }

    public Integer getSkinDataCode() {
        return skinDataCode;
    }

    public void setSkinDataCode(Integer skinDataCode) {
        this.skinDataCode = skinDataCode;
    }

    public boolean isQuestionFlag() {
        return questionFlag;
    }

    public void setQuestionFlag(boolean questionFlag) {
        this.questionFlag = questionFlag;
    }

    public String getMeasureTime() {
        return measureTime;
    }

    public void setMeasureTime(String measureTime) {
        this.measureTime = measureTime;
    }

    public List<SkinTypeListBean> getSkinTypeList() {
        return skinTypeList;
    }

    public void setSkinTypeList(List<SkinTypeListBean> skinTypeList) {
        this.skinTypeList = skinTypeList;
    }

    @Override
    public String toString() {
        return "SkinTestResult{" +
                "testScore=" + testScore +
                ", waterOilScore=" + waterOilScore +
                ", fineLinesScore=" + fineLinesScore +
                ", poreScore=" + poreScore +
                ", splashScore=" + splashScore +
                ", sensitiveScore=" + sensitiveScore +
                ", problemNumber=" + problemNumber +
                ", skinDataCode=" + skinDataCode +
                ", tagList=" + tagList +
                ", questionFlag=" + questionFlag +
                ", measureTime='" + measureTime + '\'' +
                ", skinTypeList=" + skinTypeList +
                '}';
    }

    public static class SkinTypeListBean implements Serializable {
        private int typeId;
        private String name;

        private TestResultBean testResult;

        public int getTypeId() {
            return typeId;
        }

        public void setTypeId(int typeId) {
            this.typeId = typeId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public TestResultBean getTestResult() {
            return testResult;
        }

        public void setTestResult(TestResultBean testResult2) {
            testResult = testResult2;
        }

        public static class TestResultBean {
            private String itemName;
            private int itemCode;

            public String getItemName() {
                return itemName;
            }

            public void setItemName(String itemName) {
                this.itemName = itemName;
            }

            public int getItemCode() {
                return itemCode;
            }

            public void setItemCode(int itemCode) {
                this.itemCode = itemCode;
            }
        }

        @Override
        public String toString() {
            return "SkinTypeListBean{" +
                    "typeId=" + typeId +
                    ", name='" + name + '\'' +
                    ", testResult=" + testResult +
                    '}';
        }
    }
}
