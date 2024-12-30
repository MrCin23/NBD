package org.example.model;

import com.datastax.oss.driver.api.mapper.annotations.CqlName;
import com.datastax.oss.driver.api.mapper.annotations.Entity;
import com.datastax.oss.driver.api.mapper.annotations.NamingStrategy;
import com.datastax.oss.driver.api.mapper.annotations.PropertyStrategy;
import com.datastax.oss.driver.api.mapper.entity.naming.GetterStyle;
import com.datastax.oss.driver.api.mapper.entity.naming.NamingConvention;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(defaultKeyspace = "vmrental")
@CqlName("vmachines")
@PropertyStrategy(getterStyle = GetterStyle.JAVABEANS)
@NamingStrategy(convention = NamingConvention.LOWER_CAMEL_CASE)
public class VMachine extends AbstractEntity {
    @CqlName("CPUNumber")
    private int CPUNumber;
    @CqlName("ramSize")
    private String ramSize;
    @CqlName("rented")
    private boolean isRented;
    @CqlName("actualRentalPrice")
    protected float actualRentalPrice;
    private String discriminator;

    public VMachine(int CPUNumber, String ramSize, boolean isRented, String discriminator) {
        super(UUID.randomUUID());
        this.CPUNumber = CPUNumber;
        this.ramSize = ramSize;
        this.isRented = isRented;
        this.discriminator = discriminator;
    }

    public VMachine(UUID uuid ,int CPUNumber, String ramSize, boolean isRented, String discriminator) {
        super(uuid);
        this.CPUNumber = CPUNumber;
        this.ramSize = ramSize;
        this.isRented = isRented;
        this.discriminator = discriminator;
    }

    @Override
    public String toString() {
        return "VMachine{" +
                "CPUNumber=" + CPUNumber +
                ", ramSize='" + ramSize + '\'' +
                ", isRented=" + isRented +
                ", actualRentalPrice=" + actualRentalPrice +
                ", discriminator='" + discriminator + '\'' +
                '}';
    }
};


