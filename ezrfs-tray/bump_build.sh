bump.application.version.sh src/main/resources/application.properties
bump.maven.version.sh src/main/resources/application.properties
#bump.jdeb.control.version.sh src/main/deb/control/control

mvn clean compile package
