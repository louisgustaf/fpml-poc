@echo off
cls

rd data\json /s /q
rd data\received /s /q
rd data\invalid /s /q
rd data\valid /s /q
rd data\exception /s /q

if "%OS%"=="Windows_NT" @setlocal

if "%JAVA_OPTS%"=="" set JAVA_OPTS=-Xms128m -Xmx256m

if "%JAVA_HOME%" == "" goto noJavaHome
if not exist "%JAVA_HOME%\bin\java.exe" goto noJavaHome
if not exist "%JAVA_HOME%\bin\jdb.exe" goto noJavaHome
if not exist "%JAVA_HOME%\bin\javaw.exe" goto noJavaHome
if not exist "%JAVA_HOME%\bin\javac.exe" goto noJavaHome
set _JAVACMD=%JAVA_HOME%\bin\java.exe
goto echo

:noJavaHome
echo.
echo Error: JAVA_HOME environment variable is either not set
echo        or it is not pointing to a full Java JDK.
echo        Please set the JAVA_HOME environment variable to
echo        your JDK installation directory and try again.
echo.
goto end

:echo
if "%IO_SKIP_WARNING%" == "TRUE" goto run_io
echo ****************************************************
echo * CPQi
echo * Import FpML 5.3 version.
echo * Try to import a invalid FpML missing a opening TAG.
echo * Missing TAG "<trade>" for the Trade in the document.
echo ****************************************************

pause

goto run_io

:run_io
echo Running com.cpqi.poc.route.ImportRouteBuilder ...
"%_JAVACMD%" %JAVA_OPTS% -Dlog4j.configuration=file:./log4j.properties -classpath jpm-chase-poc-0.0.1-SNAPSHOT.jar com.cpqi.poc.client.WebServiceClientPOST data/test/invalid.xml
-1.xml 

:end