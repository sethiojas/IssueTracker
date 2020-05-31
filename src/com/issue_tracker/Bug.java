package com.issue_tracker;

import java.io.Serializable;

public class Bug implements Serializable{
    private static final long serialVersionUID = 6529685098267757695L;
    final private String title;
    final private String description;
    private boolean isClosed = false;
    final private int bugId;

    Bug(String _title, String _desc, int _id){
        title = _title;
        description = _desc;
        bugId = _id;
    }

    String getTitle(){
        return title;
    }

    String getDescription(){
        return description;
    }

    int getId(){
        return bugId;
    }

    boolean isResolved(){
        return isClosed;
    }

    void resolved(){
        isClosed = true;
    }

    @Override
    public String toString(){
        return "(" + bugId + "," + title + ")";
    }

}