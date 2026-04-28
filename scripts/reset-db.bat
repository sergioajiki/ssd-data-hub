@echo off
echo Resetando banco H2...
del /f /q "%~dp0..\data\datahub.mv.db" 2>nul
del /f /q "%~dp0..\data\datahub.trace.db" 2>nul
echo Banco resetado. Suba a aplicacao para recriar o schema.
