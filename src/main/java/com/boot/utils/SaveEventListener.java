package com.boot.utils;

import java.lang.reflect.Field;

import com.boot.pojo.MediaFile;
import com.boot.pojo.SeqInfo;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;


/**
 * @author lm
 * @version 1.8
 * @date 2021/7/19 15:26
 */
@Component
public class SaveEventListener extends AbstractMongoEventListener<Object> {
    @Autowired
    private MongoTemplate mongoTemplate;
    @Override
    public void onBeforeConvert(BeforeConvertEvent<Object> event) {
        final Object source = event.getSource();
        if (source != null) {
            ReflectionUtils.doWithFields(source.getClass(),new ReflectionUtils.FieldCallback(){
                @Override
                public void doWith(Field field)throws IllegalArgumentException,
                IllegalAccessException {
                    //将一个字段设置为可读写，主要针对private字段；
                    ReflectionUtils.makeAccessible(field);
                    // 如果字段添加了我们自定义的AutoValue注解
                    if (field.isAnnotationPresent(AutoInckey.class)
                            && field.get(source) instanceof Number
                            && field.getLong(source) == 0) {
                        // 设置自增ID
                        field.set(source, getNextAutoId(source.getClass().getSimpleName()));
                    }
                }
            });
        }
    }
    /**
     * 获取下一个自增ID
     *
     * @param collName
     *            集合（这里用类名，就唯一性来说最好还是存放长类名）名称
     * @return 序列值
     */
    private Long getNextAutoId(String collName) {
        Query query = new Query(Criteria.where("collName").is(collName));
        Update update = new Update();
        update.inc("seqId", 1);
        FindAndModifyOptions options = new FindAndModifyOptions();
        options.upsert(true);
        options.returnNew(true);
        SeqInfo seq = mongoTemplate.findAndModify(query, update, options, SeqInfo.class);
        return seq.getSeqId();
    }

}
