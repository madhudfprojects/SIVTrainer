package com.det.skillinvillage;

public class Class_SubMenu {
        private  String Menu_ID;
        private  String Menu_Name;
        private  String Menu_Link;
        private  String Parent_ID;
//        private  String Menu_Sort;


        public Class_SubMenu(){}
        public Class_SubMenu(String menu_ID, String menu_Name, String menu_link, String parent_ID) {
            Menu_ID = menu_ID;
            Menu_Name = menu_Name;
            Menu_Link = menu_link;
            Parent_ID = parent_ID;
        }

//    public String getMenu_Sort() {
//        return Menu_Sort;
//    }
//
//    public void setMenu_Sort(String menu_Sort) {
//        Menu_Sort = menu_Sort;
//    }

    public String getMenu_ID() {
            return Menu_ID;
        }

        public void setMenu_ID(String menu_ID) {
            Menu_ID = menu_ID;
        }

        public String getMenu_Name() {
            return Menu_Name;
        }

        public void setMenu_Name(String menu_Name) {
            Menu_Name = menu_Name;
        }

        public String getMenu_Link() {
            return Menu_Link;
        }

        public void setMenu_Link(String menulink) {
            Menu_Link = menulink;
        }

        public String getParent_ID() {
            return Parent_ID;
        }

        public void setParent_ID(String parent_ID) {
            Parent_ID = parent_ID;
        }
    }

