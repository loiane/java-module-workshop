FROM openjdk:16-jdk-alpine as build
RUN apk add --no-cache bash binutils
CMD mkdir -p /tmp/ecommerce-modules
COPY modules/* /tmp/ecommerce-modules/
RUN $JAVA_HOME/bin/jlink --module-path /tmp/ecommerce-modules \
	  --add-modules ecommerce.api,feed.logger,console.logger \
         --verbose     --strip-debug    --compress 2     --no-header-files     --no-man-pages \
         --output /opt/ecommerce-api \
         --launcher ecomm=ecommerce.api/ecommerce.api.Application

FROM alpine:3.13.0
COPY --from=build /opt/ecommerce-api /opt/ecommerce-api
ENV JAVA_HOME=/opt/ecommerce-api
ENV PATH="$PATH:$JAVA_HOME/bin"
CMD ecomm