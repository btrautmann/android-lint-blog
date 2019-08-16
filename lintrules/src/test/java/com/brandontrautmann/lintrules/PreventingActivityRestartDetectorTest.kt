

package com.brandontrautmann.lintrules

import com.android.tools.lint.checks.infrastructure.TestFiles.xml
import com.android.tools.lint.checks.infrastructure.TestLintTask.lint
import org.junit.Test

class PreventingActivityRestartDetectorTest {

    @Test
    fun `Using configChanges on an Activity violates rule`() {
        lint()
            .allowMissingSdk()
            .files(
                xml(
                    "AndroidManifest.xml",
                    """
                        <manifest xmlns:android="http://schemas.android.com/apk/res/android" package="com.brandontrautmann.myapplication">
                            <application>
                                <activity
                                    android:name="com.brandontrautmann.MainActivity"
                                    android:configChanges="orientation|keyboardHidden|screenSize">
                                </activity>
                            </application>
                        </manifest>
                    """
                ).indented()
            )
            .issues(PreventingActivityRestartDetector.ISSUE)
            .run()
            .expect("""
                |AndroidManifest.xml:5: Error: Preventing Activity restarts using configChanges [PreventingActivityRestart]
                |            android:configChanges="orientation|keyboardHidden|screenSize">
                |            ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
                |1 errors, 0 warnings
            """.trimMargin())
    }

    @Test
    fun `Not using configChanges on an Activity does not violate rule`() {
        lint()
            .allowMissingSdk()
            .files(
                xml(
                    "AndroidManifest.xml",
                    """
                        <manifest xmlns:android="http://schemas.android.com/apk/res/android" package="com.brandontrautmann.myapplication">
                            <application>
                                <activity android:name="com.brandontrautmann.MainActivity"/>
                            </application>
                        </manifest>
                    """
                ).indented()
            )
            .issues(PreventingActivityRestartDetector.ISSUE)
            .run()
            .expectClean()
    }
}

