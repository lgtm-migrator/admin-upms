package com.hb0730.admin.commons.domain.model.query;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * 基础过滤参数
 *
 * @author bing_huang
 */
@Data
@ToString
@EqualsAndHashCode
public class BaseParams implements Serializable {
    private static final long serialVersionUID = 7979701743784191515L;
    /**
     * 排序
     * DESC
     * asc
     */
    private String sortType;
    /**
     * 排序字段
     * 表名称
     */
    private List<String> sortColumn;

    /**
     * 分组字段
     */
    private List<String> groupColumn;

    /**
     * 页数
     */
    private Long pageNum = 1L;

    /**
     * 数量
     */
    private Long pageSize = 10L;
}
