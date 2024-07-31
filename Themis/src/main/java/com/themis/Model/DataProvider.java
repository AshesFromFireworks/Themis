package com.themis.Model;




public class DataProvider {

    private Integer id;
    private String username;
    private String password;
    private String email;
    private String puk_n;
    private String puk_e;

    @Override
    public String toString() {
        return "DataProvider{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", puk_n='" + puk_n + '\'' +
                ", puk_e='" + puk_e + '\'' +
                '}';
    }

    public DataProvider(){}

    public void setEmail(String email) {
        this.email = email;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPuk_e(String puk_e) {
        this.puk_e = puk_e;
    }

    public void setPuk_n(String puk_n) {
        this.puk_n = puk_n;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
