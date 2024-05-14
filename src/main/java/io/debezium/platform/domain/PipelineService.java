package io.debezium.platform.domain;


import com.blazebit.persistence.CriteriaBuilderFactory;
import com.blazebit.persistence.view.EntityViewManager;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.debezium.outbox.quarkus.ExportedEvent;
import io.debezium.platform.data.model.PipelineEntity;
import io.debezium.platform.domain.views.Pipeline;
import io.debezium.platform.domain.views.flat.PipelineFlat;
import io.debezium.platform.domain.views.refs.PipelineReference;
import io.debezium.platform.environment.watcher.events.PipelineEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

@ApplicationScoped
public class PipelineService extends AbstractService<PipelineEntity, Pipeline, PipelineReference> {

    @Inject
    Event<ExportedEvent<?, ?>> event;

    @Inject
    ObjectMapper objectMapper;

    public PipelineService(EntityManager em, CriteriaBuilderFactory cbf, EntityViewManager evm) {
        super(PipelineEntity.class, Pipeline.class, PipelineReference.class, em, cbf, evm);
    }


    @Override
    protected void onChange(Pipeline view) {
        var flat =findByIdAs(PipelineFlat.class, view.getId()).orElseThrow();
        event.fire(PipelineEvent.update(flat, objectMapper));
    }

    @Override
    protected void onChange(Long id) {
        event.fire(PipelineEvent.delete(id));
    }
}
