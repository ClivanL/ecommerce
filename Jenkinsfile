pipeline {
    agent {
        docker {
            image 'maven:3-alpine' 
            args '-v /root/.m2:/root/.m2' 
        }
    }
    stages {
        stage('Build') { 
            steps {
                dir('cart'){
                  sh 'mvn clean install -DskipTests=true' 
               }
                dir('favourite'){
                  sh 'mvn clean install -DskipTests=true' 
               }
                dir('item'){
                  sh 'mvn clean install -DskipTests=true' 
               }
                dir('main'){
                  sh 'mvn clean install -DskipTests=true' 
               }
                dir('purchaseLog'){
                  sh 'mvn clean install -DskipTests=true' 
               }
                dir('user'){
                  sh 'mvn clean install -DskipTests=true' 
               }
            }
        }
    }
}