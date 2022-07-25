package com.example.spy.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.spy.entity.Spy;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ${author}
 * @since 2022-07-24
 */
public interface SpyService extends IService<Spy> {
    List<Map<String,Object>> find(Spy spy);
    JSONObject find2(Spy spy);
    JSONObject find3(Spy spy);
}
