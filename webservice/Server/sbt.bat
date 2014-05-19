set SCRIPT_DIR=%~dp0
java -XX:+CMSClassUnloadingEnabled -javaagent:C:\jrebel\jrebel.jar -XX:MaxPermSize=256m -Xmx512M -Xss2M  -Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=5005 -jar "%SCRIPT_DIR%\sbt-launch.jar" %*
