# Environment and Prerequisites
- ubuntu 22.04
- java 17 or higher
- curl
- docker

# Build

To Build the docker image:

```
# Build and generate jar
./gradlew clean build bootJar
#  tag and build local docker image
docker build -t chat-gpt-api .
```

# Launch

To run docker service with persistent volume mapped between current working directory ```data``` and docker container's ```/app/csv-data``` :

```
docker run -it -v $PWD/data:/app/csv-data -p 8085:8085 chat-gpt-api:latest
```

# Test 

In order to query the docker service api use the following command:

```
curl -X POST http://localhost:8085/chat-gpt/api/ask -H 'Content-Type: application/json' -d '{ "question": "why people are different?" }'
```

# Results

The results can be found in the ```data``` folder

The file will be in read mode only due to volume mount permission settings, so to remove it you need to run:

```
sudo rm -R ./data/questions_answers.csv
```


# Optional 

A jenkins file was just created for the three steps to compile, generate jar, build docker image and run it.


# Notes

OpenAI rotates API keys thet they assume leaked publicly. You may want to change the API Key in case of the current one doesn't work anymore. You can do that [here](src/main/java/com/izicap/chatgptintegration/service/ChatGptQueryApi.java)
