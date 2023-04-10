package next.sh.driving_school.Repository;

import next.sh.driving_school.models.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface PaymentDao extends JpaRepository<Payment, Long> {
    Optional<Payment> findByEleveUsername(String username);
}
