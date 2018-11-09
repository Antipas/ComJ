package j.com.jetpack.data;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.Relation;
import j.com.componentserivce.Constants;
import j.com.componentserivce.base.BaseResult;

/**
 * Created by jingbin on 2016/11/24.
 */
public class GankIoDataBean extends BaseResult implements Serializable {

    private boolean error;
    /**
     * _id : 5832662b421aa929b0f34e99
     * createdAt : 2016-11-21T11:12:43.567Z
     * desc :  深入Android渲染机制
     * publishedAt : 2016-11-24T11:40:53.615Z
     * source : web
     * type : Android
     * url : http://blog.csdn.net/ccj659/article/details/53219288
     * used : true
     * who : Chauncey
     */

    private List<GankIoItemBean> results;



    public void setResults(List<GankIoItemBean> results) {
        this.results = results;
    }

    public boolean isError() {
        return error;
    }

    public List<GankIoItemBean> getResults() {
        return results;
    }

    @Override
    public String toString() {
        return "GankIoDataBean{" +
                "error=" + error +
                ", results=" + results +
                '}';
    }
}
