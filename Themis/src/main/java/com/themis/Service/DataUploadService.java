package com.themis.Service;

import com.themis.Model.IBF;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class DataUploadService {
    public int m = 8000;
    public int k = 5;
    public int q = 1;
    public  List<BigInteger> hash_key;

     {
        hash_key = new ArrayList<>();
        hash_key.add(new BigInteger("15235891588942870891"));
        hash_key.add(new BigInteger("11696966924909762329"));
        hash_key.add(new BigInteger("10556878844359407319"));
        hash_key.add(new BigInteger("16496779204075318079"));
        hash_key.add(new BigInteger("12370012747868118247"));
        hash_key.add(new BigInteger("14589655589569635661"));
    }

    public IBF dataUpload(String[] string, String[] filename, int count, IBF ibf){
        if (count == 0){
            ibf = new IBF(this.m, this.k, this.q, string, filename, this.hash_key);
        }
        else {
            int length = ibf.str.length;
            String[] temp1 = new String[length+1];
            String[] temp2 = new String[length+1];
            for(int i = 0; i < length; i++){
                temp1[i] = ibf.str[i];
                temp2[i] = ibf.filenames[i];
            }
            temp1[length] = string[0];
            temp2[length] = filename[0];
            ibf = new IBF(this.m, this.k, ibf.q+1, temp1, temp2, ibf.hash_key);
        }
        ibf.node.show();
        return ibf;
    }

}
