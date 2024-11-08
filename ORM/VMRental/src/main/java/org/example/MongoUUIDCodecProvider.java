package org.example;

import lombok.NoArgsConstructor;
import org.bson.codecs.Codec;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;

@NoArgsConstructor
public class MongoUUIDCodecProvider implements CodecProvider {
    @Override
    public <T> Codec<T> get(Class<T> aClass, CodecRegistry codecRegistry) {
        if (aClass == MongoUUID.class) {
            return (Codec<T>) new MongoUUIDCodec(codecRegistry);
        }
        return null;
    }
}