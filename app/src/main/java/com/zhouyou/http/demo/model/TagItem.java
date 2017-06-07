package com.zhouyou.http.demo.model;

import java.io.Serializable;

/**
 * <p>描述：标签item</p>
 * 作者： zhouyou<br>
 * 日期： 2016/4/27 16:08<br>
 * 版本： v2.0<br>
 */
public class TagItem implements Serializable {
    private int tagId;//:1
    private String tagName;//:"滋润"，
    private String tagType;//:"SKINPROBLEM"
    private int userTagType;//1是系统标签2是用户标签
    private boolean selected;

    /************以下字段只是本地作为中转逻辑处理所用**********************/
    /*List<Integer> tagIds;*/
    /**
     * 栏目对应ID
     */
    public int localId;
    /**
     * 栏目对应NAME
     */
    public String name;
    /**
     * 栏目在整体中的排序顺序  rank
     */
    public int orderId;

    /**
     * 栏目是否选中
     */
    //public int selected;
    public void setData(Integer id, String name, Integer orderId) {
        this.localId = id;
        this.name = name;
        this.orderId = orderId;
    }

    public TagItem(String tagName) {
        this.tagName = tagName;
    }

    public TagItem(int tagId, String tagName, String tagType) {
        this.tagId = tagId;
        this.tagName = tagName;
        this.tagType = tagType;
    }

    public int getTagId() {
        return tagId;
    }

    public void setTagId(int tagId) {
        this.tagId = tagId;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getTagType() {
        return tagType;
    }

    public void setTagType(String tagType) {
        this.tagType = tagType;
    }

    public int getId() {
        return localId;
    }

    public void setId(Integer id) {
        this.localId = id;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

   /* public List<Integer> getTagIds() {
        return tagIds;
    }

    public void setTagIds(List<Integer> tagIds) {
        this.tagIds = tagIds;
    }*/

    public int getUserTagType() {
        return userTagType;
    }

    public void setUserTagType(int userTagType) {
        this.userTagType = userTagType;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Override
    public String toString() {
        return "TagItem{" +
                "tagId=" + tagId +
                ", tagName='" + tagName + '\'' +
                ", tagType='" + tagType + '\'' +
                '}';
    }

}
