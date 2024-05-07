package io.debezium.platform.domain;


import com.blazebit.persistence.CriteriaBuilderFactory;
import com.blazebit.persistence.view.EntityViewManager;
import io.debezium.platform.data.model.VaultEntity;
import io.debezium.platform.domain.views.Vault;
import io.debezium.platform.domain.views.refs.VaultReference;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import java.util.Optional;

import static jakarta.transaction.Transactional.TxType.SUPPORTS;


@ApplicationScoped
public class VaultService extends AbstractService<VaultEntity, Vault> {

    public VaultService(EntityManager em, CriteriaBuilderFactory cbf, EntityViewManager evm) {
        super(VaultEntity.class, Vault.class, em, cbf, evm);
    }

    @Transactional(SUPPORTS)
    public Optional<VaultReference> findReferenceById(Long id) {
        var result = evm.find(em, VaultReference.class, id);
        return Optional.ofNullable(result);
    }
}
