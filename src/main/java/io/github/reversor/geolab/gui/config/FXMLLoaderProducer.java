package io.github.reversor.geolab.gui.config;

import javafx.fxml.FXMLLoader;

import javax.enterprise.inject.Instance;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

public class FXMLLoaderProducer {

    private Instance<Object> instance;

    @Inject
    public FXMLLoaderProducer(Instance<Object> instance) {
        this.instance = instance;
    }

    @Produces
    public FXMLLoader createLoader() {
        FXMLLoader loader = new FXMLLoader();
        loader.setControllerFactory(param -> instance.select(param).get());

        return loader;
    }


}
