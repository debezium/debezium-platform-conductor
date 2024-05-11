package io.debezium.platform.environment.watcher.config;

import io.smallrye.config.ConfigMapping;
import io.smallrye.config.WithName;

@ConfigMapping(prefix = "quarkus.debezium-outbox")
public interface OutboxConfigGroup {

    @WithName("table-name")
    String table();

    @WithName("aggregate-type.name")
    String aggregateColumn();

    @WithName("aggregate-id.name")
    String aggregateIdColumn();

    @WithName("type.name")
    String typeColumn();
}
