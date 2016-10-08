LOCAL_PATH := $(call my-dir) #当前目录下

#
#模块一
#
#include $(CLEAR_VARS)

#LOCAL_MODULE := hello-jni
#LOCAL_LDFLAGS := -Wl,--build-id
#LOCAL_LDLIBS := \-llog \

#LOCAL_SRC_FILES := Android.mk Application.mk hello-jni.c

#include $(BUILD_SHARED_LIBRARY)

#
#模块二
#
include $(CLEAR_VARS)

LOCAL_MODULE := max-jni
LOCAL_LDFLAGS := -Wl,--build-id
LOCAL_LDLIBS := \-llog \

LOCAL_SRC_FILES := Android.mk Application.mk  Max.h Max.cpp Max-lib.cpp hello-jni.c

include $(BUILD_SHARED_LIBRARY)
