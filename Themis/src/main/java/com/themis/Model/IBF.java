package com.themis.Model;

import com.themis.Utils.Hash;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class IBF {

    public int m;
    public int k;
    public int q;

    public List<int[][]> list;
    public List<BigInteger> hash_key = new ArrayList<>();
    public List<BigInteger> r;
    public String[] str;
    public String[] filenames;
    public IBTree node;



    public static String Query(IBF t, String word, IBTree node){
//        System.out.println("search for character: " + word);
        if(!node.verify(word, t)){
//            System.out.println("not found");
            return "false";
        }
        node = node.search(word, t);
//        System.out.print("search result: ");
        if (node != null) {
//            node.show();
//            System.out.println("found");
//            System.out.println("file name is : "+node.filename);
            return node.filename;
        }
        else{
//            System.out.println("not found");
            return "false";
        }
    }

    public IBF(){

    }

    public IBF(int m, int k, int q, String[] string, String[] filenames, List<BigInteger> Hash){
        this.m = m;
        this.k = k;
        this.q = q;
        this.str = string;
        this.hash_key = new ArrayList<>();
        this.r = new ArrayList<>();
        this.list = new ArrayList<>();
        this.filenames = filenames;

        // 初始化参数并插入
        gen_key(Hash);
        gen_r();
        init();
        insert();
        IBTree[] nodes = new IBTree[q];
        for (int i = 0; i < q; i++) {
            // 叶子节点无子树
            nodes[i] = new IBTree(str[i], r.get(i), list.get(q + i), null, null, filenames[i]);
        }
        // 通过叶子节点构建树
        nodes = IBF.build(nodes, this);
        this.node = nodes[nodes.length-1];
//        Query(this, "age:20number:2021size:bigdegree:terrible");
    }


    public void gen_key(List<BigInteger> Hash){
        this.hash_key = Hash;
    }

    // generate key
    public void gen_r(){
        Random random = new Random();
        for(int i = 0; i < q; i++){
            this.r.add(BigInteger.probablePrime(32, random));
        }
    }

    public BigInteger hash(byte[] message){
        try{
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(message);
            BigInteger tem = new BigInteger(md.digest());
            return tem;
        } catch(NoSuchAlgorithmException e){
            System.out.println(e);
            return BigInteger.valueOf(-1);
        }
    }



    public void show(int i){
        int[][] lis = this.list.get(i);
//        System.out.println("IBF" + (i+1) % this.q + ":");
        for (int j = 0; j < m; j++){
            System.out.print(lis[j][0] + "  ");
        }
        System.out.println();
        for (int j = 0; j < m; j++){
            System.out.print(lis[j][1] + "  ");
        }
        System.out.println();
        System.out.println();
    }

    // 初始化 IBF
    public void init(){
        String random_number = "2021214508";
        for(int l = 0; l < this.q; l++){
            int[][] lis = new int[this.m][2];

//            for(int i = 0; i < this.m; i++){
//                BigInteger temp = hash(random_number.getBytes());
//                int num = temp.intValue() % 2;
//                if (num < 0) num += 2;
//                lis[i][num] = 0;
//                lis[i][1-num] = 1;
//                random_number = String.valueOf(Integer.parseInt(random_number) + 1);
//            }

            this.list.add(lis);

        }
    }

    // 插入
    public void insert(){
        for(int l = 0; l < this.q; l++){
            int[][] lis = this.list.get(l);
//            show(l);
            String word = this.str[l];
            for(int i = 0; i < this.k; i++){
                BigInteger tem1 = Hash.Hmac(this.hash_key.get(i), word.getBytes());
                int num1 = tem1.intValue() % this.m;
                if (num1 < 0) num1 += this.m;
                byte[] b0 = BigInteger.valueOf(num1).toByteArray();
                BigInteger tem2 = Hash.Hmac(this.hash_key.get(k), b0);
                BigInteger temp = tem2.xor(this.r.get(l));
                byte[] b = temp.toByteArray();
                BigInteger tem = hash(b);
                int num2 = tem.intValue() % 2;
                if (num2 < 0) num2 += 2;
                lis[num1][num2]  = 1;
                lis[num1][1-num2] = 0;
            }
            this.list.add(lis);
//            System.out.println("IBF" + (l+1) + " inserted state: ");
//            show(l + this.q);
        }
    }

    // 构建树结构
    public static IBTree[] build(IBTree[] nodes, IBF t){
        List<IBTree> list = new ArrayList<>(Arrays.asList(nodes));
        int num = nodes.length;
        int i = 0, j = 1;
        while(j < num){
            IBTree new_node = compound(list.get(i), list.get(j), t);
            list.add(new_node);
            i += 2;
            j += 2;
            num++;
        }
        IBTree[] node = new IBTree[num];
        for(int temp = 0; temp < num; temp++){
            node[temp] = list.get(temp);
        }
        return node;
    }

    // 两个子节点生成一个父节点
    public static IBTree compound(IBTree left, IBTree right, IBF t){
        int[][] lis1 = left.ibf;
        int[][] lis2 = right.ibf;
        BigInteger r1 = left.r;
        BigInteger r2 = right.r;
        Random random = new Random();
        BigInteger r_new = BigInteger.probablePrime(32, random);
        int[][] list_new = new int[lis1.length][2];
        for(int i = 0; i < lis1.length; i++){
            byte[] b = BigInteger.valueOf(i).toByteArray();
            BigInteger temp = Hash.Hmac(t.hash_key.get(t.k), b);
            BigInteger left_b = temp.xor(r1);
            BigInteger right_b = temp.xor(r2);
            BigInteger _new = temp.xor(r_new);
            BigInteger hash_left = t.hash(left_b.toByteArray());
            BigInteger hash_right = t.hash(right_b.toByteArray());
            BigInteger hash_new = t.hash(_new.toByteArray());
            int num_left = hash_left.intValue() % 2;
            if (num_left < 0) num_left += 2;
            int num_right = hash_right.intValue() % 2;
            if (num_right < 0) num_right += 2;
            int num_new = hash_new.intValue() % 2;
            if (num_new < 0) num_new += 2;
            int res = lis1[i][num_left] | lis2[i][num_right];
            list_new[i][num_new] = res;
//            list_new[i][1-num_new] = 1 - res;
        }
        return new IBTree(left.string+"|"+right.string, r_new, list_new, left, right, "");
    }

    public static void main(String[] args) {
        for(int k = 0; k < 1; k++){
        int length = 100;
        String[] filenames = new String[length];
        String[] str = new String[length];
        List<BigInteger> Hash = new ArrayList<>();
        for(int i = 0; i < 6; i++){
            Hash.add(BigInteger.probablePrime(32, new Random()));
        }
        for(int i = 0; i < length; i++){
            filenames[i] = i + ".jpg";
            str[i] = "attr" + i;
        }
        Long start = System.currentTimeMillis();

        IBF t = new IBF(8000, 5, str.length, str, filenames, Hash);
        Long stop = System.currentTimeMillis();
        System.out.println(stop-start);
        int count = 0;
        for(int i = 0; i < 100; i++){
            String query = IBF.Query(t, "attr" + i, t.node);
            if(!query.equals("false")) {
                count++;
            }
        }
        start = System.currentTimeMillis();
        System.out.println("search: "+(start-stop));
        System.out.println(count);

        filenames = new String[length];
        str = new String[length];
        for(int i = 0; i < length; i++){
            filenames[i] = i+1 + ".jpg";
            str[i] = "attr" + i+1;
        }
        start = System.currentTimeMillis();

        t = new IBF(8000, 5, str.length, str, filenames, Hash);
        stop = System.currentTimeMillis();
        System.out.println(stop-start);
        count = 0;
        for(int i = 0; i < 100; i++){
            String query = IBF.Query(t, "attr" + i+1, t.node);
            if(!query.equals("false")) {
                count++;
            }
        }
        start = System.currentTimeMillis();
        System.out.println("search: "+(start-stop));
        System.out.println(count);
    }}
}
