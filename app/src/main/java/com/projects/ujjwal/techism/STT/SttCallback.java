package com.projects.ujjwal.techism.STT;

import com.projects.ujjwal.techism.STT.Model.Data.SttResponse;

/**
 * Created by ujjwal on 21/1/17.
 */
public interface SttCallback {
void onSuccess(SttResponse sttResponse);
void onFailure(String message);
}
