@echo off


title Paperless Project
echo CURL Testing for Paperless OCR and fulltext search
echo.


REM --------------------------------------------------
echo 1) Upload files
REM Upload file
curl -X POST http://localhost:5000/docs/upload -H "Content-Type: multipart/form-data" -F "file=@C:\Users\laura\OneDrive\Desktop\FH\SWKOM\test pdfs\BAK_Checklist.pdf"
echo .
curl -X POST http://localhost:5000/docs/upload -H "Content-Type: multipart/form-data" -F "file=@C:\Users\laura\OneDrive\Desktop\FH\SWKOM\test pdfs\Ein schwieriger Auftrag.pdf"
echo .
curl -X POST http://localhost:5000/docs/upload -H "Content-Type: multipart/form-data" -F "file=@C:\Users\laura\OneDrive\Desktop\FH\SWKOM\test pdfs\Inskription.pdf"


pause


REM --------------------------------------------------
echo 2) Show documents
curl -X GET http://localhost:5000/docs
echo.


pause


REM --------------------------------------------------
echo 3) Search query
curl -X GET http://localhost:5000/docs/search?query=Laura -H "accept: /"


pause


REM --------------------------------------------------
echo 4) Download first document
curl -v "http://localhost:5000/docs/download?id=3&title=Inskription.pdf" -o ./downloadedFile.pdf


REM --------------------------------------------------
echo end...


REM this is approx a sleep
ping localhost -n 100 >NUL 2>NUL
@echo on