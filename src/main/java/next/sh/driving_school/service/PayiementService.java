package next.sh.driving_school.service;

import next.sh.driving_school.models.entity.Payiement;

import java.util.Optional;
import java.util.UUID;

public interface PayiementService {
    Payiement save(Payiement toBean);

    Optional<Payiement> findByEleveUsername(String username);
}
