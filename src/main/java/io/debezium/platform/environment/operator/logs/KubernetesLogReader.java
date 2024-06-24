package io.debezium.platform.environment.operator.logs;

import io.debezium.platform.environment.logs.LogReader;
import io.fabric8.kubernetes.client.KubernetesClientException;
import io.fabric8.kubernetes.client.dsl.LogWatch;
import io.fabric8.kubernetes.client.dsl.Loggable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;
import java.util.function.Supplier;

public class KubernetesLogReader implements LogReader {

    private final Supplier<Loggable> supplier;
    private LogWatch watch;
    private BufferedReader reader;

    public KubernetesLogReader(Supplier<Loggable> supplier) {
        this.supplier = Objects.requireNonNull(supplier, "Supplier cannot be null");
    }

    @Override
    public BufferedReader reader() throws IOException {
        return ensureReader();
    }

    @Override
    public String readLine() throws IOException {
        return reader().readLine();
    }

    @Override
    public void close() throws IOException {
        if (reader != null) {
            reader.close();
        }
        if (watch != null) {
            watch.close();
        }
    }

    private BufferedReader ensureReader() throws IOException {
        if (reader == null) {
            try {
                this.watch = supplier.get().watchLog();
                this.reader = new BufferedReader(new InputStreamReader(watch.getOutput()));
            } catch (KubernetesClientException e) {
                throw new IOException(e);
            }
        }
        return reader;
    }
}
