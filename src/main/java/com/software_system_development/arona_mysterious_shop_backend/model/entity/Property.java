package com.software_system_development.arona_mysterious_shop_backend.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName property
 */
@TableName(value ="property")
@Data
public class Property implements Serializable {
    /**
     * 属性ID
     */
    @TableId(type = IdType.AUTO)
    private Integer propertyid;

    /**
     * 属性名称
     */
    private String propertyname;

    /**
     * 属性所属分类ID
     */
    private Integer propertycategoryid;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}