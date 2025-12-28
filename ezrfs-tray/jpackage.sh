echo "jpackage..."
jpackage \
  --type deb \
  --app-version 251227.0.16 \
  --copyright "iconiux" \
  --description "ezRFS-Tray" \
  --input target/jpackage \
  --name ezrfs-tray \
  --main-jar ezrfs-tray-jar-with-dependencies.jar \
  --dest target \
  --icon src/main/resources/tray.png \
  --license-file src/main/izpack/legal/license.txt \
  --linux-package-name ezrfs-tray \
  --linux-rpm-license-type "Apache 2.0"  \
  --about-url https://github.com/vad-babushkin/ezRFS \
  --linux-deb-maintainer iconiux@gmail.com \
  --linux-menu-group Other
#  --verbose
#  --main-class com.iconiux.ezrfs.tray.TrayApplication \
  
echo "done"