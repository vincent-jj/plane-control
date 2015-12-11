#!/bin/bash
rm -rf ../../../build/*
#rm -rf build-tst/*
if (javac -Xlint:unchecked -d ../../../build/ ../util/*.java -cp ../../)
then
    echo "sim.util compiled"
    if (javac -Xlint:unchecked -cp ../../../build/ -d ../../../build ../engine/*.java -cp ../../)
    then
	echo "sim.engine compiled"
		if(javac -Xlint:unchecked -cp ../../../build -d ../../../build ./hud/*/*.java ./hud/*.java)
		then
	    echo "sim.gui.hud compiled"
	    	if(javac -Xlint:unchecked -cp ../../../build -d ../../../build ./map/shape/*.java ./map/*.java)
	    	then
			echo "sim.gui.map compiled"
				if(javac -Xlint:unchecked -cp ../../../build -d ../../../build ./Window.java)
				then
				echo "Window.java compiled"
					if(javac -Xlint:unchecked -cp ../../../build -d ../../../build ./GUI.java)
					then 
					echo "GUI compiled"
						if(javac -Xlint:unchecked -cp ../../../build -d ../../../build ./../*.java)
						then
						echo "All compiled"	
								if (! java -cp ../../../build sim.Main)
								then
		    					echo "Error while tryind to execute Window"
								else
		    					echo "All good"
								fi
						else 
						echo "Error"
					fi
					else 
					echo "Error while compiling sim.gui.GUI.java"
					fi
				else
				echo "Error while compiling sim.gui.Window.java"
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

