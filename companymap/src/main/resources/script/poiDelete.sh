#!/bin/bash

url="http://api.map.baidu.com/geodata/v3/poi/delete"
ak="ABrY0GjICSfXogULBMBdq3uK"
table_id="132060"

for i in $(seq 0 1)
do
    wget --post-data="title=$i&is_total_del=1&geotable_id=$table_id&ak=$ak" $url -a delete.log
    #sleep 1
done
