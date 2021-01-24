#!/bin/bash

mvn clean package -DskipTests
scp -i ~/Public/KeyAWS/Key081020.pem runner/target/action-runner-jar-with-dependencies.jar  ubuntu@13.212.152.111:/home/ubuntu/workspace
