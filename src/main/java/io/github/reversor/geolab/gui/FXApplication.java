package io.github.reversor.geolab.gui;

import io.github.reversor.geolab.gui.config.StartupScene;
import javafx.application.Application;
import javafx.stage.Stage;

import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.util.AnnotationLiteral;
import javax.inject.Inject;

public class FXApplication extends Application {

    BeanManager beanManager;

    @Inject
    public FXApplication(BeanManager beanManager) {
        this.beanManager = beanManager;
    }

    @Override
    public void start(Stage stage) throws Exception {
        beanManager.getEvent().select(new AnnotationLiteral<StartupScene>() {
        }).fire(stage);
    }
}
