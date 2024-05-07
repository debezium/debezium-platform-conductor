package io.debezium.platform.domain;

import com.blazebit.persistence.CriteriaBuilder;
import com.blazebit.persistence.CriteriaBuilderFactory;
import com.blazebit.persistence.view.EntityViewManager;
import com.blazebit.persistence.view.EntityViewSetting;
import io.debezium.platform.domain.views.base.IdView;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;

import static jakarta.transaction.Transactional.TxType.REQUIRED;
import static jakarta.transaction.Transactional.TxType.SUPPORTS;

@Transactional(REQUIRED)
public class AbstractService<E, T extends IdView> {

    EntityManager em;
    CriteriaBuilderFactory cbf;
    EntityViewManager evm;
    Class<T> viewType;
    Class<E> entityType;

    public AbstractService(
            Class<E> entityType,
            Class<T> viewType,
            EntityManager em,
            CriteriaBuilderFactory cbf,
            EntityViewManager evm) {
        this.em = em;
        this.cbf = cbf;
        this.evm = evm;
        this.entityType = entityType;
        this.viewType = viewType;
    }

    public AbstractService() {
        // required by CDI
    }

    protected CriteriaBuilder<E> cb() {
        return cbf.create(em, entityType);
    }

    @Transactional(SUPPORTS)
    public List<T> list() {
        return evm.applySetting(EntityViewSetting.create(viewType), cb())
                .getResultList();
    }

    @Transactional(SUPPORTS)
    public Optional<T> findById(Long id) {
        var result = evm.find(em, viewType, id);
        return Optional.ofNullable(result);
    }

    public T create(@Valid T view) {
        evm.save(em, view);
        return view;
    }

    public T update(@Valid T view) {
        evm.save(em, view);
        return evm.find(em, viewType, view.getId());
    }

    public void delete(long id) {
        evm.remove(em, id);
    }

}
