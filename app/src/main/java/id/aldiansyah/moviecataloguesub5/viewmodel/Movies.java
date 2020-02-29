package id.aldiansyah.moviecataloguesub5.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import id.aldiansyah.moviecataloguesub5.models.ModelMovies;
import id.aldiansyah.moviecataloguesub5.rest.ApiService;
import id.aldiansyah.moviecataloguesub5.rest.Client;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class Movies {
    private static Movies moviesClass;
    private ApiService apiService;

    static Movies getInstance() {
        if (moviesClass == null) {
            moviesClass = new Movies();
        }
        return moviesClass;
    }

    private Movies() {
        apiService = Client.getInstanceRetrofit();
    }

    MutableLiveData<ModelMovies> getMovies() {
        final MutableLiveData<ModelMovies> moviesData = new MutableLiveData<>();
        apiService.getDataMovie().enqueue(new Callback<ModelMovies>() {
            @Override
            public void onResponse(Call<ModelMovies> call, Response<ModelMovies> response) {
                if ((response.body() != null ? response.body().getPage() : 0) > 0) {
                    moviesData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<ModelMovies> call, Throwable t) {
                Log.d("onFailure", t.getMessage());
            }
        });
        return moviesData;
    }
}
