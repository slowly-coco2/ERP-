package com.bdqn.sys.vo;

import com.bdqn.sys.entity.Permission;
import com.bdqn.sys.entity.Role;

public class RoleVo extends Role {
    private Integer page;
    private Integer limit;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }
}
