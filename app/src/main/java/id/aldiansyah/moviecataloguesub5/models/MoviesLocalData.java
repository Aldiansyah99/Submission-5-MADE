package id.aldiansyah.moviecataloguesub5.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class MoviesLocalData implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    int id;
    @ColumnInfo(name = "data_id")
    public int dataId;
    @ColumnInfo(name = "title")
    String title;
    @ColumnInfo(name = "backdrop_path")
    String backdrop;
    @ColumnInfo(name = "poster_path")
    String posterPath;
    @ColumnInfo(name = "vote_average")
    Double voteAverage;
    @ColumnInfo(name = "popularity")
    String popularity;
    @ColumnInfo(name = "overview")
    String overview;
    @ColumnInfo(name = "release_date")
    String releaseDate;
    @ColumnInfo(name = "favorite")
    String favorite;

    public MoviesLocalData(Parcel in) {
        id = in.readInt();
        dataId = in.readInt();
        title = in.readString();
        backdrop = in.readString();
        posterPath = in.readString();
        if (in.readByte() == 0) {
            voteAverage = null;
        } else {
            voteAverage = in.readDouble();
        }
        popularity = in.readString();
        overview = in.readString();
        releaseDate = in.readString();
        favorite = in.readString();
    }

    public MoviesLocalData() {

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(dataId);
        dest.writeString(title);
        dest.writeString(backdrop);
        dest.writeString(posterPath);
        if (voteAverage == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(voteAverage);
        }
        dest.writeString(popularity);
        dest.writeString(overview);
        dest.writeString(releaseDate);
        dest.writeString(favorite);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MoviesLocalData> CREATOR = new Creator<MoviesLocalData>() {
        @Override
        public MoviesLocalData createFromParcel(Parcel in) {
            return new MoviesLocalData(in);
        }

        @Override
        public MoviesLocalData[] newArray(int size) {
            return new MoviesLocalData[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDataId() {
        return dataId;
    }

    public void setDataId(int dataId) {
        this.dataId = dataId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBackdrop() {
        return backdrop;
    }

    public void setBackdrop(String backdrop) {
        this.backdrop = backdrop;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(Double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getFavorite() {
        return favorite;
    }

    public void setFavorite(String favorite) {
        this.favorite = favorite;
    }
}
