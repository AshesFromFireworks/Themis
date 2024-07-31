package com.themis.Controller;


import com.themis.Model.DTO.UserLoginDTO;
import com.themis.Model.Result;
import com.themis.Utils.Jwt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class AdminController {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @RequestMapping("/manage")
    public Result<HashMap<String, String>> admin(){
        HashMap<String, String> map = new HashMap<>();
        String sql = "select id from dataprovider";
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql);
        map.put("dpSize", String.valueOf(maps.size()));
        sql = "select id from investigator";
        maps = jdbcTemplate.queryForList(sql);
        map.put("inSize", String.valueOf(maps.size()));
        sql = "select id, string from ibf where filename != ''";
        maps = jdbcTemplate.queryForList(sql);
        map.put("dataSize", String.valueOf(maps.size()));
        HashMap<String, String> crimeType = new HashMap<>();
        for(Map<String, Object> temp: maps){
            String type = String.valueOf(temp.get("string")).split(";")[0];
            if(crimeType.get(type) == null){
                if(type.equals("偷窃")){
                    crimeType.put("steal", "1");
                }
                if(type.equals("抢劫")){
                    crimeType.put("rob", "1");
                }
                if(type.equals("谋杀")){
                    crimeType.put("murder", "1");
                }
                if(type.equals("强奸")){
                    crimeType.put("rape", "1");
                }

            } else {
                if(type.equals("偷窃")){
                    crimeType.put("steal", String.valueOf(Integer.parseInt(crimeType.get(type))+1));
                }
                if(type.equals("抢劫")){
                    crimeType.put("rob", String.valueOf(Integer.parseInt(crimeType.get(type))+1));
                }
                if(type.equals("谋杀")){
                    crimeType.put("murder", String.valueOf(Integer.parseInt(crimeType.get(type))+1));
                }
                if(type.equals("强奸")){
                    crimeType.put("rape", String.valueOf(Integer.parseInt(crimeType.get(type))+1));
                }
            }
        }
        map.putAll(crimeType);
        return Result.success("1", "data", map);
    }

    @RequestMapping("/admin/login")
    public Result<HashMap<String, String>> admin_login(@RequestBody UserLoginDTO userLoginDTO){
        if(userLoginDTO.username.equals("admin") && userLoginDTO.password.equals("admin")){
            String s = Jwt.genJWT(userLoginDTO.password, userLoginDTO.username);

            HashMap<String, String> map = new HashMap<>();
            map.put("token", s);
            return Result.success("1", "管理员登陆成功", map);
        } else {
            return Result.error("0", "账号或密码错误");
        }
    }
}
