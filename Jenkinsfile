pipeline {
    agent any
    tools {
        jdk 'JDK11'
        maven 'Maven3'
    }
    
    triggers {
        githubPush()   
    }

    stages {

        stage('Clone') {
            steps {
                git branch: 'develop', url: 'https://github.com/dohaAz/coiff'
            }
        }
        // test for webhook
        

        stage('Build & Test with Coverage') {
            steps {
                bat 'mvn clean verify'
            }
        }

        stage('SonarQube Analysis') {
            environment {
                SONAR_TOKEN = credentials('sonar-token')
            }
            steps {
                withSonarQubeEnv('SonarQube') {
                    bat """
                        mvn clean verify sonar:sonar ^
                        -Dsonar.projectKey=coiff ^
                        -Dsonar.projectName="coiff" ^
                        -Dsonar.host.url=http://localhost:9000 ^
                        -Dsonar.token=%SONAR_TOKEN%
                    """
                }
            }
        }
    }
//
    post {
        success {
            echo 'Build et analyse terminés avec succès !'
        }
        failure {
            echo 'Échec du build ou des tests.'
        }
    }
}
