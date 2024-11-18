url=${1:-localhost}
port=${2:-8080}
http POST ${url}:${port}/api/sources @payloads/source.json
http POST ${url}:${port}/api/destinations @payloads/destination.json
http POST ${url}:${port}/api/pipelines @payloads/pipeline.json