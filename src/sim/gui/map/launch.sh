#!/bin/bash
rm -rf ../../../../build/*
#rm -rf build-tst/*
if (javac -d ../../../../build/ ../../util/*.java)
then
    echo "sim.util compiled"
    if (javac -cp ../../../../build/ -d ../../../../build ../../engine/*.java)
    then
	echo "sim.engine compiled"
	if(javac -cp ../../../../build -d ../../../../build ../../gui/hud/*/*.java ../../gui/hud/*.java)
	then
	    echo "sim.gui.hud compiled"
	    if(javac -cp ../../../../build -d ../../../../build ../../gui/map/*.java)
	    then
		echo "sim.gui.map compiled"
	
		if (! java -cp ../../../../build sim.gui.map.Window)
		then
		    echo "Error while tryind to execute Window"
		else
		    echo "All good"
		fi
	    else
		echo "Error while compiling sim.gui.map"
	    fi
	else
	    echo "Error while compiling sim.gui.hud"
	fi
    else
	echo "Error while compiling sim.engine"
    fi
else
    echo "Error compiling sim.util"
fi

