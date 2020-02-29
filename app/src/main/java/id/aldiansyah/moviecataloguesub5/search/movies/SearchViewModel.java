package id.aldiansyah.moviecataloguesub5.search.movies;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import id.aldiansyah.moviecataloguesub5.models.ModelMovies;
import id.aldiansyah.moviecataloguesub5.models.MoviesData;
import id.aldiansyah.moviecataloguesub5.rest.Client;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchViewModel extends ViewModel {

    private MutableLiveData<ArrayList<MoviesData>> listMovies = new MutableLiveData<>();

    public void getData(String type, String query) {
        if (type.equalsIgnoreCase("moviesdb")) {
            Call<ModelMovies> call = Client.getInstanceRetrofit().getSearchMovies(query);
            call.enqueue(new Callback<ModelMovies>() {
                @Override
                public void onResponse(Call<ModelMovies> call, Response<ModelMovies> response) {
                    if (response.isSuccessful()) {
                        ModelMovies responseData = response.body();
                        try {
                            listMovies.postValue(responseData.getResultMovies());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<ModelMovies> call, Throwable t) {
                    Log.e("failure", "failure");
                }
            });
        }

    }

    LiveData<ArrayList<MoviesData>> getMoviesData() {
        return listMovies;
    }
}
