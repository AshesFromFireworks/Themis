package com.themis.Model;

import com.themis.Utils.Hash;

import java.math.BigInteger;

public class IBTree {

    public String string;
    public BigInteger r;
    public int[][] ibf;
    public IBTree left;
    public IBTree right;
    public String filename;
    public IBTree(String string, BigInteger r, int[][] ibf, IBTree left, IBTree right, String file){
        this.string = string;
        this.r = r;
        this.ibf = ibf;
        this.left = left;
        this.right = right;
        this.filename = file;

    }

    public boolean is_leaf(){
        return this.left == null && this.right == null;
    }

    public void show(){
        System.out.println();
        System.out.println("IBF:");
        System.out.println("character: " + this.string);
        System.out.println("random number: " + this.r);
        System.out.println("ibf arrays:");
        for (int j = 0; j < this.ibf.length; j++){
            System.out.print(this.ibf[j][0] + "  ");
        }
        System.out.println();
        for (int j = 0; j < this.ibf.length; j++){
            System.out.print(this.ibf[j][1] + "  ");
        }
        System.out.println();
        if (this.left != null){
            System.out.println("left character: " + this.left.string);
        }
        if(this.right != null){
            System.out.println("right character: " + this.right.string);
        }
        System.out.println();
    }

    // 验证是否存在
    public boolean verify(String s, IBF t){
        boolean judge = true;
        int[][] lis = this.ibf;
        int l = lis.length;
        for(int i = 0; i < t.k; i++){
            int num = Hash.Hmac(t.hash_key.get(i), s.getBytes()).intValue() % l;
            if(num < 0) num += l;
            byte[] te = BigInteger.valueOf(num).toByteArray();
            BigInteger temp = Hash.Hmac(t.hash_key.get(t.k), te);
            temp = temp.xor(this.r);
            byte[] b = temp.toByteArray();
            int number = t.hash(b).intValue() % 2;
            if (number < 0) number += 2;
            if(lis[num][number] == 0){
                judge = false;
                break;
            }
        }
        return judge;
    }

    // 寻找对应特征的叶子节点
    public IBTree search(String s, IBF t){
        IBTree temp = this;
        while(temp.left != null && temp.right != null){
            if(temp.left.verify(s, t)){
                temp = temp.left;
            }
            else if (temp.right.verify(s, t) && temp.right != null){
                temp = temp.right;
            }
            else {
//                System.out.println("fail");
                return null;
            }
        }
        return temp;
    }



}
