#!/usr/bin/env bash

docker-compose -f docker-compose.yml rm -f
docker-compose -f docker-compose.yml up
docker-compose -f docker-compose.yml rm -f

