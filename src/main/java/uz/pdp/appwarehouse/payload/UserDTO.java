package uz.pdp.appwarehouse.payload;

import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class UserDTO {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String password;
    private Set<Integer> warehouseIds ;
}
