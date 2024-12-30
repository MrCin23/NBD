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
import org.example.consts.DBConsts;
import org.example.consts.VMConsts;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(defaultKeyspace = DBConsts.KEYSPACE)
@CqlName(VMConsts.TABLE_STRING)
@PropertyStrategy(getterStyle = GetterStyle.JAVABEANS)
@NamingStrategy(convention = NamingConvention.LOWER_CAMEL_CASE)
public class VMachine extends AbstractEntity {
    @CqlName(VMConsts.CPUNUMBER_STRING)
    private int CPUNumber;
    @CqlName(VMConsts.RAM_STRING)
    private String ramSize;
    @CqlName(VMConsts.RENTED_STRING)
    private boolean isRented;
    @CqlName(VMConsts.RENTALPRICE_STRING)
    protected float actualRentalPrice;
    @CqlName(VMConsts.DISCRIMINATOR_STRING)
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


