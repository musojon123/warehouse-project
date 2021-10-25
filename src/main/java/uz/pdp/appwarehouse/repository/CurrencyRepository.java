package uz.pdp.appwarehouse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.pdp.appwarehouse.entity.Currency;

public interface CurrencyRepository extends JpaRepository<Currency, Integer> {
    @Query("select (count(c) > 0) from Currency c where c.name = ?1")
    boolean existsByName(String name);
}
