package uz.pdp.appwarehouse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.appwarehouse.entity.User;
import uz.pdp.appwarehouse.entity.Warehouse;
import uz.pdp.appwarehouse.payload.Result;
import uz.pdp.appwarehouse.payload.UserDTO;
import uz.pdp.appwarehouse.repository.UserRepository;
import uz.pdp.appwarehouse.repository.WarehouseRepository;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    WarehouseRepository warehouseRepository;

    public Result addUser(UserDTO userDTO){
        if (userRepository.existsByPhoneNumber(userDTO.getPhoneNumber()))
            return new Result("Bunday raqamli user mavjud", false);

        User user1 = new User();
        user1.setFirstName(userDTO.getFirstName());
        user1.setLastName(userDTO.getLastName());
        user1.setPhoneNumber(userDTO.getPhoneNumber());
        user1.setPassword(userDTO.getPassword());
        //UUID generated code below we can change it later
        user1.setCode(UUID.randomUUID().toString());

        List<Warehouse> allListWarehouses = warehouseRepository.findAllById(userDTO.getWarehouseIds());
        Set<Warehouse> warehousesSet = Set.copyOf(allListWarehouses);
        user1.setWarehouses(warehousesSet);
        userRepository.save(user1);

        return new Result("User successfully added", true);
    }

    public List<User> getUsers(){
        return userRepository.findAll();
    }

    public User getUser(Integer id){
        if (!userRepository.existsById(id))
            return new User();
        return userRepository.getById(id);
    }

    public Result editUser(Integer id, UserDTO userDTO){
        if (!userRepository.existsById(id))
            return new Result("No such User with this is", false);
        User editingUser = userRepository.getById(id);
        editingUser.setFirstName(userDTO.getFirstName());
        editingUser.setLastName(userDTO.getLastName());
        editingUser.setPhoneNumber(userDTO.getPhoneNumber());
        editingUser.setPassword(userDTO.getPassword());
        editingUser.setWarehouses(Set.copyOf(warehouseRepository.findAllById(userDTO.getWarehouseIds())));
        userRepository.save(editingUser);
        return new Result("Successfully edited", true);
    }

    public Result deleteUser(Integer id){
        if (!userRepository.existsById(id))
            return new Result("No such Supplier with this id", false);
        userRepository.deleteById(id);
        return new Result("Successfully deleted",  true);
    }
}
