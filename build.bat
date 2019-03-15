echo "taner diler"

set JAVAHOME= "C:\Program Files\Java\jdk-11.0.2"

SETLOCAL
CALL :Display logger-service , logging , automatic , ""
CALL :Display console-logger , logging , automatic , "logger.service"
CALL :Display feed-logger , logging , automatic , "logger.service"
CALL :Display jeventbus , . , named , ""
CALL :Display ecommerce-shared , ecommerce-platform , automatic , "jeventbus"
CALL :Display basket-service , ecommerce-platform , automatic , "jeventbus,ecommerce.shared"
CALL :Display stock-service , ecommerce-platform , automatic , "jeventbus,ecommerce.shared"
CALL :Display catalog-service , ecommerce-platform , automatic , "jeventbus,ecommerce.shared"
CALL :Display order-service , ecommerce-platform , automatic , "jeventbus,ecommerce.shared"
CALL :Display ecommerce-api , ecommerce-platform , automatic , "jeventbus,logger.service,feed.logger,console.logger,ecommerce.shared,basket.service,stock.service,catalog.service,order.service"

EXIT /B %ERRORLEVEL%

set modules[0].module = logger-service
set modules[0].application = logging
set modules[0].moduleType = named

:Display
set moduleName=%~1
set project=%~2
set moduleType=%~3
set modulesToAdd=%~4
mkdir modules
mkdir "out/%moduleName%"

set addModules=""
IF not "%modulesToAdd%"=="" (
    set addModules="--add-modules %modulesToAdd"
)

if "%moduleType%"=="named" (
   dir /s /B %project%\%moduleName%\src\main\java\*.java > out\sources.txt
	%JAVAHOME%\bin\javac --module-path modules -cp "lib\*" -d "out\%moduleName%" @out\sources.txt 
) else (
   dir /s /B %project%\%moduleName%\src\main\java\*.java | findstr /v /i "\module-info.java$" > out\sources.txt
	%JAVAHOME%\bin\javac --module-path modules  -cp "lib\*" %addModules% -d "out\%moduleName%" @out\sources.txt
)

if exists  %project%/%moduleName%/src/main/resource/ (
   xcopy /S %project%/%moduleName%/src/main/resource/* out/%moduleName%/.
)

cd "out/%moduleName%" && %JAVAHOME%\bin\jar --create --file "../../lib/%moduleName%.jar" -c . && cd ..
cd ..
echo The value of parameter 1 is %~1
echo The value of parameter 2 is %~2
if not "%moduleType%"=="unnamed" (
   move "lib\%moduleName%.jar" modules\.
)

if "%moduleName%"=="ecommerce-api" (   
   if "%moduleType%"=="named" (
      %JAVAHOME%\bin\java --module-path modules -cp "lib/*" --module ecommerce.api/ecommerce.api.Application
   )
   
   if "%moduleType%"=="automatic" (
      %JAVAHOME%\bin\java --module-path modules -cp "lib/*" --add-modules %modulesToAdd%  --module ecommerce.api/ecommerce.api.Application
   ) else (
      %JAVAHOME%\bin\java --module-path modules -cp "lib/*" ecommerce.api.Application   
   )
)
EXIT /B 0
