package com.coldlake.app.payment.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.coldlake.app.payment.utils.TimeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    /**
     * 插入时的填充策略
     * @param metaObject
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        Long now = TimeUtils.getCurrentTimeMils();
        this.setFieldValByName("createTime", now, metaObject);
        this.setFieldValByName("updateTime", now, metaObject);
    }

    /**
     * 更新时的填充策略
     * @param metaObject
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("updateTime", TimeUtils.getCurrentTimeMils(), metaObject);
    }
}

