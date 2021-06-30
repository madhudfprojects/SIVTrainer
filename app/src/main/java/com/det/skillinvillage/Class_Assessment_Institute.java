package com.det.skillinvillage;

import android.support.annotation.NonNull;

public class Class_Assessment_Institute {


    String institute_id;
    String institute_assessment_name;

    public Class_Assessment_Institute(){}
    public Class_Assessment_Institute(String institute_id, String institute_assessment_name) {
        this.institute_id = institute_id;
        this.institute_assessment_name = institute_assessment_name;
    }

    public String getInstitute_id() {
        return institute_id;
    }

    public void setInstitute_id(String institute_id) {
        this.institute_id = institute_id;
    }

    public String getinstitute_assessment_name() {
        return institute_assessment_name;
    }

    public void setinstitute_assessment_name(String institute_assessment_name) {
        this.institute_assessment_name = institute_assessment_name;
    }

    @NonNull
    @Override
    public String toString() {
        return institute_assessment_name;
    }
}
