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

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Entity(name = "Destination")
@Getter
@Setter
public class DestinationEntity {
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
    @JoinTable(inverseJoinColumns = @JoinColumn(name = "vault_id"))
    private List<VaultEntity> vaults = new LinkedList<>();
    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, Object> config;
}
