package com.feer.feercore.users.model;

public class Response {
    private boolean status;
    private String detail;

    public Response() {
    }

    public Response(boolean status, String detail) {
        this.status = status;
        this.detail = detail;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
