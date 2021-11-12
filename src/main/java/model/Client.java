package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The Client class that is identical with the Table in the DB
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Client {

    private Integer id;
    private String name;
    private String email;
}