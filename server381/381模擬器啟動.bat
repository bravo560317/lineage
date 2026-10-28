@echo off
cd /d "%~dp0"

color b
title 天堂伺服器啟動器（天堂）

:RESTART
cls
echo ===========================================
echo             天堂伺服器啟動中...
echo ===========================================
echo.

java -server ^
 -Duser.timezone=Asia/Taipei ^
 -Xms1024m ^
 -Xmx6g ^
 -XX:+UseG1GC ^
 -XX:MaxGCPauseMillis=200 ^
 -XX:InitiatingHeapOccupancyPercent=30 ^
 -XX:G1ReservePercent=20 ^
 -XX:+UseStringDeduplication ^
 -XX:+UnlockExperimentalVMOptions ^
 -XX:-UseBiasedLocking ^
 -Xloggc:logs\gc.log ^
 -XX:+PrintGCDetails ^
 -XX:+PrintGCDateStamps ^
 -cp "381server.jar;lib/*" ^
 com.lineage.Server

echo.
echo ===========================================
echo 伺服器已關閉，3 秒後重新啟動...
echo ===========================================
timeout /t 3 >nul
goto RESTART