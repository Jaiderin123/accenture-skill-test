provider "aws" {
  # validate env to use local or prod
  access_key = var.aws_access_key
  secret_key = var.aws_secret_key
  region     = var.aws_region

  # only for local env
  s3_use_path_style           = var.is_local
  skip_credentials_validation = var.is_local
  skip_metadata_api_check     = var.is_local
  skip_requesting_account_id  = var.is_local

  # rds endpoint for localstack
  endpoints {
    //rds = var.rds_endpoint
    sqs  = "http://localhost:4566"
    sts = "http://localhost:4566"
  }
}

/*resource "aws_db_instance" "accenture_franchise" {
  allocated_storage   = 10
  db_name             = var.rds_db_name
  engine              = "postgres"
  instance_class      = "db.t3.micro"
  username            = var.rds_user_db
  password            = var.rds_user_pass
  skip_final_snapshot = true
  apply_immediately   = true
}*/

# TO DEPLOY ON PROD IS NECESSARY CONFIG ALL THE CLOUD SECURITY.


//MOCK IaC
resource "aws_sqs_queue" "accenture_simulation" {
  name = "accenture-franchise-mock-queue"

  tags = {
    Environment = var.environment
    Project     = "Accenture-Skill-Test"
  }
}