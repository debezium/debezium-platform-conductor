package io.debezium.platform.environment.operator;

import io.debezium.operator.api.model.ConfigProperties;
import io.debezium.operator.api.model.DebeziumServer;
import io.debezium.operator.api.model.DebeziumServerBuilder;
import io.debezium.operator.api.model.DebeziumServerSpecBuilder;
import io.debezium.operator.api.model.QuarkusBuilder;
import io.debezium.operator.api.model.SinkBuilder;
import io.debezium.operator.api.model.SourceBuilder;
import io.debezium.platform.domain.views.flat.PipelineFlat;
import io.debezium.platform.environment.PipelineController;
import io.fabric8.kubernetes.api.model.ObjectMetaBuilder;
import io.fabric8.kubernetes.client.KubernetesClient;
import jakarta.enterprise.context.Dependent;

import java.util.Map;


@Dependent
public class OperatorPipelineController implements PipelineController {

    public static final String LABEL_DBZ_CONDUCTOR_ID = "debezium.io/conductor-id";

    private final KubernetesClient k8s;

    public OperatorPipelineController(KubernetesClient k8s) {
        this.k8s = k8s;
    }

    @Override
    public void deploy(PipelineFlat pipeline) {
        // Create DS quarkus configuration
        var quarkusConfig = new ConfigProperties();
        quarkusConfig.setProps("log.level", pipeline.getLogLevel());
        var dsQuarkus = new QuarkusBuilder()
                .withConfig(quarkusConfig)
                .build();

        // Create DS source configuration
        var source = pipeline.getSource();
        var sourceConfig = new ConfigProperties();
        sourceConfig.setAllProps(source.getConfig());

        var dsSource = new SourceBuilder()
                .withSourceClass(source.getType())
                .withConfig(sourceConfig)
                .build();

        // Create DS sink configuration
        var sink = pipeline.getDestination();
        var sinkConfig = new ConfigProperties();
        sinkConfig.setAllProps(sink.getConfig());

        var dsSink = new SinkBuilder()
                .withType(sink.getType())
                .withConfig(sinkConfig)
                .build();

        // Create DS resource
        var ds = new DebeziumServerBuilder()
                .withMetadata(new ObjectMetaBuilder()
                        .withName(pipeline.getName())
                        .withLabels(Map.of(LABEL_DBZ_CONDUCTOR_ID, pipeline.getId().toString()))
                        .build())
                .withSpec(new DebeziumServerSpecBuilder()
                        .withQuarkus(dsQuarkus)
                        .withSource(dsSource)
                        .withSink(dsSink)
                        .build())
                .build();

        // apply to server
        k8s.resource(ds).serverSideApply();
    }

    @Override
    public void undeploy(Long id) {
        k8s.resources(DebeziumServer.class)
                .withLabels(Map.of(LABEL_DBZ_CONDUCTOR_ID, id.toString()))
                .delete();
    }
}
