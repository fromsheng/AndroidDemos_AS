#include "com_artion_androiddemos_utils_JniUtils.h"

JNIEXPORT jstring JNICALL Java_com_artion_androiddemos_utils_JniUtils_getStringFromC
        (JNIEnv *env, jclass jcs) {
                                return (*env)->NewStringUTF(env, "Hello Jni From C!");
                            }