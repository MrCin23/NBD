package org.example.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.json.bind.annotation.JsonbCreator;
import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.json.bind.annotation.JsonbSubtype;
import jakarta.json.bind.annotation.JsonbTypeInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.bson.codecs.pojo.annotations.BsonProperty;

@Getter
@Setter
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "_clazz"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Standard.class, name = "standard"),
        @JsonSubTypes.Type(value = Admin.class, name = "admin")
})
@NoArgsConstructor
@BsonDiscriminator(key="_clazz")
@JsonbTypeInfo(key = "_clazz", value = {@JsonbSubtype(alias = "admin", type = Admin.class), @JsonbSubtype(alias = "standard", type = Standard.class)})
public abstract class ClientType extends AbstractEntityMgd {

    @BsonProperty("maxRentedMachines")
    protected int maxRentedMachines;

    @BsonProperty("name")
    protected String name;


    @BsonCreator
    @JsonbCreator
    public ClientType(@BsonProperty("_id") @JsonbProperty("_id") MongoUUID uuid,
                      @BsonProperty("maxRentedMachines") @JsonbProperty("maxRentedMachines") int maxRentedMachines,
                      @BsonProperty("name") @JsonbProperty("name") String name){
        super(uuid);
        this.maxRentedMachines = maxRentedMachines;
        this.name = name;
    }

    public String toString() {
        return " " + this.getMaxRentedMachines() + ", UUID: " + super.getEntityId().getUuid().toString();
    }
}
