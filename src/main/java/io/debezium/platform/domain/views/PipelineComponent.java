package io.debezium.platform.domain.views;

import com.blazebit.persistence.view.MappingSingular;
import io.debezium.platform.domain.views.base.NamedView;
import io.debezium.platform.domain.views.refs.VaultReference;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;
import java.util.Map;

public interface PipelineComponent extends NamedView {
    @NotEmpty
    String getType();
    @NotEmpty
    String getSchema();
    List<VaultReference> getVaults();
    @MappingSingular
    Map<String, Object> getConfig();

    void setType(String type);
    void setName(String name);
    void setSchema(String schema);
    void setVaults(List<VaultReference> vaults);
    void setConfig(Map<String, Object> config);
}