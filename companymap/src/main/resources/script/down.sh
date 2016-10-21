#!/bin/bash

up=20
let i=1
for i in $(seq 1 20)
do
    wget --post-data="first=false&pn=$i&sortField=0&havemark=0" http://www.lagou.com/gongsi/2-0-0.json
    sleep 2
done
