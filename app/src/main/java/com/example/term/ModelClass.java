package com.example.term;

public class ModelClass {
    private String reason;
    private String outtime;
    private String intime;
    private String status;

    public String getStatus() {
        return status;
    }

    ModelClass(String reason, String outtime, String intime,String status)
    {
        this.status=status;
        this.reason=reason;
        this.outtime=outtime;
        this.intime=intime;
    }
    public String getReason() {
        return reason;
    }

    public String getOuttime() {
        return outtime;
    }

    public String getIntime() {
        return intime;
    }
}
