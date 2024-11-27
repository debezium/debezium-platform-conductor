{{/*
Get the database secret name.
*/}}
{{- define "debezium-platform.secretName" -}}
{{- if .Values.database.enabled -}}
    {{ include "database.secretName" .Subcharts.database }}
{{- else -}}
    {{- required "A valid .Values.database.auth.existingSecret entry required!" .Values.database.auth.existingSecret -}}
{{- end -}}
{{- end -}}

{{/*
Common labels
*/}}

{{- define "common.labels" -}}
app.kubernetes.io/instance: {{ .Release.Name }}
app.kubernetes.io/managed-by: {{ .Release.Service }}
{{- end -}}