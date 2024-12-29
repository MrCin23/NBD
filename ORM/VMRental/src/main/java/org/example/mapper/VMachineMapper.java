package org.example.mapper;

import com.datastax.oss.driver.api.mapper.annotations.DaoFactory;
import com.datastax.oss.driver.api.mapper.annotations.DaoKeyspace;
import com.datastax.oss.driver.api.mapper.annotations.DaoTable;
import com.datastax.oss.driver.api.mapper.annotations.Mapper;
import org.example.dao.VMachineDao;

@Mapper
public interface VMachineMapper {
    @DaoFactory
    VMachineDao vMachineDao(@DaoKeyspace String keyspace, @DaoTable String table);

    @DaoFactory
    VMachineDao vMachineDao();
}
