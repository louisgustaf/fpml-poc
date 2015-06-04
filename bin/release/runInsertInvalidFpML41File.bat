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
echo * Import FpML 4.1 version.
echo * Try to import a invalid FpML missing a ending TAG.
echo * Missing TAG "</tradeId>" for the Trade in
echo * the document.
echo ****************************************************

pause

xcopy data\test\invalid-Unparseable-1.xml data\received\