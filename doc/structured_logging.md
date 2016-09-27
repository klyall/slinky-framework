# Structured Logging


## JSON Logging

Being able to create log messages in JSON format is a good idea when you want to send the messages to an external system for processing such as Elastic Search or Splunk. 

Having the messages in JSON format means that the log messages do not need to be parsed as the content is already defined in JSON fields.

To output the logs in JSON form enable the `json_logging` Spring profile in the [usual way](http://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-profiles.html). 

For example:
```
mvn spring-boot:run -Dspring.profiles.active=json_logging
```