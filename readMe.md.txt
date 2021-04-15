			
_INSTRUCTION BEFORE RUNNING THE PROJECT_:
Please configure the database as per your requirement in "application.properties" file.
Please refer readMe PDF to look at the sample output as it appears. 
**Contents:**
A)	About REST endpoints
B)	About Data base Design
C)	About SimulationService.java

**A) ABOUT REST ENDPOINTS:**

For the given Java assignment, making sensible assumptions, total 3 REST end points are created.

All the REST end points are being exposed in the "ApplicationController" file.
Dice\src\main\java\com\synpulse\controller\ApplicationController.java

**1st end point:**
 @GetMapping("/simulate/{noOfDice}/{noOfSides}/{noOfRolls}")
This is for the assignment->Create a REST endpoint to execute a dice distribution simulation.
Here we can configure noOfDice, noOfSides and noOfRolls and Validations has been written as required.

A sample call from Postman: http://localhost:8080/simulate/5/8/120
Here 5->No of Dice, 8-> No of Sides, 120->No of Rolls

This returns a JSON of SUM-COUNT pairs.

**sample output:**
{
    "7": 1,
    "10": 1,
    "12": 2,
    "13": 1,
    "15": 4,
    "16": 7,
    "17": 1,
    "18": 8,
    "19": 8,
    "20": 3,
    "21": 6,
    "22": 13,
    "23": 10,
    "24": 6,
    "25": 6,
    "26": 11,
    "27": 8,
    "28": 4,
    "29": 5,
    "30": 5,
    "31": 3,
    "32": 3,
    "33": 2,
    "34": 1,
    "35": 1
}


**2nd end point:**
@GetMapping("/totaldetails")

This is for the assignment->Return the total number of simulations and total rolls made, grouped by all existing dice number–dice side combinations.
A sample call from Postman:
http://localhost:8080/totaldetails 

This returns the required details of all the existing combinations as a ResponseEntity<String>

**Sample Output:**

DiceNumber-DiceSide	        No-Of-Simulations         Total-Rolls
  3-6                        			2                      300
  4-10                      			3                      900
   5-8                        			2                      220



**3rd end point:**
@GetMapping("/relativedistribution/{noOfDice}/{noOfSides}")

This is for the assignment->For a given dice number–dice side combination, return the relative distribution, compared to the total rolls, for all 
the simulations.

A sample call from Postman:
http://localhost:8080/relativedistribution/5/8 
here 5->noOfDice and 8-> noOfSides

This returns the required details for the given diceNumber–diceSide combination as a ResponseEntity<String>

**Sample Output:**


Total rolls for the 5-8 diceNumber-diceSides combination is 220

Sum-on-Dice    Count-of-Sum    Relative-Distribution
12                 4               1.82%
17                 2               0.91%
29                 7               3.18%
34                 2               0.91%
26                 22               10.00%
31                 3               1.36%
32                 7               3.18%
18                 11               5.00%
30                 6               2.73%
28                 8               3.64%
15                 7               3.18%
22                 21               9.55%
27                 11               5.00%
11                 1               0.45%
24                 12               5.45%
7                 1               0.45%
21                 12               5.45%
25                 19               8.64%
38                 1               0.45%
10                 2               0.91%
33                 4               1.82%
23                 15               6.82%
19                 14               6.36%
16                 10               4.55%
14                 1               0.45%
35                 1               0.45%
13                 3               1.36%
20                 13               5.91%



**B) About Database Design:**

Based on the requirement for the assignment, I have created 2 tables. Hence there are 2 Models and 2 repositories in the Project.
First table is SIMULATION and the other is DISTRIBUTION and these two tables are linked by a "SimulationId".

**Simulation table:** This table is to store the total number of simulations and total number of rolls for a specific diceNumber-diceSide combination. This table has the following columns

1. simulation_id (Id given for a specific DiceNumber- diceSide combination).
2. dice_side (This is a string which is specific DiceNumber- diceSide combination).
3. simulation_count (This is total number of simulations for the specific dice_side combination)
4.  total_rolls (Total number of rolls for the specific dice_side combination).

**Distribution table:** This table is to store the Sum on dice and the number of times it occurs for a specific diceNumber-diceSide combination. This table has following columns:

1.	Id ( It’s just a generated id)
2.	count ( gives he count of a a specific Sum on the dice for a specific diceNumber-diceSide combination)
3.	sum_on_dice ( represents Sum on the dice for a specific diceNumber-diceSide combination)
4.	simulation_id ( Id that connects with Simulation table. Basically each diceNumber-diceSide combination will have a simulationId).

**C)SimulationService.java:** It is the important class where entire functionalities are written. 
Three different functions are written here, each corresponding to a REST endpoint.
