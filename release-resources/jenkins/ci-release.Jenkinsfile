def COLOR_MAP = ['SUCCESS': 'good', 'FAILURE': 'danger', 'UNSTABLE': 'danger', 'ABORTED': 'danger']

pipeline {
    agent {
        node { label 'linux-ci' }
    }
    environment {
        PGP_PASSPHRASE = credentials('revai-java-sdk/maven-pgp-signing/passphrase')
        PGP_SECRETKEY = credentials('revai-java-sdk/maven-pgp-signing/private-key')
    }
    stages {
        stage('Sign artifacts and publish') {
            steps {
                ansiColor('xterm') {
                    sh """
                    echo "hello ${PGP_PASSPHRASE} hello"
                    
                    """
                }
            }
        }
    }
    post {
        always {
            changeOwner()
            slackSend channel: '#revai-alerts-prod', color: COLOR_MAP[currentBuild.currentResult], message: getSlackMessage()
        }
    }
}

def changeOwner() {
    ansiColor('xterm') {
        sh """
        cd $PROJECT_PATH; docker-compose run --rm change-owner
        """
    }
}

def getSlackMessage() {
    return """
${env.JOB_NAME} - ${env.BUILD_NUMBER} ${currentBuild.currentResult} after ${currentBuild.durationString}
"""
}