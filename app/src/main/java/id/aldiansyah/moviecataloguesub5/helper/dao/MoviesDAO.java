package id.aldiansyah.moviecataloguesub5.helper.dao;

import android.database.Cursor;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import id.aldiansyah.moviecataloguesub5.models.MoviesLocalData;

@Dao
public interface MoviesDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMovies(MoviesLocalData movies);

    @Query("SELECT * FROM movieslocaldata")
    List<MoviesLocalData> showAllMovies();

    @Delete
    void deleteMovies(MoviesLocalData movies);

    @Query("SELECT * FROM movieslocaldata WHERE id LIKE :id")
    MoviesLocalData selectDetailMovies(int id);

    @Query("SELECT favorite FROM movieslocaldata")
    List<String> getMovieList();

    @Query("SELECT * FROM movieslocaldata")
    Cursor getMoviesFavorite();

}
