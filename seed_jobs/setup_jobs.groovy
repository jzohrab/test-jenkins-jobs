// Use job DSL.

folder('Testing') {
    description('Testing.')
}


// Set up the params on the actual job to execute.
pipelineJob('Testing/parameterized-job') {
  description("Parameterized job")

  // Declaring the params this way ensures that the params are available
  // immediately, even before the Jenkinsfile.parameterized file is called.
  // This avoids the issue "Initial run of parameterized pipeline always fails the first time"
  // (ref https://issues.jenkins-ci.org/browse/JENKINS-40235)
  parameters {
    stringParam('prmBRANCH', 'develop', 'Branch to run on')
  }
  definition {
    cpsScm {
      scm {
        git {
          remote {
            url('https://github.com/jeff-zohrab/test-jenkins-jobs.git')
            credentials('github-ci')
          }
          branches('master')
          scriptPath('Jenkinsfile.parameterized')
          extensions { }  // required as otherwise it may try to tag the repo when run
        }
      }
    }
  }
  // Lightweight checkout for pipeline job,
  // so master can get the Jenkinsfile using an API call,
  // instead of a full GitHub checkout.
  // ref https://stackoverflow.com/questions/43714739/jenkins-how-to-enable-checkout-lightweight-for-pipelinejob.
  configure {
     it / definition / lightweight(true)
  }

  // Keep history of all runs.
  //  logRotator {
  //  numToKeep(2)
  //  artifactNumToKeep(2)
  // }
}


pipelineJob('Testing/call-parameterized-job') {
  description("Call parameterized job on schedule")

  // Run every 10 mins at 11:00 am, M-F.
  triggers { cron('H/10 11 * * 1-5') }

  definition {
    cpsScm {
      scm {
        git {
          remote {
            url('https://github.com/jeff-zohrab/test-jenkins-jobs.git')
            credentials('github-ci')
          }
          branches('master')
          scriptPath('Jenkinsfile.call_parameterized')
          extensions { }  // required as otherwise it may try to tag the repo when run
        }
      }
    }
  }
  // Lightweight checkout for pipeline job,
  // so master can get the Jenkinsfile using an API call,
  // instead of a full GitHub checkout.
  // ref https://stackoverflow.com/questions/43714739/jenkins-how-to-enable-checkout-lightweight-for-pipelinejob.
  configure {
     it / definition / lightweight(true)
  }
  logRotator {
    numToKeep(2)
    artifactNumToKeep(2)
  }
}
