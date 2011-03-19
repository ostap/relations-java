#!/bin/sh

out=target

compile()
{
    mkdir -p $out
    javac -Xlint:unchecked -d $out src/*.java test/*.java
}

cmd=$1
case $cmd in
    dist)
        compile
        ;;
    test)
        compile
        java -cp $out org.bandilab.RelationTest
        ;;
    clean)
        rm -rf $out
        ;;
    *)
        echo "usage: ctl <dist|clean|test>"
        ;;
esac
