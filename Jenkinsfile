pipeline {
  agent any
  stages {
    stage("build") {
      when {
        expression {
          def isJobNameMatch = "${env.JOB_NAME}".contains("PullRequestBuild")
          return isJobNameMatch
        }
      }
      steps {
        withCredentials([string(credentialsId: 'github-statuses-token', variable: 'GITHUB_TOKEN')]){
          script {
            notifyStatus('pending', 'starting gradle test.', "${GITHUB_TOKEN}")
            echo "buildを実行します"
            echo ">> PullRequestの情報を表示します。"
            echo "PR作成者： ${env.CHANGE_AUTHOR}"
            echo "Forkリポジトリ： ${env.CHANGE_FORK}"
            echo "PRブランチ： ${env.CHANGE_BRANCH}"
            echo "ターゲットブランチ： ${env.CHANGE_TARGET}" 
            try {
              sh "gradle clean"
              def springProfileActive = "${env.CHANGE_TARGET}" == 'master' ? 'ci' : 'ci'
              def gradleTestOption = "${env.GRADLE_TEST_OPTION}"
              withEnv(["SPRING_PROFILES_ACTIVE=${springProfileActive}"]) {
                sh "gradle ${gradleTestOption} test"
              }
              notifyStatus('success', 'All tests passed.', "${GITHUB_TOKEN}")
            } catch (e) {
              notifyStatus('failure', 'Some tests failed.', "${GITHUB_TOKEN}")
            } finally {
              junit "build/test-results/test/*.xml"
              archiveArtifacts "build/test-results/test/*.xml"
            }
          }
        }
      }
    }
  }
}
def notifyStatus(state, description, token) {
  // Jsonペイロード
  def payload = groovy.json.JsonOutput.toJson([
    state: state,
    context: 'gradle test',
    description: description,
    target_url: env.BUILD_URL,
  ])
  def revision = sh(script: 'git rev-parse HEAD', returnStdout: true).trim()
  // curl で POST
  sh """
    curl -s -X POST https://mygithub.ritscm.xyz/api/v3/repos/cotos/Dentos_CommonLibs/statuses/${revision} \
      -H "Authorization: token ${GITHUB_TOKEN}" \
      -H "Content-Type: application/json" \
      -d '${payload}'
  """
}
