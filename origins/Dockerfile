# Stage 1: copia le risorse dall'immagine "pippo"
FROM harbor.rancher-playground.entando.dev/origins/agenda-bundle-templates:0.0.1 AS source

# Stage 2: immagine finale con nginx
FROM nginx:alpine

# Copia tutti i widget dalla cartella /widgets dell'immagine sorgente
COPY --from=source /widgets /usr/share/nginx/html/widgets

# Configurazione nginx personalizzata
COPY nginx.conf /etc/nginx/conf.d/default.conf

COPY index.html /usr/share/nginx/html/index.html

EXPOSE 80

