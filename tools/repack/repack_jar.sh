
cp target/ezrfs-tray-jar-with-dependencies.jar target/ezrfs-tray-jar-with-dependencies.jar.before-repack
find . -name '*.java' -exec cat {} \; | grep "/img/" | grep "png\|gif" | sed 's/.*\/img\//img\//' | sed 's/\".*;//' | sort | uniq > img.lst
7z l target/ezrfs-tray-jar-with-dependencies.jar | grep "png\|gif" | grep " img/" | sed 's/.*img\//img\//' | sort | uniq > all.lst
comm -23 <(sort all.lst) <(sort img.lst) > 4rm.lst
7z d target/ezrfs-tray-jar-with-dependencies.jar @4rm.lst
rm img.lst all.lst 4rm.lst
