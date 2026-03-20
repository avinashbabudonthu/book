// show all databases
printjson(db.adminCommand({ listDatabases: 1 }));

// create and use database
db = db.getSiblingDB("practiceDatabase");

// check database in use
print("Using db:", db.getName());

// create collections
// Note: collections are created automatically on first insert,
// so we can avoid `createCollection` issues when re-running scripts.

// insert data to employees collection
db.employees.insertMany([
  {
    "id": 1,
    "name": "Alice Johnson",
    "dept": "Engineering",
    "role": "Software Engineer",
    "salary": 120000,
    "email": "alice.johnson@company.com",
    "location": "Bengaluru"
  },
  {
    "id": 2,
    "name": "Bob Smith",
    "dept": "Engineering",
    "role": "Data Engineer",
    "salary": 130000,
    "email": "bob.smith@company.com",
    "location": "Hyderabad"
  },
  {
    "id": 3,
    "name": "Carol Williams",
    "dept": "Marketing",
    "role": "Marketing Manager",
    "salary": 95000,
    "email": "carol.williams@company.com",
    "location": "Pune"
  },
  {
    "id": 4,
    "name": "David Brown",
    "dept": "Sales",
    "role": "Account Executive",
    "salary": 87000,
    "email": "david.brown@company.com",
    "location": "Chennai"
  },
  {
    "id": 5,
    "name": "Emma Davis",
    "dept": "HR",
    "role": "HR Specialist",
    "salary": 80000,
    "email": "emma.davis@company.com",
    "location": "Gurugram"
  },
  {
    "id": 6,
    "name": "Frank Miller",
    "dept": "Finance",
    "role": "Financial Analyst",
    "salary": 99000,
    "email": "frank.miller@company.com",
    "location": "Mumbai"
  },
  {
    "id": 7,
    "name": "Grace Wilson",
    "dept": "Engineering",
    "role": "QA Engineer",
    "salary": 85000,
    "email": "grace.wilson@company.com",
    "location": "Bengaluru"
  },
  {
    "id": 8,
    "name": "Henry Moore",
    "dept": "Operations",
    "role": "Operations Manager",
    "salary": 110000,
    "email": "henry.moore@company.com",
    "location": "Hyderabad"
  },
  {
    "id": 9,
    "name": "Irene Taylor",
    "dept": "Customer Support",
    "role": "Support Lead",
    "salary": 76000,
    "email": "irene.taylor@company.com",
    "location": "Remote"
  },
  {
    "id": 10,
    "name": "Jack Anderson",
    "dept": "Engineering",
    "role": "DevOps Engineer",
    "salary": 140000,
    "email": "jack.anderson@company.com",
    "location": "Bengaluru"
  },
  {
    "id": 11,
    "name": "Karen Thomas",
    "dept": "Finance",
    "role": "Controller",
    "salary": 155000,
    "email": "karen.thomas@company.com",
    "location": "Mumbai"
  },
  {
    "id": 12,
    "name": "Liam Jackson",
    "dept": "Sales",
    "role": "Sales Engineer",
    "salary": 102000,
    "email": "liam.jackson@company.com",
    "location": "Delhi"
  },
  {
    "id": 13,
    "name": "Mia White",
    "dept": "Marketing",
    "role": "Content Strategist",
    "salary": 70000,
    "email": "mia.white@company.com",
    "location": "Pune"
  },
  {
    "id": 14,
    "name": "Noah Harris",
    "dept": "HR",
    "role": "Recruiter",
    "salary": 72000,
    "email": "noah.harris@company.com",
    "location": "Gurugram"
  },
  {
    "id": 15,
    "name": "Olivia Martin",
    "dept": "Operations",
    "role": "Supply Chain Analyst",
    "salary": 82000,
    "email": "olivia.martin@company.com",
    "location": "Chennai"
  },
  {
    "id": 16,
    "name": "Paul Thompson",
    "dept": "Engineering",
    "role": "Backend Engineer",
    "salary": 125000,
    "email": "paul.thompson@company.com",
    "location": "Hyderabad"
  },
  {
    "id": 17,
    "name": "Sophia Garcia",
    "dept": "Engineering",
    "role": "Frontend Engineer",
    "salary": 118000,
    "email": "sophia.garcia@company.com",
    "location": "Bengaluru"
  },
  {
    "id": 18,
    "name": "Ethan Robinson",
    "dept": "Customer Support",
    "role": "Support Engineer",
    "salary": 90000,
    "email": "ethan.robinson@company.com",
    "location": "Remote"
  },
  {
    "id": 19,
    "name": "Ava Clark",
    "dept": "Sales",
    "role": "Partnerships Manager",
    "salary": 105000,
    "email": "ava.clark@company.com",
    "location": "Mumbai"
  },
  {
    "id": 20,
    "name": "William Lewis",
    "dept": "Engineering",
    "role": "Security Engineer",
    "salary": 150000,
    "email": "william.lewis@company.com",
    "location": "Gurugram"
  },
]);

// insert into students
db.students.insertOne({"name": "Jack"});
db.students.insertOne({"name": "John"});
db.students.insertOne({"name": "Jim", "mail": "jim@gmail.com", 
    "department": {"name": "CSE", "lcoation":"India"}});
db.students.insertOne({"name": "Jane", "mail": "jane@gmail.com", 
    "subject": [{"name": "Java", "grade": 4.0}, {"name": "Mongo", "marks":4.0}]});
db.students.insertOne({"name": "Ana", "mail": "ana2@gmail.com"});

db.students.insertMany([
    {"name": "Jack", "mail": "jack@gmail.com", "subjects": [{"name": "Java", "grade": 4.0}], "department": {"name": "Dept1", "location": "Hyerabad"}},

    {"name": "John", "mail": "john@gmail.com", "subjects": [{"name": "MongoDB", "grade": 3.9}], "department": {"name": "Dept2", "location": "Hyerabad"}},

    {"name": "Jim", "mail": "jim@gmail.com", "subjects": [{"name": "Spring", "grade": 3.8}], "department": {"name": "Dept3", "location": "Hyerabad"}},

    {"name": "Jane", "mail": "jane@gmail.com", "subjects": [{"name": "Maven", "grade": 3.7}], "department": {"name": "Dept4", "location": "Hyerabad"}},

    {"name": "Ana", "mail": "ana@gmail.com", "subjects": [{"name": "Gradle", "grade": 3.6}], "department": {"name": "Dept5", "location": "Hyerabad"}},

    {"name": "Ami", "mail": "ami@gmail.com", "subjects": [{"name": "Spring Data Mongo", "grade": 3.5}], "department": {"name": "Dept6", "location": "Hyerabad"}},

    {"name": "Jenny", "mail": "jenny@gmail.com", "subjects": [{"name": "AWS", "grade": 3.4}], "department": {"name": "Dept7", "location": "Hyerabad"}},

    {"name": "Dan", "mail": "dan@gmail.com", "subjects": [{"name": "REST API", "grade": 3.3}], "department": {"name": "Dept8", "location": "Hyerabad"}},

    {"name": "Jenifer", "mail": "jenifer@gmail.com", "subjects": [{"name": "GraphQL", "grade": 3.2}], "department": {"name": "Dept9", "location": "Hyerabad"}},

    {"name": "Julia", "mail": "julia@gmail.com", "subjects": [{"name": "GraphDB", "grade": 3.1}], "department": {"name": "Dept10", "location": "Hyerabad"}},

    {"name": "Ami", "mail": "ami2@gmail.com", "subjects": [{"name": "Spring Data Mongo", "grade": 3.5}], "department": {"name": "Dept11", "location": "Hyerabad"}},

    {"name": "Anni", "mail": "anni2@yahoo.com", "subjects": [{"name": "Spring Data Mongo", "grade": 3.5}], "department": {"name": "Dept11", "location": "Hyerabad"}},

    {"name": "Jason", "mail": "jason@yahoo.com", "subjects": [{"name": "Spring Data Mongo", "grade": 3.5}], "department": {"name": "Dept11", "location": "Hyerabad"}},

    {"name": "Jimmy", "mail": "jimmy@yahoo.com", "subjects": [{"name": "Spring Data Mongo", "grade": 3.5}], "department": {"name": "Dept11", "location": "Hyerabad"}},
    
    {"name": "Jimmy2", "mail": "jimmy2@yahoo.com", "subjects": [{"name": "Spring Data Mongo", "grade": 3.5}], "department": {"name": "Dept11", "location": "Hyerabad"}},
    
    {"name": "Jimmy3", "mail": "jimmy3@yahoo.com", "subjects": [{"name": "Spring Data Mongo", "grade": 3.5}], "department": {"name": "Dept11", "location": "Hyerabad"}}

]);