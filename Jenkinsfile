node {
   stage('Preparation') { // for display purposes
      // Get some code from a GitHub repository
      git 'https://github.com/andvicente/Cartoes-CodeCoverage.git'
      notifyBuild(currentBuild.result)
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

    post {
       notifyBuild(currentBuild.result)
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