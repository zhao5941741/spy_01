package com.example.spy.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.spy.entity.Spy;
import com.example.spy.mapper.SpyMapper;
import com.example.spy.service.SpyService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2022-07-24
 */
@Service
public class SpyServiceImpl extends ServiceImpl<SpyMapper, Spy> implements SpyService {
    @Override
    public JSONObject find2(Spy spy) {
        JSONObject resultJson = new JSONObject();
        List<Map<String, Object>> mapList = baseMapper.find2(spy);
        List<String> cityList = new ArrayList<>();
        for (Map<String, Object> map : mapList) {
            String city = map.get("model_type").toString();
            if (!cityList.contains(city)) {
                cityList.add(city);
            }
        }
        JSONArray dataArr = new JSONArray();
        for (int i = 0; i < cityList.size(); i++) {
            String city = cityList.get(i);
            JSONObject jsonObject = new JSONObject();
            for (Map<String, Object> map : mapList) {
                System.out.println(6666);
                String city_code = map.get("model_type").toString();
                if (city_code.equals(city)) {
                    String model = map.get("city").toString();
                    String datetime = map.get("datetime").toString();
                    String number = map.get("number").toString();
                    String type = map.get("type").toString();
                    String dataStr = "";
                    switch (type) {
                        case "01":
                            dataStr = "风电";
                            break;
                        case "02":
                            dataStr = "光伏";
                            break;
                        case "03":
                            dataStr = "统调";
                            break;
                    }
                    jsonObject.put(dataStr,number);
                    jsonObject.put("model",city);
                    jsonObject.put("city",model);
                    jsonObject.put("datetime",datetime);
                }
            }
            dataArr.add(jsonObject);
        }
        resultJson.put("data",dataArr);
        return resultJson;
    }

    public JSONObject find3(Spy spy) {
        JSONObject resultJson = new JSONObject();
        //禹州	2022-07-24	Y0169  99,88,77,66 01,02,03,04
        //禹州	2022-07-24	Y0170  99,88,77,66 01,02,03,04
        List<Map<String, Object>> spyList = baseMapper.find(spy);
        JSONArray dataArr = new JSONArray();
        for (Map<String, Object> map : spyList) {
            //[99,88,77,66]
            //[01,02,03,04]
            String[] number = map.get("number").toString().split(",");
            String[] type = map.get("type").toString().split(",");
            for (int i = 0; i < number.length; i++) {
                map.put(type[i],number[i]);
                //city  date        model_type      number       type         01   02
                //禹州	2022-07-24	Y0170           99,88,77,66 01,02,03,04 + 99 + 88
            }
            map.remove("number");
            map.remove("type");
            dataArr.add(map);
        }
        resultJson.put("data",dataArr);
        return resultJson;
    }
    @Override
    public List<Map<String, Object>> find(Spy spy) {
        //禹州	2022-07-24	Y0169  99,88,77,66 01,02,03,04
        //禹州	2022-07-24	Y0170  99,88,77,66 01,02,03,04
        List<Map<String, Object>> spyList = baseMapper.find(spy);
        List list = new ArrayList();
        for (Map<String, Object> map : spyList) {
            //[99,88,77,66]
            //[01,02,03,04]
            String[] number = map.get("number").toString().split(",");
            String[] type = map.get("type").toString().split(",");
            for (int i = 0; i < number.length; i++) {
                map.put(type[i],number[i]);
                //city  date        model_type      number       type         01   02
                //禹州	2022-07-24	Y0170           99,88,77,66 01,02,03,04 + 99 + 88
            }
            map.remove("number");
            map.remove("type");
            list.add(map);
        }
        return list;
    }
}
