package org.example;

import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
public class MongoUUID {

    private UUID uuid;

    public MongoUUID(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getUuid() {
        return uuid;
    }

    @Override
    public String toString() {
        return uuid.toString();
    }
}

