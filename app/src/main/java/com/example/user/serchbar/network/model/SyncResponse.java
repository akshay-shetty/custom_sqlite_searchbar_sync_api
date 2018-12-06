package com.example.user.serchbar.network.model;

import java.util.List;

public class SyncResponse {
    String success;
    String message;
    List<DataModelResponse>  data;
    String count;

    public List<DataModelResponse> getData() {
        return data;
    }

    public void setData(List<DataModelResponse> data) {
        this.data = data;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}

