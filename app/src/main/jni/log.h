//
// Created by 蔡锦升 on 17/9/26.
//

#ifndef ANDROIDDEMOS_AS_LOG_H
#define ANDROIDDEMOS_AS_LOG_H

#endif //ANDROIDDEMOS_AS_LOG_H

#include <android/log.h>

#define TAG		"a_jni"

#define LOGI(...)	__android_log_print(ANDROID_LOG_INFO, TAG, __VA_ARGS__)
#define LOGD(...)	__android_log_print(ANDROID_LOG_DEBUG, TAG, __VA_ARGS__)
#define LOGW(...)	__android_log_print(ANDROID_LOG_WARN, TAG, __VA_ARGS__)
#define	LOGE(...)	__android_log_print(ANDROID_LOG_ERROR, TAG, __VA_ARGS__)
