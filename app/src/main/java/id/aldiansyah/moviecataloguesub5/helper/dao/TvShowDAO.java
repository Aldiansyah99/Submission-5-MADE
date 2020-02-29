package id.aldiansyah.moviecataloguesub5.helper.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import id.aldiansyah.moviecataloguesub5.models.TvShowLocalData;

@Dao
public interface TvShowDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTvShow(TvShowLocalData tvShow);

    @Query("SELECT * FROM tvshowlocaldata")
    List<TvShowLocalData> showAllTvShow();

    @Delete
    void deleteTvShow(TvShowLocalData tvShow);

    @Query("SELECT * FROM tvshowlocaldata WHERE data_id LIKE :id")
    TvShowLocalData selectDetailTvShow(int id);

}
