node {
    try {
       notifyBuildDetails('STARTED')
       stage('Preparation') { // for display purposes
          // Get some code from a GitHub repository
          git 'https://github.com/andvicente/Cartoes-CodeCoverage.git'
          populateGlobalVariables()

       }
       stage('Build') {
          // Run the maven build
          if (isUnix())
             sh "./gradlew clean build test jacocoTestReport"

       }
       stage('Production'){
           input message: 'Mandar para producao?', ok: 'Sim', submitterParameter: 'aprovador'
       }
       stage('Results') {
        junit 'build/test-results/*.xml'
       }
    } catch (e) {
        // If there was an exception thrown, the build failed
        currentBuild.result = "FAILED"
        throw e
      } finally {
        // Success or failure, always send notifications
        notifyBuildDetails(currentBuild.result)
    }
}

def notifyBuild(String buildStatus = 'STARTED') {
      // build status of null means successful
      buildStatus =  buildStatus ?: 'SUCCESSFUL'

      // Default values
      def color = 'danger'
      def subject = "${buildStatus}: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]'"
      def summary = "${subject} (${env.BUILD_URL})"

      // Override default values based on build status
      if (buildStatus == 'STARTED') {
        color = 'good'
      } else if (buildStatus == 'SUCCESSFUL') {
        color = 'good'
      } else if (buildStatus == 'FAILURE'){
        color = 'danger'
      } else {
        color = 'warning'
      }

      // Send notifications
      slackSend (color: color, message: summary)
}


def author = ""
def message = ""
def testSummary = ""
def total = 0
def failed = 0
def skipped = 0

def populateGlobalVariables = {
    getLastCommitMessage()
    getGitAuthor()
    testSummary = getTestSummary()
}


def getGitAuthor = {
    def commit = sh(returnStdout: true, script: 'git rev-parse HEAD')
    author = sh(returnStdout: true, script: "git --no-pager show -s --format='%an' ${commit}").trim()
}

def getLastCommitMessage = {
    message = sh(returnStdout: true, script: 'git log -1 --pretty=%B').trim()
}

@NonCPS
def getTestSummary = { ->
    def testResultAction = currentBuild.rawBuild.getAction(AbstractTestResultAction.class)
    def summary = ""

    if (testResultAction != null) {
        total = testResultAction.getTotalCount()
        failed = testResultAction.getFailCount()
        skipped = testResultAction.getSkipCount()

        summary = "Passed: " + (total - failed - skipped)
        summary = summary + (", Failed: " + failed)
        summary = summary + (", Skipped: " + skipped)
    } else {
        summary = "No tests found"
    }
    return summary
}



def notifyBuildDetails(String buildStatus = 'STARTED'){
    // build status of null means successful
    buildStatus =  buildStatus ?: 'SUCCESSFUL'

    // Override default values based on build status
    if (buildStatus == 'STARTED') {
        color = 'good'
    } else if (buildStatus == 'SUCCESSFUL') {
        color = 'good'
    } else if (buildStatus == 'FAILURE'){
        color = 'danger'
    } else {
        color = 'warning'
    }


    notifySlack("", [
                [
                    title: "${env.JOB_NAME}, build #${env.BUILD_NUMBER}",
                    title_link: "${env.BUILD_URL}",
                    color: ${color},
                    author_name: "${author}",
                    text: "${buildStatus}",
                    fields: [
                        [
                            title: "Branch",
                            value: "${env.GIT_BRANCH}",
                            short: true
                        ],
                        [
                            title: "Test Results",
                            value: "${testSummary}",
                            short: true
                        ],
                        [
                            title: "Last Commit",
                            value: "${message}",
                            short: false
                        ],
                        [
                            title: "Error",
                            value: "${e}",
                            short: false
                        ]
                    ]
                ]
    ])

}