package uz.pdp.appwarehouse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.pdp.appwarehouse.entity.Measurement;

@Repository
public interface MeasurementRepository extends JpaRepository<Measurement, Integer> {

    @Query("select (count(m) > 0) from Measurement m where m.name = ?1")
    boolean existsByName(String name);

}
