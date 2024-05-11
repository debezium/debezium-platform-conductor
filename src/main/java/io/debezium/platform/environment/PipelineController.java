package io.debezium.platform.environment;

import io.debezium.platform.domain.views.flat.PipelineFlat;

public interface PipelineController {

    void deploy(PipelineFlat pipeline);

    void undeploy(Long id);

}
