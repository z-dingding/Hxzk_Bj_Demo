package com.hxzk_bj_demo.javabean;

import com.hxzk_bj_demo.network.BaseResponse;

import java.util.List;

/**
 * 作者：created by ${zjt} on 2019/3/6
 * 描述:首页文章列表
 */
public class HomeListBean  {


        /**
         * curPage : 2
         * datas : [{"apkLink":"","author":"susion随心","chapterId":427,"chapterName":"susion随心","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":7997,"link":"https://mp.weixin.qq.com/s/IdkTssxRyE3gxY_yc7r2ew","niceDate":"2019-02-27","origin":"","projectLink":"","publishTime":1551196800000,"superChapterId":408,"superChapterName":"公众号","tags":[{"name":"公众号","url":"/wxarticle/list/427/1"}],"title":"Fresco架构设计赏析","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","author":"玉刚说","chapterId":410,"chapterName":"玉刚说","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":7978,"link":"https://mp.weixin.qq.com/s/iZj-DpJ6u9vUrCotMQ0rmA","niceDate":"2019-02-26","origin":"","projectLink":"","publishTime":1551110400000,"superChapterId":408,"superChapterName":"公众号","tags":[{"name":"公众号","url":"/wxarticle/list/410/1"}],"title":"推荐一个实用漂亮的弹窗库","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","author":"程序亦非猿","chapterId":428,"chapterName":"程序亦非猿","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":7979,"link":"https://mp.weixin.qq.com/s/8Ox_zAbgBwb3J0lAdE0gIw","niceDate":"2019-02-26","origin":"","projectLink":"","publishTime":1551110400000,"superChapterId":408,"superChapterName":"公众号","tags":[{"name":"公众号","url":"/wxarticle/list/428/1"}],"title":"Handler都没搞懂，拿什么去跳槽啊？！","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","author":"鸿洋","chapterId":408,"chapterName":"鸿洋","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":7980,"link":"https://mp.weixin.qq.com/s/iacVz5JAhysnnbA_8f1xxQ","niceDate":"2019-02-26","origin":"","projectLink":"","publishTime":1551110400000,"superChapterId":408,"superChapterName":"公众号","tags":[{"name":"公众号","url":"/wxarticle/list/408/1"}],"title":"轻松搞懂自定义蒙层引导原理","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","author":"承香墨影","chapterId":411,"chapterName":"承香墨影","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":7983,"link":"https://mp.weixin.qq.com/s/qadX-WqLqt7Q0IvKY8V9Ow","niceDate":"2019-02-26","origin":"","projectLink":"","publishTime":1551110400000,"superChapterId":408,"superChapterName":"公众号","tags":[{"name":"公众号","url":"/wxarticle/list/411/1"}],"title":"图解：单链表反转的三种方式！","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","author":"鸿洋","chapterId":408,"chapterName":"鸿洋","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":7976,"link":"https://mp.weixin.qq.com/s/9VXDqV7DI1qvlCGl1yp0VQ","niceDate":"2019-02-25","origin":"","projectLink":"","publishTime":1551024000000,"superChapterId":408,"superChapterName":"公众号","tags":[{"name":"公众号","url":"/wxarticle/list/408/1"}],"title":"ViewPager 要被废弃？官方ViewPager2升级版来临","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","author":"郭霖","chapterId":409,"chapterName":"郭霖","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":7977,"link":"https://mp.weixin.qq.com/s/fYnNUwQr6h1sXDvhDSJFzw","niceDate":"2019-02-25","origin":"","projectLink":"","publishTime":1551024000000,"superChapterId":408,"superChapterName":"公众号","tags":[{"name":"公众号","url":"/wxarticle/list/409/1"}],"title":"一步步带你编译哔哩哔哩ijkPlayer","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","author":"鸿洋","chapterId":408,"chapterName":"鸿洋","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":7972,"link":"https://mp.weixin.qq.com/s/nn-nwXnRI9JYSmknH1pzYg","niceDate":"2019-02-22","origin":"","projectLink":"","publishTime":1550764800000,"superChapterId":408,"superChapterName":"公众号","tags":[{"name":"公众号","url":"/wxarticle/list/408/1"}],"title":"再&ldquo;丧心病狂&rdquo;的混淆也不怕","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","author":"code小生","chapterId":414,"chapterName":"code小生","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":7973,"link":"https://mp.weixin.qq.com/s/HosVZkDGStQ5JtvqmqHLYA","niceDate":"2019-02-22","origin":"","projectLink":"","publishTime":1550764800000,"superChapterId":408,"superChapterName":"公众号","tags":[{"name":"公众号","url":"/wxarticle/list/414/1"}],"title":"Android 框架思考--工具类设计（Glide、Picasso切换实现）","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","author":"郭霖","chapterId":409,"chapterName":"郭霖","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":7974,"link":"https://mp.weixin.qq.com/s/pttfFzkAO5MnwscXDX-dDw","niceDate":"2019-02-22","origin":"","projectLink":"","publishTime":1550764800000,"superChapterId":408,"superChapterName":"公众号","tags":[{"name":"公众号","url":"/wxarticle/list/409/1"}],"title":"Airbnb开源框架，真响应式架构&mdash;&mdash;MvRx","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","author":"susion随心","chapterId":427,"chapterName":"susion随心","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":7969,"link":"https://mp.weixin.qq.com/s/4rmSSUAUxL85Zp0McnHAcQ","niceDate":"2019-02-21","origin":"","projectLink":"","publishTime":1550678400000,"superChapterId":408,"superChapterName":"公众号","tags":[{"name":"公众号","url":"/wxarticle/list/427/1"}],"title":"Android触摸事件全过程分析:由产生到Activity.dispatchTouchEvent()","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","author":"程序亦非猿","chapterId":428,"chapterName":"程序亦非猿","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":7970,"link":"https://mp.weixin.qq.com/s/kv4WiLmI3-Y0puL2o_U1fw","niceDate":"2019-02-21","origin":"","projectLink":"","publishTime":1550678400000,"superChapterId":408,"superChapterName":"公众号","tags":[{"name":"公众号","url":"/wxarticle/list/428/1"}],"title":"在Java 中安全使用接口引用","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","author":"郭霖","chapterId":409,"chapterName":"郭霖","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":7971,"link":"https://mp.weixin.qq.com/s/7qQdPLLfhZA_i7HjucfgWA","niceDate":"2019-02-21","origin":"","projectLink":"","publishTime":1550678400000,"superChapterId":408,"superChapterName":"公众号","tags":[{"name":"公众号","url":"/wxarticle/list/409/1"}],"title":"一款对Toast，Snackbar，Dialog进行优化与兼容封装的开源库","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","author":"鸿洋","chapterId":408,"chapterName":"鸿洋","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":7958,"link":"https://mp.weixin.qq.com/s/1PoO7DXBm8kddaPAhpBkZQ","niceDate":"2019-02-20","origin":"","projectLink":"","publishTime":1550592000000,"superChapterId":408,"superChapterName":"公众号","tags":[{"name":"公众号","url":"/wxarticle/list/408/1"}],"title":"&ldquo;啥是佩奇？&rdquo; Android 开发者眼里的佩奇","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","author":"玉刚说","chapterId":410,"chapterName":"玉刚说","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":7960,"link":"https://mp.weixin.qq.com/s/DsUfXmkAq8qpehaa0QtTiQ","niceDate":"2019-02-20","origin":"","projectLink":"","publishTime":1550592000000,"superChapterId":408,"superChapterName":"公众号","tags":[{"name":"公众号","url":"/wxarticle/list/410/1"}],"title":"1 行代码，搞定按钮重复点击难题","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","author":"承香墨影","chapterId":411,"chapterName":"承香墨影","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":7975,"link":"https://mp.weixin.qq.com/s/4Tg_NsXS8Z4DQPBIxwJplg","niceDate":"2019-02-20","origin":"","projectLink":"","publishTime":1550592000000,"superChapterId":408,"superChapterName":"公众号","tags":[{"name":"公众号","url":"/wxarticle/list/411/1"}],"title":"图解：单链表删除，不遍历链表也能做（时间复杂度O(1)）","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","author":"何时夕","chapterId":40,"chapterName":"Context","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":7956,"link":"https://juejin.im/post/5c6a2e97f265da2dd37c0d82","niceDate":"2019-02-19","origin":"","projectLink":"","publishTime":1550590416000,"superChapterId":10,"superChapterName":"四大组件","tags":[],"title":"四大组件以及 Application和Context的全面理解","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","author":"静默加载","chapterId":433,"chapterName":"Window","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":7955,"link":"https://www.jianshu.com/p/95c2fadd9aa4","niceDate":"2019-02-19","origin":"","projectLink":"","publishTime":1550573183000,"superChapterId":153,"superChapterName":"framework","tags":[],"title":"Dialog、Toast的Window和ViewRootImpl","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","author":"shijiacheng","chapterId":402,"chapterName":"跨平台应用","collect":false,"courseId":13,"desc":"这是一款使用Flutter编写的，MaterialDesign风格的WanAndroid客户端应用；\r\n这是一个可以用来入门Flutter的项目，我用了两周的晚上时间完成了第一个版本的开发；\r\n项目完全开源并持续更新，欢迎Star&amp;Fork，有问题请提交Issues","envelopePic":"http://www.wanandroid.com/blogimgs/e48623e8-eee4-4516-b3dc-087d16c88b7f.png","fresh":false,"id":7954,"link":"http://www.wanandroid.com/blog/show/2503","niceDate":"2019-02-19","origin":"","projectLink":"https://github.com/shijiacheng/wanandroid_flutter","publishTime":1550572356000,"superChapterId":294,"superChapterName":"开源项目主Tab","tags":[{"name":"项目","url":"/project/list/1?cid=402"}],"title":"玩Android-Flutter客户端","type":0,"userId":-1,"visible":1,"zan":0},{"apkLink":"","author":"玉刚说","chapterId":410,"chapterName":"玉刚说","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":7959,"link":"https://mp.weixin.qq.com/s/9QKDr2JKc7vTL7Htes6SaA","niceDate":"2019-02-19","origin":"","projectLink":"","publishTime":1550505600000,"superChapterId":408,"superChapterName":"公众号","tags":[{"name":"公众号","url":"/wxarticle/list/410/1"}],"title":"&ldquo;有了这个开源库，我终于敢抬头看设计妹子了&rdquo;","type":0,"userId":-1,"visible":1,"zan":0}]
         * offset : 20
         * over : false
         * pageCount : 306
         * size : 20
         * total : 6113
         */

        private int curPage;
        private int offset;
        private boolean over;
        private int pageCount;
        private int size;
        private int total;
        private List<DatasBean> datas;

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

        public List<DatasBean> getDatas() {
            return datas;
        }

        public void setDatas(List<DatasBean> datas) {
            this.datas = datas;
        }

        public static class DatasBean {
            /**
             * apkLink :
             * author : susion随心
             * chapterId : 427
             * chapterName : susion随心
             * collect : false
             * courseId : 13
             * desc :
             * envelopePic :
             * fresh : false
             * id : 7997
             * link : https://mp.weixin.qq.com/s/IdkTssxRyE3gxY_yc7r2ew
             * niceDate : 2019-02-27
             * origin :
             * projectLink :
             * publishTime : 1551196800000
             * superChapterId : 408
             * superChapterName : 公众号
             * tags : [{"name":"公众号","url":"/wxarticle/list/427/1"}]
             * title : Fresco架构设计赏析
             * type : 0
             * userId : -1
             * visible : 1
             * zan : 0
             */

            private String apkLink;
            private String author;
            private int chapterId;
            private String chapterName;
            private boolean collect;
            private int courseId;
            private String desc;
            private String envelopePic;
            private boolean fresh;
            private int id;
            private String link;
            private String niceDate;
            private String origin;
            private String projectLink;
            private long publishTime;
            private int superChapterId;
            private String superChapterName;
            private String title;
            private int type;
            private int userId;
            private int visible;
            private int zan;
            private List<TagsBean> tags;

            public String getApkLink() {
                return apkLink;
            }

            public void setApkLink(String apkLink) {
                this.apkLink = apkLink;
            }

            public String getAuthor() {
                return author;
            }

            public void setAuthor(String author) {
                this.author = author;
            }

            public int getChapterId() {
                return chapterId;
            }

            public void setChapterId(int chapterId) {
                this.chapterId = chapterId;
            }

            public String getChapterName() {
                return chapterName;
            }

            public void setChapterName(String chapterName) {
                this.chapterName = chapterName;
            }

            public boolean isCollect() {
                return collect;
            }

            public void setCollect(boolean collect) {
                this.collect = collect;
            }

            public int getCourseId() {
                return courseId;
            }

            public void setCourseId(int courseId) {
                this.courseId = courseId;
            }

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }

            public String getEnvelopePic() {
                return envelopePic;
            }

            public void setEnvelopePic(String envelopePic) {
                this.envelopePic = envelopePic;
            }

            public boolean isFresh() {
                return fresh;
            }

            public void setFresh(boolean fresh) {
                this.fresh = fresh;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getLink() {
                return link;
            }

            public void setLink(String link) {
                this.link = link;
            }

            public String getNiceDate() {
                return niceDate;
            }

            public void setNiceDate(String niceDate) {
                this.niceDate = niceDate;
            }

            public String getOrigin() {
                return origin;
            }

            public void setOrigin(String origin) {
                this.origin = origin;
            }

            public String getProjectLink() {
                return projectLink;
            }

            public void setProjectLink(String projectLink) {
                this.projectLink = projectLink;
            }

            public long getPublishTime() {
                return publishTime;
            }

            public void setPublishTime(long publishTime) {
                this.publishTime = publishTime;
            }

            public int getSuperChapterId() {
                return superChapterId;
            }

            public void setSuperChapterId(int superChapterId) {
                this.superChapterId = superChapterId;
            }

            public String getSuperChapterName() {
                return superChapterName;
            }

            public void setSuperChapterName(String superChapterName) {
                this.superChapterName = superChapterName;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public int getUserId() {
                return userId;
            }

            public void setUserId(int userId) {
                this.userId = userId;
            }

            public int getVisible() {
                return visible;
            }

            public void setVisible(int visible) {
                this.visible = visible;
            }

            public int getZan() {
                return zan;
            }

            public void setZan(int zan) {
                this.zan = zan;
            }

            public List<TagsBean> getTags() {
                return tags;
            }

            public void setTags(List<TagsBean> tags) {
                this.tags = tags;
            }

            public static class TagsBean {
                /**
                 * name : 公众号
                 * url : /wxarticle/list/427/1
                 */

                private String name;
                private String url;

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }
            }
        }

}
