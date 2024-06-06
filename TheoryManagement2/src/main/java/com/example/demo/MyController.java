package com.example.demo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.transaction.Transactional;
import lombok.NonNull;

@RestController
@CrossOrigin
@NonNull
public class MyController {
	@Autowired
	UserRepo userRepo;
	@Autowired
	LogRepo logRepo;
	@Autowired
	QuestionRepo questionRepo;
	@Autowired
	TestRepo testRepo;

	@RequestMapping("acceptreject{id}")
	public int acceptreject(@PathVariable int id, @RequestBody int verify) {
		try {

			Log log = logRepo.findById(id).get();
			log.verify = verify;
			logRepo.save(log);
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	@RequestMapping("getAllLogsForVerification")
	public List<Log> getAllLogsForVerification() {
		return logRepo.findBySubmitAndVerify(1, 0);
	}

//	@RequestMapping("submit{id}")
//	public int submit(@PathVariable int id) {
//		try {
//			Log log = logRepo.findById(id).get();
//			
//			log.submit = 1;
//			
//			logRepo.save(log);
//			return 1;
//
//		} catch (Exception e) {
//			e.printStackTrace();
//			return 0;
//		}
//	}
	@RequestMapping("submit{id}")
	@Validated
	public int submit(@PathVariable int id) {
		try {
			Log log = logRepo.findById(id).get();

			log.submit = 1;
			logRepo.save(log);
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	@RequestMapping("update{id}")
	public int update(@PathVariable int id, @RequestBody String answer) {
		try {
			Log log = logRepo.findById(id).get();
			log.answer = answer;
			logRepo.save(log);
			return 1;

		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	@RequestMapping("addQuestion")
	public Question addQuestion(@RequestBody String name) {
		Question question = new Question();
		question.name = name;
		return questionRepo.save(question);
	}

	@RequestMapping("giveQuestions")
	public List<Question> giveQuestions() {
		return questionRepo.findAll();
	}

	@RequestMapping("addTest")
	public Test addTest(@RequestBody Test test) {
		Test testdb = testRepo.save(test);
		return testdb;
	}

	@RequestMapping("giveTests")
	public List<Test> giveTests() {
		List<Test> list = testRepo.findAll();
		for (Test test : list) {
			String result = "";
			List<User> users = userRepo.findByRole(1);
			for (User user : users) {
				int count = logRepo.countByUserIdAndTestIdAndVerify(user.id, test.id, 1);
				if (count >= test.passingCount) {
					result = result + "pass(" + count + ")\n";
				} else {
					result = result + "Fail(" + count + ")\n";
				}
			}
			test.setResult(result);
			testRepo.save(test);
		}
		return list;
	}
//	@RequestMapping("giveTests")
//	public List<Test> giveTest()
//	{
//		List<Test> list=testRepo.findAll();
//		for(Test test:list)
//		{
//			String result="";
//			List<User> users=userRepo.findByRole(1);
//			for(User user:users)
//			{
//				int count=logRepo.countByUserIdAndTestIdAndVerify(user.id,test.id,1);
//				if(count>=test.passingCount)
//				{
//					result=result+user.name+" :Pass("+count+")\n";
//				}
//				else
//				{
//					result=result+user.name+" : Fail("+count+")\n";
//				}
//			}
//			test.result=result;
//		}
//		return list;
//	}

	@RequestMapping("giveActiveTests")
	public List<Test> giveActiveTests() {
		List<Test> tests = testRepo.findAll();
		List<Test> testsUi = new ArrayList<>();

		Date date = new Date();
		long now = date.getTime();

		for (Test test : tests) {
			Date start = test.getStart();
			Date end = test.getEnd();
			long startMili = start.getTime();
			long endMili = end.getTime();

			if (now > startMili && now < endMili) {
				testsUi.add(test);
			}
		}
		return testsUi;
	}

	@RequestMapping("giveMeTestLogs{userid}and{testid}")
	public List<Log> giveMeTestLogs(@PathVariable int userid, @PathVariable int testid) {
		int count = logRepo.countByUserIdAndTestId(userid, testid);
		if (count == 0) {
			Test test = testRepo.findById(testid).get();
			User user = userRepo.findById(userid).get();
			for (int i = 0; i < test.questionCount; i++) {
				Log log = new Log();
				log.answer = "";
				Question question = questionRepo.findRandomQuestion().get();
				log.question = question;
				log.submit = 0;
				log.test = test;
				log.user = user;
				log.verify = 0;
				logRepo.save(log);
			}
		}
		return logRepo.findByUserIdAndTestId(userid, testid);
	}

	@RequestMapping("login{username}")
	public int[] login(@PathVariable String username, @RequestBody String password) {

		// index0 =loginstatus
		// index1=userid
		// index2=role

		try {
			int[] a = new int[3];
			int count = userRepo.countByUsername(username);
			if (count == 0) {
				a[0] = 1;// wrong username
				return a;
			}
			if (count > 1) {
				a[0] = 2;
				return a;
			}
			User user = userRepo.findByUsername(username);
			if (user.password.equals(password)) {
				a[0] = 0;// success
				a[1] = user.id;
				a[2] = user.role;
				return a;
			} else {
				a[0] = 3;// wrong password;
				return a;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return null;

		}
	}

	@Transactional
	@RequestMapping("deleteTest/{testid}")
	public int deleteTest(@PathVariable int testid) {
		try {
			// Check if the test exists
			Optional<Test> optionalTest = testRepo.findById(testid);
			// Delete associated logs for the test
			logRepo.deleteByTestId(testid);

			// Delete the test by ID
			testRepo.deleteById(testid);

			return 1; // Return 1 for "Test deleted successfully"
		} catch (Exception e) {
			e.printStackTrace();
			return -1; // Return -1 for "Error deleting test"
		}
	}
	
//	@RequestMapping("deleteQuestions/{testid}")
//	public int deleteQuestions(@PathVariable int testid) {
//	    try {
//	        // Get the test by ID
//	        Optional<Test> optionalTest = testRepo.findById(testid);
//	        if (!optionalTest.isPresent()) {
//	            return -1; // Return -1 for "Test not found"
//	        }
//
//	        // Get the test
//	        Test test = optionalTest.get();
//
//	        // Delete associated questions for the test
//	        for (Question question : test.getQuestions()) {
//	            questionRepo.delete(question);
//	        }
//
//	        return 1; // Return 1 for "Questions deleted successfully"
//	    } catch (Exception e) {
//	        e.printStackTrace();
//	        return -1; // Return -1 for "Error deleting questions"
//	    }
//	}
}
