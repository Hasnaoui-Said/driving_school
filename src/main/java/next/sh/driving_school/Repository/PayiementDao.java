package next.sh.driving_school.Repository;

import next.sh.driving_school.models.entity.Payiement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface PayiementDao extends JpaRepository<Payiement, Long> {
    Optional<Payiement> findByEleveUsername(String username);
}
