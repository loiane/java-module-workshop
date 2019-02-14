#!/usr/bin/env bash
RED='\033[1;31m'
GREEN='\033[1;32m'
YELLOW='\033[1;33m'
PURPLE='\033[1;35m'
NC='\033[0m' # No Color

logging=('logger-service' 'logging' 'named')
feedlogger=('feed-logger' 'logging' 'named')
consolelogger=('console-logger' 'logging' 'named')
jeventbus=('jeventbus' '.' 'named')
shared=('ecommerce-shared' 'ecommerce-platform' 'named')
basket=('basket-service' 'ecommerce-platform' 'named')
stock=('stock-service' 'ecommerce-platform' 'named')
catalog=('catalog-service' 'ecommerce-platform' 'named')
order=('order-service' 'ecommerce-platform' 'named')
api=('ecommerce-api' 'ecommerce-platform' 'named')

modules=(logging feedlogger consolelogger jeventbus shared basket stock catalog order api)

recreateFolder() {
    folder="$1"
    if [ -d $folder ]; then
       rm -rf $folder/*
    else
       mkdir $folder
    fi
}

recreateFolder "out"
recreateFolder "lib"
#recreateFolder "modules"

compile() {
    moduleName="$1"
    projectName="$2"
    apiModuleType="$3"

    mkdir -p out/$moduleName

    if [ $apiModuleType = "named" ]; then
       cd out && javac --module-path ../modules -cp "../lib/*" -d $moduleName $(find ../$2/$1/src/main/java -name '*.java') && cd ..
    else
       cd out && javac --module-path ../modules -cp "../lib/*" -d $moduleName $(find ../$2/$1/src/main/java -name '*.java' -not -name 'module-info.java') && cd ..
    fi    
    
    echo -e "${YELLOW}COMPILE ::${NC} Module $moduleName@$projectName compiled to ${GREEN}out/$moduleName${NC}"
}

createJar() {
    moduleName=$1
    cd out/$moduleName && jar --create --file ../../lib/$moduleName.jar -c . && cd ../..
    echo -e "${YELLOW}JAR     ::${NC} Module $moduleName packaged to ${GREEN}lib/$moduleName.jar${NC}"
}

moveModulePathIfNeed() {
    moduleName="$1"
    moduleType="$3"
    if [ $moduleType != "unnamed" ]; then
       echo -e "${YELLOW}MODULE  ::${NC} Module $moduleName moved to module path ${GREEN}modules${NC}"
       mv lib/$moduleName.jar modules/.
    fi
}

cnt=0
total_Array=${#modules[*]}
while [ $cnt -lt $total_Array ] ; do
        srch_fld=${modules[$cnt]}
                eval var1=\${$srch_fld[@]}
                eval moduleName=\${$srch_fld[0]}            
                echo -e  "*********************** BUILDING ${PURPLE}$moduleName${NC} ************************"
                compile $var1
		createJar $var1
                moveModulePathIfNeed $var1
        ((cnt=cnt+1))
done

if [ $apiModuleType = "named" -o $apiModuleType = "automatic" ]; then
   echo execution module
   java --module-path modules -cp "lib/*" --module ecommerce.api/ecommerce.api.Application
else
   java --module-path modules -cp "lib/*" ecommerce.api.Application   
fi
