def call(body) {
    def pipelineParams= [:]
    body.resolveStrategy = Closure.DELEGATE_FIRST
    body.delegate = pipelineParams
    body()

    pipeline {
        agent any
        stages {
            stage('step 1') {
                steps {
                    script {
                        echo "step 1 complete! ${pipelineParams.param1}"
                    }
                }
            }
            stage('step 2') {
                steps {
                    script {
                        echo "step 2 complete! ${pipelineParams.param2}"
                    }
                }
            }
        }            
    }
}