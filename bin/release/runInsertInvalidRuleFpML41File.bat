@echo off
cls

rd data\json /s /q
rd data\received /s /q
rd data\invalid /s /q
rd data\valid /s /q
rd data\exception /s /q

title POC - Copy invalid-ValidationRule

echo **********************************************************************
echo * CPQi
echo * Import FpML 4.1 version.
echo * Try to import a FpML with the folloing invalid rule:
echo * "businessCentersReference" or "businessCenters" Tags must be present
echo * if and only if the value of "businessDayConvention" Tag is not
echo * equal to NONE or NotApplicable.
echo * This rule is violated on the first "swapStream" in the document.
echo **********************************************************************

xcopy data\test\invalid-ValidationRule-1 data\received\