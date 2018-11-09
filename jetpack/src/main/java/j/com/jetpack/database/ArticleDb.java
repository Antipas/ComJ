package j.com.jetpack.database;



import androidx.room.Database;
import androidx.room.RoomDatabase;
import j.com.jetpack.data.GankIoDataBean;
import j.com.jetpack.data.GankIoItemBean;

@Database(entities = {GankIoItemBean.class}, version = 1, exportSchema = false)
public abstract class ArticleDb extends RoomDatabase {

  public abstract ArticleDao articleDao();
}
