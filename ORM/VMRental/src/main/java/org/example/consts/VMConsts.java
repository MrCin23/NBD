package org.example.consts;

import com.datastax.oss.driver.api.core.CqlIdentifier;

public class VMConsts {
    private VMConsts() {}
    public static final String TABLE_STRING = "vmachines";
    public static final CqlIdentifier TABLE = CqlIdentifier.fromCql("vmachines");
    public static final String UUID_STRING = "uuid";
    public static final CqlIdentifier UUID = CqlIdentifier.fromCql("uuid");
    public static final String CPUNUMBER_STRING = "CPUNumber";
    public static final CqlIdentifier CPUNUMBER = CqlIdentifier.fromCql("CPUNumber");
    public static final String RAM_STRING = "ramSize";
    public static final CqlIdentifier RAM = CqlIdentifier.fromCql("ramSize");
    public static final String RENTED_STRING = "rented";
    public static final CqlIdentifier RENTED = CqlIdentifier.fromCql("rented");
    public static final String DISCRIMINATOR_STRING = "discriminator";
    public static final CqlIdentifier DISCRIMINATOR = CqlIdentifier.fromCql("discriminator");
    public static final String MANUFACTURER_STRING = "cpumanufacturer";
    public static final CqlIdentifier MANUFACTURER = CqlIdentifier.fromCql("cpumanufacturer");
    public static final String RENTALPRICE_STRING = "actualRentalPrice";
    public static final CqlIdentifier RENTALPRICE = CqlIdentifier.fromCql("actualRentalPrice");
}
