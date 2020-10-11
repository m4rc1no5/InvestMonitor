#!/usr/bin/env bash
docker-compose down --rmi all
docker image prune -f