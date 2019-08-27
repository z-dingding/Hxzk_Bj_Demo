package com.hxzk_bj_demo.javabean;

import org.litepal.crud.DataSupport;

/**
 * Created by ${赵江涛} on 2018-1-30.
 */

public class NoteBookBean  extends DataSupport {
    /**
     *  Litepal中有一个默认字段id,作为主键，如果我们的实体类定义了id变量则默认id的值会赋值给此id
     *  注意：如果上一条数据被删除，并不会影响此id的值，也就是-1
     */
    int id;
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
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
