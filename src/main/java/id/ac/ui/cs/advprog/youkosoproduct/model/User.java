package id.ac.ui.cs.advprog.youkosoproduct.model;

import lombok.AllArgsConstructor;
import lombok.Generated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Generated
public class User {
    private String id;
    private String email;
    private String role;
    private Profile profile;
}
