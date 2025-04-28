# No Parent Classloader
This issue showcases the problem of certain groovy features (ginq in this case) not working when the parent classloader is not set.

It is deliberately without a build system and kept as simple as possible to clearly show the problem.

To run it, execute the `run.sh` script

This script will first run with a parent classloader and then without.
