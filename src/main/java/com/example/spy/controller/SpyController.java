package com.example.spy.controller;


import com.alibaba.fastjson.JSONObject;
import com.example.spy.entity.Spy;
import com.example.spy.service.SpyService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ${author}
 * @since 2022-07-24
 */
@RestController
@RequestMapping("/spy")
public class SpyController {
    @Resource
    SpyService spyService;

    @RequestMapping("/find")
    public List<Map<String,Object>> find(@RequestBody Spy spy) {
        return spyService.find(spy);
    }

    @RequestMapping("/find2")
    public JSONObject find2(@RequestBody Spy spy) {
        return spyService.find2(spy);
    }
    @RequestMapping("/find3")
    public JSONObject find3(@RequestBody Spy spy) {
        return spyService.find3(spy);
    }
}

