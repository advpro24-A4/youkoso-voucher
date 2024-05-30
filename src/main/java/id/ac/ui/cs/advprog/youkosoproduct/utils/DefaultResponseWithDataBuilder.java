package id.ac.ui.cs.advprog.youkosoproduct.utils;

import lombok.Generated;

@Generated
public class DefaultResponseWithDataBuilder<T> {
    private int statusCode;
    private String message;
    private boolean success;
    private T data;

    public DefaultResponseWithDataBuilder<T> statusCode(int statusCode) {
        this.statusCode = statusCode;
        return this;
    }

    public DefaultResponseWithDataBuilder<T> message(String message) {
        this.message = message;
        return this;
    }

    public DefaultResponseWithDataBuilder<T> success(boolean success) {
        this.success = success;
        return this;
    }

    public DefaultResponseWithDataBuilder<T> data(T data) {
        this.data = data;
        return this;
    }

    public DefaultResponseWithData<T> build() {
        DefaultResponseWithData<T> response = new DefaultResponseWithData<>();
        response.setStatusCode(this.statusCode);
        response.setMessage(this.message);
        response.setSuccess(this.success);
        response.setData(this.data);

        return response;
    }
}