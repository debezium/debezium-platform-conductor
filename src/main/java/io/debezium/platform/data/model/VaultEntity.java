package io.debezium.platform.data.model;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;

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
    @ElementCollection
    @Column(name = "key")
    private List<String> keys = new LinkedList<>();
}
