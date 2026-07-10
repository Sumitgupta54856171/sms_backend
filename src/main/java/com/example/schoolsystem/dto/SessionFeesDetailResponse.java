package com.example.schoolsystem.dto;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SessionFeesDetailResponse {
    private Long totalfees;
    private Long totaldue;
    private Long totalpaid;
    private String sessionName;
    private Long paymentsNo;

    public SessionFeesDetailResponse(Long totalfees,Long totaldue,Long totalpaid,String sessionName,Long paymentsNo){
        this.totalfees=totalfees;
        this.totaldue=totaldue;
        this.totalpaid=totalpaid;
        this.sessionName=sessionName;
        this.paymentsNo=paymentsNo;
    }

}
