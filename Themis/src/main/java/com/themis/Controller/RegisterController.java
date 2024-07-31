package com.themis.Controller;

import com.themis.Model.DTO.DpCreditDTO;
import com.themis.Model.DTO.InInfoDTO;
import com.themis.Model.DTO.InRegisterDTO;
import com.themis.Model.Result;
import com.themis.Model.DTO.DpRegisterDTO;
import com.themis.Service.RegisterService;
import com.themis.Utils.Hash;
import com.themis.Utils.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.themis.Service.RegisterService.*;

@RestController
public class RegisterController {

    public static int index = 0;

    @Autowired
    JdbcTemplate jdbcTemplate;



    @RequestMapping("/dp/register")
    public Result<HashMap<String, String>> register_dp(
            @RequestBody DpRegisterDTO dpRegisterDTO){
        String sql = "select id from dataprovider where username = ?";
        List<Map<String, Object>> maps1 = jdbcTemplate.queryForList(sql, dpRegisterDTO.username);
        if(!maps1.isEmpty()){
            return Result.error("0", "用户名重复");
        }
        sql = "select id from investigator where username = ?";
        maps1 = jdbcTemplate.queryForList(sql, dpRegisterDTO.username);
        if(!maps1.isEmpty()){
            return Result.error("0", "用户名重复");
        }
        long start = System.currentTimeMillis();
        HashMap<String, String> map = RegisterService.register(dpRegisterDTO.username, index);
        map.put("username", dpRegisterDTO.username);
        map.put("password", dpRegisterDTO.password);
        map.put("email", dpRegisterDTO.email);
        map.put("index", String.valueOf(index));
        long stop = System.currentTimeMillis();
        System.out.println("dp register time:"+ (stop-start));
        return Result.success("1", "数据提供者注册", map);
    }

    @RequestMapping("/in/register")
    public Result<HashMap<String, String>> register_in(
            @RequestBody InRegisterDTO inRegisterDTO
    ){
        String sql = "select id from investigator where username = ?";
        List<Map<String, Object>> maps1 = jdbcTemplate.queryForList(sql, inRegisterDTO.username);
        if(!maps1.isEmpty()){
            return Result.error("0", "用户名重复");
        }
        sql = "select id from dataprovider where username = ?";
        maps1 = jdbcTemplate.queryForList(sql, inRegisterDTO.username);
        if(!maps1.isEmpty()){
            return Result.error("0", "用户名重复");
        }
        long start = System.currentTimeMillis();
        HashMap<String, String> map = RegisterService.register(inRegisterDTO.username, index);
        map.put("username", inRegisterDTO.username);
        map.put("password", inRegisterDTO.password);
        map.put("email", inRegisterDTO.email);
        map.put("attr1", inRegisterDTO.attr1);
        map.put("attr2", inRegisterDTO.attr2);
        map.put("attr3", inRegisterDTO.attr3);
        map.put("prk", "MIGTAgEAMBMGByqGSM49AgEGCCqBHM9VAYItBHkwdwIBAQQgPvLQfzptB7X9KYm4Cs6bF8japGNFBk9rqly4AWBX/3" +
                "ygCgYIKoEcz1UBgi2hRANCAAQjBDABOF0ZNvjVULWJlIPU1og1uKCAO5Hck4e9jcSMyH8PzKStE4gPVelXaStCC7lOr9jo4HG" +
                "pWNjE6s1Ku43+");
        long stop = System.currentTimeMillis();
        System.out.println("in register time:"+ (stop-start));
        return Result.success("1", "调查员注册", map);
    }



    @RequestMapping("/dp/credit")
    public Result<HashMap<String, String>> compute(
//            String cre1, String cre2, String username, String password, String email
            @RequestBody DpCreditDTO dpCreditDTO
    ){
        String cre1 = dpCreditDTO.cre1;
        String cre2 = dpCreditDTO.cre2;
        String email = dpCreditDTO.email;
        String username = dpCreditDTO.username;
        String password = dpCreditDTO.password;
        long start = System.currentTimeMillis();
        String sql = "select id from dataprovider where `username` = ?";
        List<Map<String, Object>> maps1 = jdbcTemplate.queryForList(sql, username);
        if(!maps1.isEmpty()){
            return Result.error("0", "用户名重复");
        }
        sql = "insert into `dataprovider` (`username`, `password`, `email`, `puk_n`, `puk_e`) values (?, ?, ?, ?, ?)";
        String hash_pwd = Hash.Hash_pwd(password);
        int i = jdbcTemplate.update(sql, username, hash_pwd, email, String.valueOf(puk_n), String.valueOf(puk_e));
        if(i > 0){
            BigInteger[] sigma_i = new BigInteger[]{new BigInteger(cre1), new BigInteger(cre2)};
            BigInteger DAC = Comb(sigma_i, const_t, const_n0, puk_n);
//            boolean judge = Verify(DAC, username, puk_n, puk_e, delta);

            sql = "select * from dataprovider where `username` = ? and `password` = ?";
            List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql, username, hash_pwd);
            String id = String.valueOf(maps.get(0).get("id"));
            HashMap<String,String> map = new HashMap<>();
            map.put("id", id);
            map.put("cre", String.valueOf(DAC));
            map.put("username", username);
            Boolean res  = Verify(DAC, username, puk_n, puk_e, delta);
            System.out.println("dp cre verify: " + res);
            long stop = System.currentTimeMillis();
            System.out.println("dp combine time: " + (stop-start));
            return Result.success("1", "数据提供者注册成功", map);
        } else {
            return Result.error("0", "数据提供者注册失败");
        }
    }

    @RequestMapping("/in/credit")
    public Result<HashMap<String, String>> compute_in(
//            String cre1, String cre2, String username, String password, String email
            @RequestBody InInfoDTO inInfoDTO
    ){
        String cre1 = inInfoDTO.cre1;
        String cre2 = inInfoDTO.cre2;
        String email = inInfoDTO.email;
        String username = inInfoDTO.username;
        String password = inInfoDTO.password;
        String attr1 = inInfoDTO.attr1;
        String attr2 = inInfoDTO.attr2;
        String attr3 = inInfoDTO.attr3;
        long start = System.currentTimeMillis();
        String sql = "select id from investigator where username = ?";
        List<Map<String, Object>> maps1 = jdbcTemplate.queryForList(sql, username);
        if(!maps1.isEmpty()){
            return Result.error("0", "用户名重复");
        }
        sql = "insert into `investigator` (`username`, `password`, `email`, `puk_n`, `puk_e`, " +
                "`attr1`, `attr2`, `attr3`) values (?, ?, ?, ?, ?, ?, ?, ?)";
        String hash_pwd = Hash.Hash_pwd(password);
        int i = jdbcTemplate.update(sql, username, hash_pwd, email, String.valueOf(puk_n), String.valueOf(puk_e),
                attr1, attr2, attr3);
        if(i > 0){
            BigInteger[] sigma_i = new BigInteger[]{new BigInteger(cre1), new BigInteger(cre2)};
            BigInteger DAC = Comb(sigma_i, const_t, const_n0, puk_n);
//            boolean judge = Verify(DAC, username, puk_n, puk_e, delta);
            sql = "select * from investigator where username = ? and password = ?";
            List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql, username, hash_pwd);
            String id = String.valueOf(maps.get(0).get("id"));
            HashMap<String,String> map = new HashMap<>();
            map.put("id", id);
            map.put("cre", String.valueOf(DAC));
            map.put("username", username);
            Boolean res  = Verify(DAC, username, puk_n, puk_e, delta);
            System.out.println("dp cre verify: " + res);
            String sign = RegisterService.register(Hash.hash_bytes((id+username).getBytes()), RegisterController.index).get("cre");
            HashMap<String, String> transaction = Transaction.BlockChain("IniGrant", "ReqID"+username+id+Hash.Hash_BigInteger(username+id).toString(),
                    "CaseID"+username+id+Hash.Hash_BigInteger(username+id).toString(), sign);
            map.putAll(transaction);
            long stop = System.currentTimeMillis();
            System.out.println("dp combine time: " + (stop-start));
            return Result.success("1", "调查员注册成功", map);
        } else {
            return Result.error("0", "调查员注册失败");
        }

    }

}
