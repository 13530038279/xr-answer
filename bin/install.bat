@echo off
@echo current directory "%~dp0"
@echo maven install to local repo...
 
cd ../
call mvn install -Dmaven.test.skip=true
pause 