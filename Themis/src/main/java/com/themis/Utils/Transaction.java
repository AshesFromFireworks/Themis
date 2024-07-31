package com.themis.Utils;

import com.themis.Controller.RegisterController;
import com.themis.Model.IBF;
import com.themis.Service.RegisterService;

import java.util.HashMap;
import java.util.Map;

public class Transaction {

    public static HashMap<String, String> BlockChain(String tx, String para1, String para2, String sign){
        HashMap<String, String> map = new HashMap<>();
        map.put("tx", tx);
        map.put("para1", para1);
        map.put("para2", para2);
        map.put("ts", String.valueOf(System.currentTimeMillis()));
        map.put("sign", sign);
        return map;
    }

    public static void main(String[] args) {
        IBF ibf = new IBF();
        String str = ibf.toString();
        Map<String, String> map = BlockChain("Update", Hash.hash_bytes(str.getBytes()),
                "", RegisterService.register(str, RegisterController.index).get("cre"));
        map.remove("ts");
        System.out.println(map);

        map = BlockChain("Report", Hash.hash_bytes("report".getBytes()),"",
                RegisterService.register("report", RegisterController.index).get("cre")) ;
        System.out.println(map);
        map = BlockChain("Judge", Hash.hash_bytes("judge".getBytes()),"",
                RegisterService.register("judge", RegisterController.index).get("cre")) ;
        System.out.println(map);
    }

}
