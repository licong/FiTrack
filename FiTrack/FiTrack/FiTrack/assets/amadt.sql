PRAGMA foreign_keys=OFF;
BEGIN TRANSACTION;
CREATE TABLE android_metadata (locale TEXT);
INSERT INTO "android_metadata" VALUES('en_US');
CREATE TABLE "userhistory" (
    "_id" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    "u_id" INTEGER NOT NULL,
    "date" INTEGER NOT NULL,
    "weight" INTEGER NOT NULL
);
CREATE TABLE "scheduledwod" (
    "_id" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    "w_id" INTEGER NOT NULL,
    "date" INTEGER NOT NULL
);
CREATE TABLE "user" (
    "_id" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    "username" TEXT NOT NULL,
    "email" TEXT NOT NULL,
    "sessiontoken" TEXT
);
CREATE TABLE exercise (
    "_id" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    "name" TEXT NOT NULL,
    "type" TEXT NOT NULL,
    "description" TEXT,
    "video" TEXT, 
    "isolympic" INTEGER   DEFAULT (0));
INSERT INTO "exercise" VALUES(1,'Pull-up','Strength','Step up and grasp bar with overhand wide grip.Pull body up until chin is above bar. Lower body until arms and shoulders are fully extended. ',NULL,0);
INSERT INTO "exercise" VALUES(2,'Push-up','Strength','Lie prone on floor with hands slightly wider than shoulder width. Raise body up off floor by extend arms with body straight. Keeping body straight, lower body to floor by bending arms. Push body up until arms are extended.',NULL,0);
INSERT INTO "exercise" VALUES(3,'Sit-up','Strength','lie supine with hips bent. Raise torso by bending waist and hips. Return until back of shoulders contact the floor',NULL,0);
INSERT INTO "exercise" VALUES(4,'Squat','Strength','Bend knees forward while allowing hips to bend back behind, keeping back straight and knees pointed same direction as feet. Descend until thighs are just past parallel to floor. Extend knees and hips until legs are straight.',NULL,0);
INSERT INTO "exercise" VALUES(5,'Deadlift','Strength','With feet flat beneath bar, squat down and grasp bar with shoulder width or slightly wider over hand or mixed grip. Lift bar by extending hips and knees to full extension. Pull shoulders back at top of lift if rounded. Return and repeat.',NULL,0);
INSERT INTO "exercise" VALUES(6,'Handstand Push-up','Strength','Stand facing wall between two benches positioned side by side, slightly apart, and slightly away and perpendicular to wall. Place hands on ends of benches closest to wall. Place forefeet on bench and kick lower body up to handstand position with arms and legs straight. Maintain balance with lower body against wall. Lower head between ends of benches by bending arms. Push body back up to original position by extending arms.',NULL,0);
INSERT INTO "exercise" VALUES(7,'Clean','Strength','Pull bar up off floor by extending hips and knees. As bar reaches knees vigorously raise shoulders while keeping barbell close to thighs. When barbell passes mid-thigh, allow it to contact thighs. Jump upward extending body. Shrug shoulders and pull barbell upward with arms allowing elbows to flex out to sides, keeping bar close to body. Aggressively pull body under bar, rotating elbows around bar. Catch bar on shoulders while moving into squat position. Hitting bottom of squat, stand up immediately.',NULL,1);
INSERT INTO "exercise" VALUES(8,'Thruster','Strength','Start with the barbell lifted up to the upper chest. Lower the body by bending knees and hips while keeping the back straight. Raise the body while thrusting the barbell completely overhead.',NULL,0);
INSERT INTO "exercise" VALUES(9,'Clean and Jerk','Strength','Stand over barbell with balls of feet positioned under bar slightly wider apart than hip width. Squat down and grip bar with over hand grip slightly wider than shoulder width. Position shoulders over bar with back arched tightly. Arms are straight with elbows pointed along bar. Pull bar up off floor by extending hips and knees. As bar reaches knees vigorously raise shoulders while keeping barbell close to thighs. When barbell passes mid-thigh, allow it to contact thighs. Jump upward extending body. Shrug shoulders and pull barbell upward with arms allowing elbows to flex out to sides, keeping bar close to body. Aggressively pull body under bar, rotating elbows around bar. Catch bar on shoulders while moving into squat position. Hitting bottom of squat, stand up immediately.',NULL,1);
INSERT INTO "exercise" VALUES(10,'Wall Ball','Strength','Place feet shoulder width apart. Place hands and elbow beneath the ball while holding it above the chest. Squat down while maintaining the lumbar curve. Explode out of the squat while throwing a ball up 8-10 feet. Catch the ball. Repeat',NULL,0);
INSERT INTO "exercise" VALUES(11,'Bench','Strength','Lie supine on bench. Dismount barbell from rack over upper chest using wide oblique overhand grip.Lower weight to mid-chest. Press bar upward until arms are extended. Repeat.',NULL,0);
INSERT INTO "exercise" VALUES(12,'1-legged Squat','Strength','Squats using only one leg',NULL,0);
INSERT INTO "exercise" VALUES(13,'Run','Cardio','to go quickly by moving the legs more rapidly than at a walk and in such a manner that for an instant in each step all or both feet are off the ground.',NULL,0);
INSERT INTO "exercise" VALUES(14,'Overhead Squat','Strength','Place barbell overhead with very wide overhand grip. Position toes outward with wide stance. Maintain bar behind head with arms extended. Descend until knees and hips are fully bent or until thighs are just past parallel to floor. Knees travel in direction of toes. Extend knees and hips until legs are straight. Return and repeat.',NULL,0);
INSERT INTO "exercise" VALUES(15,'Double Unders','Cardio','Grasp jump rope handles on each side. Jump and swiftly swing rope around body and under feet twice before landing. Repeat.',NULL,0);
INSERT INTO "exercise" VALUES(16,'Kettlebell Swing','Strength','Straddle kettlebell with feet slightly wider apart than shoulder width. Squat down with arm extended downward between legs and grasp kettlebell handle with overhand grip. Position shoulder over kettlebell with taut low back and trunk close to vertical. Pull kettlebell up off floor, slightly forward, just above height of ankles. Immediately dip down slightly and swing kettlebell back under hips. Quickly swing kettlebell up by raising upper body upright and extending legs. Continue to swing kettlebell back down between legs and up higher on each swing until height just above head can be mantained. ',NULL,0);
INSERT INTO "exercise" VALUES(17,'Box Jump','Cardio','Stand in front of a secured box or platform. Jump onto box and immediately back down to same position. Immediately repeat.',NULL,0);
INSERT INTO "exercise" VALUES(18,'Snatch','Strength','Stand over barbell with balls of feet positioned under bar hip width or slightly wider than hip width apart. Squat down and grip bar with very wide over hand grip. Pull bar up off floor by extending hips and knees. Jump upward extending body. Shrug shoulders and pull barbell upward with arms allowing elbows to pull up to sides, keeping them over bar as long as possible. Aggressively pull body under bar. Catch bar at arms length while moving into squat position. As soon as barbell is caught on locked out arms in squat position, squat up into standing position with barbell overhead.',NULL,0);
INSERT INTO "exercise" VALUES(19,'Ring-dip','Strength','Jump up and, using the arms, support the body on the rings. Be sure the arms are straight. Lower the body by bending the elbows and rotating the shoulder towards the back. Raise the body by straightening the arms.',NULL,0);
INSERT INTO "exercise" VALUES(20,'Back-extension','Strength','Position thighs prone on large pad and lower legs under padded brace. Hold weight to chest or behind neck. Lower body by bending waist until fully flexed. Raise, or extend waist until torso is parallel to legs. Repeat.
',NULL,0);
INSERT INTO "exercise" VALUES(21,'Muscle-up','Strength','Hang from a false grip. Pull the rings to your chest or pull-up. Roll your chest over the bottom of the rings. Press to support or dip',NULL,0);
INSERT INTO "exercise" VALUES(22,'Snatch','Strength','Stand over barbell with balls of feet positioned under bar hip width or slightly wider than hip width apart. Squat down and grip bar with very wide over hand grip. Position shoulders over bar with back arched tightly. Arms are straight with elbows pointed along bar. Pull bar up off floor by extending hips and knees. As bar reaches knees back stays arched and maintains same angle to floor as in starting position. When barbell passes knees vigorously raise shoulders while keeping bar as close to legs as possible. When bar passes upper thighs allow it to contact thighs. Jump upward extending body. Shrug shoulders and pull barbell upward with arms allowing elbows to pull up to sides, keeping them over bar as long as possible. Aggressively pull body under bar. Catch bar at arms length while moving into squat position. As soon as barbell is caught on locked out arms in squat position, squat up into standing position with barbell overhead.',NULL,1);
INSERT INTO "exercise" VALUES(23,'Power Snatch','Strength','Stand over barbell with balls of feet positioned under bar hip width or slightly wider than hip width apart. Squat down and grip bar with very wide over hand grip. Position shoulders over bar with back arched tightly. Arms are straight with elbows pointed along bar. Pull bar up off floor by extending hips and knees. As bar reaches knees back stays arched and maintains same angle to floor as in starting position. When barbell passes knees vigorously raise shoulders while keeping bar as close to legs as possible. When bar passes upper thighs allow it to contact thighs. Jump upward extending body. Shrug shoulders and pull barbell upward with arms allowing elbows to pull up to sides, keeping them over bar as long as possible. Aggressively pull body under bar. Catch bar at arms length before knees bend lower than 90°. As soon as barbell is caught on locked out arms in partial squat position, stand up with barbell overhead immediately so thighs ride no lower than parallel to floor.',NULL,1);
INSERT INTO "exercise" VALUES(24,'Dumbbell Snatch','Strength','Stand with feet apart and toes pointing outward slightly. Position dumbbell in front of thigh with knuckles forward. Squat down with back arched and lower dumbbell between knees with arm straight and shoulder over dumbbell. Pull dumbbell up by extending hips and knees. Jump upward extending body. Shrug shoulders and pull dumbbell upward with arm allowing elbow to pull up to side, keeping elbow over dumbbell as long as possible. Aggressively pull body under dumbbell. Catch dumbbell at arms  length while moving into squat position. As soon as dumbbell is caught on locked out arm in squat position, squat up into standing position with dumbbell overhead.',NULL,1);
INSERT INTO "exercise" VALUES(25,'Rope-climb','Strength','',NULL,0);
INSERT INTO "exercise" VALUES(26,'Split Clean','Strength','',NULL,0);
INSERT INTO "exercise" VALUES(27,'Burpee','Strength','Stand upright with arms to sides. Bend over and squat down. Place hands on floor, slightly wider than shoulder width. While holding upper body in place, kick legs back. Land on forefeet with body in straight, plank position. Keeping upper body in place, pull legs forward under body returning feet in original position. Rise up to original standing posture.',NULL,0);
INSERT INTO "exercise" VALUES(28,'Glute-ham Sit-up','Strength','Place ankles between ankle roller pads with feet on vertical platform and position knees on pad with lower thighs against large padded hump. Do a sit-up from the position',NULL,0);
INSERT INTO "exercise" VALUES(29,'Hang Power Clean','Strength','Stand with barbell with over hand grip slightly wider than shoulder width. Feet point forward hips width apart or slightly wider. Bend knees and hips so barbell touches mid-thigh; shoulders over bar with back arched. Arms are straight with elbows pointed along bar. Chest is spread and wrists are slightly flexed. Jump upward extending body. Shrug shoulders and pull barbell upward with arms allowing elbows to flex out to sides, keeping bar close to body. Aggressively pull body under bar, rotating elbows around bar. Catch bar on shoulders before knees bend lower than 90 degrees. Stand up immediately so thighs ride no lower than parallel to floor.',NULL,1);
INSERT INTO "exercise" VALUES(30,'Push Jerk','Strength','Standing, holding bar in front squat position. Drop hips back and down into a quarter squat position and immediately explode back up through the starting position. As the hips fully extend, push the bar overhead and drop underneath the bar, catching it overhead with arms straight and hips and knees slightly flexed. Slowly lower the bar down to the starting position and repeat for prescribed number of repetitions',NULL,1);
INSERT INTO "exercise" VALUES(31,'Push Press','Strength','Grasp barbell from rack or clean barbell from floor with overhand grip, slightly wider than shoulder width. Position bar chest high with torso tight. Retract head back. Dip body by bending knees, hips and ankles slightly. Explosively drive upward with legs, driving barbell up off shoulders, vigorously extending arms overhead. Return to shoulders and repeat.',NULL,0);
INSERT INTO "exercise" VALUES(32,'Sumo Deadlift Highpull','Strength','To start the sumo deadlift high pull, start with the weight resting on the ground in front of you. Take a wide or sumo stance. Your stance should be wide but comfortable. Your stance should be narrow enough that your knees still track over your feet. If your knees cave in then you should narrow your stance. If you are using a kettlebell then the kettlebell should be directly between your feet. If you are using a barbell, then the barbell should be resting directly over the middle of the foot as in the deadlift. Squat down to the weight as in the deadlift. Take a narrow grip between your legs. Look straight ahead, and make sure you maintain a good, natural curve in your lower back, in order to protect your lumbar spine. Take a deep breath and hold throughout the rest of the movement.. The Sumo Deadlift High Pull can be subdivided into two pulls. The first pull is accomplished through a hip extension. When you are set, extend the hips violently lifting the weigh off of the ground, as if doing a deadlift. Be sure to keep the back in a good solid position through this movement. The momentum created by the hip extension should be powerful enough carry the weight above the waist.Pull with your legs only until they are at full extension. When you reach full extension of the hips, powerfully shrug your shoulders to initiate the second pull, and immediately pull with the arms to continue the motion of the weight upward. Keep the elbows high and outside as you come to the top of the movement. Keep the bar under control as you return along the same path to the ground.',NULL,0);
INSERT INTO "exercise" VALUES(33,'Dumbbell Squat Clean','Strength','Stand with your feet approximately hip width apart. Squat down and grasp the dumbbell in an overhand grip, with your hands wider than shoulder width apart. Your back should be slightly arched. Lift the barbell off the ground by extending your knees and hips. Keep your arms straight. Shrug your shoulders up as the barbell reaches your knees.As the bar reaches mid-thigh, jump upward and extend your hips and legs. Most of the power for the lift should come from your hips and your jump, not from your arms. As the bar is moving up, pull your body under the bar by bending and lifting your elbows.Catch the bar on your shoulders while at the same time moving into a Front Squat. Your thighs should be at least parallel with the floor. Your feet should move out slightly so you land with your feet shoulder width apart.Extend your legs and hips to stand straight up. This exercise should be done as one quick, fluid movement.',NULL,1);
INSERT INTO "exercise" VALUES(34,'Stiff-legged Deadlift','Strength','Stand with shoulder width or narrower stance on shallow platform with feet flat beneath bar. Bend knees and bend over with lower back straight. Grasp barbell with shoulder width overhand or mixed grip, shoulder width or slightly wider. Lift weight to standing position. Lower bar to top of feet by bending hips. Bend knees slightly during descent and keep waist straight, flexing only slightly at bottom. With knees bent, lift bar by extending at hips until standing upright. Pull shoulders back slightly if rounded. Extend knees at top if desired. Repeat.',NULL,0);
INSERT INTO "exercise" VALUES(35,'L Pull-up','Strength','Step up and grasp bar with overhand wide grip. Lift your legs up until they are straight out in front of the body. Pull body up until chin is above bar. Lower body until arms and shoulders are fully extended. Repeat.',NULL,0);
INSERT INTO "exercise" VALUES(36,'Ring Handstand Push-up','Strength','DO headstand push-ups while balancing on a ring',NULL,0);
INSERT INTO "exercise" VALUES(37,'Knees to Elbows','Strength','Grasp a pull up bar with an overhand grip with your arms slightly wider than shoulder width apart. Hang straight down from the bar. Bend your knees up as you bend at the waist and raise your hips. At the same time, push your upper body away from the bar and lean back. Continue to raise your knees until they touch your elbows. Your torso should now be parallel to the ground. Lower your legs back down and bring your body up as you fully extend. Repeat.',NULL,0);
INSERT INTO "exercise" VALUES(38,'Overhead Walk','Strength','Lift barbel overhead with any lift. Walk',NULL,0);
INSERT INTO "exercise" VALUES(39,'Kettlebell Turkish Get-up','Strength','Raise or clean kettlebell over head with right arm. Stand with right arm fully extended vertically supporting kettlebell throughout movement. Extend left arm out out to side.Step back with left leg and kneel down as in downward phase of Overhead Rear Lunge. Lean to left side and place left hand on floor, well left of right foot. Shift weight onto left arm. Pull left leg forward between right leg and left arm. Sit with left leg extended outward onto floor and right leg bent upward. Extend right leg outward onto floor and gently lie down. Pull right shoulder toward hip slightly by contracting lats and obliques. Sit up with assistance of left arm on floor to side. bend right leg so right foot is placed on floor close to hip while leaning on extended left arm.Pull left leg back between right leg and left arm and position forefoot and knee on floor behind right foot and left hand. Position torso upright.Stand up in original position as in upward phase of Overhead Rear Lunge.
Repeat with opposite side.',NULL,0);
INSERT INTO "exercise" VALUES(40,'Ring Push-up','Strength','Place hands on rings. Do push up',NULL,0);
INSERT INTO "exercise" VALUES(41,'Strict Pull-up','Strength','Do a pull-up. While doing a the pull up, look up and let the chin touch the top of the bar',NULL,0);
INSERT INTO "exercise" VALUES(42,'Toes to Bar','Strength','In the Toes to bar, the athlete must go from a full hang to having the toes touch the pull-up bar. The arms and hips must be fully extended at the bottom and the the feet must be brought back to behind the bar, not out front. Both feet must touch the bar together at some point. The arms can be bent or straight.',NULL,0);
INSERT INTO "exercise" VALUES(43,'Front Squat','Strength','Grasp barbell from rack or clean barbell from floor with overhand open grip, slightly wider than shoulder width. Position barbell chest high with back arched. Place bar in front of shoulders with elbows placed forward as high as possible and finger under bar to each side With heels hip width or slightly wider, position feet outward at approximately 45°. Descend until knees and hips are fully bent or until thighs are just past parallel to floor. Knees travel outward in direction of toes. Extend knees and hips until legs are straight. Return and repeat.',NULL,0);
INSERT INTO "exercise" VALUES(44,'Chest-to-bar Pull-up','Strength','Do pull-up. Make sure the chest touches the bar',NULL,0);
INSERT INTO "exercise" VALUES(45,'Bear Crawl','Cardio','Get on all fours. Crawl forward while making sure both knees are only an inch off the floor',NULL,0);
INSERT INTO "exercise" VALUES(46,'Standing Broad Jump','Cardio','Squat down and jump forward as far as possible using a double arm swing. Upon landing, immediately jump forward again.',NULL,0);
INSERT INTO "exercise" VALUES(47,'Walking Lunge','Cardio','Step forward with first leg. Land on heel then forefoot. Lower body by flexing knee and hip of front leg until knee of rear leg is almost in contact with floor. Stand on forward leg with assistance of rear leg. Lunge forward with opposite leg. Repeat by alternating lunge with opposite legs.',NULL,0);
INSERT INTO "exercise" VALUES(48,'Carry','Cardio','Lift and hold an object up while moving a set distance',NULL,0);
INSERT INTO "exercise" VALUES(49,'Back Squat','Strength','Place barbell behind the neck, across the traps.  Hold the bar with a comfortable grip.  Your hands should at least shoulder width and no farther out than the collars of the bar.  Remove the bar from the rack and take 2 steps back.  Position your feet slightly wider than shoulder width apart with your toes slightly turned out.  Keep your back straight, abs tight, and chest inflated with air. Squat straight down keeping your feet flat, do not lean forward on the toes.  Drop down to a point where the top of your thigh is parallel to the ground.  In this position your hips may be slightly lower than your knees.  Be sure to keep your back straight, abs tight, and chest upright.Drive out of the bottom position keeping the chest upright and the back straight until standing erect with the bar.  Exhale and reset for the next repetition being sure to again fill the chest with air.',NULL,0);
INSERT INTO "exercise" VALUES(50,'Farmers Carry','Strength','Deadlift two weights from the floor. Walk.',NULL,0);
INSERT INTO "exercise" VALUES(51,'Parallette Handstand Push-up','Strength','Do hand stand on a parallette. Do handstand push-up.',NULL,0);
INSERT INTO "exercise" VALUES(52,'Toes through Rings','Strength','Hang limber from the rings. Lift the legs up and put them through the rings. Return to original position. Repeat',NULL,0);
INSERT INTO "exercise" VALUES(53,'Medicine Ball Clean','Strength','DO "clean" with medicine balls',NULL,0);
INSERT INTO "exercise" VALUES(54,'Burpee Pull-up','Strength','Do a burpee. On the jump of the burpee grab on to a bar and do a pull up',NULL,0);
INSERT INTO "exercise" VALUES(55,'Hang Snatch','Strength','Stand with barbell with very wide over hand grip. Bend knees and hips so barbell touches upper-thigh; shoulders over bar with back arched. Arms are straight with elbows pointed along bar.
 Jump upward extending body. Shrug shoulders and pull barbell upward with arms allowing elbows to pull up to sides, keeping them over bar as long as possible. Aggressively pull body under bar. Catch bar at arms ength while moving into squat position. As soon as barbell is caught on locked out arms in squat position, squat up into standing position with barbell overhead.',NULL,0);
INSERT INTO "exercise" VALUES(56,'Hang Split Clean','Strength','Do a hang snatch. On the catch have the legs in the split position',NULL,1);
INSERT INTO "exercise" VALUES(57,'Sprint','Cardio','Run as fast as possible',NULL,0);
INSERT INTO "exercise" VALUES(58,'Hang Squat','Strength','Squat down and grasp the barbell in an overhand grip, with your hands wider than shoulder width apart. Your back should be slightly arched. Lift the barbell off the ground by extending your knees and hips until it rests against your body near the middle of your thighs. This is the starting position for this lift. Lean your upper body forward slightly. Push your hips forward and jump up by explosively extending your hips and legs. Most of the power for the lift should come from your hips and your jump, not from your arms. As the bar is moving up, drop your body down and get under the bar so that the weight is caught above your head with your legs fully bent and your thighs at least parallel with the floor. Your head should be forward of the bar as you catch it. Your feet should move out slightly so you land with your feet shoulder width apart. Stand up while bringing the barbell back to the thighs',NULL,0);
INSERT INTO "exercise" VALUES(59,'Squat Clean','Strength','Squat down and grasp the barbell in an overhand grip, with your hands wider than shoulder width apart. Your back should be slightly arched. Lift the barbell off the ground by extending your knees and hips. Keep your arms straight. Shrug your shoulders up as the barbell reaches your knees. As the bar reaches mid-thigh, jump upward and extend your hips and legs. Most of the power for the lift should come from your hips and your jump, not from your arms. As the bar is moving up, pull your body under the bar by bending and lifting your elbows. Catch the bar on your shoulders while at the same time moving into a Front Squat. Your thighs should be at least parallel with the floor. Your feet should move out slightly so you land with your feet shoulder width apart. Extend your legs and hips to stand straight up. This exercise should be done as one quick, fluid movement.',NULL,1);
INSERT INTO "exercise" VALUES(60,'Shuttle Sprint','Cardio','Sprint to a destination. Touch the floor. Sprint back',NULL,0);
INSERT INTO "exercise" VALUES(61,'Jerk','Strength','Lift weight to the chest. Adjust grip if necessary. Inhale and position chest high with torso tight. Keeping pressure on heels, dip body by bending knees and ankles slightly. Explosively drive upward with legs, driving barbell up off shoulders. Drop body downward and split one foot forward and other backward as fast as possible while vigorously extending arms overhead. The split position places front shin vertical to floor with front foot flat on floor. The rear knee is slightly bent with rear foot positioned on toes. The bar should be positioned directly over ears at arms length with back straight. Push up with both legs. Position feet side by side by bringing front foot back part way and then rear foot forward. Push up with both legs. Position feet side by side by bringing front foot back part way and then rear foot forward.',NULL,1);
INSERT INTO "exercise" VALUES(62,'Bar-facing Burpee','Cardio','Do a burpee. On the jump, jump over the bar in front of you',NULL,0);
INSERT INTO "exercise" VALUES(63,'Row','Cardio','While using the indoor row machine, keep back straight and press using thighs. As you move back, pull the row bar towards the abdomen while keeping you elbows ticked in.',NULL,0);
CREATE TABLE workout (
    "_id" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    "w_id" INTEGER NOT NULL,
    "e_id" INTEGER NOT NULL,
    "reps" INTEGER DEFAULT (0),
    "weight" INTEGER DEFAULT (0)
, "distance" INTEGER   DEFAULT (0));
INSERT INTO "workout" VALUES(1,26,14,21,95,0);
INSERT INTO "workout" VALUES(2,26,1,42,0,0);
INSERT INTO "workout" VALUES(3,26,14,15,95,0);
INSERT INTO "workout" VALUES(4,26,1,30,0,0);
INSERT INTO "workout" VALUES(5,26,14,9,95,0);
INSERT INTO "workout" VALUES(6,26,1,18,0,0);
INSERT INTO "workout" VALUES(7,27,4,100,0,0);
INSERT INTO "workout" VALUES(8,27,21,5,0,0);
INSERT INTO "workout" VALUES(9,27,4,74,0,0);
INSERT INTO "workout" VALUES(10,27,21,10,0,0);
INSERT INTO "workout" VALUES(11,27,4,50,0,0);
INSERT INTO "workout" VALUES(12,27,21,15,0,0);
INSERT INTO "workout" VALUES(13,27,4,25,0,0);
INSERT INTO "workout" VALUES(14,27,21,20,0,0);
INSERT INTO "workout" VALUES(15,28,59,30,95,0);
INSERT INTO "workout" VALUES(16,28,1,30,0,0);
INSERT INTO "workout" VALUES(17,28,13,0,0,800);
INSERT INTO "workout" VALUES(18,29,24,21,40,0);
INSERT INTO "workout" VALUES(19,29,35,21,0,0);
INSERT INTO "workout" VALUES(20,29,24,21,40,0);
INSERT INTO "workout" VALUES(21,29,1,21,0,0);
INSERT INTO "workout" VALUES(22,30,21,2,0,0);
INSERT INTO "workout" VALUES(23,30,6,4,0,0);
INSERT INTO "workout" VALUES(24,30,16,8,72,0);
INSERT INTO "workout" VALUES(25,31,23,75,75,0);
INSERT INTO "workout" VALUES(26,32,8,21,21,0);
INSERT INTO "workout" VALUES(27,32,25,12,0,0);
INSERT INTO "workout" VALUES(28,32,8,15,21,0);
INSERT INTO "workout" VALUES(29,32,25,9,0,0);
INSERT INTO "workout" VALUES(30,32,8,9,21,0);
INSERT INTO "workout" VALUES(31,32,25,6,0,0);
INSERT INTO "workout" VALUES(32,33,13,0,0,800);
INSERT INTO "workout" VALUES(33,33,13,0,0,400);
INSERT INTO "workout" VALUES(34,33,13,0,0,800);
INSERT INTO "workout" VALUES(35,33,13,0,0,400);
INSERT INTO "workout" VALUES(36,34,21,7,0,0);
INSERT INTO "workout" VALUES(37,34,27,21,0,0);
INSERT INTO "workout" VALUES(38,35,33,15,40,0);
INSERT INTO "workout" VALUES(39,35,1,21,0,0);
INSERT INTO "workout" VALUES(40,36,13,0,0,400);
INSERT INTO "workout" VALUES(41,36,28,30,0,0);
INSERT INTO "workout" VALUES(42,36,5,15,250,0);
INSERT INTO "workout" VALUES(43,37,5,12,155,0);
INSERT INTO "workout" VALUES(44,37,29,9,155,0);
INSERT INTO "workout" VALUES(45,37,30,6,155,0);
INSERT INTO "workout" VALUES(46,38,17,30,0,0);
INSERT INTO "workout" VALUES(47,38,31,20,115,0);
INSERT INTO "workout" VALUES(48,38,1,30,0,0);
INSERT INTO "workout" VALUES(49,39,16,30,72,0);
INSERT INTO "workout" VALUES(50,39,27,30,0,0);
INSERT INTO "workout" VALUES(51,39,28,30,0,0);
INSERT INTO "workout" VALUES(52,40,21,7,0,0);
INSERT INTO "workout" VALUES(53,40,32,21,95,0);
INSERT INTO "workout" VALUES(54,41,5,20,275,0);
INSERT INTO "workout" VALUES(55,41,13,0,0,400);
INSERT INTO "workout" VALUES(56,41,16,20,72,0);
INSERT INTO "workout" VALUES(57,41,13,0,0,400);
INSERT INTO "workout" VALUES(58,41,14,20,115,0);
INSERT INTO "workout" VALUES(59,41,13,0,0,400);
INSERT INTO "workout" VALUES(60,41,27,20,0,0);
INSERT INTO "workout" VALUES(61,41,13,0,0,400);
INSERT INTO "workout" VALUES(62,41,1,20,0,0);
INSERT INTO "workout" VALUES(63,41,13,0,0,400);
INSERT INTO "workout" VALUES(64,41,17,20,0,0);
INSERT INTO "workout" VALUES(65,41,13,0,0,400);
INSERT INTO "workout" VALUES(66,41,33,20,90,0);
INSERT INTO "workout" VALUES(67,41,13,0,0,400);
INSERT INTO "workout" VALUES(68,42,28,30,0,0);
INSERT INTO "workout" VALUES(69,42,20,30,0,0);
INSERT INTO "workout" VALUES(70,42,37,30,0,0);
INSERT INTO "workout" VALUES(71,42,34,30,95,0);
INSERT INTO "workout" VALUES(72,42,28,25,0,0);
INSERT INTO "workout" VALUES(73,42,20,25,0,0);
INSERT INTO "workout" VALUES(74,42,37,25,0,0);
INSERT INTO "workout" VALUES(75,42,34,25,95,0);
INSERT INTO "workout" VALUES(76,42,28,20,0,0);
INSERT INTO "workout" VALUES(77,42,20,20,0,0);
INSERT INTO "workout" VALUES(78,42,37,20,0,0);
INSERT INTO "workout" VALUES(79,42,34,20,95,0);
INSERT INTO "workout" VALUES(80,42,28,15,0,0);
INSERT INTO "workout" VALUES(81,42,20,15,0,0);
INSERT INTO "workout" VALUES(82,42,37,15,0,0);
INSERT INTO "workout" VALUES(83,42,34,15,95,0);
INSERT INTO "workout" VALUES(84,42,28,10,0,0);
INSERT INTO "workout" VALUES(85,42,20,10,0,0);
INSERT INTO "workout" VALUES(86,42,37,10,0,0);
INSERT INTO "workout" VALUES(87,42,34,10,95,0);
INSERT INTO "workout" VALUES(88,42,28,5,0,0);
INSERT INTO "workout" VALUES(89,42,20,5,0,0);
INSERT INTO "workout" VALUES(90,42,37,5,0,0);
INSERT INTO "workout" VALUES(91,42,34,5,95,0);
INSERT INTO "workout" VALUES(92,43,4,75,0,0);
INSERT INTO "workout" VALUES(93,43,36,25,0,0);
INSERT INTO "workout" VALUES(94,43,35,25,0,0);
INSERT INTO "workout" VALUES(95,44,21,25,0,0);
INSERT INTO "workout" VALUES(96,44,4,100,0,0);
INSERT INTO "workout" VALUES(97,44,28,35,0,0);
INSERT INTO "workout" VALUES(98,45,5,5,275,0);
INSERT INTO "workout" VALUES(99,45,2,13,0,0);
INSERT INTO "workout" VALUES(100,45,17,9,0,0);
INSERT INTO "workout" VALUES(101,46,15,50,0,0);
INSERT INTO "workout" VALUES(102,46,37,35,0,0);
INSERT INTO "workout" VALUES(103,46,38,0,185,20);
INSERT INTO "workout" VALUES(104,47,13,0,0,1609);
INSERT INTO "workout" VALUES(105,47,63,0,0,2000);
INSERT INTO "workout" VALUES(106,47,13,0,0,1609);
INSERT INTO "workout" VALUES(107,48,6,10,0,0);
INSERT INTO "workout" VALUES(108,48,5,15,250,0);
INSERT INTO "workout" VALUES(109,48,17,25,0,0);
INSERT INTO "workout" VALUES(110,48,1,50,0,0);
INSERT INTO "workout" VALUES(111,48,10,100,20,0);
INSERT INTO "workout" VALUES(112,48,15,200,0,0);
INSERT INTO "workout" VALUES(113,48,13,0,45,400);
INSERT INTO "workout" VALUES(114,49,39,21,72,0);
INSERT INTO "workout" VALUES(115,49,16,50,72,0);
INSERT INTO "workout" VALUES(116,49,14,21,72,0);
INSERT INTO "workout" VALUES(117,49,16,50,72,0);
INSERT INTO "workout" VALUES(118,49,14,21,72,0);
INSERT INTO "workout" VALUES(119,49,16,50,72,0);
INSERT INTO "workout" VALUES(120,49,39,21,72,0);
INSERT INTO "workout" VALUES(121,50,6,7,0,0);
INSERT INTO "workout" VALUES(122,50,8,7,135,0);
INSERT INTO "workout" VALUES(123,50,37,7,0,0);
INSERT INTO "workout" VALUES(124,50,5,7,245,0);
INSERT INTO "workout" VALUES(125,50,27,7,0,0);
INSERT INTO "workout" VALUES(126,50,16,7,72,0);
INSERT INTO "workout" VALUES(127,50,1,7,0,0);
INSERT INTO "workout" VALUES(128,51,13,0,0,800);
INSERT INTO "workout" VALUES(129,51,25,5,0,15);
INSERT INTO "workout" VALUES(130,51,2,50,0,0);
INSERT INTO "workout" VALUES(131,52,13,0,0,1000);
INSERT INTO "workout" VALUES(132,52,21,10,0,0);
INSERT INTO "workout" VALUES(133,52,4,100,0,0);
INSERT INTO "workout" VALUES(134,53,5,9,245,0);
INSERT INTO "workout" VALUES(135,53,21,8,0,0);
INSERT INTO "workout" VALUES(136,53,59,9,155,0);
INSERT INTO "workout" VALUES(137,54,5,15,225,0);
INSERT INTO "workout" VALUES(138,54,17,20,0,0);
INSERT INTO "workout" VALUES(139,54,2,25,0,0);
INSERT INTO "workout" VALUES(140,55,5,24,295,0);
INSERT INTO "workout" VALUES(141,55,17,24,0,0);
INSERT INTO "workout" VALUES(142,55,10,24,20,0);
INSERT INTO "workout" VALUES(143,55,11,24,195,0);
INSERT INTO "workout" VALUES(144,55,10,24,20,0);
INSERT INTO "workout" VALUES(145,55,17,24,0,0);
INSERT INTO "workout" VALUES(146,55,10,24,20,0);
INSERT INTO "workout" VALUES(147,55,7,24,145,0);
INSERT INTO "workout" VALUES(148,56,8,10,95,0);
INSERT INTO "workout" VALUES(149,56,40,10,0,0);
INSERT INTO "workout" VALUES(150,57,41,50,0,0);
INSERT INTO "workout" VALUES(151,57,2,100,0,0);
INSERT INTO "workout" VALUES(152,57,13,0,0,5000);
INSERT INTO "workout" VALUES(153,58,13,0,0,800);
INSERT INTO "workout" VALUES(154,58,59,30,50,0);
INSERT INTO "workout" VALUES(155,58,27,30,0,0);
INSERT INTO "workout" VALUES(156,59,31,10,115,0);
INSERT INTO "workout" VALUES(157,59,16,10,54,0);
INSERT INTO "workout" VALUES(158,59,17,10,0,0);
INSERT INTO "workout" VALUES(159,60,35,20,0,0);
INSERT INTO "workout" VALUES(160,60,42,30,0,0);
INSERT INTO "workout" VALUES(161,60,27,40,0,0);
INSERT INTO "workout" VALUES(162,60,13,0,0,800);
INSERT INTO "workout" VALUES(163,61,13,0,0,150);
INSERT INTO "workout" VALUES(164,61,44,7,0,0);
INSERT INTO "workout" VALUES(165,61,43,7,135,0);
INSERT INTO "workout" VALUES(166,61,6,7,0,0);
INSERT INTO "workout" VALUES(167,62,45,0,0,100);
INSERT INTO "workout" VALUES(168,62,46,0,0,100);
INSERT INTO "workout" VALUES(169,62,27,50,0,0);
INSERT INTO "workout" VALUES(170,63,47,0,45,100);
INSERT INTO "workout" VALUES(171,63,17,30,0,0);
INSERT INTO "workout" VALUES(172,63,10,20,20,0);
INSERT INTO "workout" VALUES(173,63,6,10,0,0);
INSERT INTO "workout" VALUES(174,64,48,0,50,400);
INSERT INTO "workout" VALUES(175,64,31,12,115,0);
INSERT INTO "workout" VALUES(176,64,17,12,0,0);
INSERT INTO "workout" VALUES(177,64,32,12,95,0);
INSERT INTO "workout" VALUES(178,65,25,1,0,15);
INSERT INTO "workout" VALUES(179,65,49,29,95,0);
INSERT INTO "workout" VALUES(180,65,50,0,135,10);
INSERT INTO "workout" VALUES(181,66,16,22,72,0);
INSERT INTO "workout" VALUES(182,66,17,22,0,0);
INSERT INTO "workout" VALUES(183,66,13,0,0,400);
INSERT INTO "workout" VALUES(184,66,27,22,0,0);
INSERT INTO "workout" VALUES(185,66,10,22,20,0);
INSERT INTO "workout" VALUES(186,67,15,200,0,0);
INSERT INTO "workout" VALUES(187,67,14,50,135,0);
INSERT INTO "workout" VALUES(188,67,1,50,0,0);
INSERT INTO "workout" VALUES(189,67,13,0,0,1609);
INSERT INTO "workout" VALUES(190,68,5,6,225,0);
INSERT INTO "workout" VALUES(191,68,27,7,0,0);
INSERT INTO "workout" VALUES(192,68,16,10,72,0);
INSERT INTO "workout" VALUES(193,68,13,0,0,200);
INSERT INTO "workout" VALUES(194,69,8,5,115,0);
INSERT INTO "workout" VALUES(195,69,1,10,0,0);
INSERT INTO "workout" VALUES(196,69,57,0,0,100);
INSERT INTO "workout" VALUES(197,70,51,5,0,0);
INSERT INTO "workout" VALUES(198,70,52,10,0,0);
INSERT INTO "workout" VALUES(199,70,53,15,20,0);
INSERT INTO "workout" VALUES(200,71,16,15,54,0);
INSERT INTO "workout" VALUES(201,71,7,15,95,0);
INSERT INTO "workout" VALUES(202,71,17,15,0,0);
INSERT INTO "workout" VALUES(203,72,21,9,0,0);
INSERT INTO "workout" VALUES(204,72,54,15,0,0);
INSERT INTO "workout" VALUES(205,72,1,21,0,0);
INSERT INTO "workout" VALUES(206,72,13,0,0,800);
INSERT INTO "workout" VALUES(207,73,35,10,0,0);
INSERT INTO "workout" VALUES(208,73,2,15,0,0);
INSERT INTO "workout" VALUES(209,73,44,15,0,0);
INSERT INTO "workout" VALUES(210,73,2,15,0,0);
INSERT INTO "workout" VALUES(211,73,1,20,0,0);
INSERT INTO "workout" VALUES(212,73,2,15,0,0);
INSERT INTO "workout" VALUES(213,74,13,0,0,1609);
INSERT INTO "workout" VALUES(214,74,9,21,155,0);
INSERT INTO "workout" VALUES(215,74,13,0,0,800);
INSERT INTO "workout" VALUES(216,74,9,21,155,0);
INSERT INTO "workout" VALUES(217,74,13,0,0,1609);
INSERT INTO "workout" VALUES(218,75,7,5,135,0);
INSERT INTO "workout" VALUES(219,75,43,10,135,0);
INSERT INTO "workout" VALUES(220,75,61,5,135,0);
INSERT INTO "workout" VALUES(221,75,1,20,0,0);
CREATE TABLE wodgoal (
    "_id" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    "w_id" INTEGER NOT NULL,
    "goaltime" INTEGER NOT NULL,
    "startdate" INTEGER NOT NULL, 
    "iscomplete" INTEGER   DEFAULT (0), 
    "enddate" INTEGER);
CREATE TABLE wod (
    "_id" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    "name" TEXT NOT NULL,
    "type" TEXT NOT NULL,
    "description" TEXT,
    "video" TEXT);
INSERT INTO "wod" VALUES(1,'Angie','Benchmark Girl','
100 Pull-ups 
100 Push-ups 
100 Sit-ups 
100 Squats

This workout is done for time. Complete all reps of each exercise before moving to the next.',NULL);
INSERT INTO "wod" VALUES(2,'Barbara','Benchmark Girl','
20 Pull-ups 
30 Push-ups 
40 Sit-ups 
50 Squats 

This workout is done for 5 rounds where each round is timed. Rest precisely three minutes between each round.',NULL);
INSERT INTO "wod" VALUES(3,'Chelsea','Benchmark Girl','
5 Pull-ups 
10 Push-ups 
15 Squats 

Each min on the min for 30 min ',NULL);
INSERT INTO "wod" VALUES(4,'Cindy','Benchmark Girl','
5 Pull-ups 
10 Push-ups 
15 Squats 

As many rounds as possible in 20 min ',NULL);
INSERT INTO "wod" VALUES(5,'Diane','Benchmark Girl','
Deadlift 225 lbs 
Handstand push-ups

21-15-9 reps, for time',NULL);
INSERT INTO "wod" VALUES(6,'Elizabeth','Benchmark Girl','
Clean 135 lbs 
Ring Dips

21-15-9 reps, for time ',NULL);
INSERT INTO "wod" VALUES(7,'Fran','Benchmark Girl','
Thruster 95 lbs 
Pull-ups 

21-15-9 reps, for time',NULL);
INSERT INTO "wod" VALUES(8,'Grace','Benchmark Girl','
Clean and Jerk 135 lbs

30 reps for time ',NULL);
INSERT INTO "wod" VALUES(9,'Helen','Benchmark Girl','
400 meter run 
1.5 pood (54.165 lbs) Kettlebell swing x 21 
Pull-ups 12 reps

3 rounds for time',NULL);
INSERT INTO "wod" VALUES(10,'Isabel','Benchmark Girl','

Snatch 135 pounds 



30 reps for time',NULL);
INSERT INTO "wod" VALUES(11,'Jackie','Benchmark Girl','
1000 meter row 
Thruster 45 lbs (50 reps) 
Pull-ups (30 reps)

For time',NULL);
INSERT INTO "wod" VALUES(12,'Karen','Benchmark Girl','Wall-ball 150 shots

For time',NULL);
INSERT INTO "wod" VALUES(13,'Linda','Benchmark Girl','Deadlift 1.5xBW   
Bench BW 
Clean .75x BW',NULL);
INSERT INTO "wod" VALUES(14,'Mary','Benchmark Girl','
5 Handstand push-ups 
10 1-legged squats 
15 Pull-ups

As many rounds as possible in 20 min
',NULL);
INSERT INTO "wod" VALUES(15,'Nancy','Benchmark Girl','
400 meter run 
Overhead squat 95 lbs x 15

5 rounds for time',NULL);
INSERT INTO "wod" VALUES(16,'Annie','New Girl','
Double-unders 
Sit-ups

50-40-30-20 and 10 rep rounds; for time ',NULL);
INSERT INTO "wod" VALUES(17,'Eva','New Girl','
Run 800 meters 
2 pood (70 lbs) KB swing, 30 reps 
30 pullups

5 rounds for time.',NULL);
INSERT INTO "wod" VALUES(18,'Kelly','New Girl','
Run 400 meters 
30 box jump, 24 inch box 
30 Wall ball shots, 20 pound ball

Five rounds for time ',NULL);
INSERT INTO "wod" VALUES(19,'Lynne','New Girl','
Bodyweight bench press (e.g., same amount on bar as you weigh) 
pullups

5 rounds for max reps. There is no time component for this WOD.
',NULL);
INSERT INTO "wod" VALUES(20,'Nicole','New Girl','
Run 400 meters 
Max rep Pull-ups

As many rounds as possible in 20 minutes.
Note number of pull-ups completed for each round.',NULL);
INSERT INTO "wod" VALUES(21,'Amanda','New Girl','
Muscle-ups 
Snatches (135/95 lb.)

Reps of 9, 7 and 5; for time. ',NULL);
INSERT INTO "wod" VALUES(22,'JT','Hero','Handstand push-ups
Ring dips
Push-ups
21-15-9 reps, for time',NULL);
INSERT INTO "wod" VALUES(23,'Michael','Hero','Run 800 meters
50 Back Extensions
50 Sit-ups

3 rounds for time',NULL);
INSERT INTO "wod" VALUES(24,'Murph','Hero','1 mile Run
100 Pull-ups
200 Push-ups
300 Squats
1 mile Run

For time. Partition the pull-ups, push-ups, and squats as needed. Start and finish with a mile run. If you''ve got a twenty pound vest or body armor, wear it.',NULL);
INSERT INTO "wod" VALUES(25,'Daniel','Hero','50 Pull-ups
400 meter run
95 pound Thruster, 21 reps
800 meter run
95 pound Thruster, 21 reps
400 meter run
50 Pull-ups

For time.',NULL);
INSERT INTO "wod" VALUES(26,'Josh','Hero','95 pound Overhead squat, 21 reps
42 Pull-ups
95 pound Overhead squat, 15 reps
30 Pull-ups
95 pound Overhead squat, 9 reps
18 Pull-ups

For time.',NULL);
INSERT INTO "wod" VALUES(27,'Jason','Hero','100 Squats
5 Muscle-ups
75 Squats
10 Muscle-ups
50 Squats
15 Muscle-ups
25 Squats
20 Muscle-ups

For time.',NULL);
INSERT INTO "wod" VALUES(28,'Badger','Hero','95 pound Squat clean, 30 reps
30 Pull-ups
Run 800 meters

3 rounds for time.',NULL);
INSERT INTO "wod" VALUES(29,'Joshie','Hero','40 pound Dumbbell snatch, 21 reps, right arm
21 L Pull-ups
40 pound Dumbbell snatch, 21 reps, left arm
21 L Pull-ups

The snatches are full squat snatches.3 rounds for time.',NULL);
INSERT INTO "wod" VALUES(30,'Nate','Hero','2 Muscle-ups
4 Handstand Push-ups
8 2-Pood Kettlebell swings

As many rounds as possible in 20 minutes',NULL);
INSERT INTO "wod" VALUES(31,'Randy','Hero','Power snatch, 75 reps

For time',NULL);
INSERT INTO "wod" VALUES(32,'Tommy V','Hero','115 pound Thruster, 21 reps
15 ft Rope Climb, 12 ascents
115 pound Thruster, 15 reps
15 ft Rope Climb, 9 ascents
115 pound Thruster, 9 reps
15 ft Rope Climb, 6 ascents

For time',NULL);
INSERT INTO "wod" VALUES(33,'Griff','Hero','Run 800 meters
Run 400 meters backwards
Run 800 meters
Run 400 meters backwards

For time',NULL);
INSERT INTO "wod" VALUES(34,'Ryan','Hero','7 Muscle-ups
21 Burpees

Five rounds; for time. Each burpee terminates with a jump one foot above max standing reach.',NULL);
INSERT INTO "wod" VALUES(35,'Erin','Hero','Five rounds 
40 pound Dumbbells split clean, 15 reps
21 Pull-ups

For time',NULL);
INSERT INTO "wod" VALUES(36,'Mr. Joshua','Hero','Five rounds
Run 400 meters
30 Glute-ham sit-ups
250 pound Deadlift, 15 reps

For time',NULL);
INSERT INTO "wod" VALUES(37,'DT','Hero','Five rounds
155 pound Deadlift, 12 reps
155 pound Hang power clean, 9 reps
155 pound Push jerk, 6 reps
For time',NULL);
INSERT INTO "wod" VALUES(38,'Danny','Hero','Rounds in 20 min
24" box jump, 30 reps
115 pound push press, 20 reps
30 pull-ups

Rounds completed.',NULL);
INSERT INTO "wod" VALUES(39,'Hansen','Hero','Five rounds
30 reps, 2 pood Kettlebell swing
30 Burpees
30 Glute-ham sit-ups

For time.',NULL);
INSERT INTO "wod" VALUES(40,'Tyler','Hero','Five rounds
7 Muscle-ups
21 reps 95 pound Sumo-deadlift high-pull

For time.',NULL);
INSERT INTO "wod" VALUES(41,'Lumberjack 20','Hero','20 Deadlifts (275lbs)
Run 400m
20 KB swings (2pood)
Run 400m
20 Overhead Squats (115lbs)
Run 400m
20 Burpees
Run 400m
20 Pullups (Chest to Bar)
Run 400m
20 Box jumps (24")
Run 400m

For time.',NULL);
INSERT INTO "wod" VALUES(42,'Stephen','Hero','30-25-20-15-10-5 rep
GHD sit-up
Back extension
Knees to elbow
95 pound Stiff legged deadlift

For time.
',NULL);
INSERT INTO "wod" VALUES(43,'Garrett','Hero','Three rounds
75 Squats
25 Ring handstand push-ups
25 L-pull-ups

For time.',NULL);
INSERT INTO "wod" VALUES(44,'War Frank','Hero','Three rounds
25 Muscle-ups
100 Squats
35 GHD situps

For time',NULL);
INSERT INTO "wod" VALUES(45,'McGhee','Hero','275 pound Deadlift, 5 reps
13 Push-ups
9 Box jumps, 24 inch box

Rounds in 30 min.',NULL);
INSERT INTO "wod" VALUES(46,'Paul','Hero','Five rounds
50 Double unders
35 Knees to elbows
185 pound Overhead walk, 20 yards

For Time.',NULL);
INSERT INTO "wod" VALUES(47,'Jerry','Hero','Run 1 mile
Row 2K
Run 1 mile

For Time.',NULL);
INSERT INTO "wod" VALUES(48,'Nutts','Hero','10 Handstand push-ups
250 pound Deadlift, 15 reps
25 Box jumps, 30 inch box
50 Pull-ups
100 Wallball shots, 20 pounds, 10''
200 Double-unders
Run 400 meters with a 45lb plate

For Time.',NULL);
INSERT INTO "wod" VALUES(49,'Arnie','Hero','With a single 2 pood kettlebell
21 Turkish get-ups, Right arm
50 Swings
21 Overhead squats, Left arm
50 Swings
21 Overhead squats, Right arm
50 Swings
21 Turkish get-ups, Left arm

For Time.',NULL);
INSERT INTO "wod" VALUES(50,'The Seven','Hero','Seven rounds
7 Handstand push-ups
135 pound Thruster, 7 reps
7 Knees to elbows
245 pound Deadlift, 7 reps
7 Burpees
7 Kettlebell swings, 2 pood
7 Pull-ups

For Time.',NULL);
INSERT INTO "wod" VALUES(51,'RJ','Hero','Five rounds
Run 800 meters
15 ft Rope Climb, 5 ascents
50 Push-ups

For Time.',NULL);
INSERT INTO "wod" VALUES(52,'Luce','Hero','Wearing a 20 pound vest, three rounds
Run 1K
10 Muscle-ups
100 Squats

For Time.',NULL);
INSERT INTO "wod" VALUES(53,'Johnson','Hero','245 pound Deadlift, 9 reps
8 Muscle-ups
155 pound Squat clean, 9 reps

Rounds in 20 Min.',NULL);
INSERT INTO "wod" VALUES(54,'Roy','Hero','Five rounds
225 pound Deadlift, 15 reps
20 Box jumps, 24 inch box
25 Pull-ups

For Time.',NULL);
INSERT INTO "wod" VALUES(55,'Adam Brown','Hero','Two rounds
295 pound Deadlift, 24 reps
24 Box jumps, 24 inch box
24 Wallball shots, 20 pound ball
195 pound Bench press, 24 reps
24 Box jumps, 24 inch box
24 Wallball shots, 20 pound ball
145 pound Clean, 24 reps

For Time.',NULL);
INSERT INTO "wod" VALUES(56,'Coe','Hero','Ten rounds
95 pound Thruster, 10 reps
10 Ring push-ups

For Time.
',NULL);
INSERT INTO "wod" VALUES(57,'Severin','Hero','50 Strict Pull-ups
100 Push-ups, release hands from floor at the bottom
Run 5K

For Time.',NULL);
INSERT INTO "wod" VALUES(58,'Helton','Hero','Three rounds
Run 800 meters
30 reps, 50 pound dumbbell squat cleans
30 Burpees

For Time.',NULL);
INSERT INTO "wod" VALUES(59,'Jack','Hero','115 pound Push press, 10 reps
10 KB Swings, 1.5 pood
10 Box jumps, 24 inch box

Max Rounds in 20 Min.',NULL);
INSERT INTO "wod" VALUES(60,'Forrest','Hero','Three rounds
20 L-pull-ups
30 Toes to bar
40 Burpees
Run 800 meters

For Time.',NULL);
INSERT INTO "wod" VALUES(61,'Bulger','Hero','Ten rounds
Run 150 meters
7 Chest to bar pull-ups
135 pound Front squat, 7 reps
7 Handstand push-ups

For Time.',NULL);
INSERT INTO "wod" VALUES(62,'Brenton','Hero','Five rounds
Bear crawl 100 feet
Standing broad-jump, 100 feet

Do three Burpees after every five broad-jumps. If you''ve got a twenty pound vest or body armor, wear it.

For Time.',NULL);
INSERT INTO "wod" VALUES(63,'Blake','Hero','Four rounds
100 foot Walking lunge with 45lb plate held overhead
30 Box jump, 24 inch box
20 Wallball shots, 20 pound ball
10 Handstand push-ups

For Time.',NULL);
INSERT INTO "wod" VALUES(64,'Collin','Hero','Six rounds
Carry 50 pound sandbag 400 meters
115 pound Push press, 12 reps
12 Box jumps, 24 inch box
95 pound Sumo deadlift high-pull, 12 reps

For Time.',NULL);
INSERT INTO "wod" VALUES(65,'Thompson','Hero','10 rounds
15 ft Rope Climb, 1 ascent
95 pound Back squat, 29 reps
135 pound barbells Farmer carry, 10 meters
Begin the rope climbs seated on the floor.

For Time.',NULL);
INSERT INTO "wod" VALUES(66,'Whitten','Hero','Five rounds
22 Kettlebell swings, 2 pood
22 Box jump, 24 inch box
Run 400 meters
22 Burpees
22 Wall ball shots, 20 pound ball

For Time.',NULL);
INSERT INTO "wod" VALUES(67,'Bull','Hero','Two rounds
200 Double-unders
135 pound Overhead squat, 50 reps
50 Pull-ups
Run 1 mile

For Time.',NULL);
INSERT INTO "wod" VALUES(68,'Rankel','Hero','225 pound Deadlift, 6 reps
7 Burpee pull-ups
10 Kettlebell swings, 2 pood
Run 200 meters

AMRAP in 20 Minutes.',NULL);
INSERT INTO "wod" VALUES(69,'Holbrook','Hero','Ten rounds
115 pound Thruster, 5 reps
10 Pull-ups
100 meter Sprint
Rest 1 minute

Each Round For Time.',NULL);
INSERT INTO "wod" VALUES(70,'Ledesma','Hero','Complete as many rounds as possible
5 Parallette handstand push-ups
10 Toes through rings
20 pound Medicine ball cleans, 15 reps
in 20 Minutes',NULL);
INSERT INTO "wod" VALUES(71,'Wittman','Hero','Seven rounds
1.5 pood Kettlebell swing, 15 reps
95 pound Power clean, 15 reps
15 Box jumps, 24" box

For Time.',NULL);
INSERT INTO "wod" VALUES(72,'McCluskey','Hero','Three rounds
9 Muscle-ups
15 Burpee pull-ups
21 Pull-ups
Run 800 meters

For Time.',NULL);
INSERT INTO "wod" VALUES(73,'Weaver','Hero','Four rounds
10 L-pull-ups
15 Push-ups
15 Chest to bar Pull-ups
15 Push-ups
20 Pull-ups
15 Push-ups

For Time.',NULL);
INSERT INTO "wod" VALUES(74,'Abbate','Hero','Run 1 mile
155 pound Clean and jerk, 21 reps
Run 800 meters
155 pound Clean and jerk, 21 reps
Run 1 Mile

For Time.',NULL);
INSERT INTO "wod" VALUES(75,'Hammer','Hero','Five rounds
135 pound Power clean, 5 reps
135 pound Front squat, 10 reps
135 pound Jerk, 5 reps
20 Pull-ups
Rest 90 seconds between each round

Each Round For Time.',NULL);
INSERT INTO "wod" VALUES(76,'Moore','Hero','15 ft Rope Climb, 1 ascent
Run 400 meters
Max rep Handstand push-ups

Rounds in 20 min.',NULL);
INSERT INTO "wod" VALUES(77,'Wilmot','Hero','Six rounds
50 Squats
25 Ring dips

For Time.',NULL);
INSERT INTO "wod" VALUES(78,'Moon','Hero','Seven rounds
40 pound dumbbell Hang split snatch, 10 reps Right arm
15 ft Rope Climb, 1 ascent
40 pound dumbbell Hang split snatch, 10 reps Left arm
15 ft Rope Climb, 1 ascent
Alternate feet in the split snatch sets.

For Time.',NULL);
INSERT INTO "wod" VALUES(79,'Small','Hero','Three rounds
Row 1000 meters
50 Burpees
50 Box jumps, 24" box
Run 800 meters

For Time.',NULL);
INSERT INTO "wod" VALUES(80,'Morrison','Hero','50-40-30-20 and 10 rep rounds
Wall ball shots, 20 pound ball
Box jump, 24 inch box
Kettlebell swings, 1.5 pood

For Time.',NULL);
INSERT INTO "wod" VALUES(81,'Manion','Hero','Seven rounds
Run 400 meters
135 pound Back squat, 29 reps

For Time.
',NULL);
INSERT INTO "wod" VALUES(82,'Gator','Hero','Eight rounds
185 pound Front squat, 5 reps
26 Ring push-ups

For Time.',NULL);
INSERT INTO "wod" VALUES(83,'Bradley','Hero','10 rounds
Sprint 100 meters
10 Pull-ups
Sprint 100 meters
10 Burpees
Rest 30 seconds

For Time.',NULL);
INSERT INTO "wod" VALUES(84,'Meadows','Hero','20 Muscle-ups
25 Lowers from an inverted hang on the rings, slowly, with straight body and arms
30 Ring handstand push-ups
35 Ring rows
40 Ring push-ups

For Time.',NULL);
INSERT INTO "wod" VALUES(85,'Santiago','Hero','Seven rounds
35 pound Dumbbell hang squat clean, 18 reps
18 Pull-ups
135 pound Power clean, 10 reps
10 Handstand push-ups

For Time.',NULL);
INSERT INTO "wod" VALUES(86,'Carse','Hero','21-18-15-12-9-6-3 reps
95 pound Squat clean
Double-under
185 pound Deadlift
24" Box jump
Begin each round with a 50 meter Bear crawl.

For Time.',NULL);
INSERT INTO "wod" VALUES(87,'Bradshaw','Hero','10 rounds
3 Handstand push-ups
225 pound Deadlift, 6 reps
12 Pull-ups
24 Double-unders

For Time.',NULL);
INSERT INTO "wod" VALUES(88,'White','Hero','Five rounds
15'' Rope climb, 3 ascents
10 Toes to bar
21 Walking lunge steps with 45lb plate held overhead
Run 400 meters

For Time.',NULL);
INSERT INTO "wod" VALUES(89,'Santora','Hero','Three rounds
155 pound Squat cleans, 1 minute
20'' Shuttle sprints (20'' forward + 20'' backwards = 1 rep), 1 minute
245 pound Deadlifts, 1 minute
Burpees, 1 minute
155 pound Jerks, 1 minute
Rest 1 minute

For Reps.',NULL);
INSERT INTO "wod" VALUES(90,'Wood','Hero','5 Rounds
Run 400 meters
10 Burpee box jumps, 24" box
95 pound Sumo-deadlift high-pull, 10 reps
95 pound Thruster, 10 reps
Rest 1 minute

For Time.',NULL);
INSERT INTO "wod" VALUES(91,'Hidalgo','Hero','Run 2 miles
Rest 2 minutes
135 pound Squat clean, 20 reps
20 Box jump, 24" box
20 Walking lunge steps with 45lb plate held overhead
20 Box jump, 24" box
135 pound Squat clean, 20 reps
Rest 2 minutes
Run 2 miles

If you''ve got a twenty pound vest or body armor, wear it.

For Time.',NULL);
INSERT INTO "wod" VALUES(92,'Ricky','Hero','10 Pull-ups
75 pound dumbbell Deadlift, 5 reps
135 pound Push-press, 8 reps

Rounds in 20 Min.',NULL);
INSERT INTO "wod" VALUES(93,'Dae Han','Hero','Three rounds
Run 800 meters with a 45 pound barbell
15 foot Rope climb, 3 ascents
135 pound Thruster, 12 reps

For Time.',NULL);
INSERT INTO "wod" VALUES(94,'Desforges','Hero','Five rounds
225 pound Deadlift, 12 reps
20 Pull-ups
135 pound Clean and jerk, 12 reps
20 Knees to elbows

For Time.',NULL);
INSERT INTO "wod" VALUES(95,'Rahoi','Hero','24 inch Box Jump, 12 reps
95 pound Thruster, 6 reps
6 Bar-facing burpees

AMRAP in 12 Min.',NULL);
INSERT INTO "wod" VALUES(96,'Zimmerman','Hero','11 Chest-to-bar pull-ups
2 Deadlifts, 315 pounds
10 Handstand push-ups

AMRAP in 25 Min',NULL);
INSERT INTO "wod" VALUES(97,'Klepto','Hero','27 Box jumps, 24" box
20 Burpees
11 Squat cleans, 145 pounds

For Time.',NULL);
INSERT INTO "wod" VALUES(98,'Pheezy','Hero','Three rounds
165 pound Front squat, 5 reps
18 Pull-ups
225 pound Deadlift, 5 reps
18 Toes-to-bar
165 pound Push jerk, 5 reps
18 Hand-release push-ups

For Time.',NULL);
INSERT INTO "wod" VALUES(99,'J.J.','Hero','185 pound Squat clean, 1 rep
10 Parallette handstand push-ups
185 pound Squat clean, 2 reps
9 Parallette handstand push-ups
185 pound Squat clean, 3 reps
8 Parallette handstand push-ups
185 pound Squat clean, 4 reps
7 Parallette handstand push-ups
185 pound Squat clean, 5 reps
6 Parallette handstand push-ups
185 pound Squat clean, 6 reps
5 Parallette handstand push-ups
185 pound Squat clean, 7 reps
4 Parallette handstand push-ups
185 pound Squat clean, 8 reps
3 Parallette handstand push-ups
185 pound Squat clean, 9 reps
2 Parallette handstand push-ups
185 pound Squat clean, 10 reps
1 Parallette handstand push-up

For Time.',NULL);
INSERT INTO "wod" VALUES(100,'JAG 28','Hero','Run 800 meters
28 Kettlebell swings, 2 pood
28 Strict Pull-ups
28 Kettlebell clean and jerk, 2 pood each
28 Strict Pull-ups
Run 800 meters

For Time.',NULL);
INSERT INTO "wod" VALUES(101,'Brian','Hero','Three rounds
15 foot Rope climb, 5 ascents
185 pound Back squat, 25 reps

For Time.',NULL);
CREATE TABLE wodhistory (
    "_id" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    "w_id" INTEGER NOT NULL,
    "date" INTEGER NOT NULL,
    "time" INTEGER NOT NULL,
    "usernotes" TEXT);
DELETE FROM sqlite_sequence;
INSERT INTO "sqlite_sequence" VALUES('exercise',64);
INSERT INTO "sqlite_sequence" VALUES('workout',221);
COMMIT;
