About
We use Swing package as foundation of our GUI.And use serialization for storing our date.

Here we briefly introduce our user types and what they can do:

Hiring Manager (the person who creates jobs and assign them to recruiters)
1.	To post a job: click “Create Posting” and then fill all the information about the job.
2.	To view all job postings of the company and their applications: click “View Posting”; choose a job if you want to see all applications of it.
3.	To search job postings by tags: type the keywords in the search box.

Recruiter (the person who is responsible for the whole recruitment process).
1.	To view all applications to the company: click “All Applicants”.
2.	To view documents/ a document of an applicant: choose an application after clicking “All Applicants” and then select a document on the right panel and click “View Document”.
3.	To manage a job hiring process: switch to “Job Managing” page:
a.	Add interview round: choose a job and click “Add Round” with its name typed in the text field
b.	Turn to next round when the current round is finished: choose a job and click “Next Round” (you can only start next round iff current round has ended and job posting is closed)
c.	Match an interview with an interviewer:
    i.	select both a (PROCESSING) job and its (MATCHING) interview round and click “View/Edit”;
    ii.	the page will be switched to “Interview Round Manager” where you can view all the applications in that round and their corresponding interviews;
    iii.click “Start Matching” to match interview;
    iv.	select both an unmatched application and an interviewer on each search panel, and then click “Match”.
d.	Hire an applicant: repeat (i) (ii) above, then select an application and click “Hire” (if current interview round is finished).
e.	End a job hiring process: select a job and click “End JobPosting”.

Interviewer:
1.	To search interviews: enter the keywords in search box
2.	To view an interview and submitted documents of the applicant: choose an interview and then choose a document; click “View document” to view the complete file.
3.	To pass/fail an applicant and write recommendation for him/her: type in the recommendation and click “Pass”/ “Fail”.

Applicant:
1. To view interviews, go to "Upcoming Interviews", where both upcoming and past interviews are included.
2. To look for new jobs, go to "Apply Jobs", where all open jobs are shown, select the job and click "Create Application" to apply, the page will be switched to "Manage Application".
3. To manage applications:
    i.  go to "Manage Application"
    ii. select the application you want to manage and click "Edit Application"
    iii.select documents from the right filter panel and click "Add" to add to the application, or select documents from the left filter panel and click "Delete" to remove from the application
4. In "Manage Application", applicant can select an application first, and then choose to delete, apply or withdraw. Applicant can also select a document and view content.
5. In "My Documents", applicant can select existing document and click "Delete" to delete document; applicant can also click "Add" to search for txt file on their local computer and upload it.


To register a new user, click "Register" in the login frame, then choose which user you want to create. (Hiring manager, recruiter and interviewer are Employee, choose correct Position when register)

Since we can not manipulate time, we created a time system to move to next date.
In Login frame, click "Restart" to save all the data in the program, and type in how many days you want to add. (You can check the "current" date at the upper left corner of any scenario.)
Note that every time you log in, the initial time is the real current date, so it is better to click "Restart" and set the date to the same program date you logged out last time.

When you finish using the program, remember to log out and then click "Restart" to save all the changes you made, then enter "0" days or simply close the program, otherwise the program would not save what you did.


We include the link to our uml and flow chart here, use a Google account to log in and view:
https://www.draw.io/?state=%7B%22ids%22:%5B%221OMYzXn9bgOBj3qtc7cwVGy_bkqMWp9wM%22%5D,%22action%22:%22open%22,%22userId%22:%22100182153422960194843%22%7D#G1OMYzXn9bgOBj3qtc7cwVGy_bkqMWp9wM
https://www.draw.io/?state=%7B%22ids%22:%5B%221olBhbXteK5Ii97GUlC200ge9a3coki8F%22%5D,%22action%22:%22open%22,%22userId%22:%22100182153422960194843%22%7D#G1olBhbXteK5Ii97GUlC200ge9a3coki8F


There are several bugs we found and failed to correct:
1. When adding documents as an applicant, it seems like Mac system does not work. We used the same code on Windows and Mac to add the same file, and it failed on Mac.
2.