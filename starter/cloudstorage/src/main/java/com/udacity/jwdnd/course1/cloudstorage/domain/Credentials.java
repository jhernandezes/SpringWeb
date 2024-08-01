package com.udacity.jwdnd.course1.cloudstorage.domain;

public class Credentials {

    private String userName;
    private Integer userId;
    private Integer credentialId;
    private String url;
    private String key;
    private String password;

    public Credentials(String userName, Integer userId, Integer credentialId, String url, String key, String password) {
        this.userName = userName;
        this.userId = userId;
        this.credentialId = credentialId;
        this.url = url;
        this.key = key;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getCredentialId() {
        return credentialId;
    }

    public void setCredentialId(Integer credentialId) {
        this.credentialId = credentialId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
