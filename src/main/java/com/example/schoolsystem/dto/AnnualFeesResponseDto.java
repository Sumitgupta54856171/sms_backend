package com.example.schoolsystem.dto;


import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@NoArgsConstructor
public class AnnualFeesResponseDto {

    private Long feesAmount;

    public AnnualFeesResponseDto(Long feesAmount) {
        this.feesAmount = feesAmount;
    }




}
