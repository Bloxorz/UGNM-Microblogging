mkdir temp
javac -target 1.7 -source 1.7 -cp "lib/*" -sourcepath "src/main" -d "temp" src/main/i5/las2peer/services/servicePackage/ServiceClass.java
rmdir temp