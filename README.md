# IN DEVELOPMENT - Java CLI Password Manager
## Description
This is intended as a vehicle for me to be able to learn Java as well as apply new skills I have learnt since developing the python version of this project. Overall this is intended to be more usable and robust than my python version.

## Features
- Command Line Application
- Two types of password generator
  1. Random Char Password: Generates a password of random letters and symbols.
  2. Three Word Password: Intended as an easier to remember password gen based on the NCSC guidelines

## Next to implemented - Not in order
- Edit Existing Data
- Data Encryption
  - Already partially implemented this is planned to be based on RSA. Work needs to be done on generating and allocating keys for encryption and decryption.
- Cloud Storage
  - The user should be able to access their data from any device.

## Bugs
  1. An exception is thrown when the last isntance of data within the storage file attempted to be deleted.
  2. The user input function does not return/terminate. 
