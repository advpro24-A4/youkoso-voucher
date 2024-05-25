package id.ac.ui.cs.advprog.youkosoproduct.dto;

import id.ac.ui.cs.advprog.youkosoproduct.model.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthResponse {
    private String message;
    private User user;
}