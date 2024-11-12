package org.example;

import org.bson.codecs.Codec;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;

public class VMachineCodecProvider implements CodecProvider {
    public VMachineCodecProvider() {
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> Codec<T> get(Class<T> clazz, CodecRegistry registry) {
        if (clazz == VMachine.class) {
            return (Codec<T>) new VMachineCodec(registry);
        }
        // return null when there is no provider for the requested class
        return null;
    }
}
