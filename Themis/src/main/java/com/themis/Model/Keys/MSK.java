package com.themis.Model.Keys;

import it.unisa.dia.gas.jpbc.Element;

public class MSK {
    private Element g, h, a1, a2, b1, b2, g_d1, g_d2, g_d3;

    public Element[] getKey(){
        return new Element[]{g, h, a1, a2, b1, b2, g_d1, g_d2, g_d3};
    }
    public MSK(Element g, Element h, Element a1, Element a2, Element b1, Element b2, Element g1, Element g2, Element g3){
        this.g = g;
        this.h = h;
        this.a1 = a1;
        this.a2 = a2;
        this.b1 = b1;
        this.b2 = b2;
        this.g_d1 = g1;
        this.g_d2 = g2;
        this.g_d3 = g3;
    }
}
