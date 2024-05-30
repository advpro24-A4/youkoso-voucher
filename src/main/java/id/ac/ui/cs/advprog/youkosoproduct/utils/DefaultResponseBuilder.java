package id.ac.ui.cs.advprog.youkosoproduct.utils;

import lombok.Generated;

@Generated
public class DefaultResponseBuilder {
    private int statusCode;
    private String message;
    private boolean success;

    public DefaultResponseBuilder statusCode(int statusCode) {
        this.statusCode = statusCode;
        return this;
    }

    public DefaultResponseBuilder message(String message) {
        this.message = message;
        return this;
    }

    public DefaultResponseBuilder success(boolean success) {
        this.success = success;
        return this;
    }

    public DefaultResponse build() {
        DefaultResponse response = new DefaultResponse();
        response.setStatusCode(this.statusCode);
        response.setMessage(this.message);
        response.setSuccess(this.success);

        return response;
    }
}