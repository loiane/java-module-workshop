echo "taner diler"

set JAVAHOME= "C:\Program Files\Java\jdk-11.0.2"

SETLOCAL
CALL :Display logger-service , logging , named
CALL :Display console-logger , logging , named
CALL :Display feed-logger , logging , named
CALL :Display jeventbus , . , named
CALL :Display ecommerce-shared , ecommerce-platform , named
CALL :Display basket-service , ecommerce-platform , named
CALL :Display stock-service , ecommerce-platform , named
CALL :Display catalog-service , ecommerce-platform , named
CALL :Display order-service , ecommerce-platform , named
CALL :Display ecommerce-api , ecommerce-platform , named
EXIT /B %ERRORLEVEL%

set modules[0].module = logger-service
set modules[0].application = logging
set modules[0].moduleType = named

:Display
set moduleName=%~1
set project=%~2
set moduleType=%~3
mkdir modules
mkdir "out/%moduleName%"

if "%moduleType%"=="named" (
   dir /s /B %project%\%moduleName%\src\main\java\*.java > out\sources.txt
	%JAVAHOME%\bin\javac --module-path modules -cp "lib\*" -d "out\%moduleName%" @out\sources.txt 
) else (
   dir /s /B %project%\%moduleName%\src\main\java\*.java | findstr /v /i "\module-info.java$" > out\sources.txt
	%JAVAHOME%\bin\javac --module-path modules  -cp "lib\*" -d "out\%moduleName%" @out\sources.txt
)
cd "out/%moduleName%" && %JAVAHOME%\bin\jar --create --file "../../lib/%moduleName%.jar" -c . && cd ..
cd ..
echo The value of parameter 1 is %~1
echo The value of parameter 2 is %~2
if not "%moduleType%"=="unnamed" (
   move "lib\%moduleName%.jar" modules\.
)
EXIT /B 0

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



set len=4
set i=0
:loop
if %i% equ %len% goto :eof
set modules[1]
set cur.module=
set cur.application=
set cur.moduleType=
for /f "usebackq delims==  tokens=1-3" %%j in (`set modules[%i%]`) do (
	set cur.%%k=%%j
)

echo "taner diler"
set cur.module
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
