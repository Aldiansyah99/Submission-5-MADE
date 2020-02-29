package id.aldiansyah.favorite.helper;

import android.database.Cursor;

import java.util.ArrayList;

import id.aldiansyah.favorite.data.MoviesData;

public class MappingHelper {
    public static ArrayList<MoviesData> mapCursorToArrayListMovies(Cursor cursor) {
        ArrayList<MoviesData> moviesList = new ArrayList<>();

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            String title = cursor.getString(cursor.getColumnIndexOrThrow("title"));
            double voteAverage = cursor.getDouble(cursor.getColumnIndexOrThrow("vote_average"));
            String posterPath = cursor.getString(cursor.getColumnIndexOrThrow("poster_path"));
            moviesList.add(new MoviesData(id, title, voteAverage, posterPath));
        }
        return moviesList;
    }
}
