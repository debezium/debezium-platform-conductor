package io.debezium.platform.domain;


import com.blazebit.persistence.CriteriaBuilderFactory;
import com.blazebit.persistence.view.EntityViewManager;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.debezium.outbox.quarkus.ExportedEvent;
import io.debezium.platform.data.model.PipelineEntity;
import io.debezium.platform.domain.views.Pipeline;
import io.debezium.platform.domain.views.flat.PipelineFlat;
import io.debezium.platform.environment.watcher.events.PipelineEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import java.util.Optional;

@ApplicationScoped
public class PipelineService extends AbstractService<PipelineEntity, Pipeline> {

    @Inject
    Event<ExportedEvent<?, ?>> event;

    @Inject
    ObjectMapper objectMapper;

    public PipelineService(EntityManager em, CriteriaBuilderFactory cbf, EntityViewManager evm) {
        super(PipelineEntity.class, Pipeline.class, em, cbf, evm);
    }

    @Transactional(Transactional.TxType.SUPPORTS)
    public Optional<PipelineFlat> findByIdFlat(Long id) {
        var result = evm.find(em, PipelineFlat.class, id);
        return Optional.ofNullable(result);
    }

    @Override
    public Pipeline create(Pipeline view) {
        var result = super.create(view);
        fireUpdateEvent(result.getId());
        return result;
    }

    @Override
    public Pipeline update(Pipeline view) {
        var result = super.update(view);
        fireUpdateEvent(result.getId());
        return result;
    }

    @Override
    public void delete(long id) {
        super.delete(id);
        fireDeleteEvent(id);
    }

    private void fireUpdateEvent(Long id) {
        var flat = findByIdFlat(id);
        flat.ifPresent(pipeline -> event.fire(PipelineEvent.update(pipeline, objectMapper)));
    }

    private void fireDeleteEvent(Long id) {
        event.fire(PipelineEvent.delete(id));
    }

}
