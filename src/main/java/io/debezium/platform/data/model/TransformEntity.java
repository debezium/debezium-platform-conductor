package io.debezium.platform.data.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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
    @JoinTable(joinColumns = @JoinColumn(name = "transform_id"), inverseJoinColumns = @JoinColumn(name = "vault_id"))
    private Set<VaultEntity> vaults = new HashSet<>();
    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, Object> config;
}
