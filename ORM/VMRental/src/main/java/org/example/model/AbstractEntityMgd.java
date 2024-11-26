package org.example.model;

import jakarta.json.bind.annotation.JsonbProperty;
import lombok.Getter;
import lombok.Setter;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonProperty;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
public abstract class AbstractEntityMgd implements Serializable {
    @BsonProperty("_id")
    @JsonbProperty("_id")
    @BsonId
    private final MongoUUID entityId;

    public AbstractEntityMgd() {
//        this.entityId = new MongoUUID(UUID.randomUUID());
        this.entityId = new MongoUUID();
    }

    @BsonCreator
    public AbstractEntityMgd(@BsonProperty MongoUUID entityId) {
        this.entityId = entityId;
    }
}