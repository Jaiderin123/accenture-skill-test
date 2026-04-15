variable "aws_region" {
  description = "AWS region"
  type        = string
  default     = "us-east-1"
}

variable "aws_access_key" {
  description = "AWS access key"
  type        = string
  default     = "test"
}

variable "aws_secret_key" {
  description = "AWS secret key"
  type        = string
  default     = "test"
}

variable "environment" {
  description = "Deploy env (local, dev, prod)"
  type        = string
}

variable "rds_endpoint" {
  description = "only with localstack"
  type        = string
  default     = null
}

variable "rds_db_name" {
  type        = string
  default     = null
}

variable "rds_user_db" {
  type        = string
  default     = null
}

variable "rds_user_pass" {
  type        = string
  default     = null
}

variable "is_local" {
  description = "var to validate the enviroment"
  type        = bool
  default     = false
}