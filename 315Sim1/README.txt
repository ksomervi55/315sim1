CPSC 315 Simulation Assignment
Authors: Tyler Somerville and Marios Bourtzonis
This project includes a process scheduler for a small psuedo-operating system. This assignment was done for Professor Madelene Spezialetti's System Software class prompt below:

Program:  For this program, you are to write a system simulation which meets the following specifications:  An external priority is assigned to all processes entering the system.  When entering the system, jobs are designated as High Priority (HP) or Low Priority (LP).  The CPU scheduling strategy is a 3-level priority based system.  Ready queue H1, which is the High Priority (HP) job queue, has the highest priority and is scheduled non-preemptive First Come First Served.  Queues L2 and L3 are for Low Priority (LP) jobs.  Queue L2 has the second highest priority overall (and is the highest priority queue for LP jobs) and is scheduled Round Robin with a quantum designated L2quant, which will be provided in the input file for the simulation.  Queue L3 is the lowest priority overall (and is the lower priority queue for LP jobs) and is scheduled Round Robin with a quantum of L3quant, which will also be provided in the input file for the simulation.  A job is selected to run from the L2 queue only if there are no ready H1 jobs.  Similarly, a job is only selected to run from the L3 queue only if there are no ready jobs on the H1 or L2 ready queues. If a lower priority job is running when a higher priority job becomes ready, the lower priority job will NOT be preempted.  Rather, the running job will be allowed to complete its current time allotment on the CPU (i.e. will remain on the CPU until it blocks or is preempted at the end of its quantum), after which the highest priority ready job will be chosen to run.

High priority jobs will only ever be placed on the highest priority (H1) queue.  They will never be placed on or migrate onto queues L2 or L3. However, LP jobs can migrate between the L2 and L3 queues.  The aim of the migration strategy is to lower the priority of LP jobs that are “acting CPU bound” in their CURRENT CPU burst, in which “acting CPU bound” means having a CPU burst length that is larger than 3 times the L2quant, the quantum length of the L2 queue.  Thus, an LP job which enters the system is automatically placed on queue L2.  If at any point an LP job which had been chosen from the L2 queue has been preempted 3 times due to reaching the end of its quantum for the CURRENT CPU burst, the job will be demoted to the L3 queue. A low priority job returning from being blocked will be placed on queue L2, since, on return, it has not been preempted in its current CPU burst.

As an example, assume a low priority (LP) job A is on queue L2 and has a current remaining CPU burst size of 12, and a low priority (LP) job B on queue L3 with current remaining CPU burst of size 13. Further, assume there are no HP jobs ready and there are no other ready jobs. Finally assume that the quantum for queue L2 is 2. The CPU is currently idle so job A is selected to run and runs for its first quantum of 2.  At the end of its quantum, since there are no other higher or equal priority jobs ready, job A would be assigned another quantum of 2. At the end of that quantum, it will still have 8 units left in its current CPU burst. However, assume that at this point, a High Priority job, C, is waiting on the H1 ready queue.  C will be chosen to run and Job A will be placed onto queue L2.  After job C, completes, Job B will again be chosen to run and preempted again (remember, it is still on its original CPU burst).  However, at this point, it had used up an entire quantum 3 consecutive times while working on a single CPU burst, so it will be demoted. Since there is a ready job B on queue L3, job A will be removed from the CPU and placed onto the back of the L3 queue and, job B will be chosen to run.  Remember however, being on the L3 queue is only designed to demote a job that seems to be currently experiencing a long CPU burst.  So be careful that when an LP job comes back from being blocked, it will always be placed on the L2 queue. Again, it only migrates to being placed on the L3 queue if the current burst length is more than 3 times the quantum associated with the L2 queue. And it will remain on the lowest priority level until it returns from being blocked after the end of its current CPU burst.

filetoread.txt is a test file but project will work with files formatted as follows:

Job id (a single character)
Priority (HP or LP)
Arrival time
CPU burst length
Blocked Time
CPU burst length
Etc
-1(delimits the end of this job description)

To compile from command:
javac *.java
java sim filetoread.txt
