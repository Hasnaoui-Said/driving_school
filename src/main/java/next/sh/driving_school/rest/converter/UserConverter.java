package next.sh.driving_school.rest.converter;

import next.sh.driving_school.models.entity.User;
import next.sh.driving_school.rest.provided.vo.UserVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserConverter {
    public User toBean(UserVo vo) {
        User bean = new User();
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

    public UserVo toVo(User bean) {
        UserVo vo = new UserVo();
        if (bean == null)
            return null;
        vo.setUuid(bean.getUuid().toString());
        vo.setUsername(bean.getUsername());
        vo.setEmail(bean.getEmail());
        vo.setPassword(bean.getPassword());
        vo.setFirstName(bean.getFirstName());
        vo.setLastName(bean.getLastName());
        vo.setCreateDate(bean.getCreateDate());
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

    public List<UserVo> toVos(List<User> beans) {
//        List<UserVo> vos = new ArrayList<>();
//        beans.forEach(b -> vos.add(this.toVo(b)));
        return beans
                .stream()
                .map(this::toVo)
                .collect(Collectors.toList());
    }

    public Page<UserVo> toVos(Page<User> beans) {
        List<UserVo> userVos = toVos(beans.getContent());

        return new PageImpl<>(userVos, beans.getPageable(), beans.getTotalElements());
    }
}
