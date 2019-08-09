About
This is a project of a Human Resource system, driven by phase2 requirements.
We use JAVA.swing package as  foundation of our GUI.And use serialization in storing our date.

1 We have a register case, we split this scenario in two specific cases
  (a) register a applicant:
     please fill the information the press "register" button,then there is an applicant in the system.
  (b) register a employee(hiringmanager,interviewer,recruiter )
     First you need choose which kind of employee you want register.Then fill the information for this
     employee.


2 we have a login case , when you select the type of user and type in username and password,you can login.
    (a) HiringManager case
    In our program , hiringmanger is the one to creat job and assign the recruiter for this job.
    (b) Recruiter case
     In our program ,  recruiter is the one to add the interview round for a job , and assign the
     interviewer for an interview(application).
         When you want add an interview round for a job,  the job status must be processing(after the closed date),
            then type in the round name you want add and click "Add round".
         When you want view/eidt an interview round you need select both the job and interview round,
            then click "View/Edit" button , then click the applications and interview and press"start matching",
            then you can assign an interviewer for an application(applicant).
    (c) Interviewer case
        After you login , you may receive an message if there is a new interview you need to deal with.
        Then you could select an interview and view their documents to decide whether pass  or fail
        it . In addition , you could add the comment for this interview.
    (d) Applicant case
        After login , you might receive a message if there was an interview has been passed or failed
        Then , you could view the past and ongoing interviews in "Upcoming interview page".
        Then , you  could apply for a job in"apply jobs" page , select the job and click "creat application",
            you will automatically jump to "Manage Applicants" page.
        Then , you could select an application and edit it(add more documents for it) , then back to
            "manage applications" page , then select the job you want apply and click "apply".
        Lastly , in "my documents" page , you could submit document by click "add" button.

    Have a good time in playing with out program, if you have any question , you could email us. Thanks!

