package com.haohe.zskportal.sys.vo;

import com.haohe.zskportal.sys.model.User;
import lombok.Data;

/**
 * @author 微笑の掩饰
 * @date 2019/6/26 16:30
 * @description
 */
@Data
public class UserVO extends User {

    /**
     * 在线识别用户信息唯一编码（加密）
     */
    private String activeId;

    /**
     * 部门名称
     */
    private String departmentName;

    /**
     * 大区名称
     */
    private String largeAreaName;

    /**
     * 医院名称
     */
    private String hospitalName;

    /**
     * 权限名称
     */
    private String roleName;

    public UserVO(){

    }

    public UserVO(User user){
        super();
        super.setId(user.getId());
        super.setUsername(user.getUsername());
        super.setPassword(user.getPassword());
        super.setEmail(user.getEmail());
        super.setHospitalId(user.getHospitalId());
        super.setIsEnabled(user.getIsEnabled());
        super.setIsExpired(user.getIsExpired());
        super.setIsLocked(user.getIsLocked());
        super.setIsSys(user.getIsSys());
        super.setMechanismId(user.getMechanismId());
        super.setName(user.getName());
        super.setSubzoneId(user.getSubzoneId());
        super.setSwPassword(user.getSwPassword());
        super.setWcName(user.getWcName());
    }

    public Integer getAuthCacheKey() {
        return super.getId();
    }

}
