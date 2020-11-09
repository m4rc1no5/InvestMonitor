# InvestMonitor

## Information about the application

This application is being base on https://github.com/awsdocs/aws-lambda-developer-guide/tree/main/sample-apps/blank-java

## How to use it

### Workers

- BestProfitCalculatorWorker - calculate the best parameters for entry and exit subfund
- PkoTfiProfitWorker - shows profits for subfund
- PkoTfiArithmeticMovingAverageWorker - calculate an actual situation for subfund

### Setup

To create a new bucket for deployment artifact, run `1-create-bucked.sh`.

### Deploy a new version to AWS Lambda

To deploy the application, run `2-deploy.sh`.

### Test

To invoke the function, run `3-invoke.sh`.

### Cleanup

To delete the application, run `4-cleanup.sh`.