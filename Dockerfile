FROM opensearchproject/opensearch:3.7.0

ARG VERSION="3.7.0-SNAPSHOT"

COPY ./build/distributions/search-redact-${VERSION}.zip /tmp/

RUN /usr/share/opensearch/bin/opensearch-plugin install --batch file:/tmp/search-redact-${VERSION}.zip
