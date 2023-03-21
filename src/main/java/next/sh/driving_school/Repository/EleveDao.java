package next.sh.driving_school.Repository;

import next.sh.driving_school.models.entity.Eleve;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface EleveDao extends JpaRepository<Eleve, Long> {
    @Query("SELECT u FROM Eleve u WHERE u.email=:q or u.username=:q")
    Optional<Eleve> findByUsernameOrEmail(@Param("q") String q);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    Optional<Eleve> findByUuid(UUID uuid);

    Eleve findByEmail(String email);

    int deleteByUuid(UUID uuid1);

    int deleteByEmail(String email);
}
