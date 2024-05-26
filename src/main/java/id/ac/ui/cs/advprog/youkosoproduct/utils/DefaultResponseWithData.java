package id.ac.ui.cs.advprog.youkosoproduct.utils;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DefaultResponseWithData<T> extends DefaultResponse{
    private T data;
}