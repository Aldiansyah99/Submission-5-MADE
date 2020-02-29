package id.aldiansyah.moviecataloguesub5.provider;

import android.database.Cursor;
import android.provider.BaseColumns;

public class DatabaseContract {
    public static final String AUTHORITY = "id.aldiansyah.moviecataloguesub5.provider";

    public DatabaseContract() {
    }

    public static final class MoviesColumns implements BaseColumns {
        public static final String TABLE_NAME = "moviesdb";
    }

    public static String getColumnString(Cursor cursor, String columnName) {
        return cursor.getString(cursor.getColumnIndex(columnName));
    }

    public static int getColumnInt(Cursor cursor, String columnName) {
        return cursor.getInt(cursor.getColumnIndex(columnName));
    }

    public static long getColumnLong(Cursor cursor, String columnName) {
        return cursor.getLong(cursor.getColumnIndex(columnName));
    }

    public static double getColumnDouble(Cursor cursor, String columnName) {
        return cursor.getDouble(cursor.getColumnIndex(columnName));
    }
}
