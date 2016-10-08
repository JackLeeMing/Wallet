//
// Created by HelloMac on 2016/9/26.
//

#include <jni.h>
#include "Max.h"
extern "C" {
  jint Java_work_android_smartbow_com_wallet_copy_CMakeActivity_maxFromJNI
  ( JNIEnv* env,jobject object,jint a,jint b)
  {
      jmethodID instanceMethodId;
      instanceMethodId = (*env).GetMethodID(env,object,"","");
      return max(a,b);
  }

}