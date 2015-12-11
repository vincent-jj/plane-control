#!/bin/sh
if [ $0 != './compile.sh' ];
then
    echo "This script is suposed to be launched from root dir (above src dir)."
fi

if [ ! -e sphinx ];
then
    echo "sphinx dir not found. Have you run ./getSphinx.sh ?"
    echo "trying to compile anyway"
fi
javac -d build -cp src:sphinx/jsapi.jar:sphinx/sphinx4.jar:sphinx/jsapi-1.0-base.jar:sphinx/WSJ_8gau_13dCEP_16k_40mel_130Hz_6800Hz.jar:sphinx/Dialog.jar src/sim/Main.java
