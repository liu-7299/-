package com.fh.admin.param;

import com.fh.admin.commons.Page;

import java.io.Serializable;

public class BrandParam extends Page implements Serializable {

    private Integer sortp;

    public Integer getSortp() {
        return sortp;
    }

    public void setSortp(Integer sortp) {
        this.sortp = sortp;
    }
}
