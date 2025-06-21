@echo off

echo Cleaning project...
call mvn clean

echo Running tests via TestNG...
call mvn test

echo Checking Allure results...
IF EXIST target\allure-results (
    echo Starting Allure report...
    call allure serve target\allure-results
) ELSE (
    echo Allure results folder not found! Tests might not have executed correctly.
)

pause
