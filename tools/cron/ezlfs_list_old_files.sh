find /opt/ezlfs/.storage  -type f -not -newermt `date -d "-1 week" +%Y%m%d` -print0
