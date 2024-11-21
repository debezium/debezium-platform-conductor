url=${1:-localhost}
port=${2:-8080}
payloads_dir=${3:-payloads}
http POST ${url}:${port}/api/sources @${payloads_dir}/source.json
http POST ${url}:${port}/api/destinations @${payloads_dir}/destination.json
http POST ${url}:${port}/api/pipelines @${payloads_dir}/pipeline.json