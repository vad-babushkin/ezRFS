echo verter-am
curl -vvv -X POST -u uploader:eiZe4Ore http://example.com/actuator/shutdown
echo
echo У вас 30 секунд для деплоя

echo deploy...
scp target/ezrfs-daemon.jar root@verter-am3:/opt/ezrfs/ezrfs-daemon.jar
echo done.