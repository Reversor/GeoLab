package io.github.reversor.geolab.producer;

import java.sql.SQLException;
import java.util.Map;
import javax.annotation.Resource;
import javax.enterprise.inject.Produces;
import javax.sql.DataSource;
import liquibase.integration.cdi.CDILiquibaseConfig;
import liquibase.integration.cdi.annotations.LiquibaseType;
import liquibase.resource.ClassLoaderResourceAccessor;
import liquibase.resource.ResourceAccessor;
import org.postgresql.ds.PGSimpleDataSource;

public class LiquibaseProducer {

    @Resource
    private DataSource dataSource;

    @Produces
    @LiquibaseType
    public CDILiquibaseConfig createConfig() {
        CDILiquibaseConfig config = new CDILiquibaseConfig();
        config.setContexts("init");
        //FIXME Parameters doesn't work
        config.setParameters(Map.ofEntries(
                Map.entry("database.databaseChangeLogLockTableName", "changelog_geolab_lock"),
                Map.entry("database.databaseChangeLogTableName", "changelog_geolab")
        ));
        config.setChangeLog("db.changelog-master.xml");
        return config;
    }

    @Produces
    @LiquibaseType
    public DataSource createDataSource() throws SQLException {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setURL("jdbc:postgresql://localhost:5432/postgres");
        dataSource.setUser("postgres");
        dataSource.setPassword("postgres");
        this.dataSource = dataSource;
        return dataSource;
    }

    @Produces
    @LiquibaseType
    public ResourceAccessor create() {
        return new ClassLoaderResourceAccessor(getClass().getClassLoader());
    }

}
