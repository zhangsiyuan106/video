package com.boot.controller.lmcontroller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.boot.constant.LexJSONResult;
import com.boot.pojo.OperationLog;
import com.boot.service.OperaLogService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author memgzong
 * @create 2021/5/29
 *
 * 日志操作
 */
@RestController
@RequestMapping("/log")
public class LogController {
    @Autowired
    private OperaLogService logService;




    @GetMapping("/getLogList")
    public String logList(@RequestParam("pageNo") int pageNo, @RequestParam("pageSize") int pageSize,
                          @RequestParam("str") String str){
        String flag = "error";
        Page<OperationLog> logs = logService.logListPage(pageNo, pageSize, str);
        List<OperationLog> content = logs.getRecords();
        long count = logs.getTotal();
        Map<String,Object> map = new HashMap<>();
        if (content != null){
            flag="ok";
        }
        map.put("flag",flag);
        map.put("allLog",content);
        map.put("count",count);
        String s = JSON.toJSONString(map);
        return s;
    }

    @DeleteMapping("/deleteLog/{id}")
    public String deleteLog(@PathVariable("id") int id){
        int deleteLog = logService.deleteLog(id);
        if (deleteLog <=0 ){
            return "error";
        }
        return "success";
    }
    @DeleteMapping("/deleteLogByIds/{id}")
    public LexJSONResult deleteLogs(@PathVariable("id") int[] id){
         logService.deleteAllLogByIds(id);

        return LexJSONResult.ok();
    }

}
