@echo off
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

set modules[0].module = logger-service
set modules[0].application = logging
set modules[0].moduleType = named 

set modules[1].module = feed-service
set modules[1].application = logging
set modules[1].moduleType = named

set modules[2].module = console-service
set modules[2].application = logging
set modules[2].moduleType = named

set modules[3].module = jeventbus
set modules[3].application = .
set modules[3].moduleType = named

set modules[4].module = ecommerce-shared
set modules[4].application = ecommerce-platform
set modules[4].moduleType = named

set modules[4].module = basket-service
set modules[4].application = ecommerce-platform
set modules[4].moduleType = named

set modules[4].module = stock-service
set modules[4].application = ecommerce-platform
set modules[4].moduleType = named

set modules[4].module = catalog-service
set modules[4].application = ecommerce-platform
set modules[4].moduleType = named

set modules[4].module = order-service
set modules[4].application = ecommerce-platform
set modules[4].moduleType = named

set modules[4].module = api
set modules[4].application = ecommerce-platform
set modules[4].moduleType = named


set i=0
:loop
if %i% equ %len% goto :eof

set cur.module=
set cur.application=
set cur.moduleType=
for /f "usebackq delims==. tokens=1-3" %%j in (`set obj[%i%]`) do (
	set cur.%%k=%%l
)

mkdir out/%%cur.module%%
if "%cur.moduleType%"=="named" (
	cd out && javac --module-path ../modules -cp "../lib/*" -d $moduleName $(find ../$2/$1/src/main/java -name '*.java') && cd ..
) else (
	cd out && javac --module-path ../modules -cp "../lib/*" -d $moduleName $(find ../$2/$1/src/main/java -name '*.java' -not -name 'module-info.java') && cd ..
)
echo [93mCOMPILE ::[0m Module %%cur.module%%@%%cur.application%% compiled to [92mout/$moduleName[0


cd out/%cur.module% && jar --create --file ../../lib/%cur.module%.jar -c . && cd ../..
echo [93mJAR     ::[0m Module %cur.module% packaged to [92mlib/%cur.module.jar[0m"

if not %cur.moduleType% = "unnamed" (
   echo [93mMODULE  ::[0m Module %cur.module% moved to module path [92mmodules[0m"
   move lib/%cur.module%.jar modules/.
)

set /a i=%i%+1
goto loop


if %modules[4].moduleType% = "named" or %modules[4].moduleType% == "automatic" (
   echo execution module
   java --module-path modules -cp "lib/*" --module ecommerce.api/ecommerce.api.Application
) else (
   java --module-path modules -cp "lib/*" ecommerce.api.Application   
)
