package com.example.demo.util;

import org.springframework.core.convert.converter.Converter;
import org.springframework.core.serializer.support.DeserializingConverter;
import org.springframework.core.serializer.support.SerializingConverter;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

public class ObjectRedisSerializer implements RedisSerializer<Object> {

    private Converter<Object, byte[]> serializeConverter = new SerializingConverter();
    private Converter<byte[], Object> deSerializeConverter = new DeserializingConverter();


    @Override
    public byte[] serialize(Object o) throws SerializationException {
        if(o == null){
            return new byte[0];
        }else{
            return serializeConverter.convert(o);

        }
    }

    @Override
    public Object deserialize(byte[] bytes) throws SerializationException {
        if (bytes == null || bytes.length == 0) {
            return null;
        }else{
            return deSerializeConverter.convert(bytes);
        }
    }
}
