ECHO OFF

set JAVAHOME= "C:\Program Files\Java\jdk-11.0.2"

SETLOCAL
CALL :Build ecommerce-shared , ecommerce-platform , unnamed
CALL :Build basket-service , ecommerce-platform , unnamed
CALL :Build stock-service , ecommerce-platform , unnamed
CALL :Build catalog-service , ecommerce-platform , unnamed
CALL :Build order-service , ecommerce-platform , unnamed
CALL :Build ecommerce-api , ecommerce-platform , unnamed

EXIT /B %ERRORLEVEL%

set modules[0].module = logger-service
set modules[0].application = logging
set modules[0].moduleType = named

:Build
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

if not "%moduleType%"=="unnamed" (
   move "lib\%moduleName%.jar" modules\.
)

if "%moduleName%"=="ecommerce-api" (   
   if "%moduleType%"=="named" (
      %JAVAHOME%\bin\java --module-path modules -cp "lib/*" --module ecommerce.api/ecommerce.api.Application
   )
   
   if "%moduleType%"=="automatic" (
      %JAVAHOME%\bin\java --module-path modules -cp "lib/*"  --module ecommerce.api/ecommerce.api.Application
   ) else (
      %JAVAHOME%\bin\java --module-path modules -cp "lib/*" ecommerce.api.Application   
   )
)
EXIT /B 0
