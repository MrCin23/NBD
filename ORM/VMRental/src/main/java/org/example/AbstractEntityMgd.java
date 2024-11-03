package org.example;

import org.bson.codecs.pojo.annotations.BsonProperty;

import java.io.Serializable;

public abstract class AbstractEntityMgd implements Serializable {
    @BsonProperty("_id")
    private final MongoUUID entityId;
    public MongoUUID getEntityId() { return entityId; }
    public AbstractEntityMgd(MongoUUID entityId) { this.entityId = entityId; }

}