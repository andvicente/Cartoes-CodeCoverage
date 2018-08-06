node {
    try{
           notifyBuild('STARTED')
           def notifySlackGroovy = load("notifySlack.groovy")
           stage('Preparation') { // for display purposes
              // Get some code from a GitHub repository
              git 'https://github.com/andvicente/Cartoes-CodeCoverage.git'
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
               notifySlackGroovy.call(currentBuild.result)
       }

       post {
           always {
                // By @danielschaff
                // https://danielschaaff.com/2018/02/09/better-jenkins-notifications-in-declarative-pipelines/
                notifySlackGroovy.call(currentBuild.result)
                notifySlack currentBuild.result
           }
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