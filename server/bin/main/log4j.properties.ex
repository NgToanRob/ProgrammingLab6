# Declare root logger, appender file and stdout
log4j.rootLogger = DEBUG, stdout, file

# Print log info to console
log4j.appender.stdout = org.apache.log4j.ConsoleAppender
log4j.appender.stdout.Threshold = debug
log4j.appender.stdout.Target = System.out
log4j.appender.stdout.layout = org.apache.log4j.PatternLayout
log4j.appender.stdout.layout.ConversionPattern = %d{yyyy-MM-dd HH:mm:ss} %-5p %c{1}:%L - %m%n

# Print log info to file
log4j.appender.file = org.apache.log4j.RollingFileAppender
log4j.appender.file.File = logs/%d{yyyy-MM-dd}_logApp.log
log4j.appender.file.Threshold = debug
log4j.appender.file.MaxFileSize = 5MB
log4j.appender.file.MaxBackupIndex = 10
log4j.appender.file.layout = org.apache.log4j.PatternLayout
log4j.appender.file.layout.ConversionPattern = %d{yyyy-MM-dd HH:mm:ss} %-5p %c{1}:%L - %m%n