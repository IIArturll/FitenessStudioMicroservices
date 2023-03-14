FROM swaggerapi/swagger-ui:latest
MAINTAINER ITAcademy

ADD /product-service/spec/product-service.yml /usr/share/nginx/html/doc/product-service.yml
ADD /user-service/spec/user-service.yml /usr/share/nginx/html/doc/user-service.yml

ENV URLS="[{ url: 'doc/product-service.yml', name: 'Product'},{ url: 'doc/user-service.yml', name: 'User'},]"
ENV URLS_PRIMARY_NAME="User"
