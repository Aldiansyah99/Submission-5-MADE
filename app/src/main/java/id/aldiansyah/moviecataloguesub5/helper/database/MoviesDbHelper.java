package id.aldiansyah.moviecataloguesub5.helper.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import id.aldiansyah.moviecataloguesub5.helper.dao.MoviesDAO;
import id.aldiansyah.moviecataloguesub5.models.MoviesLocalData;

@Database(entities = {MoviesLocalData.class}, version = 1, exportSchema = false)
public abstract class MoviesDbHelper extends RoomDatabase {

    public abstract MoviesDAO moviesDAO();
}
