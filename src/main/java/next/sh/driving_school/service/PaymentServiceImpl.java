package next.sh.driving_school.service;

import next.sh.driving_school.Repository.PaymentDao;
import next.sh.driving_school.models.entity.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    PaymentDao paymentDao;
    @Override
    public Payment save(Payment bean) {
        bean.setUuid(UUID.randomUUID());
        bean.setPaymentDate(new Date());
        return paymentDao.save(bean);
    }

    @Override
    public Optional<Payment> findByEleveUsername(String username) {
        return paymentDao.findByEleveUsername(username);
    }

}
