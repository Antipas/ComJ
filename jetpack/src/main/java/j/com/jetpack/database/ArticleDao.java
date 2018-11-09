package j.com.jetpack.database;



import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import io.reactivex.Flowable;
import j.com.componentserivce.Constants;
import j.com.jetpack.data.GankIoDataBean;
import j.com.jetpack.data.GankIoItemBean;

@Dao
public interface ArticleDao {
  @Query("SELECT * FROM " + Constants.ARTICLE_TABLE_NAME)
  Flowable<List<GankIoItemBean>> getAllArticles();

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  void insert(GankIoItemBean resultBean);

  @Query("DELETE FROM " + Constants.ARTICLE_TABLE_NAME)
  void deleteAll();
}
