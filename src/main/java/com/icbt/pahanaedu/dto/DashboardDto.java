package com.icbt.pahanaedu.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

@Getter
@Setter
public class DashboardDto {
    private Long userId;
    private HashMap<String, Double> incomes;
    private HashMap<String, Long> OrderCount;
    private HashMap<String, Double> popularBookStock;
    private HashMap<String, Long> membershipCount;
}
