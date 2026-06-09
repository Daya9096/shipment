pipeline {

```
agent any

tools {
    jdk 'JDK17'
    maven 'Maven3'
}

environment {
    IMAGE_NAME = "YOUR_DOCKERHUB_USERNAME/shipment-service"
    IMAGE_TAG = "${BUILD_NUMBER}"
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
            -t $IMAGE_NAME:$IMAGE_TAG \
            -t $IMAGE_NAME:latest .
            '''
        }
    }

    stage('Trivy Image Scan') {
        steps {
            sh '''
            trivy image $IMAGE_NAME:latest
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

                docker push $IMAGE_NAME:$IMAGE_TAG
                docker push $IMAGE_NAME:latest
                '''
            }
        }
    }

    stage('Deploy') {
        steps {
            sh '''
            echo "Stopping old deployment..."
            docker-compose down || true

            echo "Removing unused containers..."
            docker container prune -f || true

            echo "Pulling latest image..."
            docker-compose pull

            echo "Starting application stack..."
            docker-compose up -d

            echo "Deployment completed"

            docker ps
            '''
        }
    }
}

post {

    success {
        echo 'Pipeline executed successfully!'
    }

    failure {
        echo 'Pipeline failed. Check logs.'
    }
}
```

}
