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
import org.example.consts.VMConsts;
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
                            .setUuid(VMConsts.UUID_STRING, appleArch.getUuid())
                            .setInt(VMConsts.CPUNUMBER_STRING, appleArch.getCPUNumber())
                            .setString(VMConsts.RAM_STRING, appleArch.getRamSize())
                            .setBoolean(VMConsts.RENTED_STRING, appleArch.isRented())
                            .setString(VMConsts.DISCRIMINATOR, appleArch.getDiscriminator())
                            .setFloat(VMConsts.RENTALPRICE_STRING, appleArch.getActualRentalPrice());
                }
                case "x86" -> {
                    x86 x86 = (x86) vmachine;
                    yield session.prepare(x86EntityHelper.insert().build())
                            .bind()
                            .setUuid(VMConsts.UUID_STRING, x86.getUuid())
                            .setInt(VMConsts.CPUNUMBER_STRING, x86.getCPUNumber())
                            .setString(VMConsts.RAM_STRING, x86.getRamSize())
                            .setBoolean(VMConsts.RENTED_STRING, x86.isRented())
                            .setString(VMConsts.MANUFACTURER_STRING, x86.getCPUManufacturer())
                            .setString(VMConsts.DISCRIMINATOR, x86.getDiscriminator())
                            .setFloat(VMConsts.RENTALPRICE_STRING, x86.getActualRentalPrice());
                }
                default -> throw new IllegalStateException("Unexpected value: " + vmachine.getDiscriminator());
            }
        );
    }

    public List<VMachine> getAll() {
        Select select = QueryBuilder
                .selectFrom(VMConsts.TABLE_STRING)
                .all();
        ResultSet resultSet = session.execute(select.build());
        List<VMachine> vMachines = new ArrayList<>();
        for (Row row : resultSet) {
            switch (Objects.requireNonNull(row.getString(VMConsts.DISCRIMINATOR))) {
                case "AppleArch" -> {
                    vMachines.add(new AppleArch(
                            row.getUuid(VMConsts.UUID_STRING),
                            row.getInt(VMConsts.CPUNUMBER_STRING),
                            row.getString(VMConsts.RAM_STRING)
                    ));
                }
                case "x86" -> {
                    vMachines.add(new x86(
                            row.getUuid(VMConsts.UUID_STRING),
                            row.getString(VMConsts.MANUFACTURER_STRING),
                            row.getInt(VMConsts.CPUNUMBER_STRING),
                            row.getString(VMConsts.RAM_STRING)
                    ));
                }
                default -> throw new IllegalStateException("Unexpected value: " + row.getString(VMConsts.DISCRIMINATOR));
            }
        }
        return vMachines;
    }

    public void update(VMachine vmachine) {
        session.execute(
            switch (vmachine.getDiscriminator()) {
                case "AppleArch" -> {
                    AppleArch appleArch = (AppleArch) vmachine;
                    yield session.prepare(QueryBuilder.update(VMConsts.TABLE_STRING)
                            .setColumn(VMConsts.RAM_STRING, literal(appleArch.getRamSize()))
                            .setColumn(VMConsts.RENTED_STRING, literal(appleArch.isRented()))
                            .setColumn(VMConsts.DISCRIMINATOR, literal(appleArch.getDiscriminator()))
                            .setColumn(VMConsts.RENTALPRICE_STRING, literal(appleArch.getActualRentalPrice()))
                            .where(Relation.column(VMConsts.UUID_STRING).isEqualTo(literal(vmachine.getUuid())))
                            .where(Relation.column(VMConsts.CPUNUMBER_STRING).isEqualTo(literal(vmachine.getCPUNumber())))
                            .build()).bind();
                }
                case "x86" -> {
                    x86 x86 = (x86) vmachine;
                    yield session.prepare(QueryBuilder.update(VMConsts.TABLE_STRING)
                            .setColumn(VMConsts.RAM_STRING, literal(x86.getRamSize()))
                            .setColumn(VMConsts.RENTED_STRING, literal(x86.isRented()))
                            .setColumn(VMConsts.MANUFACTURER_STRING, literal(x86.getCPUManufacturer()))
                            .setColumn(VMConsts.DISCRIMINATOR, literal(x86.getDiscriminator()))
                            .setColumn(VMConsts.RENTALPRICE_STRING, literal(x86.getActualRentalPrice()))
                            .where(Relation.column(VMConsts.UUID_STRING).isEqualTo(literal(vmachine.getUuid())))
                            .where(Relation.column(VMConsts.CPUNUMBER_STRING).isEqualTo(literal(vmachine.getCPUNumber())))
                            .build()).bind(); // XDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD co
                }
                default -> throw new IllegalStateException("Unexpected value: " + vmachine.getDiscriminator());
            }
        );
    }


    public VMachine findById(UUID uuid) {
        Select select = QueryBuilder
                .selectFrom(VMConsts.TABLE)
                .all()
                .where(Relation.column(VMConsts.UUID_STRING).isEqualTo(literal(uuid)));
        Row row = session.execute(select.build()).one();
        assert row != null;
        String discriminator = row.getString(VMConsts.DISCRIMINATOR);
        assert discriminator != null;
        return switch (discriminator) {
            case "AppleArch" -> getAppleArch(row);
            case "x86" -> getx86(row);
            default -> throw new IllegalArgumentException("Illegal discriminator");
        };
    }

    private AppleArch getAppleArch(Row row) {
        return new AppleArch(
                row.getUuid(VMConsts.UUID_STRING),
                row.getInt(VMConsts.CPUNUMBER_STRING),
                row.getString(VMConsts.RAM_STRING)
        );
    }

    private x86 getx86(Row row){
        return new x86(
                row.getUuid(VMConsts.UUID_STRING),
                row.getString(VMConsts.MANUFACTURER_STRING),
                row.getInt(VMConsts.CPUNUMBER_STRING),
                row.getString(VMConsts.RAM_STRING)
        );
    }
}
