package id.ac.ui.cs.advprog.youkosoproduct.utils;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DefaultResponse {
    private int statusCode;
    private String message;
    private boolean success;
}