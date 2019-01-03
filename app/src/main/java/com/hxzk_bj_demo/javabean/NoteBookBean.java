package com.hxzk_bj_demo.javabean;

import org.litepal.crud.DataSupport;

/**
 * Created by ${赵江涛} on 2018-1-30.
 * 作用:
 */

public class NoteBookBean  extends DataSupport {
    String content;
    String  date;

    public NoteBookBean(String content, String date) {
        this.content = content;
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
