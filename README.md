# comparethemarket data engineering exercise

Please allocate 2-4 hours for this task and treat the exercise as writing a production application.
We'd like you to do this in Scala. 

## General instructions

We'd like you to write an application which will read events (representing a customer interaction with comparethemarket) 
from a Kafka topic, flatten the data to extract some meaningful information and write the result in the local filesystem. 
Assume the volume of events being written to the topic is extremely large and the topic has a short retention policy.

Please make sure all the dependencies are accessible through publicly available repositories.

A docker-compose for kafka is provided to quick-start your testing.

Once done, please provide the solution in a zip file.

### Part 1
Your app should read the events from a Kafka topic. The events are in json format and not all fields are necessarily 
mandatory.

### Part 2
We would like you to extract a list of companies per user from the input data.

#### Example
Input: 
```
{
  "event_type": "click_summary",
  "occurred_at": "2019-01-01T16:23:38.4707723+00:00",
  "user": "hodor",
  "clicks": [
    {
      "target": "linkId1",
      "company": "Compare the Market",
      "url": "http://www.comparethemarket.com/"
    },
    {
      "target": "linkId2",
      "company": "Example",
      "url": "http://www.example.com/"
    }
  ]
}
```

Output:
```
{
  "user": "hodor",
  "companies": [
    "Compare the Market",
    "Example"
  ]
}
``` 

### Part 3
The end result should be written as json files in the local filesystem. There should be one file per user (for 
simplicity you can assume you will always only get one event for a user), e.g. `output/clicks_{username}.json`

## Candidate's corner
Please use this section to explain your choices of libraries, your selection of a specific technology/framework 
and why you feel it is justified for this particular task, any assumptions you've made, 
any improvements you might make given more time and anything else you'd like us to know.

IMPORTANT NOTE:
Please run the following commands on the kafka docker container:
mkdir -p /output/error
chmod 666 /output
chmod 666 /output/error
 
Libraries:
- For the JSON parser the chosen library is lift-json, as it's fast and reliable, have a lot of documentation and testing.
- For Kafka, the version chosen was 2.2.0, which have a nice balance between new improvements and stability (as the latest version was just released some months ago).
  
The process was created assuming that each message is singular event type.

Given more time, some of the tunes that can be applied are:
- Get the file path and file names from an external config file, so there is no need to deploy in case of changes in the gateway.
- Improve the error handling, adding more exceptions (e.g. if there is any problem adding the file, if any of the folders does not exists)
- Error logging can be improved 
- Maybe some tweaks to tune the consumer performance (i.e. consumer buffers), but for this is necessary to test the performance with real data.
  
 To run the jar:
 java -cp ctm-data-eng-exercise-assembly-0.1.jar com.Consumer [Topic-name]  [group-name] localhost:9092  
