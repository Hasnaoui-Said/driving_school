package next.sh.driving_school.rest.converter;

import next.sh.driving_school.models.entity.Payment;
import next.sh.driving_school.rest.provided.vo.PaymentVo;
import org.springframework.stereotype.Component;

@Component
public class PaymentConverter {
    public Payment toBean(PaymentVo vo) {
        Payment bean = new Payment();
        bean.setTotalPay(vo.getTotalPay());
        return bean;
    }
    public PaymentVo toVo(Payment bean) {
        PaymentVo vo = new PaymentVo();
        if (bean == null)
            return null;
        vo.setTotalPay(bean.getTotalPay());
        vo.setUuid(bean.getUuid());
        vo.setPayiementDate(bean.getPaymentDate());
        return vo;
    }

}
