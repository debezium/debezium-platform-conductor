package io.debezium.platform.domain;


import com.blazebit.persistence.CriteriaBuilderFactory;
import com.blazebit.persistence.view.EntityViewManager;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.debezium.outbox.quarkus.ExportedEvent;
import io.debezium.platform.data.model.VaultEntity;
import io.debezium.platform.domain.views.Vault;
import io.debezium.platform.domain.views.refs.VaultReference;
import io.debezium.platform.environment.watcher.events.VaultEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import java.util.Optional;

import static jakarta.transaction.Transactional.TxType.SUPPORTS;


@ApplicationScoped
public class VaultService extends AbstractService<VaultEntity, Vault> {

    @Inject
    Event<ExportedEvent<?, ?>> event;

    @Inject
    ObjectMapper objectMapper;

    public VaultService(EntityManager em, CriteriaBuilderFactory cbf, EntityViewManager evm) {
        super(VaultEntity.class, Vault.class, em, cbf, evm);
    }

    @Transactional(SUPPORTS)
    public Optional<VaultReference> findReferenceById(Long id) {
        var result = evm.find(em, VaultReference.class, id);
        return Optional.ofNullable(result);
    }

    @Override
    public Vault create(Vault view) {
        var result = super.create(view);
        fireUpdateEvent(result.getId());
        return result;
    }

    @Override
    public Vault update(Vault view) {
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
        var flat = findById(id);
        flat.ifPresent(vault -> event.fire(VaultEvent.update(vault, objectMapper)));
    }

    private void fireDeleteEvent(Long id) {
        event.fire(VaultEvent.delete(id));
    }
}
