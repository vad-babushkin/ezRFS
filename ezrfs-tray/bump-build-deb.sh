echo "build..."
./bump_build.sh
echo "repack..."
./repack_jar.sh

mkdir -p target/jpackage
cp target/ezrfs-tray-jar-with-dependencies.jar target/jpackage

echo "jpackage..."
FILE=src/main/resources/application.properties
REGEX="application\.version=\([0-9]\+\.[0-9]\+\.[0-9]\+\)"
version=$(sed -ne "s/${REGEX}/\1/p" ${FILE} | head -1)
echo $version

jpackage \
  --type deb \
  --app-version $version \
  --copyright "iconiux" \
  --description "ezRFS Tray Monitor" \
  --input target/jpackage \
  --name ezrfs-tray \
  --main-jar ezrfs-tray-jar-with-dependencies.jar \
  --dest target \
  --icon src/main/resources/tray.png \
  --license-file src/main/resources/license.txt \
  --linux-package-name ezrfs-tray \
  --linux-rpm-license-type "Apache 2.0"  \
  --about-url https://github.com/vad-babushkin/ezRFS \
  --linux-deb-maintainer iconiux@gmail.com \
  --linux-menu-group Other
#  --verbose
#  --main-class com.iconiux.ezrfs.tray.TrayApplication \

echo "done"