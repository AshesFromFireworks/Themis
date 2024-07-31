package com.themis.Controller;


import com.themis.Model.Result;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
public class UserController {

    @Autowired
    JdbcTemplate jdbcTemplate;


    @RequestMapping("/dp/info/{id}")
    public Result<HashMap<String, String>> dp(@PathVariable(value = "id") String id){
        String sql = "select * from dataprovider where id = ?";
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql, id);
        HashMap<String, String> map = new HashMap<>();
        map.put("username", String.valueOf(maps.get(0).get("username")));
        map.put("email", String.valueOf(maps.get(0).get("email")));
        map.put("id", String.valueOf(maps.get(0).get("id")));
        map.put("registerTime", String.valueOf(maps.get(0).get("register_time")));
        map.put("updateTime", String.valueOf(maps.get(0).get("update_time")));
        return Result.success("1", "数据提供者的信息", map);
    }


    @RequestMapping("/in/info/{id}")
    public Result<HashMap<String, String>> in(@PathVariable(value = "id") String id){
        String sql = "select * from investigator where id = ?";
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql, id);
        HashMap<String, String> map = new HashMap<>();
        map.put("username", String.valueOf(maps.get(0).get("username")));
        map.put("email", String.valueOf(maps.get(0).get("email")));
        map.put("id", String.valueOf(maps.get(0).get("id")));
        map.put("registerTime", String.valueOf(maps.get(0).get("register_time")));
        map.put("updateTime", String.valueOf(maps.get(0).get("update_time")));
        return Result.success("1", "调查员的信息", map);
    }

    @RequestMapping("/dp/change")
    public Result<HashMap<String, String>> dpChange(){
        return null;
    }

}
