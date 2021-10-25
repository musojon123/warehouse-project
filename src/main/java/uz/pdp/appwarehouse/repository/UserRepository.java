package uz.pdp.appwarehouse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.pdp.appwarehouse.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("select (count(u) > 0) from users u where u.phoneNumber = ?1")
    boolean existsByPhoneNumber(String phoneNumber);
}
