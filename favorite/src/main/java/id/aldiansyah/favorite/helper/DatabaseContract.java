package id.aldiansyah.favorite.helper;

import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContract {
    private static final String AUTHORITY = "id.aldiansyah.moviecataloguesub5.provider";
    private static final String SCHEME = "content";

    public static final class MoviesColumns implements BaseColumns {
        private static final String TABLE_NAME = "moviesdb";

        public static final Uri CONTENT_URI = new Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build();
    }
}
