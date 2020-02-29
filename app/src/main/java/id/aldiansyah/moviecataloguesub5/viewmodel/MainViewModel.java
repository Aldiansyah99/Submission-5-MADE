package id.aldiansyah.moviecataloguesub5.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import id.aldiansyah.moviecataloguesub5.models.ModelMovies;
import id.aldiansyah.moviecataloguesub5.models.ModelTvShow;

public class MainViewModel extends ViewModel {
    private MutableLiveData<ModelMovies> responseMovies;
    private MutableLiveData<ModelTvShow> responseTvShow;

    public void initializeMovies() {
        if (responseMovies != null) {
            return;
        }
        Movies movies = Movies.getInstance();
        responseMovies = movies.getMovies();
    }

    public LiveData<ModelMovies> getMoviesModel() {
        return responseMovies;
    }

    public void initializeTvShow() {
        if (responseTvShow != null) {
            return;
        }
        TvShow tvShow = TvShow.getInstance();
        responseTvShow = tvShow.getTvShow();
    }

    public LiveData<ModelTvShow> getTvShowModel() {
        return responseTvShow;
    }
}
