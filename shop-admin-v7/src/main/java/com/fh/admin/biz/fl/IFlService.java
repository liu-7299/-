package com.fh.admin.biz.fl;

import com.fh.admin.po.Fl;

import java.util.List;

public interface IFlService {
    List<Fl> queruFl();

    Integer addFl(Fl fl);

    void updateFl(Fl fl);

    void deleteFlAll(String[] str);

    List<Fl> queryFlsById(Integer id);
}
