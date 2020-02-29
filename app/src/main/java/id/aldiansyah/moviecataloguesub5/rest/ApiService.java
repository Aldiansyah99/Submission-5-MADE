package id.aldiansyah.moviecataloguesub5.rest;

import id.aldiansyah.moviecataloguesub5.BuildConfig;
import id.aldiansyah.moviecataloguesub5.models.ModelMovies;
import id.aldiansyah.moviecataloguesub5.models.ModelTvShow;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("discover/movie?api_key=" + BuildConfig.API_KEY + "&language=en-US")
    Call<ModelMovies> getDataMovie();

    @GET("discover/tv?api_key=" + BuildConfig.API_KEY + "&language=en-US")
    Call<ModelTvShow> getDataTvShow();

    @GET("search/movie?api_key=" + BuildConfig.API_KEY + "&language=en-US")
    Call<ModelMovies> getSearchMovies(@Query("query") String query);

    @GET("search/tv?api_key=" + BuildConfig.API_KEY + "&language=en-US")
    Call<ModelTvShow> getSearchTvShow(@Query("query") String query);

    @GET("discover/movie?api_key=" + BuildConfig.API_KEY)
    Call<ModelMovies> getMovieRelease(@Query("primary_release_date.gte") String DateGTENow,
                                      @Query("primary_release_date.lte") String DateLTENow);

}
