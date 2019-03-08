package com.hxzk_bj_demo.javabean;

import org.litepal.crud.DataSupport;

/**
 * 作者：created by ${zjt} on 2019/3/8
 * 描述:
 */
public class SearchTagBean extends DataSupport {
    String name ;
    String isSelect;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIsSelect() {
        return isSelect;
    }

    public void setIsSelect(String isSelect) {
        this.isSelect = isSelect;
    }
}
