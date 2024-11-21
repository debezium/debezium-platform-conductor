{{/*
Get the database secret name.
*/}}
{{- define "database.secretName" -}}
{{- if .Values.auth.existingSecret -}}
    {{- printf "%s" .Values.auth.existingSecret -}}
{{- else -}}
    {{- printf "%s-%s" .Chart.Name "secrets" -}}
{{- end -}}
{{- end -}}