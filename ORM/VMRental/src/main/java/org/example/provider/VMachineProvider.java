package org.example.provider;

import com.datastax.oss.driver.api.core.CqlIdentifier;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.PagingIterable;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.Row;
import com.datastax.oss.driver.api.mapper.MapperContext;
import com.datastax.oss.driver.api.mapper.entity.EntityHelper;
import com.datastax.oss.driver.api.querybuilder.QueryBuilder;
import com.datastax.oss.driver.api.querybuilder.relation.Relation;
import com.datastax.oss.driver.api.querybuilder.select.Select;
import com.datastax.oss.driver.api.querybuilder.update.Update;
import org.example.model.AppleArch;
import org.example.model.VMachine;
import org.example.model.x86;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static com.datastax.oss.driver.api.querybuilder.QueryBuilder.literal;

public class VMachineProvider {
    private final CqlSession session;

    private final EntityHelper<AppleArch> appleArchEntityHelper;
    private final EntityHelper<x86> x86EntityHelper;

    public VMachineProvider(MapperContext ctx, EntityHelper<AppleArch> appleArchEntityHelper,
                                   EntityHelper<x86> x86EntityHelper) {
        this.session = ctx.getSession();
        this.appleArchEntityHelper = appleArchEntityHelper;
        this.x86EntityHelper = x86EntityHelper;
    }

    public void create(VMachine vmachine) {
        session.execute(
            switch (vmachine.getDiscriminator()) {
                case "AppleArch" -> {
                    AppleArch appleArch = (AppleArch) vmachine;
                    yield session.prepare(appleArchEntityHelper.insert().build())
                            .bind()
                            .setUuid("uuid", appleArch.getUuid())
                            .setInt("CPUNumber", appleArch.getCPUNumber())
                            .setString("ramSize", appleArch.getRamSize())
                            .setBoolean("rented", appleArch.isRented())
                            .setString("discriminator", appleArch.getDiscriminator())
                            .setFloat("actualRentalPrice", appleArch.getActualRentalPrice());
                }
                case "x86" -> {
                    x86 x86 = (x86) vmachine;
                    yield session.prepare(x86EntityHelper.insert().build())
                            .bind()
                            .setUuid("uuid", x86.getUuid())
                            .setInt("CPUNumber", x86.getCPUNumber())
                            .setString("ramSize", x86.getRamSize())
                            .setBoolean("rented", x86.isRented())
                            .setString("cpumanufacturer", x86.getCPUManufacturer())
                            .setString("discriminator", x86.getDiscriminator())
                            .setFloat("actualRentalPrice", x86.getActualRentalPrice());
                }
                default -> throw new IllegalStateException("Unexpected value: " + vmachine.getDiscriminator());
            }
        );
    }

    public List<VMachine> getAll() {
        Select select = QueryBuilder
                .selectFrom(CqlIdentifier.fromCql("vmachines"))
                .all();
        ResultSet resultSet = session.execute(select.build());
        List<VMachine> vMachines = new ArrayList<>();
        for (Row row : resultSet) {
            switch (Objects.requireNonNull(row.getString("discriminator"))) {
                case "AppleArch" -> {
                    vMachines.add(new AppleArch(
                            row.getUuid("uuid"),
                            row.getInt("CPUNumber"),
                            row.getString("ramSize")
                    ));
                }
                case "x86" -> {
                    vMachines.add(new x86(
                            row.getUuid("uuid"),
                            row.getString("cpumanufacturer"),
                            row.getInt("CPUNumber"),
                            row.getString("ramSize")
                    ));
                }
                default -> throw new IllegalStateException("Unexpected value: " + row.getString("discriminator"));
            }
        }
        return vMachines;
    }

    public void update(VMachine vmachine) {
        session.execute(
            switch (vmachine.getDiscriminator()) {
                case "AppleArch" -> {
                    AppleArch appleArch = (AppleArch) vmachine;
                    yield session.prepare(QueryBuilder.update("vmachines")
                            .setColumn("ramSize", literal(appleArch.getRamSize()))
                            .setColumn("rented", literal(appleArch.isRented()))
                            .setColumn("discriminator", literal(appleArch.getDiscriminator()))
                            .setColumn("actualRentalPrice", literal(appleArch.getActualRentalPrice()))
                            .where(Relation.column("uuid").isEqualTo(literal(vmachine.getUuid())))
                            .where(Relation.column("CPUNumber").isEqualTo(literal(vmachine.getCPUNumber())))
                            .build()).bind();
                }
                case "x86" -> {
                    x86 x86 = (x86) vmachine;
                    yield session.prepare(QueryBuilder.update("vmachines")
                            .setColumn("ramSize", literal(x86.getRamSize()))
                            .setColumn("rented", literal(x86.isRented()))
                            .setColumn("cpumanufacturer", literal(x86.getCPUManufacturer()))
                            .setColumn("discriminator", literal(x86.getDiscriminator()))
                            .setColumn("actualRentalPrice", literal(x86.getActualRentalPrice()))
                            .where(Relation.column("uuid").isEqualTo(literal(vmachine.getUuid())))
                            .where(Relation.column("CPUNumber").isEqualTo(literal(vmachine.getCPUNumber())))
                            .build()).bind(); // XDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD co
                }
                default -> throw new IllegalStateException("Unexpected value: " + vmachine.getDiscriminator());
            }
        );
    }


    public VMachine findById(UUID uuid) {
        Select select = QueryBuilder
                .selectFrom(CqlIdentifier.fromCql("vmachines"))
                .all()
                .where(Relation.column("uuid").isEqualTo(literal(uuid)));
        Row row = session.execute(select.build()).one();
        assert row != null;
        String discriminator = row.getString("discriminator");
        assert discriminator != null;
        return switch (discriminator) {
            case "AppleArch" -> getAppleArch(row);
            case "x86" -> getx86(row);
            default -> throw new IllegalArgumentException("Illegal discriminator");
        };
    }

    private AppleArch getAppleArch(Row row) {
        return new AppleArch(
                row.getUuid("uuid"),
                row.getInt("CPUNumber"),
                row.getString("ramSize")
        );
    }

    private x86 getx86(Row row){
        return new x86(
                row.getUuid("uuid"),
                row.getString("cpumanufacturer"),
                row.getInt("CPUNumber"),
                row.getString("ramSize")
        );
    }
}
