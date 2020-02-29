package id.aldiansyah.moviecataloguesub5.search.tvShow;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import id.aldiansyah.moviecataloguesub5.models.ModelTvShow;
import id.aldiansyah.moviecataloguesub5.models.TvShowData;
import id.aldiansyah.moviecataloguesub5.rest.Client;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchTvShowViewModel extends ViewModel {

    private MutableLiveData<ArrayList<TvShowData>> listTvShow = new MutableLiveData<>();

    public void getData(String type, String query) {
        if (type.equalsIgnoreCase("tvshowdb")) {
            Call<ModelTvShow> call = Client.getInstanceRetrofit().getSearchTvShow(query);
            call.enqueue(new Callback<ModelTvShow>() {
                @Override
                public void onResponse(Call<ModelTvShow> call, Response<ModelTvShow> response) {
                    if (response.isSuccessful()) {
                        ModelTvShow responseData = response.body();
                        try {
                            listTvShow.postValue(responseData.getResultTvShow());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<ModelTvShow> call, Throwable t) {
                    Log.e("failure", "failure");
                }
            });
        }
    }

    LiveData<ArrayList<TvShowData>> getTvShowData() {
        return listTvShow;
    }
}
