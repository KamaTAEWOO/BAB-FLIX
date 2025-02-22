package com.meronacompany.bab_flix

import timber.log.Timber

class CustomDebugTree(private val appName: String) : Timber.DebugTree() {

    override fun createStackElementTag(element: StackTraceElement): String {
        // 파일명, 라인 번호, 메서드명을 태그로 설정
        return "$appName - ${element.fileName}:${element.lineNumber}#${element.methodName}"
    }
}
