#!/bin/sh
if [ $0 != './launch.sh' ];
then
    echo "This script is suposed to be launched from root dir (above src dir)."
fi

java -cp build sim.Main KORD
