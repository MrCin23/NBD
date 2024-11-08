package org.example;

import java.util.UUID;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode
public class MongoUUID {
    private UUID uuid;

    public MongoUUID(UUID uuid) {
        this.uuid = uuid;
    }

    @Override
    public String toString() {
        return uuid.toString();
    }
}
