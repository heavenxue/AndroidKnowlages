
#include <stdio.h>
#include <jni.h>
#include "../../../../../../android/sdk/ndk/21.3.6528147/toolchains/llvm/prebuilt/darwin-x86_64/sysroot/usr/include/jni.h"


/**
 * 引起 crash
 */
void Crash() {
    volatile int *a = (int *) (NULL);
    *a = 1;
}

extern "C"
JNIEXPORT void JNICALL
Java_com_lixue_admin_ndk_MainNdkActivity_crash(JNIEnv *env, jobject thiz) {
// TODO: implement crash()
    Crash();
}