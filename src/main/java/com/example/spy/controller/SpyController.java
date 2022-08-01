package com.example.spy.controller;


import com.alibaba.fastjson.JSONObject;
import com.example.spy.entity.Spy;
import com.example.spy.service.SpyService;
import com.example.spy.utils.ExcelUtil;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
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
@CrossOrigin
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

    @RequestMapping("importExcel")
    public JSONObject importExcel(@RequestParam MultipartFile file) {
        JSONObject result = new JSONObject();
        try {
            List<Map<String, Object>> list = ExcelUtil.excelToList(file);
            result.put("data",list);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        result.put("success", false);
        return result;
    }
    @RequestMapping("exportExcle")
    public void exportExcle(@RequestBody Map<String,Object> data, HttpServletResponse response)  {
        System.out.println(data.get("data"));
        List<Map<String,Object>> mapList = (List<Map<String, Object>>) data.get("data");
//        for (Map<String, Object> map : mapList) {
//            map.remove("校对值");
//            map.remove("初始化时间");
//        }
        ExcelUtil.exportToExcel(response,"导出Excel", mapList);
    }
}

