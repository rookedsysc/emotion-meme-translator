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
                    cd ${WORKSPACE}  // 프로젝트 디렉토리로 이동
                    docker-compose down
                    docker-compose up -d
                '''
            }
        }
    }
}
//Docker 그룹에 Jenkins 사용자 추가:
bash
RUN groupadd -f docker
RUN usermod -aG docker jenkins
