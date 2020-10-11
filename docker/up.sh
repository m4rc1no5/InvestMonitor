#!/usr/bin/env bash

echo -e "Update InvestMonitor-api war..."
cp -f ../api/target/InvestMonitor-api.war api/InvestMonitor-api.war

if [ $# -gt 0 ]; then
    docker-compose up "$@"
else
    docker-compose up
fi