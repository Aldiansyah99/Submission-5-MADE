package id.aldiansyah.moviecataloguesub5.provider;

import android.annotation.SuppressLint;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.Room;

import id.aldiansyah.moviecataloguesub5.helper.database.MoviesDbHelper;

import static id.aldiansyah.moviecataloguesub5.provider.DatabaseContract.AUTHORITY;
import static id.aldiansyah.moviecataloguesub5.provider.DatabaseContract.MoviesColumns.TABLE_NAME;

@SuppressLint("Registered")
public class MoviesFavoriteProvider extends ContentProvider {
    private static final int MOVIES = 1;
    private static final int MOVIES_ID = 2;
    private MoviesDbHelper db;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(AUTHORITY, TABLE_NAME, MOVIES);
        sUriMatcher.addURI(AUTHORITY, TABLE_NAME + "/#", MOVIES_ID);
    }

    @Override
    public boolean onCreate() {
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        Cursor cursor;
        switch (sUriMatcher.match(uri)) {
            case MOVIES:
                db = Room.databaseBuilder(getContext(), MoviesDbHelper.class, "moviesdb").build();
                cursor = db.moviesDAO().getMoviesFavorite();
                break;
            default:
                cursor = null;
                break;
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}
