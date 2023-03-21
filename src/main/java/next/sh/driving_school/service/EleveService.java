package next.sh.driving_school.service;

import next.sh.driving_school.models.entity.Eleve;
import next.sh.driving_school.models.entity.User;
import next.sh.driving_school.rest.provided.vo.EleveVo;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EleveService {
    Eleve save(Eleve toBean);

    List<Eleve> findAll();

    Eleve findByUuid(UUID uuid1);

    int deleteByUuid(UUID uuid1);

    int deleteByEmail(String email);

    Optional<Eleve> findByUsername(String username);

    Eleve findByEmail(String email);
}
