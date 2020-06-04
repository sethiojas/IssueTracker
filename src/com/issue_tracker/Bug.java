package com.issue_tracker;

import java.io.Serializable;

public class Bug implements Serializable{
    private static final long serialVersionUID = 6529685098267757695L;
    final private String title;
    final private String description;
    final private int bugId;

    public Bug(String _title, String _desc){
        title = _title;
        description = _desc;
        // bugId = _id;
    }

    public String getTitle(){
        return title;
    }

    public String getDescription(){
        return description;
    }

    public int getId(){
        return bugId;
    }

    @Override
    public String toString(){
        return "(" + bugId + "," + title + ")";
    }

}