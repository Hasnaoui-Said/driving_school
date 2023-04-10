package next.sh.driving_school.service;

import next.sh.driving_school.models.entity.Payment;

import java.util.Optional;

public interface PaymentService {
    Payment save(Payment toBean);

    Optional<Payment> findByEleveUsername(String username);
}
