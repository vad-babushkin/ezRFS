FILE="$1"
echo $1
#FILE="application.properties"
REGEX="\(application\.version=\)\([0-9]\+\)\.\([0-9]\+\)\.\([0-9]\+\)"

version1=$(sed -ne "s/${REGEX}/\2/p" ${FILE} | head -1)
version2=$(sed -ne "s/${REGEX}/\3/p" ${FILE} | head -1)
version3=$(sed -ne "s/${REGEX}/\4/p" ${FILE} | head -1)

echo $version1
echo $version2
echo $version3

((version3++))

version1=`date +%y%m%d`

#sed -ie "s/${REGEX}/\1${version1}.${version2}.${version3}/" ${FILE}
sed -i "s/${REGEX}/\1${version1}.${version2}.${version3}/" ${FILE}

#export DNC_DAEMON_VERSION=${version1}.${version2}.${version3}