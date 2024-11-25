package org.example.codec;

import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.bson.codecs.configuration.CodecRegistry;
import org.example.model.Admin;
import org.example.model.ClientType;
import org.example.model.Standard;

public class ClientTypeCodec implements Codec<ClientType> {
    private final CodecRegistry codecRegistry;

    public ClientTypeCodec(CodecRegistry codecRegistry) {
        this.codecRegistry = codecRegistry;
    }

    @Override
    public ClientType decode(BsonReader bsonReader, DecoderContext decoderContext) {
        bsonReader.readStartDocument();
        String type = bsonReader.readString();
        int maxRentedMachines = bsonReader.readInt32();
        String clientType = bsonReader.readString();
        bsonReader.readEndDocument();

        if(type.equals("standard")) {
            return new Standard();
        }
        else if(type.equals("admin")) {
            return new Admin();
        }
        else {
            throw new RuntimeException("Unsupported type: " + type);
        }
    }

    @Override
    public void encode(BsonWriter bsonWriter, ClientType clientType, EncoderContext encoderContext) {
        bsonWriter.writeStartDocument();
        bsonWriter.writeString("_clazz", clientType.getClass().getSimpleName().toLowerCase());
        bsonWriter.writeInt32("maxRentedMachines" ,clientType.getMaxRentedMachines());
        bsonWriter.writeString("name", clientType.getName());
        bsonWriter.writeEndDocument();
    }

    @Override
    public Class<ClientType> getEncoderClass() {
        return ClientType.class;
    }
}
