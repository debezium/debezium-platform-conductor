package io.debezium.platform.data.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.HashMap;
import java.util.Map;

@Entity(name = "Vault")
@Getter
@Setter
public class VaultEntity {
    @Id
    @GeneratedValue
    private Long id;
    @NotEmpty
    private String name;
    private boolean plaintext = false;
    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, String> items = new HashMap<>();
}
