package io.debezium.platform.environment.operator;

import io.debezium.platform.environment.watcher.config.WatcherConfigGroup;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.quarkus.arc.profile.IfBuildProfile;
import io.quarkus.runtime.Startup;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;

import java.net.MalformedURLException;
import java.net.URI;

@Startup
@ApplicationScoped
@IfBuildProfile("dev")
public class DevCrdInstaller {

    Logger logger;
    KubernetesClient k8s;
    WatcherConfigGroup watcherConfig;

    public DevCrdInstaller(Logger logger, KubernetesClient k8s, WatcherConfigGroup watcherConfig) {
        this.logger = logger;
        this.k8s = k8s;
        this.watcherConfig = watcherConfig;
    }

    @PostConstruct
    public void init() {
        watcherConfig.crd().ifPresent(this::install);
    }

    public void install(String crdUrl) {
        try {
            var url = URI.create(crdUrl).toURL();
            var crds = k8s.apiextensions().v1().customResourceDefinitions();
            var crd = crds.load(url).item();

            crds.resource(crd).serverSideApply();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
}
