package com.wwg.markerMapper;


import tk.mybatis.mapper.common.ConditionMapper;
import tk.mybatis.mapper.common.MySqlMapper;
import tk.mybatis.mapper.common.RowBoundsMapper;

/**
 * Created by W2G on 2018/12/21.
 */
public interface CommonMapper<T> extends BaseMapper<T>,RowBoundsMapper<T>, ConditionMapper<T>, MySqlMapper<T> {

}
