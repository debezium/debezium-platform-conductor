package io.debezium.platform.data.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Entity(name = "Transform")
@Getter
@Setter
public class TransformEntity {
    @Id
    @GeneratedValue
    private Long id;
    @NotEmpty
    private String name;
    @NotEmpty
    private String type;
    @NotEmpty
    private String schema;
    @ManyToMany
    private List<VaultEntity> vaults = new LinkedList<>();
    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, Object> config;
}
