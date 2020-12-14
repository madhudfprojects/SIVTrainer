package com.det.skillinvillage.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetMobileSubMenuResponseList {

    @SerializedName("Menu_ID")
    @Expose
    private Integer menuID;
    @SerializedName("Menu_Name")
    @Expose
    private String menuName;
    @SerializedName("Menu_Link")
    @Expose
    private String menuLink;
    @SerializedName("Menu_Sort")
    @Expose
    private Object menuSort;
    @SerializedName("Parent_ID")
    @Expose
    private Integer parentID;

    public Integer getMenuID() {
        return menuID;
    }

    public void setMenuID(Integer menuID) {
        this.menuID = menuID;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getMenuLink() {
        return menuLink;
    }

    public void setMenuLink(String menuLink) {
        this.menuLink = menuLink;
    }

    public Object getMenuSort() {
        return menuSort;
    }

    public void setMenuSort(Object menuSort) {
        this.menuSort = menuSort;
    }

    public Integer getParentID() {
        return parentID;
    }

    public void setParentID(Integer parentID) {
        this.parentID = parentID;
    }
}
