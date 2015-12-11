#!/bin/sh
actual_path=`pwd`
cd /tmp
wget "http://downloads.sourceforge.net/project/cmusphinx/sphinx4/1.0%20beta6/sphinx4-1.0beta6-bin.zip?r=&ts=1427292586&use_mirror=heanet" -c -O sphinx4.zip
rm -r sphinx4-1.0beta6
unzip sphinx4.zip
cd sphinx4-1.0beta6/
cd lib
/bin/sh ./jsapi.sh
mkdir $actual_path/sphinx
cp jsapi.jar $actual_path/sphinx
cp jsapi-1.0-base.jar $actual_path/sphinx
cp sphinx4.jar $actual_path/sphinx
cd $actual_path
