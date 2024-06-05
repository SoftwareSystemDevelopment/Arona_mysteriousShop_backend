package com.software_system_development.arona_mysterious_shop_backend.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName propertyvalue
 */
@TableName(value ="propertyvalue")
@Data
public class Propertyvalue implements Serializable {
    /**
     * 属性值ID
     */
    @TableId(type = IdType.AUTO)
    private Integer propertyvalueid;

    /**
     * 属性值名称
     */
    private String propertyvaluevalue;

    /**
     * 属性值对应属性ID
     */
    private Integer propertyvaluepropertyid;

    /**
     * 属性值对应商品ID
     */
    private Integer propertyvalueproductid;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}