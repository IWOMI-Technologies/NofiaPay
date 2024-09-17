package com.iwomi.nofiaPay.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CombineHistory {

    private List<Map<String, Object>> transactionHistory;

    private List<Map<String, Object>> accountHistory;


}
