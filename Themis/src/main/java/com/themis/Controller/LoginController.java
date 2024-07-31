package com.themis.Controller;

import com.themis.Model.DTO.UserLoginDTO;
import com.themis.Model.Result;
import com.themis.Utils.Hash;
import com.themis.Utils.Jwt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class LoginController {

    @Autowired
    JdbcTemplate jdbcTemplate;


    @RequestMapping("/login")
    public Result<HashMap<String, String>> login(@RequestBody UserLoginDTO userLoginDTO){
        long start = System.currentTimeMillis();
        String sql = "select id from dataprovider where username = ? and password = ?";
        String username = userLoginDTO.username;
        String password = userLoginDTO.password;
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql, new Object[]{username, Hash.Hash_pwd(password)});
        if(!maps.isEmpty()){
            String id = String.valueOf(maps.get(0).get("id"));
            HashMap<String, String> data = new HashMap<>();
            data.put("id", id);
            data.put("username", username);
            data.put("token", Jwt.genJWT(id, username));
            long stop = System.currentTimeMillis();
            System.out.println("dp login time: "+ (stop-start));
            return Result.success("1", "数据提供者登陆成功", data);
        } else{
            sql = "select id from investigator where username = ? and password = ?";
            maps = jdbcTemplate.queryForList(sql, username, Hash.Hash_pwd(password));
            if(!maps.isEmpty()) {
                String id = String.valueOf(maps.get(0).get("id"));
                HashMap<String, String> data = new HashMap<>();
                data.put("id", id);
                data.put("username", username);
                data.put("token", Jwt.genJWT(id, username));
                long stop = System.currentTimeMillis();
                System.out.println("in login time: "+ (stop-start));
                return Result.success("2", "调查员登陆成功", data);
            } else{
                return Result.error("0", "用户名或密码错误");
            }

        }
    }


}
