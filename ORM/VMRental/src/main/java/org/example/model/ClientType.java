package org.example.model;

import com.datastax.oss.driver.api.core.type.DataType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class ClientType {

    private long id;

    protected int maxRentedMachines;

    protected String name;

    public ClientType() {
        this.name = getClass().getSimpleName();
    }

    public abstract String toString();

    public static ClientType fromString(String type) {
        return switch (type) {
            case "Admin" -> new Admin();
            case "Standard" -> new Standard();
            default -> throw new IllegalArgumentException("Unknown type: " + type);
        };
    }
}
