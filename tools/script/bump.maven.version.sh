FILE="$1"
echo $1

REGEX="application\.version=\([0-9]\+\.[0-9]\+\.[0-9]\+\)"
version=$(sed -ne "s/${REGEX}/\1/p" ${FILE} | head -1)
echo $version

mvn versions:set -DnewVersion=$version -DgenerateBackupPoms=false -DprocessAllModules=false

