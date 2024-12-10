Chart for Debezium platform

# Prerequisites
The chart use an ingress to expose `debezium-stage (UI)` and `debezium-conductor (backend)`,
this will require to have an [ingress controller](https://kubernetes.io/docs/concepts/services-networking/ingress-controllers/) installed in you cluster.
You need also to have domain that must point to the cluster IP and then configure the `domain.url` property in you `values.yaml` with your domain.

### Local K8s cluster with Minikube
On minikube you can obtain the cluster ip with 

```shell
minikube ip
```
then you just need to add the IP to the `/etc/hosts` 

or you can do it with the following command

```shell
sudo echo $(minikube ip) platform.debezium.io >> /etc/hosts
```

# Install

```shell
helm dependency build
```
Thi will download the required debezium operator chart and, only if `kafka.enabled` is `true`, the strimzi operator chart.

```shell
helm install <release_name> .
```

# Uninstall

Find the release name you want to uninstall

```shell
helm list --all
```

then uninstall it

```shell
helm uninstall <release_name>
```