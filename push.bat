@echo off
echo -----------------------------------------------
set HTTP_PROXY=
set HTTPS_PROXY=
echo -----------------------------------------------
cd /d %cd%
echo Current directory: %cd%
echo -----------------------------------------------
echo Pulling latest commits before push
call pull.bat
echo -----------------------------------------------
echo Checking status:
git status
echo -----------------------------------------------
echo Adding files to staging area
git add .
echo Added to staging area
echo -----------------------------------------------
echo Checking status:
git status
echo -----------------------------------------------
echo Committing:
set /p commitMessage=Enter Commit Message: 
git commit -m "%commitMessage%"
: :git commit -m "feat:[XITARAPDP-1] common changes"
echo Committed
echo -----------------------------------------------
echo Pushing:
git push
echo Pushed
echo -----------------------------------------------
pause
