About

We include the link to our uml and flow chart here, use a Google account to log in and view:
gui chart: https://drive.google.com/file/d/1OMYzXn9bgOBj3qtc7cwVGy_bkqMWp9wM/view?usp=sharing
class chart: https://drive.google.com/file/d/1olBhbXteK5Ii97GUlC200ge9a3coki8F/view?usp=sharing

Please run phase2\\main\\Main.main to start program.
We use Swing package as foundation of our GUI and use serialization for storing our date.
Here we briefly introduce our user types and what they can do as well as other aspects of gui:

Register:
1. To register a new user, click "Register" in the login frame, then choose which user you want to create.
    (Hiring manager, recruiter and interviewer are Employee, choose correct Position when register)

Hiring Manager (the person who creates jobs and assign them to recruiters):
1.	To post a job: click “Create Posting” and then fill all the information about the job.
2.	To view all job postings of the company and their applications: click “View Posting”;
    choose a job if you want to see all applications of it.
3.	To search job postings by tags: type the keywords in the search box.

Recruiter (the person who is responsible for the whole recruitment process):
1.	To view all applications to the company: click “All Applicants”.
2.	To view documents/ a document of an applicant: choose an application after clicking “All Applicants”
    and then select a document on the right panel and click “View Document”.
3.	To manage a job hiring process: switch to “Job Managing” page:
    a.	Add interview round: choose a job and click “Add Round” with its name typed in the text field
    b.	Turn to next round when the current round is finished: choose a job and click “Next Round”
        (you can only start next round iff current round has ended and job posting is closed)
    c.	Match an interview with an interviewer:
        i.	select both a (PROCESSING) job and its (MATCHING) interview round and click “View/Edit”;
        ii.	the page will be switched to “Interview Round Manager” where you can view all the applications
            in that round and their corresponding interviews;
        iii.click “Start Matching” to match interview;
        iv.	select both an unmatched application and an interviewer on each search panel, and then click “Match”.
    d.	Hire an applicant: repeat (i) (ii) above, then select an application and click “Hire”
        (if current interview round is finished).
    e.	End a job hiring process: select a job and click “End JobPosting”.

Interviewer:
1.	To search interviews: enter the keywords in search box
2.	To view an interview and submitted documents of the applicant:
    choose an interview and then choose a document; click “View document” to view the complete file.
3.	To pass/fail an applicant and write recommendation for him/her:
    type in the recommendation and click “Pass”/ “Fail”.

Applicant:
1. To view interviews, go to "Upcoming Interviews", where both upcoming and past interviews are included.
2. To look for new jobs, go to "Apply Jobs", where all open jobs are shown, select the job and click
    "Create Application" to apply, the page will be switched to "Manage Application".
3. To manage applications:
    i.  go to "Manage Application"
    ii. select the application you want to manage and click "Edit Application"
    iii.select documents from the right filter panel and click "Add" to add to the application,
        or select documents from the left filter panel and click "Delete" to remove from the application
4. In "Manage Application", applicant can select an application first, and then choose to delete,
    apply or withdraw. Applicant can also select a document and view content.
5. In "My Documents", applicant can select existing document and click "Delete" to delete document;
    applicant can also click "Add" to search for txt file on their local computer and upload it.

Time and load/save:
1. To load, when you run the program, it is automatically load data from data.ser.
    A message dialog will be shown if something wrong happened.
2. To set time or save, since we can not manipulate time, we created a time system to move to next date.
    In Login frame, click "Restart" to save all the data in the program, and type in how many days you want to add.
    (You can check the "current" date at the upper left corner of any scenario.)
3. We do not store time, we think it will spoil our classes, it is better that every time you log in,
    (the initial time is the real current date) click "Restart" and set the date to the date you logged out last time.
4. When you finish using the program, remember to log out and then click "Restart" to save all the changes you made,
    then enter "0" days or simply close the program, otherwise the program would not save what you did.

There are several small mistakes we found after 8/8 11:00 am and here is our report and reflection:
1. In class InterviewRoundScenario line 186: should call update() rather than initLeftFilter()
    This is caused by communication misunderstanding between members and called the wrong method.
    Consequence: After hiring, the gui view is not update properly,
        you will be able to see changes by return to job managing and goes to view/edit again.
2. In class JobPostingRegisterScenario line 136: should use Main.getCurrentDate rather than LocalDate.now()
    We added time system at very end, forgot to change this line of code.
    Consequence: It will be funny that after you set the date to year 2021 and still can create job closed in 2020.
3. In class Filter line 55 and line 82: method getFilterValues() and isMatched() is not implemented perfectly.
    We forgot to do more test on filtering, usually just one keyword but bad things happened when there are two or more.
    Consequence: getFilterValues() not implement well so the regex is strictly XX;XXX;XX, not allowed spaces in between.
    When due with multiple keywords, isMatched() is actually doing "or" relationship for matching which is wired.
4. In class ApplicationManageScenario line 85: In method initLeftFilter() should add a line;
    leftFilter.addSelectionListener(new LeftFilterListener());
    For this part, we have added documentation before finished coding, therefore inner class LeftFilterListener dose not
    showed color grey to indicate it is not used, and we just simply forgot.
    Consequence: In ApplicationManageScenario, clicking on applications in left side list will not show its application
    documents on right side list.

A final note: When adding documents as an applicant, it seems like Mac system does not work.
    We used the same code on Windows and Mac to add the same file, and it failed on Mac.

Thank you!