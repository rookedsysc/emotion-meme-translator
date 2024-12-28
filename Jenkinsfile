pipeline {
    agent any
    stages {
        stage('Checkout') {
            steps {
                git branch: 'main',
                    url: 'https://github.com/rookedsysc/emotion-meme-translator.git',
                    credentialsId: 'jjeongmin98_git'
            }
        }
        stage('Deploy') {
            steps {
                sh '''
                    cd /home/meme/var/app  // 프로젝트 디렉토리로 이동
                    docker-compose down
					docker-compose build
                    docker-compose up -d
                '''
            }
        }
    }
}
