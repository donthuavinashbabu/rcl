@echo off
echo -----------------------------------------------
set HTTP_PROXY=
set HTTPS_PROXY=
echo -----------------------------------------------
cd /d %cd%
echo Current directory - %cd%
echo -----------------------------------------------
git pull
pause