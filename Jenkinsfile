pipeline {

agent any

tools {
    jdk 'JDK17'
    maven 'Maven3'
}

environment {
    IMAGE_NAME = "daya9096/shipment-service"
    IMAGE_TAG  = "${BUILD_NUMBER}"
}

stages {

    stage('Checkout') {
        steps {
            checkout scm
        }
    }

    stage('Build') {
        steps {
            sh 'mvn clean package'
        }
    }

    stage('Test') {
        steps {
            sh 'mvn test'
        }
    }

    stage('SonarQube Analysis') {
        steps {
            withSonarQubeEnv('sonarqube') {
                sh '''
                mvn sonar:sonar \
                -Dsonar.projectKey=shipment-service \
                -Dsonar.projectName=shipment-service
                '''
            }
        }
    }

    stage('Trivy File Scan') {
        steps {
            sh 'trivy fs .'
        }
    }

    stage('Docker Build') {
        steps {
            sh '''
            docker build \
            -t ${IMAGE_NAME}:${IMAGE_TAG} .
            '''
        }
    }


    stage('Docker Push') {
        steps {
            withCredentials([
                usernamePassword(
                    credentialsId: 'dockerhub-creds',
                    usernameVariable: 'DOCKER_USER',
                    passwordVariable: 'DOCKER_PASS'
                )
            ]) {
                sh '''
                echo $DOCKER_PASS | docker login -u $DOCKER_USER --password-stdin

                docker push ${IMAGE_NAME}:${IMAGE_TAG}

                docker tag ${IMAGE_NAME}:${IMAGE_TAG} ${IMAGE_NAME}:latest

                docker push ${IMAGE_NAME}:latest
                '''
            }
        }
    }

    stage('Deploy') {
    steps {
        sh '''
        docker-compose down || true
        docker-compose up -d
        '''
    }
}
}


}

