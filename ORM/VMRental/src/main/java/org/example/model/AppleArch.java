package org.example.model;

import com.datastax.oss.driver.api.mapper.annotations.CqlName;
import com.datastax.oss.driver.api.mapper.annotations.Entity;
import com.datastax.oss.driver.api.mapper.annotations.NamingStrategy;
import com.datastax.oss.driver.api.mapper.entity.naming.NamingConvention;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@Entity(defaultKeyspace = "vmrental")
@CqlName("vmachines")
@NamingStrategy(convention = NamingConvention.LOWER_CAMEL_CASE)
public class AppleArch extends VMachine{
    public AppleArch(int CPUNumber, String ramSize) {
        super(CPUNumber, ramSize, false, "AppleArch");
        this.actualRentalPrice = getActualRentalPrice();
    }
    public AppleArch(UUID uuid, int CPUNumber, String ramSize) {
        super(uuid, CPUNumber, ramSize, false, "AppleArch");
        this.actualRentalPrice = getActualRentalPrice();
    }

    @Override
    public float getActualRentalPrice() {
        float basePrice = 20;

        int ramInGB = Integer.parseInt(getRamSize().replaceAll("[^0-9]", ""));

        if (ramInGB > 4) {
            int timesDoubled = (int) (Math.log(ramInGB / 4) / Math.log(2));
            for (int i = 0; i < timesDoubled; i++) {
                basePrice += basePrice / 2;
            }
        }
        float threadMultiplier = getCPUNumber() / 2.0f;

        return 10 * basePrice * threadMultiplier;
    }

    @Override
    public String toString() {
        return super.toString()+"AppleArch{}";
    }
}
