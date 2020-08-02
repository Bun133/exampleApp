package com.ndsl.sddh.util;

public class Section {
    public String section_name;
    public Section(String section_name){
        this.section_name=section_name;
    }


    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Section) {
            Section s=(Section) obj;
            return s.section_name.equals(section_name);
        }else return false;
    }
}
