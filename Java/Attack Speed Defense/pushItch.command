cd "`dirname $0`"
./gradlew html:dist
cd html
cd build
cp -r dist itch
cd itch
rm -rf WEB-INF
cd ..
zip -r itch.zip itch
butler push itch.zip pjrader1/bouncy-bullets:win-linux-mac
sleep 10
butler status pjrader1/bouncy-bullets
afplay /System/Library/Sounds/Purr.aiff
