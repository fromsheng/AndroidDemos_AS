#!/usr/bin/env bash

echo 'start gradlew build'
./gradlew assembleDebug

echo 'cp so'
cp ./app/build/intermediates/ndk/debug/lib/armeabi/libhelloJni.so ./app/src/main/jniLibs/armeabi/libhelloJni.so

