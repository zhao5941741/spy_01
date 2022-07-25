package com.example.spy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.spy.entity.Spy;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since 2022-07-24
 */
public interface SpyMapper extends BaseMapper<Spy> {
    List<Map<String, Object>> find(Spy spy);
    List<Map<String, Object>> find2(Spy spy);
    List<Map<String, Object>> find3(Spy spy);


}
