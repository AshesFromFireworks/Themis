package com.themis.Controller;

import com.themis.Model.DTO.RequestFileDTO;
import com.themis.Model.IBF;
import com.themis.Model.IBTree;
import com.themis.Model.Keys.KEY;
import com.themis.Model.Keys.PKMSK;
import com.themis.Model.Result;
import com.themis.Service.CPABEService;
import com.themis.Service.DataUploadService;
import com.themis.Service.RegisterService;
import com.themis.Utils.*;
import it.unisa.dia.gas.jpbc.Pairing;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;
import java.io.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class DataController {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @RequestMapping("/postFile")
    public Result<HashMap<String, String>> postFile(
//            @RequestBody PostFileDTO postFileDTO
            MultipartFile file,
            String id,
            String cre,
            String type,
            String spot,
            String desc
            ){
//        MultipartFile file = postFileDTO.file;
//        String id = postFileDTO.id;
//        String cre = postFileDTO.cre;;
//        String type = postFileDTO.type;
//        String spot = postFileDTO.spot;
//        String desc = postFileDTO.desc;
        String sql = "select puk_n, puk_e, username from dataprovider where `id` = ?";
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql, Integer.parseInt(id));
        BigInteger n = new BigInteger(String.valueOf(maps.get(0).get("puk_n")));
        BigInteger e = new BigInteger(String.valueOf(maps.get(0).get("puk_e")));
        BigInteger DAC = new BigInteger(cre);
        String rm = String.valueOf(maps.get(0).get("username"));
        Boolean verify = RegisterService.Verify(DAC, rm, n, e, BigInteger.valueOf(24));//24
        if (verify){
            System.out.println("cre verify pass!");
            DataUploadService dataUploadService = new DataUploadService();
            IBF ibf = read(new IBF());
            int count = 0;
            if(ibf == null){
                count = 1;
            } else{
                count = ibf.filenames.length + 1;
            }
            String st = file.getOriginalFilename();
            String dir = new File("").getAbsolutePath();
            String strPath = dir + "/picture/" + count + "." + st.split("\\.")[1];
            st = count + "." + st.split("\\.")[1];
            ibf = dataUploadService.dataUpload(new String[]{type+";"+spot+desc}, new String[]{st}, count-1, ibf);
            save(ibf);
            File file2 = new File(strPath);
            BufferedOutputStream out = null;
            try {
                out = new BufferedOutputStream(new FileOutputStream(file2));
                out.write(file.getBytes());
                out.flush();
                out.close();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            System.out.println("file upload success!");
        } else {
            System.out.println("cre verify fail!");
            return Result.error("0", "凭证验证失败");
        }
        HashMap<String, String> map = new HashMap<>();
        try {
            String sign = RegisterService.register(id, RegisterController.index).get("cre");
            HashMap<String, String> upload = Transaction.BlockChain("Upload", cre, Hash.hash_bytes(file.getBytes()),
                    sign);
            map.putAll(upload);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        return Result.success("1", "数据上传成功", map);
    }

    @RequestMapping("/request")
    public Result<HashMap<String,String>> request(
            @RequestBody RequestFileDTO requestFileDTO
//            String type,
//            String spot,
//            String desc,
//            String cre,
//            String id,
//            String prk
            ){
        String type = requestFileDTO.type;
        String spot = requestFileDTO.spot;
        String desc = requestFileDTO.desc;
        String cre = requestFileDTO.cre;
        String id = requestFileDTO.id;
        IBF ibf = read(new IBF());
        long start = System.currentTimeMillis();
        if(ibf == null){
            return Result.error("0", "未找到文件");
        }
        String filename = IBF.Query(ibf, type+";"+spot+desc, ibf.node);
        if(filename.equals("false")){
            return Result.error("0", "未找到文件");
        }
        long stop;
        String sql = "select attr1, attr2, attr3, puk_n, puk_e, username from investigator where `id` = ?";
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql, id);
        if(maps.isEmpty()){
            return Result.error("0", "用户ID不存在");
        } else{
            BigInteger n = new BigInteger(String.valueOf(maps.get(0).get("puk_n")));
            BigInteger e = new BigInteger(String.valueOf(maps.get(0).get("puk_e")));
            BigInteger DAC = new BigInteger(cre);
            String rm = String.valueOf(maps.get(0).get("username"));
            Boolean verify = RegisterService.Verify(DAC, rm, n, e, BigInteger.valueOf(24));//24
            String s = SM2.Decrypt(SM2.prk, SM2.Encrypt(SM2.puk, String.valueOf(System.currentTimeMillis())));
            s = s +" ";
            stop = System.currentTimeMillis();

            System.out.println("request time: "+(stop-start));
            HashMap<String, String> map = new HashMap<>();
            map.put("filepath", filename);
            String filepath = "/result/" + filename;
            String[] str = new String[]{String.valueOf(maps.get(0).get("attr1")),
                    String.valueOf(maps.get(0).get("attr2")), String.valueOf(maps.get(0).get("attr3"))};
            if(!verify){
                return Result.error("0", "凭证验证失败");
            } else {
                String sign = RegisterService.register(Hash.hash_bytes((str[0]+str[1]+str[2]+id).getBytes()), RegisterController.index).get("cre");
                HashMap<String, String> upload = Transaction.BlockChain("Upload", cre,
                        Hash.hash_bytes((str[0]+str[1]+str[2]).getBytes()),
                        sign);
                start = System.currentTimeMillis();
                Pairing bp = PairingFactory.getPairing("a.properties");
                PKMSK pkmsk = CPABEService.Setup(bp);
                KEY key = CPABEService.Keygen(bp, pkmsk.msk, str);
                String dir = new File("").getAbsolutePath();
                filename = dir + "/picture/" + filename;
                stop = System.currentTimeMillis();
                System.out.println("initial time: " + (stop-start));
                try {
//                    String res = CPABEService.ABE(filename, key, str, pkmsk);
//                    res  = CPABEService.ABD(res, bp, pkmsk, key, str);
                    String res = CPABEService.deal(filename, key, pkmsk, str, bp);
                    start = System.currentTimeMillis();
                    System.out.println("deal time: " + (start - stop));
                    map.put("res", res);
                    ImgBase64.Base64ToImage(res, dir + filepath);
                } catch (Exception exception){
                    exception.printStackTrace();
                }
                map.putAll(upload);
                return Result.success("1", "数据请求成功", map);
            }

        }
    }

    @RequestMapping("/load/{filename}")
    public void img(HttpServletResponse response, @PathVariable("filename") String filename){
//        File file = ImgBase64.Base64ToImage("result.jpg");
        System.out.println(filename);
        String dir = new File("").getAbsolutePath();
        File file = new File(dir+"/result/"+filename);
        try {
            FileInputStream in = new FileInputStream(file);
            OutputStream out = response.getOutputStream();
            response.setContentType("application/octet-stream; charset=UTF-8");
            byte[] a = new byte[1024];
            while(in.read(a) != -1){
                out.write(a);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public IBF read(IBF ib){
        String sql = "select * from ibf where id >0";
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql);
        if(maps.isEmpty()){
            ib = null;
            return ib;
        }
        int len = maps.size();
        IBTree[] nodes = new IBTree[len];
        List<String> strings = new ArrayList<>();
        List<String> filenames = new ArrayList<>();
        for(int i = 0; i < len; i++){
            Map<String, Object> temp = maps.get(i);
            int[][] ibf = IBFSave.stringToArray(temp.get("ibf").toString());
            BigInteger r = new BigInteger((String)temp.get("r"));
            String string = String.valueOf(temp.get("string"));
            String filename = String.valueOf(temp.get("filename"));
            nodes[i] = new IBTree(string, r, ibf, null, null, filename);
        }
        for(int i = 0; i < len; i++){
            Map<String, Object> temp = maps.get(i);
            int left = (int) temp.get("left");
            int right = (int) temp.get("right");
            if(left != 0){
                nodes[i].left = nodes[left-1];
            }
            if(right != 0){
                nodes[i].right = nodes[right-1];
            }
            if(left+right==0){
                strings.add(String.valueOf(temp.get("string")));
                filenames.add(String.valueOf(temp.get("filename")));
            }
        }
        String[] string = new String[strings.size()];
        String[] filename = new String[filenames.size()];
        for(int j = 0; j < strings.size(); j++){
            string[j] = strings.get(j);
            filename[j] = filenames.get(j);
        }
        DataUploadService dataUploadService = new DataUploadService();
        ib = new IBF(80, 5, string.length, string, filename, dataUploadService.hash_key);
        return ib;
    }

    public void save(IBF ibf){
        String sql = "delete from ibf where id > 0";
        jdbcTemplate.update(sql);
        IBFSave.flash();
        IBFSave.ibfSave(ibf.node, jdbcTemplate);
    }

}
