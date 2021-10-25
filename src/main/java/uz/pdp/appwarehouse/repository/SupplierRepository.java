package uz.pdp.appwarehouse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.pdp.appwarehouse.entity.Supplier;

public interface SupplierRepository extends JpaRepository<Supplier, Integer> {
    @Query("select (count(s) > 0) from Supplier s where s.phoneNumber = ?1")
    boolean existsByPhoneNumber(String phoneNumber);
}
