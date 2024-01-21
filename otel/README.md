```
docker run -it \
-v /var/lib/docker/containers:/var/lib/docker/containers \
-v ${PWD}/fluent-bit/docker-metadata.lua:/fluent-bit/bin/docker-metadata.lua \
-v /proc:/host/proc \
-v /sys:/host/sys \
-p 3001:3001 fluent/fluent-bit:latest
```