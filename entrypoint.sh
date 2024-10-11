#!/bin/bash

chroma run --host 0.0.0.0 --port 8080 --path /app/datadb &

sleep 5

cd /app/backend

./main