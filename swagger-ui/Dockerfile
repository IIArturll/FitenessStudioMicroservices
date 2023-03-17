FROM swaggerapi/swagger-ui:latest
MAINTAINER ITAcademy

ADD /product-service/spec/product-service.yml /usr/share/nginx/html/doc/product-service.yml
ADD /user-service/spec/user-service.yml /usr/share/nginx/html/doc/user-service.yml
ADD /audit-service/spec/audit-service.yml /usr/share/nginx/html/doc/audit-service.yml
ADD /report-service/spec/report-service.yml /usr/share/nginx/html/doc/report-service.yml

ENV URLS="[{ url: 'doc/product-service.yml', name: 'Product'},{ url: 'doc/user-service.yml', name: 'User'},{url: 'doc/audit-service.yml', name: 'Audit'},{url: 'doc/report-service.yml', name: 'Report'}]"
ENV URLS_PRIMARY_NAME="User"