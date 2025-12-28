
jpackage \
  --type dmg \
  --app-version 251227.0.15 \
  --copyright "iconiux" \
  --description "ezRFS-Tray" \
  --input build \
  --name ezrfs-tray-native \
  --main-jar ezrfs-tray-jar-with-dependencies.jar \
  --main-class com.iconiux.ezrfs.tray.TrayApplication \
  --icon src/main/resources/tray.png \
  --license-file src/main/izpack/legal/license.txt \
  --linux-package-name ezrfs-tray-native \
  --linux-rpm-license-type "Apache 2.0" \
  --verbose
