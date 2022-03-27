package com.medlink.api.medlinkapi.exception;

import com.google.gson.Gson;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ExceptionHandle {
    private String statusCode;
    private String message;

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ExceptionHandle(String statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    @Override
    public String toString() {


        return "ExceptionHandle: {" +
                "statusCode='" + statusCode + '\'' +
                ", message='" + message + '\'' +
                '}';
    }


    public ExceptionHandle() {
    }


    public String updateError(ExceptionHandle exceptionHandle) {
        exceptionHandle.setStatusCode("300");
        exceptionHandle.setMessage("Id bạn đang để trống hoặc không tồn tại ");
        Gson g = new Gson();
        String response = g.toJson(exceptionHandle);
        return response;
    }

    public ExceptionHandle deleteIdNotExist(ExceptionHandle exceptionHandle) {
        exceptionHandle.setStatusCode("300");
        exceptionHandle.setMessage("Xóa thất bại vì Id không tồn tại");
        return exceptionHandle;
    }

    public ExceptionHandle deleteIdIsNull(ExceptionHandle exceptionHandle) {
        exceptionHandle.setStatusCode("300");
        exceptionHandle.setMessage("Xóa thất bại vì Id bạn đang để trống");
        return exceptionHandle;
    }

    public String getIdNotExist(ExceptionHandle exceptionHandle) {
        exceptionHandle.setStatusCode("300");
        exceptionHandle.setMessage("Bạn đang truyền thiếu mã thuốc");
        Gson g = new Gson();
        String response = g.toJson(exceptionHandle);
        return response;
    }


    public String getIdIsNull(ExceptionHandle exceptionHandle) {
        exceptionHandle.setStatusCode("300");
        exceptionHandle.setMessage("Bạn đang truyền thiếu mã thuốc");
        Gson g = new Gson();
        String response = g.toJson(exceptionHandle);
        return response;
    }

}
