package com.themis.Utils;

import com.themis.Model.IBF;
import com.themis.Model.IBTree;
import org.springframework.jdbc.core.JdbcTemplate;

public class IBFSave {

     static int j = 1;
     static int count = 1;
     public static void flash(){
         j = 1;
         count = 1;
    }

    public static void ibfSave(IBTree node, JdbcTemplate jdbcTemplate){

        String sql = "insert into ibf (`id`, `ibf`, `left`, `right`, `r`, `string`, `filename`) values(?, ?, ?, ?, ?, ?, ?)";
        String r = node.r.toString();
        String string = node.string;
        String filename = node.filename;
        int[][] bf = node.ibf;
        StringBuilder ibf = new StringBuilder();
        for(int j = 0; j < 2; j++){
            for(int k = 0; k < 80; k++){
                ibf.append(bf[k][j]);
            }
        }
        int left = 0, right = 0;

        if(node.left != null){

            ibfSave(node.left, jdbcTemplate);
            left = j-1;
        }
        if(node.right != null){
            
            ibfSave(node.right, jdbcTemplate);
            right = j-1;

        }
        jdbcTemplate.update(sql, j, ibf, left, right, r, string, filename);
        j++;

    }

    public static int[][] stringToArray(String string){
         int[][] temp = new int[80][2];
         for(int i = 0; i < 160; i++){
             String str = string.substring(i, i+1);
             temp[i%80][i/80] = Integer.parseInt(str);
         }
         return temp;
    }

//    public static void main(String[] args) {
//        int[][] res = stringToArray("01010000010100001100010001000001000010010000100010011101001001000111000011000" +
//                "01000101001101010000011101110110110000100000111011101000010000110010000001100000100");
//        for (int j = 0; j < 80; j++){
//            System.out.print(res[j][0] + "  ");
//        }
//        System.out.println();
//        for (int j = 0; j < 80; j++){
//            System.out.print(res[j][1] + "  ");
//        }
//        System.out.println();
//
//    }

}
