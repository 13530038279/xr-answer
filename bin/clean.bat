@echo off
@echo current directory "%~dp0"
@echo maven clean from local repo...
 
cd ../
call mvn clean
pause 