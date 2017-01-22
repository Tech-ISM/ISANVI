package com.projects.ujjwal.techism.STT.Model;

import android.util.Log;

import com.projects.ujjwal.techism.Helper.Urls;

import com.projects.ujjwal.techism.STT.Api.SttApi;
import com.projects.ujjwal.techism.STT.Model.Data.SttResponse;
import com.projects.ujjwal.techism.STT.SttCallback;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ujjwal on 21/1/17.
 */
public class RetrofitSttHelper implements SttHelper {

	SttApi sttApi;
	public RetrofitSttHelper() {

		HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
		interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
		OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

		Retrofit retrofit= new Retrofit.Builder().baseUrl(Urls.BASE_URL).client(client)
								   .addConverterFactory(GsonConverterFactory.create()).build();
		sttApi= retrofit.create(SttApi.class);

	}

	@Override
	public void getUserInput(String object, final SttCallback sttCallback) {
		Call<SttResponse> call=sttApi.getobject(object);
		call.enqueue(new Callback<SttResponse>() {
			@Override
			public void onResponse(Call<SttResponse> call, Response<SttResponse> response) {
				Log.i("RetrofitHomeHelper","Got Response "+response.body().getMessage().toString());
				sttCallback.onSuccess(response.body());

			}

			@Override
			public void onFailure(Call<SttResponse> call, Throwable t) {
				t.printStackTrace();
				sttCallback.onFailure(t.getMessage());
}
		});
	}
}
