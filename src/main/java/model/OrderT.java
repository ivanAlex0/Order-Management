package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The Order class that is identical with the Table in the DB
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderT {

    Integer id;
    Integer clientId;
    Integer productId;
    Integer quantity;
}
