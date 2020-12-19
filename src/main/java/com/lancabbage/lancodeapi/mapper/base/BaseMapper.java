package com.lancabbage.lancodeapi.mapper.base;

import com.lancabbage.lancodeapi.bean.dto.ClassInfoDto;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.SelectProvider;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.provider.ExampleProvider;
import tk.mybatis.mapper.provider.SpecialProvider;

import java.util.Collection;
import java.util.List;

/**
 * @ClassName: BaseMapper
 * @Description:TODO (父接口)
 * @author: lanyanhua
 * @date: 2020/2/23 6:49 下午
 * @Copyright:
 */
public interface BaseMapper<T> extends Mapper<T>, MySqlMapper<T> {

}
