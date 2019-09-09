package com.haohe.zskportal.sys.vo;

import com.haohe.zskportal.sys.model.Permission;
import lombok.Data;

import java.util.List;

/**
 * @author 微笑の掩饰
 * @date 2019/6/27 14:15
 * @description
 */
@Data
public class PermissionVO extends Permission {

    private String creatorName;

    private List<Integer> menus;

}
