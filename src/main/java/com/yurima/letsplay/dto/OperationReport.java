package com.yurima.letsplay.dto;

import lombok.Data;

@Data
public class OperationReport {
    private int successful = 0;
    private int error = 0;

    public void success() {
        successful++;
    }

    public void error() {
        error++;
    }
}
