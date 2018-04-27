# Experiments.

## Scheduled job calling another job with parameters.

`Jenkinsfile.call_parameterized` calls `Jenkinsfile.parameterized` on a cron.

The cron setup, and the params for the parameterized job, are set up in `seed_jobs/seed_jobs.groovy`.

Set up:

* create a `testing_seed_job` Pipeline job in Jenkins, using the
  `seed_jobs/Jenkinsfile` for the job.
* run the `testing_seed_job` job in Jenkins (approve the script and
  re-run as needed)
* Jenkins will have a new `Testing` folder, and 2 new jobs.  Run
  `Testing/call-parameterized-job`, and then check the results in
  `Testing/parameterized-job`.
