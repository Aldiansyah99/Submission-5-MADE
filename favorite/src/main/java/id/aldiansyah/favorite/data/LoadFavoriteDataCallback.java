package id.aldiansyah.favorite.data;

import android.database.Cursor;

public interface LoadFavoriteDataCallback {
    void postExecute(Cursor favorite);
}
