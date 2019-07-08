package com.leyou.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum ExceptionEnum {

    PRICE_CANNOT_BE_NULL(400,"价格不能为空"),
    CATEGORY_NOT_FOUND(404,"商品分类未查到"),
    INVALID_USER_DATA_TYPE(400,"用户数据类型无效"),
    INVALID_VERIFY_CODE(400,"无效的验证码")
    ;
    private int code;
    private String msg;
}
