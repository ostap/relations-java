#!/bin/sh

DEST=target
VERS=alpha

compile()
{
    mkdir -p $DEST
    javac -Xlint:unchecked -d $DEST src/*.java test/*.java
}

cmd=$1
case $cmd in
    dist)
        compile
        cd $DEST
        jar cvf bandij-$VERS.jar org
        ;;
    test)
        compile
        java -cp $DEST org.bandilab.RelationTest
        ;;
    clean)
        rm -rf $DEST
        ;;
    *)
        echo "usage: ctl <dist|clean|test>"
        ;;
esac
