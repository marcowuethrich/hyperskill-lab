import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testcase.TestCase;
import org.hyperskill.hstest.testing.TestedProgram;

import java.util.Arrays;
import java.util.List;

public class Test extends StageTest<String> {
	@Override
	public List<TestCase<String>> generate() {
		return Arrays.asList(
				new TestCase<String>().setDynamicTesting(() -> {
					TestedProgram main = new TestedProgram();

					// tests of functionality of previous steps
					main.start();

					// test of /help
					String output = main.execute("/help").trim();
					if (output.length() < 4) {
						return CheckResult.wrong(
								"It seems like there was no any \"help\" message.");
					}

					// input empty string
					output = main.execute("");
					if (output.length() != 0) {
						return CheckResult.wrong(
								"Incorrect response to an empty string. " +
										"The program should not print anything.");
					}

					// testing basic assignment
					output = main.execute("n = 32");
					if (output.length() != 0) {
						return CheckResult.wrong(
								"Unexpected reaction after assignment." +
										"The program should not print anything in this case.");
					}

					// testing a big amount of numbers
					output = main.execute("33 + 20 + 11 + 49 - 32 - 9 + 1 - 80 + 4").trim();
					if (!output.equals("-3")) {
						return CheckResult.wrong(
								"The program cannot process addition " +
										"and subtraction operations correctly.");
					}

					// the same with a variable
					output = main.execute("33 + 20 + 11 + 49 - n - 9 + 1 - 80 + 4").trim();
					if (!output.equals("-3")) {
						return CheckResult.wrong(
								"The program cannot process addition " +
										"and subtraction operations correctly.");
					}


					output = main.execute("c = n \nc = 2");
					if (output.length() != 0) {
						return CheckResult.wrong(
								"Unexpected reaction after assignment." +
										"The program should not print anything in this case.");
					}

					// check value
					output = main.execute("  c  ").trim();
					if (!output.equals("2")) {
						return CheckResult.wrong(
								"The variable stores not a correct value." +
										"May be the program could not assign the value " +
										"of one variable to another one.");
					}

					// the sum of the numbers is zero
					output = main.execute("11 - 13 + c").trim();
					if (!output.equals("0")) {
						return CheckResult.wrong(
								"The problem when sum is equal to 0 has occurred.");
					}

					// test of multiple operations
					output = main.execute("5 --- 2 ++++++ 4 -- 2 ---- 1").trim();
					if (!output.equals("10")) {
						return CheckResult.wrong(
								"The program cannot process " +
										"multiple operations with several operators.");
					}

					// test of a nonexistent command
					output = main.execute("/start").trim().toLowerCase();
					if (!output.startsWith("unknown")) {
						return CheckResult.wrong(
								"The program should print \"Unknown command\" " +
										"when a nonexistent command is entered.");
					}

					// testing invalid variable name
					output = main.execute("var1 = 1").trim().toLowerCase();
					if (!output.startsWith("invalid")) {
						return CheckResult.wrong(
								"The name of variable should contain only Latin letters.");
					}

					// testing invalid value
					output = main.execute("var = 2a").trim().toLowerCase();
					if (!output.startsWith("invalid")) {
						return CheckResult.wrong(
								"The value can be an integer number or a value of another variable");
					}

					// testing multiple equalization
					output = main.execute("c = 7 - 1 = 5").trim().toLowerCase();
					if (!output.startsWith("invalid")) {
						return CheckResult.wrong(
								"The program could not handle a invalid assignment.");
					}

					// testing assignment by unassigned variable
					output = main.execute("variable = f").trim().toLowerCase();
					if (!(output.startsWith("unknown") || output.startsWith("invalid"))) {
						return CheckResult.wrong(
								"The program should not allow an assignment by unassigned variable.");
					}

					// checking case sensitivity
					main.execute("variable = 777");
					output = main.execute("Variable").trim().toLowerCase();
					if (!output.startsWith("unknown")) {
						return CheckResult.wrong("The program should be case sensitive.");
					}

					// checking case sensitivity
					main.execute("variable = 777");
					output = main.execute("Variable").trim().toLowerCase();
					if (!output.startsWith("unknown")) {
						return CheckResult.wrong("The program should be case sensitive.");
					}

					// test of /exit
					output = main.execute("/exit").trim().toLowerCase();
					if (!output.startsWith("bye")) {
						return CheckResult.wrong(
								"Your program didn't print \"bye\" after entering \"/exit\".");
					}

					return new CheckResult(main.isFinished(),
							"Your program should exit after entering \"/exit\".");
				}),

				new TestCase<String>().setDynamicTesting(() -> {
					TestedProgram main = new TestedProgram();

					// A test suit for multiplication and division
					main.start();

					String output = main.execute("4 * 3").trim();
					if (!output.equals("12")) {
						return CheckResult.wrong(
								"The program has problems with multiplication operation.");
					}

					output = main.execute("91 / 13").trim();
					if (!output.equals("7")) {
						return CheckResult.wrong(
								"The program has problems with division operation.");
					}

					// testing all operators
					main.execute(" a= 7 \n b =2");
					output = main.execute("a * 4 / b - (3 - 1)").trim();
					if (!output.equals("12")) {
						return CheckResult.wrong(
								"The program cannot correctly process several operations.");
					}

					// test of /exit
					output = main.execute("/exit").trim().toLowerCase();
					if (!output.startsWith("bye")) {
						return CheckResult.wrong(
								"Your program didn't print \"bye\" after entering \"/exit\".");
					}

					return new CheckResult(main.isFinished(),
							"Your program should exit after entering \"/exit\".");
				}),

				new TestCase<String>().setDynamicTesting(() -> {
					TestedProgram main = new TestedProgram();

					// testing priority of parentheses
					main.start();

					// expression from the example
					String output = main.execute(
							"7 + 3 * ((4 + 3) * 7 + 1) - 6 / (2 + 1)").trim();

					if (!output.equals("155")) {
						return CheckResult.wrong(
								"The program cannot reproduce an example from the task.");
					}

					// test of /exit
					output = main.execute("/exit").trim().toLowerCase();
					if (!output.startsWith("bye")) {
						return CheckResult.wrong(
								"Your program didn't print \"bye\" after entering \"/exit\".");
					}

					return new CheckResult(main.isFinished(),
							"Your program should exit after entering \"/exit\".");
				}),

				new TestCase<String>().setDynamicTesting(() -> {
					TestedProgram main = new TestedProgram();

					// a set of negative tests
					main.start();

					// tests with unclosed brackets
					String output = main.execute("8 * (2 + 3").trim().toLowerCase();
					String output1 = main.execute("4 + 5)").trim().toLowerCase();
					if (!output.startsWith("invalid") || !output1.startsWith("invalid")) {
						return CheckResult.wrong(
								"The program could not handle an invalid expression.");
					}

					// sequence of * test
					output = main.execute("2 ************ 2").trim().toLowerCase();
					if (!output.startsWith("invalid")) {
						return CheckResult.wrong(
								"A sequence of \"*\" should return \"Invalid expression\".");
					}

					// sequence of / test
					output = main.execute("2 // 2").trim().toLowerCase();
					if (!output.startsWith("invalid")) {
						return CheckResult.wrong(
								"A sequence of \"/\" should return \"Invalid expression\".");
					}

					// test of /exit
					output = main.execute("/exit").trim().toLowerCase();
					if (!output.startsWith("bye")) {
						return CheckResult.wrong(
								"Your program didn't print \"bye\" after entering \"/exit\".");
					}

					return new CheckResult(main.isFinished(),
							"Your program should exit after entering \"/exit\".");
				}),

				new TestCase<String>().setDynamicTesting(() -> {
					TestedProgram main = new TestedProgram();

					// test suit for the 8th stage with very large numbers
					main.start();

					// testing basic assignment
					String output = main.execute("n = 32000000000000000000");
					if (output.length() != 0) {
						return CheckResult.wrong(
								"Unexpected reaction after assignment." +
										"The program should not print anything in this case.");
					}

					// testing a big amount of numbers
					output = main.execute(
							"33000000000000000000 + 20000000000000000000 + 11000000000000000000 + " +
									"49000000000000000000 - 32000000000000000000 - 9000000000000000000 " +
									"+ 1000000000000000000 - 80000000000000000000 + 4000000000000000000 + 1").trim();
					if (!output.equals("-2999999999999999999")) {
						return CheckResult.wrong(
								"The program cannot process addition " +
										"and subtraction operations correctly.");
					}

					// the same with a variable
					output = main.execute(
							"33000000000000000000 + 20000000000000000000 + 11000000000000000000 + " +
									"49000000000000000000 - n - 9000000000000000000 " +
									"+ 1000000000000000000 - 80000000000000000000 + 4000000000000000000 + 1").trim();
					if (!output.equals("-2999999999999999999")) {
						return CheckResult.wrong(
								"The program cannot process addition " +
										"and subtraction operations correctly.");
					}

					// testing reassignment with big values
					output = main.execute("c = n \nc = 2000000000000000000000");
					if (output.length() != 0) {
						return CheckResult.wrong(
								"Unexpected reaction after assignment." +
										"The program should not print anything in this case.");
					}

					// check value
					output = main.execute("  c   ").trim();
					if (!output.equals("2000000000000000000000")) {
						return CheckResult.wrong(
								"The variable stores not a correct value." +
										"May be the program could not assign the value " +
										"of one variable to another one.");
					}

					// the sum of the numbers is zero
					output = main.execute(
							"11000000000000000000 - 9000000000000000000 - " +
									"c + 1998000000000000000000").trim();
					if (!output.equals("0")) {
						return CheckResult.wrong("The problem when sum is equal to 0 has occurred.");
					}

					// test of multiple operations
					output = main.execute(
							"5000000000000000000 --- 2000000000000000000 " +
									"++++++ 4000000000000000000 -- 2000000000000000000 ---- 1000000000000000000").trim();
					if (!output.equals("10000000000000000000")) {
						return CheckResult.wrong("The program cannot process multiple " +
								"operations with several operators.");
					}

					// testing all operators, with variables
					main.execute(" a= 7000000000000000000 \n b =2000000000000000000");
					output = main.execute("a * 4000000000000000000 / " +
							"b - (3000000000000000000 - 1000000000000000000)").trim();
					if (!output.equals("12000000000000000000")) {
						return CheckResult.wrong(
								"The program cannot correctly process several operations.");
					}

					// test of /exit
					output = main.execute("/exit").trim().toLowerCase();
					if (!output.startsWith("bye")) {
						return CheckResult.wrong(
								"Your program didn't print \"bye\" after entering \"/exit\".");
					}

					return new CheckResult(main.isFinished(),
							"Your program should exit after entering \"/exit\".");
				})

		);
	}
}
