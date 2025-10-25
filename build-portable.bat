@echo offsetlocal enabledelayedexpansion
set /p VERSION="Enter release version (e.g., 0.2.0-alpha): "
if "%VERSION%"=="" (
    echo Version cannot be empty!
    pause
    exit /b 1
)
if not exist "releases" mkdir releases
set BUILD_DIR=releases\JNote Portable (%VERSION%)
set JAR_NAME=jnote-%VERSION%.jar
set ZIP_NAME=releases\JNote-%VERSION%-portable.zip
echo.
echo ========================================
echo Building JNote Portable v%VERSION%
echo ========================================
echo.
echo [1/6] Building JAR with Maven...
call mvn clean package -q
if errorlevel 1 (
    echo ERROR: Maven build failed!
    pause
    exit /b 1
)
for %%F in (target\*.jar) do set SOURCE_JAR=%%F
if not exist "%SOURCE_JAR%" (
    echo ERROR: No JAR found in target folder!
    pause
    exit /b 1
)
echo Found: %SOURCE_JAR%
echo [2/6] Creating portable folder structure...
if exist "%BUILD_DIR%" (
    echo WARNING: Release v%VERSION% already exists!
    set /p OVERWRITE="Overwrite? (y/n): "
    if /i not "!OVERWRITE!"=="y" (
        echo Cancelled.
        pause
        exit /b 0
    )
    rmdir /s /q "%BUILD_DIR%"
)
mkdir "%BUILD_DIR%"
echo [3/6] Copying and renaming JAR to %JAR_NAME%...
copy "%SOURCE_JAR%" "%BUILD_DIR%\%JAR_NAME%" > nul
echo [4/6] Creating minimal JRE with jlink (this may take a minute)...
for /f "delims=" %%i in ('jdeps --ignore-missing-deps --print-module-deps "%SOURCE_JAR%"') do set MODULES=%%i
if "%MODULES%"=="" (
    echo WARNING: Could not detect modules, using base modules
    set MODULES=java.base,java.desktop
)
echo Detected modules: %MODULES%
jlink --add-modules %MODULES% --output "%BUILD_DIR%\runtime" --strip-debug --no-header-files --no-man-pages --compress=2
if errorlevel 1 (
    echo ERROR: jlink failed!
    pause
    exit /b 1
)
echo [5/6] Creating launcher scripts and README...
(echo @echo off
echo runtime\bin\java.exe -jar %JAR_NAME% %%*) > "%BUILD_DIR%\jnote.bat"
(echo #!/bin/bash
echo DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" ^&^& pwd )"
echo "$DIR/runtime/bin/java" -jar "$DIR/%JAR_NAME%" "$@") > "%BUILD_DIR%\jnote"
(echo @echo off
echo echo Installing JNote...
echo echo.
echo setx PATH "%%PATH%%;%%CD%%"
echo echo.
echo echo JNote installed! Restart your terminal and type 'jnote' to use it.
echo pause) > "%BUILD_DIR%\install.bat"
(echo #!/bin/bash
echo echo "Installing JNote..."
echo INSTALL_DIR="$HOME/.local/bin"
echo mkdir -p "$INSTALL_DIR"
echo ln -sf "$(pwd)/jnote" "$INSTALL_DIR/jnote"
echo chmod +x "$(pwd)/jnote"
echo echo ""
echo echo "JNote installed! Type 'jnote' to use it.") > "%BUILD_DIR%\install.sh"
(echo JNote v%VERSION%
echo ==================
echo.
echo QUICK START:
echo.
echo Windows:
echo   1. Run install.bat
echo   2. Restart your terminal
echo   3. Type: jnote
echo.
echo Linux/Mac:
echo   1. Run: chmod +x install.sh ^&^& ./install.sh
echo   2. Type: jnote
echo.
echo MANUAL USAGE ^(without installing^):
echo   Windows: jnote.bat [your arguments]
echo   Linux/Mac: ./jnote [your arguments]
echo.
echo No Java installation required - JNote includes its own runtime!) > "%BUILD_DIR%\README.txt"
echo [6/6] Creating ZIP archive...
if exist "%ZIP_NAME%" del "%ZIP_NAME%"
powershell -Command "Compress-Archive -Path '%BUILD_DIR%' -DestinationPath '%ZIP_NAME%' -Force"
if errorlevel 1 (
    echo WARNING: Could not create ZIP automatically
    echo You can create it manually from: %BUILD_DIR%
) else (
    echo ZIP created: %ZIP_NAME%
)
echo.
echo ========================================
echo SUCCESS! Release built.
echo ========================================
echo.
echo Folder: %BUILD_DIR%
echo ZIP:    %ZIP_NAME%
echo.
echo Test it: cd "%BUILD_DIR%" ^&^& jnote.bat --help
echo.
pause