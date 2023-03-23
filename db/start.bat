@java -cp h2-2.1.214.jar org.h2.tools.Server -tcp -baseDir . -tcpPassword 123456
@if errorlevel 1 pause