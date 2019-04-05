start http://localhost:8080/crud/v1/task/getTasks
if "%ERRORLEVEL%" == "0" go to end
goto fail

:fail
echo.
echo Cannot run browser

:end
echo.
echo Everything is fine