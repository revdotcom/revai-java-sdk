def COLOR_MAP = ['SUCCESS': 'good', 'FAILURE': 'danger', 'UNSTABLE': 'danger', 'ABORTED': 'danger']

pipeline {
    agent {
        node { label 'linux-ci' }
    }
    environment {
        PGP_PASSPHRASE = credentials('revai-java-sdk/maven-pgp-signing/passphrase')
        DANTEST = credentials('dantest/hiskey')
    }
    stages {
        stage('Sign artifacts and publish') {
            steps {
                ansiColor('xterm') {
                    sh """
                    docker build --tag revai-sdk-release
                    docker run mvn clean deploy -Dmaven.test.skip=true -Dsonatype.username=yourusername -Dsonatype.password=yourpassword -P release
                    echo "hello ${PGP_PASSPHRASE} hello ${DANTEST} hi"
                    
                    """
                }
            }
        }
    }
}

def getSlackMessage() {
    return """
${env.JOB_NAME} - ${env.BUILD_NUMBER} ${currentBuild.currentResult} after ${currentBuild.durationString}
"""
}