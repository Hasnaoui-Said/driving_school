package next.sh.driving_school.rest.converter;

import next.sh.driving_school.models.entity.Eleve;
import next.sh.driving_school.rest.provided.vo.EleveVo;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class EleveConverter {
    public Eleve toBean(EleveVo vo) {
        Eleve bean = new Eleve();
        bean.setUsername(vo.getUsername());
        bean.setEmail(vo.getEmail());
        bean.setPassword(vo.getPassword());
        bean.setCin(vo.getCin());
        bean.setGender(vo.getGender());
        bean.setPhone(vo.getPhone());
        bean.setAccountState("PENDING");
        bean.setStatus(false);
        bean.setRole("USER");

        if (vo.getFirstName() != null)
            bean.setFirstName(vo.getFirstName());
        if (vo.getLastName() != null)
            bean.setLastName(vo.getLastName());
        if (vo.getVille() != null)
            bean.setVille(vo.getVille());
        if (vo.getAddress() != null)
            bean.setAddress(vo.getAddress());
        return bean;
    }

    public EleveVo toVo(Eleve bean) {
        EleveVo vo = new EleveVo();
        if (bean == null)
            return null;
        vo.setUuid(bean.getUuid().toString());
        vo.setUsername(bean.getUsername());
        vo.setEmail(bean.getEmail());
        vo.setPassword(bean.getPassword());
        vo.setFirstName(bean.getFirstName());
        vo.setLastName(bean.getLastName());
        vo.setCreateDate(bean.getCreateDate());
        vo.setStartDate(bean.getStartDate());
        vo.setEndDate(bean.getEndDate());
        vo.setUpdateDate(bean.getUpdateDate());
        vo.setUuid(bean.getUuid().toString());
        vo.setCin(bean.getCin());
        vo.setGender(bean.getGender());
        vo.setPhone(bean.getPhone());
        vo.setAccountState(bean.getAccountState());
        vo.setStatus(bean.isActive());
        vo.setRoles(bean.getRole());
        vo.setVille(bean.getVille());
        vo.setAddress(bean.getAddress());
        return vo;
    }
    public List<EleveVo> toVos(List<Eleve> beans) {
        List<EleveVo> vos = new ArrayList<>();
        beans.forEach(eleveVo -> vos.add(this.toVo(eleveVo)));
        return vos;
    }
}
