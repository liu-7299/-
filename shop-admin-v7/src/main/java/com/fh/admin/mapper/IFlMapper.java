package com.fh.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fh.admin.po.Fl;

import java.util.List;

public interface IFlMapper extends BaseMapper<Fl> {

    void addFl(Fl reg);

    void updateFl(Fl reg);

    Long deleteFl(String[] ids);

    void deleteFlById(Integer id);
}
