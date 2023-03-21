package next.sh.driving_school.rest.converter;

import next.sh.driving_school.models.entity.Payiement;
import next.sh.driving_school.rest.provided.vo.PayiementVo;
import org.springframework.stereotype.Component;

@Component
public class PayiementConverter {
    public Payiement toBean(PayiementVo vo) {
        Payiement bean = new Payiement();
        bean.setTotalPay(vo.getTotalPay());
        return bean;
    }
    public PayiementVo toVo(Payiement bean) {
        PayiementVo vo = new PayiementVo();
        vo.setTotalPay(bean.getTotalPay());
        vo.setUuid(bean.getUuid());
        vo.setPayiementDate(bean.getPayiementDate());
        return vo;
    }

}
