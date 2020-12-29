package com.hb0730.admin.commons.domain.model.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 基础DTO
 *
 * @author bing_huang
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString
public class BaseDTO extends DomainDTO {
    private static final long serialVersionUID = -887093251765431049L;
}
