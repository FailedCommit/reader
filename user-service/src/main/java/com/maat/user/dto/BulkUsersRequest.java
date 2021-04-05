package com.maat.user.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.data.domain.Sort;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BulkUsersRequest {
    private int offset;
    private int rows;
    private Sort sort;
}
