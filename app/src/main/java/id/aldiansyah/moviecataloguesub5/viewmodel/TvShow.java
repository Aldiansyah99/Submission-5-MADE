package id.aldiansyah.moviecataloguesub5.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import id.aldiansyah.moviecataloguesub5.models.ModelTvShow;
import id.aldiansyah.moviecataloguesub5.rest.ApiService;
import id.aldiansyah.moviecataloguesub5.rest.Client;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class TvShow {
    private static TvShow tvShowClass;
    private ApiService apiService;

    static TvShow getInstance() {
        if (tvShowClass == null) {
            tvShowClass = new TvShow();
        }
        return tvShowClass;
    }

    private TvShow() {
        apiService = Client.getInstanceRetrofit();
    }

    MutableLiveData<ModelTvShow> getTvShow() {
        final MutableLiveData<ModelTvShow> tvShowData = new MutableLiveData<>();
        apiService.getDataTvShow().enqueue(new Callback<ModelTvShow>() {
            @Override
            public void onResponse(Call<ModelTvShow> call, Response<ModelTvShow> response) {
                if ((response.body() != null ? response.body().getPage() : 0) > 0) {
                    tvShowData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<ModelTvShow> call, Throwable t) {
                Log.d("onFailure", t.getMessage());
            }
        });
        return tvShowData;
    }
}
