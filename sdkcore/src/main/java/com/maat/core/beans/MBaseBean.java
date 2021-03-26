package com.maat.core.beans;

import lombok.Data;

@Data
public class MBaseBean {
    private Long createdTime;
    private Long modifiedTime;
    /** To support soft deletes */
    private boolean deleted;
}
