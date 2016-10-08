//
// Created by HelloMac on 2016/9/26.
//

#include <jni.h>
#include "Max.h"
extern "C" {
jint Java_work_android_smartbow_com_wallet_copy_CMakeActivity_maxFromJNI
        ( JNIEnv* env,jobject object,jint a,jint b)
{
    return max(a,b);
}

}

//JNIEXPORT jint JNICALL Java_work_android_smartbow_com_wallet_copy_CMakeActivity_maxFromJNI
//        (JNIEnv* env, jobject thiz, jint a, jint b)
//{
//    return max(a,b);
//}