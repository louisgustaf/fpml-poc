@echo off
cls

rd data\json /s /q
rd data\received /s /q
rd data\invalid /s /q
rd data\valid /s /q
rd data\exception /s /q

title POC - Copy invalid

echo ****************************************************
echo * CPQi
echo * Import FpML 5.3 version.
echo * Try to import a invalid FpML missing a opening TAG.
echo * Missing TAG "<trade>" for the Trade in the document.
echo ****************************************************

xcopy data\test\invalid.xml data\received\