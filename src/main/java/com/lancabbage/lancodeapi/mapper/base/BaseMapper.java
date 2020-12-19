package com.lancabbage.lancodeapi.mapper.base;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * @ClassName: BaseMapper
 * @Description:TODO (父接口)
 * @author: lanyanhua
 * @date: 2020/2/23 6:49 下午
 * @Copyright:
 */
public interface BaseMapper<T> extends Mapper<T>, MySqlMapper<T> {

}
