package id.ac.ui.cs.advprog.youkosoproduct.utils;

import lombok.Getter;
import lombok.Setter;
import lombok.Generated;

@Getter
@Setter
@Generated
public class DefaultResponseWithData<T> extends DefaultResponse{
    private T data;
}