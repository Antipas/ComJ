package j.com.jetpack.data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jingbin on 2016/11/24.
 */

public class GankIoDayBean implements Serializable {


    private boolean error;

    private ResultsBean results;

    private List<String> category;

    public static class ResultsBean {
        /**
         * _id : 56cc6d23421aa95caa707a69
         * createdAt : 2015-08-06T07:15:52.65Z
         * desc : 类似Link Bubble的悬浮式操作设计
         * images : ["http://img.gank.io/2f0b6c5f-6de7-4ba3-94ad-98bf721ee447"]
         * source : web
         * publishedAt : 2015-08-07T03:57:48.45Z
         * type : Android
         * url : https://github.com/recruit-lifestyle/FloatingView
         * used : true
         * who : mthli
         */


        private List<GankIoDataBean> Android;


        private List<GankIoDataBean> iOS;


        private List<GankIoDataBean> front;


        private List<GankIoDataBean> app;


        private List<GankIoDataBean> restMovie;


        private List<GankIoDataBean> resource;


        private List<GankIoDataBean> recommend;


        private List<GankIoDataBean> welfare;


        public List<GankIoDataBean> getAndroid() {
            return Android;
        }

        public List<GankIoDataBean> getiOS() {
            return iOS;
        }

        public List<GankIoDataBean> getRestMovie() {
            return restMovie;
        }

        public List<GankIoDataBean> getResource() {
            return resource;
        }

        public List<GankIoDataBean> getRecommend() {
            return recommend;
        }

        public List<GankIoDataBean> getWelfare() {
            return welfare;
        }

        public List<GankIoDataBean> getFront() {
            return front;
        }

        public List<GankIoDataBean> getApp() {
            return app;
        }
    }

    public boolean isError() {
        return error;
    }

    public ResultsBean getResults() {
        return results;
    }

    public List<String> getCategory() {
        return category;
    }
}
