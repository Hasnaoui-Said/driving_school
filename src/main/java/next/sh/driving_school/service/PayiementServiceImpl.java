package next.sh.driving_school.service;

import next.sh.driving_school.Repository.PayiementDao;
import next.sh.driving_school.models.entity.Payiement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class PayiementServiceImpl implements PayiementService {
    @Autowired
    PayiementDao payiementDao;
    @Override
    public Payiement save(Payiement bean) {
        bean.setUuid(UUID.randomUUID());
        bean.setPayiementDate(new Date());
        return payiementDao.save(bean);
    }

    @Override
    public Optional<Payiement> findByEleveUsername(String username) {
        return payiementDao.findByEleveUsername(username);
    }

}
