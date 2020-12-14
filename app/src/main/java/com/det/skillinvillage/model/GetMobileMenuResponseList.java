package com.det.skillinvillage.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetMobileMenuResponseList {

    @SerializedName("Menu_ID")
    @Expose
    private Integer menuID;
    @SerializedName("Menu_Name")
    @Expose
    private String menuName;
    @SerializedName("Menu_Sort")
    @Expose
    private String menuSort;
    @SerializedName("SubMenu")
    @Expose
    private List<GetMobileSubMenuResponseList> subMenu = null;

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

    public String getMenuSort() {
        return menuSort;
    }

    public void setMenuSort(String menuSort) {
        this.menuSort = menuSort;
    }

    public List<GetMobileSubMenuResponseList> getSubMenu() {
        return subMenu;
    }

    public void setSubMenu(List<GetMobileSubMenuResponseList> subMenu) {
        this.subMenu = subMenu;
    }

}
