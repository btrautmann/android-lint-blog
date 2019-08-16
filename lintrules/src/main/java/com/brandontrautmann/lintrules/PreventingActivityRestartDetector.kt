package com.brandontrautmann.lintrules

import com.android.SdkConstants
import com.android.tools.lint.detector.api.*
import org.w3c.dom.Element

class PreventingActivityRestartDetector : Detector(), XmlScanner {

    companion object {

        val ISSUE: Issue = Issue.create(
            // used in @SuppressLint warnings etc
            id = "PreventingActivityRestart",
            // Title -- shown in the IDE's preference dialog, as category headers in the
            // Analysis results window, etc
            briefDescription = "Preventing Activity restarts using configChanges",
            // Full explanation of the issue; you can use some markdown markup such as
            // `monospace`, *italic*, and **bold**.
            explanation = """
                Indicating that your Activity will handle configuration changes itself is usually \
                done to avoid the challenges that come with the Activity lifecycle; however it often \
                leads to poor user experience. Instead, allow the system to handle the configuration \
                change and use best practices to restore the state of your UI.
                """.trimMargin(),
            category = Category.CORRECTNESS,
            priority = 7,
            severity = Severity.ERROR,
            implementation = Implementation(
                PreventingActivityRestartDetector::class.java,
                Scope.MANIFEST_SCOPE
            )
        )

        const val CONFIG_CHANGES_ATTRIBUTE = "android:configChanges"
    }

    override fun getApplicableElements(): Collection<String>? {
        return listOf(SdkConstants.TAG_ACTIVITY) // "activity"
    }

    override fun visitElement(context: XmlContext, element: Element) {
        if (SdkConstants.TAG_ACTIVITY.equals(element.nodeName, ignoreCase = true)) {
            if (element.hasAttributes() && element.getAttributeNode(CONFIG_CHANGES_ATTRIBUTE) != null) {
                context.report(
                    issue = ISSUE,
                    location = context.getLocation(element.getAttributeNode(CONFIG_CHANGES_ATTRIBUTE)),
                    message = ISSUE.getBriefDescription(TextFormat.TEXT)
                )
            }
        }
    }
}


