package next.sh.driving_school.service;

import next.sh.driving_school.Repository.EleveDao;
import next.sh.driving_school.Repository.UserDao;
import next.sh.driving_school.exception.BadRequestException;
import next.sh.driving_school.models.entity.Eleve;
import next.sh.driving_school.util.UtilString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EleveServiceIml implements EleveService{
    @Autowired
    EleveDao eleveDao;
    @Override
    public Eleve save(Eleve eleve) {
        if (eleveDao.existsByUsername(eleve.getUsername()))
            throw new BadRequestException("Username is already token!!");
        if (eleveDao.existsByEmail(eleve.getEmail()))
            throw new BadRequestException("Email is already token!!");
        eleve.setUuid(UUID.randomUUID());
        eleve.setCreateDate(new Date());
        eleve.setUpdateDate(new Date());
        eleve.setPassword(UtilString.passwordEncoder(eleve.getPassword()));
        return eleveDao.save(eleve);
    }

    @Override
    public List<Eleve> findAll() {
        return eleveDao.findAll();
    }

    @Override
    public Eleve findByUuid(UUID uuid1) {
        return eleveDao.findByUuid(uuid1).orElse(null);
    }

    @Override
    @Transactional
    public int deleteByUuid(UUID uuid1) {
        return eleveDao.deleteByUuid(uuid1);
    }

    @Override
    @Transactional
    public int deleteByEmail(String email) {
        return eleveDao.deleteByEmail(email);
    }

    @Override
    public Optional<Eleve> findByUsername(String username) {
        return eleveDao.findByUsernameOrEmail(username);
    }

    @Override
    public Eleve findByEmail(String email) {
        return eleveDao.findByEmail(email);
    }
}
