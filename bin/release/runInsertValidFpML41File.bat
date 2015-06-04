@echo off
cls

rd data\json /s /q
rd data\received /s /q
rd data\invalid /s /q
rd data\valid /s /q
rd data\exception /s /q

title POC - Copy valid

echo ****************************************************
echo * CPQi
echo * Import a valid FpML in 4.1 version.
echo ****************************************************

xcopy data\test\valid-1.xml data\received\