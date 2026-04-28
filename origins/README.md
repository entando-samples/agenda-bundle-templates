# AGENDA MFE CONTAINER

This container was made to serve the MFE as found in the
bundle image create by the Entando CLI.

_NOTE_: at the current stage we are not consuming any real microservices


```shell
docker build --no-cache -t agenda-nginx .
docker run --rm -p 8080:80 agenda-nginx:latest
```

then open the browser at `http://localhost:8080/`
