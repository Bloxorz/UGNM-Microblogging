REM	curl test script for las2peer service

REM test authentication with test user alice
curl -v -X GET http://localhost:8080/microblogging/validate --user alice:pwalice
PAUSE
curl -v -X GET http://localhost:8080/microblogging/getUser/2 --user alice:pwalice
pause
curl -v -X PUT -d "{""rating"":""UNRATED"",""imagePath"":""http://edited"",""contactInfo"":""http://new.html"",""email"":""fromPut"",""pass"":""asge90"",""id"":""3""}" http://localhost:8080/microblogging/editUser/3 --user alice:pwalice
pause
curl -v -X GET http://localhost:8080/microblogging/getUser/3 --user alice:pwalice

REM more curl commandlines...
PAUSE

