# debezium-platform-conductor

The back-end component of Debezium management platform. Conductor provides a set APIs which can be 
used to orchestrate and control Debezium deployments. It's intended to be interacted with through a front-end client.


## Debezium Management Platform
Debezium Management Platform (Debezium Orchestra) aims to provide means to simplify the deployment of 
Debezium to various environments in highly opinionated manner. The goal is not to provide 
total control over environment specific configuration. To achieve this goal the platform uses
a data-centric view on Debezium components.