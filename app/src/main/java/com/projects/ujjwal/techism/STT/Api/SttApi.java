package com.projects.ujjwal.techism.STT.Api;

import com.projects.ujjwal.techism.Helper.Urls;
import com.projects.ujjwal.techism.STT.Model.Data.SttResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Streaming;

/**
 * Created by ujjwal on 21/1/17.
 */
public interface SttApi {

	@GET(Urls.SUB_URL_STT)
	Call<SttResponse> getobject(@Query("user_input")String userInput);

}
