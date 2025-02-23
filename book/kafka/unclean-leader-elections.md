# Unclean Leader Election
------
## unclean.leader.election.enable
- If all your In Sync Replicas go offline (but you still have out of sync replicas up), you have the following option:
  - Wait for an ISR to come back online (default)
  - Enable `unclean.leader.election.enable=true` and start producing to non ISR partitions
- If you enable `unclean.leader.election.enable=true`, you improve availability, but you will lose data because other messages on ISR will be discarded when they come back online and replicate data from the new leader.
- Overall, this is a very dangerous setting, and its implications must be understood fully before enabling it.
- Use cases include metrics collection, log collection, and other cases where data loss is somewhat acceptable, at the trade-off of availability.