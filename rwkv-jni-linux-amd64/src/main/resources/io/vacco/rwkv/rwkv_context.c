#include <jni.h>
#include "rwkv.h"

// Guess who wrote this stuff... https://youtu.be/G2SuBGH8ilQ

JNIEXPORT jlong JNICALL Java_io_vacco_rwkv_RkContext_rwkvInitFromFile(JNIEnv *env, jobject obj, jstring modelFilePath, jint numThreads) {
    const char *modelFilePathChars = (*env)->GetStringUTFChars(env, modelFilePath, 0);
    struct rwkv_context *ctx = rwkv_init_from_file(modelFilePathChars, (uint32_t) numThreads);
    (*env)->ReleaseStringUTFChars(env, modelFilePath, modelFilePathChars);
    return (jlong) ctx;
}

JNIEXPORT jboolean JNICALL Java_io_vacco_rwkv_RkContext_rwkvEval(JNIEnv *env, jobject obj, jlong contextPtr, jint token, jfloatArray stateIn, jfloatArray stateOut, jfloatArray logitsOut) {
    float *stateInPtr = NULL;
    float *stateOutPtr = NULL;
    float *logitsOutPtr = NULL;

    if (stateIn != NULL) {
        stateInPtr = (*env)->GetFloatArrayElements(env, stateIn, NULL);
        if (stateInPtr == NULL) {
            return JNI_FALSE;
        }
    }

    if (stateOut != NULL) {
        stateOutPtr = (*env)->GetFloatArrayElements(env, stateOut, NULL);
        if (stateOutPtr == NULL) {
            if (stateInPtr != NULL) {
                (*env)->ReleaseFloatArrayElements(env, stateIn, stateInPtr, JNI_ABORT);
            }
            return JNI_FALSE;
        }
    }

    if (logitsOut != NULL) {
        logitsOutPtr = (*env)->GetFloatArrayElements(env, logitsOut, NULL);
        if (logitsOutPtr == NULL) {
            if (stateInPtr != NULL) {
                (*env)->ReleaseFloatArrayElements(env, stateIn, stateInPtr, JNI_ABORT);
            }
            if (stateOutPtr != NULL) {
                (*env)->ReleaseFloatArrayElements(env, stateOut, stateOutPtr, JNI_ABORT);
            }
            return JNI_FALSE;
        }
    }

    jboolean result = rwkv_eval((struct rwkv_context *) contextPtr, (uint32_t) token, stateInPtr, stateOutPtr, logitsOutPtr);

    if (stateInPtr != NULL) {
        (*env)->ReleaseFloatArrayElements(env, stateIn, stateInPtr, JNI_ABORT);
    }

    if (stateOutPtr != NULL) {
        (*env)->ReleaseFloatArrayElements(env, stateOut, stateOutPtr, 0);
    }

    if (logitsOutPtr != NULL) {
        (*env)->ReleaseFloatArrayElements(env, logitsOut, logitsOutPtr, 0);
    }

    return result;
}

JNIEXPORT jint JNICALL Java_io_vacco_rwkv_RkContext_rwkvGetStateBufferElementCount(JNIEnv *env, jobject obj, jlong contextPtr) {
    return (jint) rwkv_get_state_buffer_element_count((struct rwkv_context *) contextPtr);
}

JNIEXPORT jint JNICALL Java_io_vacco_rwkv_RkContext_rwkvGetLogitsBufferElementCount(JNIEnv *env, jobject obj, jlong contextPtr) {
    return (jint) rwkv_get_logits_buffer_element_count((struct rwkv_context *) contextPtr);
}

JNIEXPORT void JNICALL Java_io_vacco_rwkv_RkContext_rwkvFree(JNIEnv *env, jobject obj, jlong contextPtr) {
    rwkv_free((struct rwkv_context *) contextPtr);
}

JNIEXPORT jboolean JNICALL Java_RWKVContext_rwkvQuantizeModelFile(JNIEnv *env, jclass cls, jstring modelFilePathIn, jstring modelFilePathOut, jstring formatName) {
    const char *modelFilePathInChars = (*env)->GetStringUTFChars(env, modelFilePathIn, 0);
    const char *modelFilePathOutChars = (*env)->GetStringUTFChars(env, modelFilePathOut, 0);
    const char *formatNameChars = (*env)->GetStringUTFChars(env, formatName, 0);

    jboolean result = rwkv_quantize_model_file(modelFilePathInChars, modelFilePathOutChars, formatNameChars);

    (*env)->ReleaseStringUTFChars(env, modelFilePathIn, modelFilePathInChars);
    (*env)->ReleaseStringUTFChars(env, modelFilePathOut, modelFilePathOutChars);
    (*env)->ReleaseStringUTFChars(env, formatName, formatNameChars);

    return result;
}

JNIEXPORT jstring JNICALL Java_io_vacco_rwkv_RkContext_rwkvGetSystemInfoString(JNIEnv *env, jclass cls) {
    const char *systemInfoString = rwkv_get_system_info_string();
    jstring result = (*env)->NewStringUTF(env, systemInfoString);
    return result;
}
