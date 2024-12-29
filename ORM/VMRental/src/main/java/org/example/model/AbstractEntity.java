package org.example.model;

import com.datastax.oss.driver.api.mapper.annotations.PartitionKey;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class AbstractEntity implements Serializable {
    @PartitionKey
    private UUID uuid;

    public AbstractEntity() {
        uuid = UUID.randomUUID();
    }
}
