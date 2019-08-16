


package com.brandontrautmann.lintrules

import com.android.tools.lint.client.api.IssueRegistry
import com.android.tools.lint.detector.api.CURRENT_API
import com.android.tools.lint.detector.api.Issue
import java.util.*

class MyIssueRegistry : IssueRegistry() {

    override val issues: List<Issue> = Collections.singletonList(PreventingActivityRestartDetector.ISSUE)

    override val api: Int = CURRENT_API
}


