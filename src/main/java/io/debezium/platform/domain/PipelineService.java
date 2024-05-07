package io.debezium.platform.domain;


import com.blazebit.persistence.CriteriaBuilderFactory;
import com.blazebit.persistence.view.EntityViewManager;
import io.debezium.platform.data.model.PipelineEntity;
import io.debezium.platform.domain.views.Pipeline;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;

@ApplicationScoped
public class PipelineService extends AbstractService<PipelineEntity, Pipeline> {

    public PipelineService(EntityManager em, CriteriaBuilderFactory cbf, EntityViewManager evm) {
        super(PipelineEntity.class, Pipeline.class, em, cbf, evm);
    }
}
