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
                    cd $PROJECT_PATH/release-resources/docker; docker-compose run -v ${PGP_SECRETKEY}:${PGP_SECRETKEY} -e PGP_SECRETKEY=keyfile:${PGP_SECRETKEY} -e PGP_PASSPHRASE=literal:${PGP_PASSPHRASE} java-sdk-builder
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