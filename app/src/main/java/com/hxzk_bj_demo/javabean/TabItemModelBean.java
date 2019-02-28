package com.hxzk_bj_demo.javabean;

/**
 * 作者：created by ${zjt} on 2019/2/28
 * 描述:Fragmetn+Tablayout顶部导航栏tab实体
 */
public class TabItemModelBean {

    private String tabTitle;
    private String tabUrl;

    public TabItemModelBean(String tabTitle, String tabUrl){
        this.tabTitle = tabTitle;
        this.tabUrl = tabUrl;
    }

    public TabItemModelBean(String tabTitle) {
        this.tabTitle = tabTitle;
    }

    public String getTabTitle() {
        return tabTitle;
    }

    public void setTabTitle(String tabTitle) {
        this.tabTitle = tabTitle;
    }

    public String getTabUrl() {
        return tabUrl;
    }

    public void setTabUrl(String tabUrl) {
        this.tabUrl = tabUrl;
    }
}
