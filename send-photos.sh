#!/bin/bash

input="photos.txt"
while IFS= read -r line || [[ -n "$line" ]]
do
  curl \
    --location --request POST 'localhost:8000/photo' \
    --header 'Content-Type: application/json' \
    --data-raw "$line"
done < "$input"