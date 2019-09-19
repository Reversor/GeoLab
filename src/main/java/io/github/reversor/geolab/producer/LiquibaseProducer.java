package io.github.reversor.geolab.producer;

import java.sql.SQLException;
import java.util.Map;
import javax.annotation.Resource;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.sql.DataSource;
import liquibase.integration.cdi.CDILiquibaseConfig;
import liquibase.integration.cdi.annotations.LiquibaseType;
import liquibase.resource.ClassLoaderResourceAccessor;
import liquibase.resource.ResourceAccessor;

public class LiquibaseProducer {

    private Map<String, String> params;

    private DataSource myDataSource;

    @Inject
    public LiquibaseProducer(Map<String, String> params) {
        this.params = params;
    }

    @Resource
    public void setMyDataSource(DataSource myDataSource) {
        this.myDataSource = myDataSource;
    }

    @Produces
    @LiquibaseType
    public CDILiquibaseConfig createConfig() {
        CDILiquibaseConfig config = new CDILiquibaseConfig();
        config.setContexts("init");
        config.setParameters(params);
        config.setChangeLog("db.changelog-master.xml");
        return config;
    }

    @Produces
    @LiquibaseType
    public DataSource createDataSource() throws SQLException {
        return myDataSource;
    }

    @Produces
    @LiquibaseType
    public ResourceAccessor create() {
        return new ClassLoaderResourceAccessor(getClass().getClassLoader());
    }

}
