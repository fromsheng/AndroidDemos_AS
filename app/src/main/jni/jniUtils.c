#include "com_artion_androiddemos_utils_JniUtils.h"
#include "log.h"

JNIEXPORT jstring JNICALL Java_com_artion_androiddemos_utils_JniUtils_getStringFromC
        (JNIEnv *env, jclass jcs) {

                                LOGE("Hello Jni From C");
                                return (*env)->NewStringUTF(env, "Hello Jni From C!");
                            }