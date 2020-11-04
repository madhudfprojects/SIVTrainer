package com.det.skillinvillage;

public class Class_LearningOption {
    String Option_ID;
    String Group_Name;
    String Option_Name;
    String Option_Status;

    public Class_LearningOption(String option_ID, String group_Name, String option_Name, String option_Status) {
        Option_ID = option_ID;
        Group_Name = group_Name;
        Option_Name = option_Name;
        Option_Status = option_Status;
    }

    public Class_LearningOption() {

    }

    public String getOption_ID() {
        return Option_ID;
    }

    public void setOption_ID(String option_ID) {
        Option_ID = option_ID;
    }

    public String getGroup_Name() {
        return Group_Name;
    }

    public void setGroup_Name(String group_Name) {
        Group_Name = group_Name;
    }

    public String getOption_Name() {
        return Option_Name;
    }

    public void setOption_Name(String option_Name) {
        Option_Name = option_Name;
    }

    public String getOption_Status() {
        return Option_Status;
    }

    public void setOption_Status(String option_Status) {
        Option_Status = option_Status;
    }

    @Override
    public String toString() {
        return Option_Name;
    }
}
