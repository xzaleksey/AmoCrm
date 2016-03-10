package com.valyakinaleksey.amocrm.util;

import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.ResponseBody;
import com.valyakinaleksey.amocrm.domain.ServiceGenerator;
import com.valyakinaleksey.amocrm.models.api.APIError;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import retrofit.Converter;
import retrofit.Response;

public class ErrorUtils {

    public static final Type ERROR_TYPE = new TypeToken<com.valyakinaleksey.amocrm.models.api.Response<APIError>>() {
    }.getType();

    public static APIError parseError(Response<?> response) {
        Converter<ResponseBody, com.valyakinaleksey.amocrm.models.api.Response<APIError>> converter =
                ServiceGenerator.retrofit()
                        .responseConverter(ERROR_TYPE, new Annotation[0]);

        APIError error;

        try {
            com.valyakinaleksey.amocrm.models.api.Response<APIError> apiErrorResponse = converter.convert(response.errorBody());
            error = apiErrorResponse.response;
        } catch (IOException e) {
            Logger.d(e.toString());
            return new APIError();
        }

        return error;
    }
}
