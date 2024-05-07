package io.debezium.platform.domain.views;

import com.blazebit.persistence.view.CreatableEntityView;
import com.blazebit.persistence.view.EntityView;
import com.blazebit.persistence.view.UpdatableEntityView;
import io.debezium.platform.data.model.VaultEntity;
import io.debezium.platform.domain.views.refs.VaultReference;

import java.util.List;

@EntityView(VaultEntity.class)
@CreatableEntityView
@UpdatableEntityView
public interface Vault extends VaultReference {
    boolean isPlaintext();
    List<String> getKeys();

    void setName(String name);
    void setPlaintext(boolean plaintext);
    void setKeys(List<String> keys);
}
