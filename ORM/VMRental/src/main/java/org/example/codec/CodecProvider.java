package org.example.codec;

import org.bson.codecs.Codec;
import org.bson.codecs.configuration.CodecRegistry;
import org.example.model.ClientType;
import org.example.model.MongoUUID;
import org.example.model.VMachine;

public class CodecProvider implements org.bson.codecs.configuration.CodecProvider {
    public CodecProvider() {
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> Codec<T> get(Class<T> clazz, CodecRegistry registry) {
        if (clazz == ClientType.class) {
            return (Codec<T>) new ClientTypeCodec(registry);
        }
        if (clazz == VMachine.class) {
            return (Codec<T>) new VMachineCodec(registry);
        }
        if (clazz == MongoUUID.class) {
            return (Codec<T>) new MongoUUIDCodec(registry);
        }
        // return null when there is no provider for the requested class
        return null;
    }
}
