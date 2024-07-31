package com.themis.Model.Keys;

import it.unisa.dia.gas.jpbc.Element;

import java.util.HashMap;
import java.util.Map;

public class KEY {
    public Element[] sk0 = new Element[3];
    public Map<String, Element[]> sky = new HashMap<>();
    public Element[] sk_ = new Element[3];
    public Element[] g_sigma = new Element[3];
    public void set_sk0(Element[] sk0){
        this.sk0 = sk0;
    }
    public void set_sky(String key, Element[] value){
        this.sky.put(key, value);
    }
    public void setG_sigma(Element[] g_sigma){
        this.g_sigma = g_sigma;
    }
    public  void setSk_(Element[] sk_){
        this.sk_ = sk_;
    }
}
