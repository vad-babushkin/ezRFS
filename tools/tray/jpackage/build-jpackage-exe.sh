echo "build..."
mvn clean compile package

echo "repack..."
./repack_jar.sh

mkdir -p target/jpackage
cp target/ezrfs-tray-jar-with-dependencies.jar target/jpackage

echo "jpackage..."
jpackage \
  --type exe \
  --app-version 251227.0.16 \
  --copyright "iconiux" \
  --description "ezRFS-Tray" \
  --input target/jpackage \
  --name ezrfs-tray \
  --main-jar ezrfs-tray-jar-with-dependencies.jar \
  --main-class com.iconiux.ezrfs.tray.TrayApplication \
  --icon src/main/resources/tray.png \
  --license-file src/main/izpack/legal/license.txt \
  --linux-package-name ezrfs-tray \
  --linux-rpm-license-type "Apache 2.0" 
#  --verbose
  
mv ezrfs-tray*.deb target

echo "done"