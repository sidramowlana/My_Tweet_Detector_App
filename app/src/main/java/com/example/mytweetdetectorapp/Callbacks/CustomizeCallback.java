package com.example.mytweetdetectorapp.Callbacks;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomizeCallback<T> implements Callback<T> {

    private ResponseCallback responseCallBack;
    public CustomizeCallback(ResponseCallback responseCallBack) {
        this.responseCallBack = responseCallBack;
    }

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if (!response.isSuccessful() && response.errorBody() != null) {
            String error = null;
            error = response.errorBody().toString();
            responseCallBack.onError(error);
        } else if (response.body() != null) {
            responseCallBack.onSuccess(response);
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        String errorMessage = t.getMessage();
        responseCallBack.onError(errorMessage);
    }
}