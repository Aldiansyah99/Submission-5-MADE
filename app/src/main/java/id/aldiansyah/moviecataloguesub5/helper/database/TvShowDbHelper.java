package id.aldiansyah.moviecataloguesub5.helper.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import id.aldiansyah.moviecataloguesub5.helper.dao.TvShowDAO;
import id.aldiansyah.moviecataloguesub5.models.TvShowLocalData;

@Database(entities = {TvShowLocalData.class}, version = 1, exportSchema = false)
public abstract class TvShowDbHelper extends RoomDatabase {

    public abstract TvShowDAO tvShowDAO();

}
