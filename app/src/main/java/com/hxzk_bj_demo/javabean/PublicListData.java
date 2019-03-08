package com.hxzk_bj_demo.javabean;

import java.util.List;

/**
 * 作者：created by ${zjt} on 2019/3/8
 * 描述:公众号列表
 */
public class PublicListData {


        /**
         * curPage : 1
         * datas : []
         * offset : 0
         * over : true
         * pageCount : 0
         * size : 20
         * total : 0
         */

        private int curPage;
        private int offset;
        private boolean over;
        private int pageCount;
        private int size;
        private int total;
        private List<?> datas;

        public int getCurPage() {
            return curPage;
        }

        public void setCurPage(int curPage) {
            this.curPage = curPage;
        }

        public int getOffset() {
            return offset;
        }

        public void setOffset(int offset) {
            this.offset = offset;
        }

        public boolean isOver() {
            return over;
        }

        public void setOver(boolean over) {
            this.over = over;
        }

        public int getPageCount() {
            return pageCount;
        }

        public void setPageCount(int pageCount) {
            this.pageCount = pageCount;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<?> getDatas() {
            return datas;
        }

        public void setDatas(List<?> datas) {
            this.datas = datas;

    }
}
