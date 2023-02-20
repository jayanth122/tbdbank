package org.ece.dto;

import lombok.Data;

import java.time.LocalDate;
@Data
public class StatementRequest {

    private String sessioinId;

    private LocalDate fromDate;

    private LocalDate toDate;

}
